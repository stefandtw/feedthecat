/**
 * 
 */
package org.quickflower.application;

import org.quickflower.datasource.DataSource;
import org.quickflower.datasource.XmlFileDataSource;

import com.google.inject.AbstractModule;

public class DefaultModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DataSource.class).to(XmlFileDataSource.class);
	}
}