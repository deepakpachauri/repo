package org.interview.twitter.view;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.interview.twitter.entity.TwitterMessage;
import org.interview.twitter.entity.TwitterUser;

public interface PrintView {

	void print(Map<TwitterUser, List<TwitterMessage>> messageMap,
			PrintStream out);

}
