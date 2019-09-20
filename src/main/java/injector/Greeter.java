package injector;

import injector.annotations.AutoWired;
import injector.annotations.Component;

@Component
public class Greeter {

	@AutoWired
	private GreeterHelper greeterHelper;

	public String sayHello(){
		greeterHelper.setMessage("You just made your own dependency Injector");
		return greeterHelper.getMessage();
	}
}
