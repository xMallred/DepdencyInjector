package injector;

import java.util.logging.Logger;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(DependencyInjector.class.getName());

	public static void main(String[] args){

		DependencyInjector dependencyInjector = new DependencyInjector();

		try {

//			Object greeter = dependencyInjector.getComponent("Greeter");
//			Greeter greeter = dependencyInjector.getComponentByClass(Greeter.class);
			Greeter greeter = dependencyInjector.getComponentByNameAndType("Greeter", Greeter.class);

			System.out.println(greeter.sayHello());

		} catch(Exception ex){
			LOGGER.severe(ex.getMessage());
		}

	}
}
