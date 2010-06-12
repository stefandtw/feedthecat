package feedthecat.datasource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import feedthecat.webpagefilter.Config;
import feedthecat.webpagefilter.FeedConfig;
import feedthecat.webpagefilter.PageConfig;

import com.google.inject.Singleton;
import com.thoughtworks.xstream.XStream;

@Singleton
public class XmlFileDataSource implements DataSource {

	private final XStream xstream;

	public XmlFileDataSource() {
		xstream = new XStream();
	}

	@Override
	public PageConfig loadPageConfig(String name) {
		return (PageConfig) loadConfig(name);
	}

	private Config loadConfig(String name) {
		Reader reader;
		try {
			reader = new FileReader(name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		Config config = (Config) xstream.fromXML(reader);
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}

	@Override
	public void savePageConfig(PageConfig pageConfig) {
		saveConfig(pageConfig);
	}

	private void saveConfig(Config pageConfig) {
		Writer writer;
		try {
			writer = new FileWriter(pageConfig.getName());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		xstream.toXML(pageConfig, writer);
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public FeedConfig loadFeedConfig(String name) {
		return (FeedConfig) loadConfig(name);
	}

	@Override
	public void saveFeedConfig(FeedConfig feedConfig) {
		saveConfig(feedConfig);
	}

}
