package feedthecat.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import feedthecat.datasource.DataSource;
import feedthecat.server.webpagefilter.FeedBuilder;
import feedthecat.shared.FeedConfig;

@Singleton
public class FeedBuilderServlet extends HttpServlet {

	public static final String PARAMETER_KEY_NAME = "name";

	@Inject
	private DataSource dataSource;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String feedName = req.getParameter(PARAMETER_KEY_NAME);
		byte[] feedContent = loadFeedContent(feedName);
		resp.getOutputStream().write(feedContent);
		resp.getOutputStream().close();
		resp.setContentType("application/atom+xml");
	}

	private byte[] loadFeedContent(String feedName) {
		FeedConfig feedConfig = dataSource.loadFeedConfig(feedName);
		FeedBuilder builder = new FeedBuilder(feedConfig);
		String resultSource = builder.getFeedSource();
		try {
			return resultSource.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("error reading feed", e);
		}
	}
}
