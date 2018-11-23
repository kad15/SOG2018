import java.util.Random;

public class Data {

	public static double tabPoids[];
	public static double tabValeurs[];
	public static double[][] tabVilles;
	public static int tabAvions[];
	private static Random generateur = new Random(123);

	/************************************************************/
	/* parametres de generation */
	private static double RAYON = 100.0;
	private static int POIDS = 100;
	private static int VALEURS = 100;
	/************************************************************/

	public static int separation[][] = { { 4, 5, 6 }, { 3, 3, 4 }, { 3, 3, 3 } };

	// generation de n objets pour le probleme du sac a dos
	public static void genererObjets(int n) {
		tabPoids = new double[n];
		tabValeurs = new double[n];
		for (int i = 0; i < n; i++) {
			tabPoids[i] = 1 + generateur.nextInt(POIDS);
			tabValeurs[i] = 1 + generateur.nextInt(VALEURS);
		}
	}

	// affichage des objets
	public static void afficherObjets() {
		System.out.println("***************************** Objets******************************");
		for (int i = 0; i < tabPoids.length; i++) {
			System.out.println("Objet " + i + " poids= " + tabPoids[i] + " valeur=" + tabValeurs[i]);
		}
	}

	// generation de n villes sur le cercle (TSP)
	public static void genererVillesCercle(int n) {
		double theta, x, y;
		tabVilles = new double[n][2];
		for (int i = 0; i < n; i++) {
			theta = 2 * Math.PI * generateur.nextDouble();
			x = RAYON * Math.cos(theta);
			y = RAYON * Math.sin(theta);

			tabVilles[i][0] = x;
			tabVilles[i][1] = y;
		}
	}

	// generation de n villes dans un carre RAYONxRAYON (TSP)
	public static void genererVilles(int n) {
		double x, y;

		tabVilles = new double[n][2];
		for (int i = 0; i < n; i++) {
			x = RAYON * generateur.nextDouble();
			y = RAYON * generateur.nextDouble();
			tabVilles[i][0] = x;
			tabVilles[i][1] = y;
		}
	}

	// affichage des villes
	public static void afficherVilles() {
		System.out.println("***************************** Villes ******************************");
		for (int i = 0; i < tabVilles.length; i++) {
			System.out.println("Ville " + i + " x= " + tabVilles[i][0] + " y= " + tabVilles[i][1]);
		}
	}

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

	public static void main(String[] args) {
		int dim = 100;
		genererObjets(dim);
		afficherObjets();
		genererVillesCercle(dim);
		afficherVilles();
		genererVilles(dim);
		afficherVilles();
		genererAvions(dim);
		afficherAvions();
	}
}