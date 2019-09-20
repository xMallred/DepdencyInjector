package injector;

import injector.annotations.AutoWired;
import injector.annotations.Component;
import injector.exception.NoBeanException;
import injector.exception.NoBeanWithRequiredType;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
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
			LOGGER.log(Level.SEVERE, "Dependency injection failed to start", e);
		}
	}

	public Object getComponentByName(String beanName) throws NoBeanException {

		Object component = applicationScope.get(beanName);

		if(component == null){
			throw new NoBeanException("Unable to find bean with name: " + beanName);
		} else {
			return component;
		}

	}

	public <T>T getComponentByClass(Class<T> clazz) throws NoBeanWithRequiredType {

		T result = null;

		Collection<Object> components = applicationScope.values();

		for(Object object : components){

			if (clazz == object.getClass()){
				result = (T) object;
				break;
			}
		}

		if(result == null){
			throw new NoBeanWithRequiredType("No bean found with type: " + clazz.getName());
		} else {
			return result;
		}

	}

	public <T>T getComponentByNameAndType(String beanName, Class<T> clazz) throws NoBeanException{

		Object object = getComponentByName(beanName);

		T result = null;

		if (clazz == object.getClass()){
			result = (T) object;
		}

		if(result == null){
			throw new NoBeanException("No bean found with name: " + beanName + " and type: " + clazz.getName());
		} else {
			return result;
		}

	}

	public Object returnBean(String beanName){ return beans.get(beanName);}
}
