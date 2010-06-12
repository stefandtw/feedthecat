/**
 * 
 */
package feedthecat.application;

import feedthecat.datasource.DataSource;
import feedthecat.datasource.XmlFileDataSource;

import com.google.inject.AbstractModule;

public class DefaultModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DataSource.class).to(XmlFileDataSource.class);
	}
}