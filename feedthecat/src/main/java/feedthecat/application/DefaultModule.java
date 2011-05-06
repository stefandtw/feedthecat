/**
 * 
 */
package feedthecat.application;

import com.google.inject.AbstractModule;

import feedthecat.datasource.DataSource;
import feedthecat.datasource.XmlFileDataSource;

public class DefaultModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DataSource.class).to(XmlFileDataSource.class);
	}

}