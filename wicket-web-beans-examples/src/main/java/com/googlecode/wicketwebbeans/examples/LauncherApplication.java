package com.googlecode.wicketwebbeans.examples;



import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;


public class LauncherApplication extends WebApplication {

	public LauncherApplication() {
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return LauncherPage.class;
	}
}
