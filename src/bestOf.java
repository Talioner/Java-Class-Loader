import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class bestOf {
	
	private int min = Integer.MAX_VALUE;
	
	bestOf() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		URL[]  urlsToLoadFrom = new URL []  { new File("C:\\Users\\KacperT\\Desktop\\Java2\\lab4src").toURI().toURL() };
		URLClassLoader loader = new URLClassLoader(urlsToLoadFrom) ;
		Class<?> cl1 = Class.forName("randomTour", true, loader);
		Class<?> cl2 = Class.forName("closestNeighbour", true, loader);
		Method m1 = cl1.getMethod("countTourCost", new  Class[] { int[][].class, int.class });
		Method m2 = cl2.getMethod("countTourCost", new  Class[] { int[][].class, int.class });
		if((Integer)(m1.invoke(null, new Object[] { FinalApp.costMatrix, FinalApp.size })) < min) {
			min = (Integer)(m1.invoke(null, new Object[] { FinalApp.costMatrix, FinalApp.size }));
		}
		if((Integer)(m2.invoke(null, new Object[] { FinalApp.costMatrix, FinalApp.size })) < min) {
			min = (Integer)(m2.invoke(null, new Object[] { FinalApp.costMatrix, FinalApp.size }));
		}
	}

	public int getMin() {
		return min;
	}
}
