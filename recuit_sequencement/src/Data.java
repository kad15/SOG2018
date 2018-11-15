import java.util.Random;

public class Data {

	public static int tabAvions[];
	private static Random generateur = new Random(123);

	public static int separation[][] = { { 4, 5, 6 }, { 3, 3, 4 }, { 3, 3, 3 } };

	// generation de n avions (classe heavy(0), Medium(1), Small(2))
	public static void genererAvions(int n) {
		tabAvions = new int[n];
		for (int i = 0; i < n; i++) {
			tabAvions[i] = generateur.nextInt(3);
		}
	}

	// affichage sequence avions
	public static void afficherAvions() {
		System.out.println("***************************** Avions ******************************");
		for (int i = 0; i < tabAvions.length; i++) {
			System.out.println("Avion " + i + " classe= " + tabAvions[i]);
		}
	}

	// public static void main(String[] args) {
	// int dim = 100;
	// genererAvions(dim);
	// afficherAvions();
	// }
}