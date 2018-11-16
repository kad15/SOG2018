import java.util.Random;

public class Data {

	public static int tabClasseAvions[];
	private static Random generateur = new Random(123);

	public static int separation[][] = { { 4, 5, 6 }, { 3, 3, 4 }, { 3, 3, 3 } };

	// generation de n avions (classe heavy(0), Medium(1), Small(2))
	public static void genererAvions(int n) {
		tabClasseAvions = new int[n];
		for (int i = 0; i < n; i++) {
			tabClasseAvions[i] = generateur.nextInt(3);
		}
	}

	// affichage des classes des avions
	public static void afficherClassesAvions() {
		System.out.println("***************************** Avions ******************************");
		for (int i = 0; i < tabClasseAvions.length; i++) {
			System.out.println("Avion " + i + " classe= " + tabClasseAvions[i]);
		}
	}
}