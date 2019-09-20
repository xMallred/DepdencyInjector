package injector;

public class Main {

	public static void main(String[] args){
		DependencyInjector dependencyInjector = new DependencyInjector();
		Object greeter = dependencyInjector.getComponent("Greeter");
		System.out.println(((Greeter)greeter).sayHello());
	}
}
