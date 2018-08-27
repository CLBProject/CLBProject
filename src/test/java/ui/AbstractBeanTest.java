package ui;

import static org.junit.Assert.assertFalse;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractBeanTest {

	@Before
	public void init() {

		Object bean = getBean();

		for(Field f: bean.getClass().getDeclaredFields()) {
			try {
				String methodForGet = "get" + f.getName().toUpperCase().charAt(0) + f.getName().substring(1);
				String methodForSet = "set" + f.getName().toUpperCase().charAt(0) + f.getName().substring(1);

				//Invoke Get Methods
				if(!Modifier.isFinal(f.getModifiers()) && !Modifier.isStatic(f.getModifiers()) && !f.getName().startsWith("$SWITCH_TABLE$")) {

					Object newInstance;
					
					f.setAccessible(true);

					if(f.get(bean) != null) {
						newInstance = f.get(bean);
					}
					else if(f.getType().isPrimitive() || f.getType().equals(String.class)  || f.getType().isEnum() || f.getType().isArray()) {
						newInstance = getPrimitiveInstance(f.getType());
					}else { 
						newInstance =  Mockito.mock(f.getType());
					}
					
					bean.getClass().getDeclaredMethod(methodForGet).invoke(bean);
					bean.getClass().getDeclaredMethod(methodForSet,f.getType()).invoke(bean, newInstance);
				}

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
				assertFalse(e.getMessage(), true);
			}		
		}
	}
	
	@Test
	public void runInitAsTest() {
		
	}

	private Object getPrimitiveInstance(Class<?> primitive) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {	

		if(primitive.isArray()) {
			return Array.newInstance(primitive.getComponentType(),5);
		}

		if(primitive.isEnum()){
			return primitive.getEnumConstants()[0];
		}

		else if(primitive.equals(String.class)) {
			return "";
		}
		else if(primitive.equals(boolean.class)) {
			return true;
		}
		else if(primitive.equals(int.class)) {
			return 1;
		}
		else if(primitive.equals(byte.class)) {
			return 2;
		}
		else if(primitive.equals(char.class)) {
			return 'c';
		}
		else if(primitive.equals(double.class)) {
			return 6.5;
		}
		else if(primitive.equals(long.class)) {
			return 1L;
		}
		else if(primitive.equals(short.class)) {
			return 10;
		}
		else if(primitive.equals(float.class)) {
			return 3.0f;
		}

		return new Object();
	}
	

	public abstract Object getBean();

	public abstract void initBean();

}
