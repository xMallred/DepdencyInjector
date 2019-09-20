package injector;

import injector.Annotations.AutoWired;
import injector.Annotations.Component;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class DependencyInjector {
	private static final Logger LOGGER = Logger.getLogger(DependencyInjector.class.getName());

	private static Map<String, Object> beans = new HashMap<>();

	private static Map<String, Object> applicationScope = new HashMap<>();


	DependencyInjector(){
		LOGGER.info("Starting dependency Injection");
		Reflections reflections = new Reflections("");
		Set<Class<?>> types = reflections.getTypesAnnotatedWith(Component.class);
		try {
			for (Class baseClass : types){
				Object classInstance = baseClass.getDeclaredConstructor().newInstance();
				for (Field field : baseClass.getDeclaredFields()){
					if (field.isAnnotationPresent(AutoWired.class)){
							field.setAccessible(true);

							Object fieldInstance = field.getType().newInstance();
							field.set(classInstance, fieldInstance);
							beans.put(field.getType().getSimpleName(),fieldInstance);
					}
				}
				applicationScope.put(baseClass.getSimpleName(), classInstance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getComponent(String beanName){
		return applicationScope.get(beanName);
	}

	public Object returnBean(String beanName){ return beans.get(beanName);}
}
