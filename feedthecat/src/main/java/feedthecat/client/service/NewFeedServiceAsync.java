package feedthecat.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import feedthecat.shared.FeedConfig;

public interface NewFeedServiceAsync {

	void createNewFeed(FeedConfig feedConfig, AsyncCallback<Void> callback);
}
