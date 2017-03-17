package org.interview.twitter.entity;

import static org.interview.twitter.TwitterUtils.convertDate;

import org.json.JSONObject;

public class TwitterUser implements Comparable<TwitterUser> {

	private Long userId;

	private String screenName;

	private String fullName;

	private Long creationTime;

	public TwitterUser() {
	}

	public TwitterUser(Long userId, String screenName, String fullName,
			Long creationTime) {
		this.userId = userId;
		this.screenName = screenName;
		this.fullName = fullName;
		this.creationTime = creationTime;
	}

	public void enrichUserwithJSON(JSONObject userObj) {
		this.userId = userObj.getLong("id");
		this.screenName = userObj.getString("screen_name");
		this.fullName = userObj.getString("name");
		this.creationTime = convertDate(userObj.getString("created_at"));
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName
	 *            the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwitterUser other = (TwitterUser) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TwitterUser [userId=" + userId + ", screenName=" + screenName
				+ ", fullName=" + fullName + ", creationTime=" + creationTime
				+ "]";
	}

	public int compareTo(TwitterUser user) {
		return this.creationTime.compareTo(user.getCreationTime());
	}
}
