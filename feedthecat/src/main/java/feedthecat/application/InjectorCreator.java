package feedthecat.application;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class InjectorCreator extends
		com.google.inject.servlet.GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new InjectionBindings(),
				new ServletConfig());
	}
}