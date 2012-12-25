package feedthecat.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import feedthecat.shared.FeedConfig;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("feeds")
public interface FeedsService extends RemoteService {

	List<FeedConfig> getFeedConfigs();

	void deleteFeed(FeedConfig feedConfig);
}
