package org.interview.twitter.view.impl;

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.interview.twitter.entity.TwitterMessage;
import org.interview.twitter.entity.TwitterUser;
import org.interview.twitter.view.PrintView;

public class ConsolePrintView implements PrintView {

	public void print(Map<TwitterUser, List<TwitterMessage>> messageMap,
			PrintStream out) {

		for (Map.Entry<TwitterUser, List<TwitterMessage>> entry : messageMap
				.entrySet()) {
			out.format("%-10s: %-25d", "UserId", entry.getKey().getUserId());
			out.format("%-10s: %-25s\n", "UserName", entry.getKey()
					.getFullName());
			out.format("%-7s: %-25s", "ScreenName", entry.getKey()
					.getScreenName());
			out.format("%-7s: %-25s\n", "CreatedTime", entry.getKey()
					.getCreationTime());
			out.format(StringUtils.repeat("-", 70));
			List<TwitterMessage> sortedList = entry.getValue();
			Collections.sort(sortedList);
			for (TwitterMessage message : sortedList) {
				out.format("\n%-10s: %-25d", "MessageId",
						message.getMessageId());
				out.format("%-10s: %-25d\n", "CreationTime",
						message.getCreationTime());
				out.format("%-10s: %-50s\n\n\n", "Message",
						message.getMessage());
			}
		}

	}

}
