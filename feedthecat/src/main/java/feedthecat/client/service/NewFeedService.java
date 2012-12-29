package feedthecat.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import feedthecat.application.ServletConfig;
import feedthecat.shared.FeedConfig;

@RemoteServiceRelativePath(ServletConfig.NEW_FEED_SERVICE_RELATIVE_PATH)
public interface NewFeedService extends RemoteService {

	void createNewFeed(FeedConfig feedConfig);

}
