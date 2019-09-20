package injector;

import injector.Annotations.AutoWired;
import injector.Annotations.Component;

@Component
public class Greeter {

	@AutoWired
	private GreeterHelper greeterHelper;

	public String sayHello(){
		greeterHelper.setMessage("You just made your own dependency Injector");
		return greeterHelper.getMessage();
	}
}
