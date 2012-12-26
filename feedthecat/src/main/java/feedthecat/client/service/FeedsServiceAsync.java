package feedthecat.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import feedthecat.shared.FeedConfig;

public interface FeedsServiceAsync {

	void deleteFeed(FeedConfig feedConfig, AsyncCallback<Void> callback);

	void getFeedConfigs(AsyncCallback<List<FeedConfig>> callback);

}
