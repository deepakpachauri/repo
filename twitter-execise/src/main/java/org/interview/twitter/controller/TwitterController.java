package org.interview.twitter.controller;

import static org.interview.twitter.TwitterConstants.CONSUMER_KEY;
import static org.interview.twitter.TwitterConstants.CONSUMER_SECRET;
import static org.interview.twitter.TwitterConstants.DELIMITED_LENGTH_VALUE;
import static org.interview.twitter.TwitterConstants.DELIMITED_PARAM_NAME;
import static org.interview.twitter.TwitterConstants.PUBLIC_FILTER_ENDPOINT;
import static org.interview.twitter.TwitterConstants.TRACK_PARAM_NAME;
import static org.interview.twitter.TwitterUtils.getTwitterMessageFromJSON;
import static org.interview.twitter.TwitterUtils.getTwitterUserFromJSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.interview.twitter.entity.TwitterMessage;
import org.interview.twitter.entity.TwitterUser;
import org.interview.twitter.oauth.TwitterAuthenticator;
import org.interview.twitter.view.PrintView;
import org.json.JSONObject;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;

public class TwitterController {

	private TwitterAuthenticator auth;

	private HttpRequestFactory requestFactory;

	private BufferedReader responseReader;

	private HttpResponse response;

	int messageCounter = 0;

	private Map<TwitterUser, List<TwitterMessage>> messageMap = new TreeMap<TwitterUser, List<TwitterMessage>>();

	private boolean shouldContinue = true;

	private long startTime = 0;

	private PrintStream out;

	public TwitterController() {
		out = System.out;
		auth = new TwitterAuthenticator(out, CONSUMER_KEY, CONSUMER_SECRET);
	}

	public void startStreaming() {
		Future<String> result = null;
		out.println("Reading Tweets for track = bieber");
		try {
			requestFactory = auth.getAuthorizedHttpRequestFactory();
			ExecutorService executor = Executors.newSingleThreadExecutor();
			result = executor.submit(new Callable<String>() {
				public String call() throws Exception {
					int i = 0;
					while (!Thread.currentThread().isInterrupted()
							&& shouldContinue) {
						if (i++ == 30) {
							break;
						}
						try {
							response = executeFilterRequest();
							responseReader = new BufferedReader(
									new InputStreamReader(
											response.getContent(), response
													.getContentCharset()));

							while (!Thread.currentThread().isInterrupted()
									&& parseTwitterResponse()) {
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return "Done";
				}
			});
			executor.shutdown();
			startTime = System.currentTimeMillis();
			// wait for result "Done" for 30 Seconds, after 30 Seconds, it throws exception.
			result.get(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			// It will Intrupt the running thread, as 30 Seconds has passed.
			result.cancel(true);
		} finally {
			try {
				response.disconnect();
				responseReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PrintView.print(messageMap, out);
	}

	private boolean parseTwitterResponse() {

		char[] messageBuffer;

		try {
			String nextMessageLength;
			do {
				nextMessageLength = responseReader.readLine();

				if (nextMessageLength == null) {
					// end of input stream, reconnect.
					return false;
				}
			} while (nextMessageLength.length() < 1);

			int nextMessageLengthInt = Integer.parseInt(nextMessageLength);
			messageBuffer = new char[nextMessageLengthInt];
			int dataRead = responseReader.read(messageBuffer, 0,
					nextMessageLengthInt);
			if (dataRead != nextMessageLengthInt) {
				// We were unable to read the whole message, due to end of
				// stream.
				return false;
			}
			String tweet = new String(messageBuffer);
			JSONObject jsonObj = new JSONObject(tweet);

			JSONObject userObj = (JSONObject) jsonObj.get("user");
			TwitterUser user = getTwitterUserFromJSON(userObj);

			TwitterMessage message = getTwitterMessageFromJSON(jsonObj);
			addMessage(user, message);

			if (++messageCounter == 100) {
				shouldContinue = false;
				return false;
			}
			showProgress();
		} catch (Exception e) {
			shouldContinue = false;
			return false;
		}

		return true;

	}

	private void showProgress() {
		String padded = StringUtils.repeat("-", 100 - messageCounter);
		padded = StringUtils.leftPad(padded, 100, "=");
		out.format("%s %s %3d %s %s %3d %s %d %s\n", padded, "message (",
				messageCounter, " / 100), ", "Time Elapsed = (",
				(System.currentTimeMillis() - startTime) / 1000, "/", 30,
				" Sec)");
	}

	private HttpResponse executeFilterRequest() throws IOException {
		GenericUrl url = new GenericUrl(PUBLIC_FILTER_ENDPOINT);
		url.put(DELIMITED_PARAM_NAME, DELIMITED_LENGTH_VALUE);
		url.put(TRACK_PARAM_NAME, "bieber");

		HttpRequest request = requestFactory.buildPostRequest(url, null);
		request.setReadTimeout(45 * 1000);

		return request.execute();
	}

	// synchronized method, so that if one thread put into resource, then second
	// thread can read the data.
	// synchronized method ensures that resouce write to main cache instead of
	// local cache.
	private synchronized void addMessage(TwitterUser user,
			TwitterMessage message) {
		if (messageMap.containsKey(user)) {
			messageMap.get(user).add(message);
		} else {
			List<TwitterMessage> list = new ArrayList<TwitterMessage>();
			list.add(message);
			messageMap.put(user, list);
		}
	}
	
	public static void main(String args[]) {
		TwitterController controller = new TwitterController();
		controller.startStreaming();
	}

}
