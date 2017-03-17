package org.interview.twitter;

import static org.interview.twitter.TwitterConstants.DATE_FORMAT;
import static org.interview.twitter.TwitterUtils.convertDate;

import org.interview.twitter.entity.TwitterMessage;
import org.interview.twitter.entity.TwitterUser;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

public final class TwitterUtils {

	public static long convertDate(String dateTime) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern(DATE_FORMAT);
		// Parsing the date
		return dtf.parseDateTime(dateTime).getMillis();
	}

	public static TwitterUser getTwitterUserFromJSON(JSONObject userObj) {
		TwitterUser user = new TwitterUser();
		user.setUserId(userObj.getLong("id"));
		user.setScreenName(userObj.getString("screen_name"));
		user.setFullName(userObj.getString("name"));
		user.setCreationTime(convertDate(userObj.getString("created_at")));
		return user;
	}

	public static TwitterMessage getTwitterMessageFromJSON(JSONObject messageObj) {
		TwitterMessage message = new TwitterMessage();
		message.setMessageId(messageObj.getLong("id"));
		message.setMessage(messageObj.getString("text"));
		message.setCreationTime(convertDate(messageObj.getString("created_at")));
		return message;
	}
}
