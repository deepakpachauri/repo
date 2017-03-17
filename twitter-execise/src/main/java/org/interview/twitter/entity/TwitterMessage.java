package org.interview.twitter.entity;

import static org.interview.twitter.TwitterUtils.convertDate;

import org.json.JSONObject;

public class TwitterMessage implements Comparable<TwitterMessage> {

	private Long messageId;

	private String message;

	private Long creationTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TwitterMessage [messageId=" + messageId + ", message="
				+ message + ", creationTime=" + creationTime + "]";
	}

	public TwitterMessage() {
	}

	public TwitterMessage(Long messageId, String message, long creationTime) {
		this.messageId = messageId;
		this.message = message;
		this.creationTime = creationTime;
	}

	public void enrichMessagewithJSON(JSONObject messageObj) {
		this.messageId = messageObj.getLong("id");
		this.message = messageObj.getString("text");
		this.creationTime = convertDate(messageObj.getString("created_at"));
	}

	/**
	 * @return the messageId
	 */
	public Long getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the creationTime
	 */
	public Long getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime
	 *            the creationTime to set
	 */
	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}

	public int compareTo(TwitterMessage o) {
		return this.creationTime.compareTo(o.getCreationTime());
	}

}
