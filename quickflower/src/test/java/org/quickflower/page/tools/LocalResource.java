package org.quickflower.page.tools;

public class LocalResource {

	private final String fileName;

	public LocalResource(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return getClass().getResource("/" + fileName).toString();
	}

}
