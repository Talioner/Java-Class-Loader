import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class FinalApp {

	static int [][] costMatrix;
	static int size;
	static Path currentRelativePath = Paths.get("");
	static String path = currentRelativePath.toAbsolutePath().toString() + "\\data\\tsp_15.txt";
	static String [] loadedAlgorithms = new String[2];
	
	static void loadMatrix(String filePath) {
		try{
			File file = new File(filePath);
			Scanner inputStream = new Scanner(file);
			size = Integer.parseInt(inputStream.next());
			costMatrix = new int [size][size];
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					costMatrix[i][j] = Integer.parseInt(inputStream.next());
				}
			}
			inputStream.close();
		}catch(FileNotFoundException e) {
			
		}
	}
	
	static void showMatrix() {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				System.out.print(costMatrix[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	static void showMenu() {
		System.out.print("Wybierz opcjê: \n"
				+ "1. Wyœwietl macierz kosztów.\n"
				+ "2. Wczytaj algorytm\n"
				+ "3. Wybierz algorytm do obliczenia kosztu trasy.\n"
				+ "4. Informacje o algorytmach.\n"
				+ "5. bestOf - wymaga za³adowania obydwu algorytmów.\n"
				+ "0. Wyjœcie.");
	}
	
	static void showLoadedAlgorithms() {
		if(loadedAlgorithms[0] == null) 
			System.out.print("Nie wczytano jeszcze algorytmu.\n\n");
		else {
			int i = 1;
			for(String s : loadedAlgorithms) {
				if(s == null) break;
				System.out.println(i + ". " + s);
				i++;
			}
		}
		System.out.print("\n");
	}
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, MalformedURLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		loadMatrix(path);
		Scanner scanner = new Scanner(System.in);
		int i = 0;
		Method m1 = null, m2 = null, m1desc = null, m2desc = null;
		
		int command = 10;
		int command2 = 10;
		int command3 = 10;
		while(command != 0) {
			showMenu();
			command = scanner.nextInt();
			switch(command) {
			case 1:
				showMatrix();
				break;
			case 2:
				URL[]  urlsToLoadFrom = new URL []  { new File("C:\\Users\\KacperT\\Desktop\\Java2\\lab4src\\").toURI().toURL() };
				URLClassLoader loader = new URLClassLoader(urlsToLoadFrom) ;
				System.out.print("\nWybierz algorytm do za³adowania:\n"
						+ "1. Algorytm losowy.\n"
						+ "2. Algorytm najblizszego s¹siada.\n");
				command2 = scanner.nextInt();
				switch(command2) {
				case 1:
					Class<?> cl1 = Class.forName("randomTour", true, loader);
					m1 = cl1.getMethod("countTourCost", new  Class[] { int[][].class, int.class });
					m1desc = cl1.getMethod("getDescription", new  Class[] {});
					loadedAlgorithms[i] = "randomTour";
					i++;
					System.out.println("Wczytano algorytm");
					System.out.println("");
					break;
				case 2:
					Class<?> cl2 = Class.forName("closestNeighbour", true, loader);
					m2 = cl2.getMethod("countTourCost", new  Class[] { int[][].class, int.class });
					m2desc = cl2.getMethod("getDescription", new  Class[] {});
					System.out.println("Wczytano algorytm");
					loadedAlgorithms[i] = "closestNeighbour";
					i++;
					System.out.println("");
					break;
				}
				break;
			case 3:
				showLoadedAlgorithms();
				if(loadedAlgorithms[0] != null) {
					command3 = scanner.nextInt();
					switch(command3) {
					case 1:
						if(loadedAlgorithms[0] == "randomTour") 
							System.out.println("Koszt trasy: " + (Integer)(m1.invoke(null, new Object[] { costMatrix, size })));
						if(loadedAlgorithms[0] == "closestNeighbour") 
							System.out.println("Koszt trasy: " + (Integer)(m2.invoke(null, new Object[] { costMatrix, size })));
						break;
					case 2:
						if(loadedAlgorithms[1] == "randomTour") 
							System.out.println("Koszt trasy: " + (Integer)(m1.invoke(null, new Object[] { costMatrix, size })));
						if(loadedAlgorithms[1] == "closestNeighbour") 
							System.out.println("Koszt trasy: " + (Integer)(m2.invoke(null, new Object[] { costMatrix, size })));
						break;
					}
				}
				break;
			case 4:
				showLoadedAlgorithms();
				if(loadedAlgorithms[0] != null) {
					command3 = scanner.nextInt();
					switch(command3) {
					case 1:
						if(loadedAlgorithms[0] == "randomTour") 
							System.out.println((String)(m1desc.invoke(null, new Object[] { })));
						if(loadedAlgorithms[0] == "closestNeighbour") 
							System.out.println((String)(m2desc.invoke(null, new Object[] { })));
						break;
					case 2:
						if(loadedAlgorithms[1] == "randomTour") 
							System.out.print((String)(m1desc.invoke(null, new Object[] { })));
						if(loadedAlgorithms[1] == "closestNeighbour") 
							System.out.print((String)(m2desc.invoke(null, new Object[] { })));
						break;
					}
				}
				break;
			case 5:
				bestOf best = new bestOf();
				System.out.println("Najlepszy wynik: " + best.getMin());
				break;
			case 0:
				break;
			}
			scanner.close();
		}
		
		
		/*URL[]  urlsToLoadFrom = new URL []  { new File("C:\\Users\\KacperT\\Desktop\\Java2\\lab4src").toURI().toURL() };
		URLClassLoader loader = new URLClassLoader(urlsToLoadFrom) ;
		Class<?> cl1 = Class.forName("randomTour", true, loader);
		Class<?> cl2 = Class.forName("closestNeighbour", true, loader);
		Method m1 = cl1.getMethod("countTourCost", new  Class[] { int[][].class, int.class });
		Method m2 = cl2.getMethod("countTourCost", new  Class[] { int[][].class, int.class });
		System.out.println((Integer)(m1.invoke(null, new Object[] { costMatrix, size })));
		System.out.println((Integer)(m2.invoke(null, new Object[] { costMatrix, size })));*/
		
		
	}

}
