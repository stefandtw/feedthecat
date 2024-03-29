package feedthecat.datasource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;
import com.thoughtworks.xstream.XStream;

import feedthecat.shared.FeedConfig;

@Singleton
public class XmlFileDataSource implements DataSource {

	private static final Logger logger = LoggerFactory
			.getLogger(XmlFileDataSource.class);
	private static final String FEED_EXTENSION = ".feed";
	private final XStream xstream;

	public XmlFileDataSource() {
		xstream = new XStream();
	}

	private FeedConfig loadConfig(String fileName) {
		Reader reader;
		try {
			reader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			logger.error("Can't read " + fileName, e);
			return null;
		}
		FeedConfig config = (FeedConfig) xstream.fromXML(reader);
		try {
			reader.close();
		} catch (IOException e) {
			logger.error("Can't close " + fileName, e);
		}
		return config;
	}

	private void saveConfig(FeedConfig config, String fileName) {
		Writer writer;
		try {
			writer = new FileWriter(fileName);
		} catch (IOException e) {
			logger.error("Can't write " + fileName, e);
			return;
		}
		xstream.toXML(config, writer);
		try {
			writer.close();
		} catch (IOException e) {
			logger.error("Can't close " + fileName, e);
		}
	}

	@Override
	public FeedConfig loadFeedConfig(String name) {
		return loadConfig(name + FEED_EXTENSION);
	}

	@Override
	public void saveFeedConfig(FeedConfig feedConfig) {
		saveConfig(feedConfig, feedConfig.getName() + FEED_EXTENSION);
	}

	@Override
	public List<FeedConfig> loadFeeds() {
		String[] fileNames = new File(".").list(new FilenameFilter() {
			@Override
			public boolean accept(File file, String fileName) {
				return fileName.endsWith(FEED_EXTENSION);
			}
		});
		List<FeedConfig> feeds = new ArrayList<FeedConfig>();
		for (String fileName : fileNames) {
			feeds.add(loadConfig(fileName));
		}
		return feeds;
	}

	@Override
	public void deleteFeed(FeedConfig feedConfig) {
		new File(feedConfig.getName() + FEED_EXTENSION).delete();
	}

}
