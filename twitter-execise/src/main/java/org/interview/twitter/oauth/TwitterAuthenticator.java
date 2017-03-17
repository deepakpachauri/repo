package org.interview.twitter.oauth;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import com.google.api.client.auth.oauth.OAuthAuthorizeTemporaryTokenUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public class TwitterAuthenticator {

	private final PrintStream out;
	
	private final String consumerKey;
	private final String consumerSecret;
	
	private HttpRequestFactory factory;

	private static final HttpTransport TRANSPORT = new NetHttpTransport();
	private static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
	private static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
	private static final String REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token";

    /**
     * Location and fileNames to store the oauth credentials.  This is probably not best practice,
     * since it seems oauth tokens should be treated like passwords, but for testing, this is far
     * more convenient than re-authing the app every time.
     * 
     * TODO: investigate best practices for storing oauth tokens.
     */
    private static final String TOKEN_FILENAME = ".twitter_interview_oauth_token";
    private static final String TOKEN_SECRET_FILENAME = ".twitter_interview_oauth_token_secret";
    private static final String HOME_PROPERTY_NAME = "user.home";

	public TwitterAuthenticator(final PrintStream out, final String consumerKey, final String consumerSecret) {
		this.out = out;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}

    /**
     * Get a file in the user's profile directory.
     * @param fileName the name of the file to get.
     */
    private File getUserFile(String fileName) {
        return new File(System.getProperty(HOME_PROPERTY_NAME) + File.separator + fileName);
    }

    /**
     * Write a credential to a given filename, it will be stored in the user's profile directory.
     * @param fileName the name of the file to write credential in.
     * @param credential the credential to write into the file.
     */
    private void writeCredentialFile(String fileName, String credential) {
        BufferedWriter writer = null;

        try {
            File credentialFile = getUserFile(fileName);
            writer = new BufferedWriter(new FileWriter(credentialFile, false));
            writer.write(credential);
            writer.close();
        } catch (IOException e) {
            System.err.println("Unable to write credentials: " + e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException e) {}
        }
    }

    /**
     * Determine if a credential file with fileName exists.
     * @returns true iff the credential file exists
     */
    private boolean credentialFileExists(String fileName) {
        File f = getUserFile(fileName);
        return f.exists() && !f.isDirectory();
    }

    /**
     * Read the contents of the credential file named fileName
     * @returns The contents of the credential file as a string.
     */
    private String readCredentialFile(String fileName) {
        BufferedReader reader = null;

        try {
            StringBuilder builder = new StringBuilder();
            File f = getUserFile(fileName);
            reader = new BufferedReader(new FileReader(f));

            while (true) {
               int fileContent = reader.read();
               if (fileContent == -1) { break; }
               builder.append((char)fileContent);
            }

            reader.close();
            return builder.toString();
        } catch(IOException e) {
            if (reader != null) {
                try {
                    reader.close();
                } catch(IOException ioe) {}
            }

            return null;
        }
    }

    /**
     * Logout is achieved by deleting any existing credential files from the user's
     * profile.
     */
    public void logOut() {
        File tokenFile = getUserFile(TOKEN_FILENAME);
        File tokenSecretFile = getUserFile(TOKEN_SECRET_FILENAME);
        if (tokenFile.exists() && !tokenFile.isDirectory()) {
            tokenFile.delete();
        }

        if (tokenSecretFile.exists() && !tokenSecretFile.isDirectory()) {
            tokenSecretFile.delete();
        }
    }

	public synchronized HttpRequestFactory getAuthorizedHttpRequestFactory() throws TwitterAuthenticationException {
		if (factory != null) {
			return factory;
		}

		OAuthHmacSigner signer = new OAuthHmacSigner();

		signer.clientSharedSecret = consumerSecret;

        if (credentialFileExists(TOKEN_FILENAME) && credentialFileExists(TOKEN_SECRET_FILENAME)) {
            String token = readCredentialFile(TOKEN_FILENAME);
            String tokenSecret = readCredentialFile(TOKEN_SECRET_FILENAME);

            if (token != null && token.length() > 0 && tokenSecret != null && tokenSecret.length() > 0) {
                signer.tokenSharedSecret = tokenSecret;

    		    OAuthParameters parameters = new OAuthParameters();
	       	    parameters.consumerKey = consumerKey;
		        parameters.token = token;
		        parameters.signer = signer;

        	    factory = TRANSPORT.createRequestFactory(parameters);
		        return factory;
	        }
        }

		OAuthGetTemporaryToken requestToken = new OAuthGetTemporaryToken(REQUEST_TOKEN_URL);
		requestToken.consumerKey = consumerKey;
		requestToken.transport = TRANSPORT;
		requestToken.signer = signer;

		OAuthCredentialsResponse requestTokenResponse;
		try {
			requestTokenResponse= requestToken.execute();
		} catch (IOException e) {
			throw new TwitterAuthenticationException("Unable to aquire temporary token: " + e.getMessage(), e);
		}

		out.println("Aquired temporary token...\n");

		signer.tokenSharedSecret = requestTokenResponse.tokenSecret;

		OAuthAuthorizeTemporaryTokenUrl authorizeUrl = new OAuthAuthorizeTemporaryTokenUrl(AUTHORIZE_URL);
		authorizeUrl.temporaryToken = requestTokenResponse.token;

		String providedPin;
		Scanner scanner = new Scanner(System.in);
		try {
			out.println("Go to the following link in your browser:\n" + authorizeUrl.build());
			out.println("\nPlease enter the retrieved PIN:");
			providedPin = scanner.nextLine();
		} finally {
			scanner.close();
		}

		if (providedPin == null) {
			throw new TwitterAuthenticationException("Unable to read entered PIN");
		}

		OAuthGetAccessToken accessToken = new OAuthGetAccessToken(ACCESS_TOKEN_URL);
		accessToken.verifier = providedPin;
		accessToken.consumerKey = consumerSecret;
		accessToken.signer = signer;
		accessToken.transport = TRANSPORT;
		accessToken.temporaryToken = requestTokenResponse.token;


		OAuthCredentialsResponse accessTokenResponse;
		try {
			accessTokenResponse = accessToken.execute();
		} catch (IOException e) {
			throw new TwitterAuthenticationException("Unable to authorize access: " + e.getMessage(), e);
		}
		out.println("\nAuthorization was successful");

		signer.tokenSharedSecret = accessTokenResponse.tokenSecret;

		OAuthParameters parameters = new OAuthParameters();
		parameters.consumerKey = consumerKey;
		parameters.token = accessTokenResponse.token;
		parameters.signer = signer;

        writeCredentialFile(TOKEN_FILENAME, accessTokenResponse.token);
        writeCredentialFile(TOKEN_SECRET_FILENAME, accessTokenResponse.tokenSecret);

		factory = TRANSPORT.createRequestFactory(parameters);
		return factory;
	}
}