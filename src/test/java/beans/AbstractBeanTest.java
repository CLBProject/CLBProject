package beans;

import static org.junit.Assert.assertFalse;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.runner.RunWith;
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
					bean.getClass().getDeclaredMethod(methodForGet).invoke(bean);
					bean.getClass().getDeclaredMethod(methodForSet,f.getType()).invoke(bean,f.getType().newInstance());
					System.out.println("Invoked Get and Set from " + f.getName());
				}
				
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | InstantiationException e) {
				e.printStackTrace();
				assertFalse(e.getMessage(), true);
			}		
		}
		
		initBean();
	}
	
	public abstract Object getBean();
	public abstract void initBean();
}
