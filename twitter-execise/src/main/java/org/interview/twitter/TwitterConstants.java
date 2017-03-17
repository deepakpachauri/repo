package org.interview.twitter;

public final class TwitterConstants {

	public static final String CONSUMER_KEY = "RLSrphihyR4G2UxvA0XBkLAdl";

	public static final String CONSUMER_SECRET = "FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4";

	/**
	 * The endpoint in the twitter API to access the public filtering endpoint.
	 * It returns tweets matching certain filtering chriteria. Post request is
	 * expected https://dev.twitter.com/streaming/reference/post/statuses/filter
	 */
	public static final String PUBLIC_FILTER_ENDPOINT = "https://stream.twitter.com/1.1/statuses/filter.json";

	/**
	 * The query parameter that allows us to track a certain term. This filters
	 * tweets with a given term.
	 * 
	 * @see track syntax:
	 *      https://dev.twitter.com/streaming/overview/request-parameters#track
	 */
	public static final String TRACK_PARAM_NAME = "track";

	/**
	 * the query parameter requesting that the responses be delimited with a
	 * length.
	 * 
	 * @see https
	 *      ://dev.twitter.com/streaming/overview/request-parameters#delimited
	 */
	public static final String DELIMITED_PARAM_NAME = "delimited";

	/**
	 * The query parameter value requested a length delimination scheme.
	 * 
	 * @see https
	 *      ://dev.twitter.com/streaming/overview/request-parameters#delimited
	 */
	public static final String DELIMITED_LENGTH_VALUE = "length";

	public static final String DATE_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";

}
