package feedthecat.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import feedthecat.application.ServletConfig;
import feedthecat.shared.FeedConfig;

@RemoteServiceRelativePath(ServletConfig.FEEDS_SERVICE_RELATIVE_PATH)
public interface FeedsService extends RemoteService {

	List<FeedConfig> getFeedConfigs();

	void deleteFeed(FeedConfig feedConfig);
}
