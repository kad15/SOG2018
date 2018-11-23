import java.util.Random;

// recuit pour le problème du sac à dos
public class EtatKP {

	public int dimEtat;
	public int[] X;

	private static Random generateur = new Random(999);
	private int oldIndexI;
	private double capacite = 2000;
	private double P, V; // p : somme des poids et V somme des valeurs,
	// ce sont des attributs de la classe EtatKP initialisés
	// dans la méthode EtatKP::calculCritere

	/*******************************************************************************************************************/
	/* Methodes locales */
	/*******************************************************************************************************************/
	// echange deux valeurs du vecteur d'etat X
	public void swap(int a, int b) {
		int tmp;
		tmp = X[a];
		X[a] = X[b];
		X[b] = tmp;
	}
	/*
	 * public double getP() { return P; }
	 */
	/*******************************************************************************************************************/

	/* Constructeur */
	/*******************************************************************************************************************/
	public EtatKP(int dimEtat) {
		this.dimEtat = dimEtat;
		X = new int[dimEtat];
	}

	/*******************************************************************************************************************/
	/* Initialisation aleatoire de l'etat */
	/*******************************************************************************************************************/
	public void initAleatEtat() {
		for (int i = 0; i < dimEtat; i++)
			X[i] = generateur.nextInt(2);
	}

	/*******************************************************************************************************************/
	/* Evaluation des objectifs */
	/*******************************************************************************************************************/
	public double calculCritere() {
		double valeur = 0;
		double poids = 0;

		for (int i = 0; i < dimEtat; ++i) {
			valeur += X[i] * Data.tabValeurs[i];
			poids += X[i] * Data.tabPoids[i];
		}
		V = valeur;
		P = poids;
		double delta = poids - capacite; // dans le cas de débordement du sac qu'on cherche à éviter delta > 0
		double epsilon = delta / capacite; // variable pour d'autres relaxations
		// on cherche à maximiser valeur subject to constraint poids < capacité C du sac
		// on relaxe le pb en choisissant un critère "y" qui va pénaliser les cas où les
		// objets "débordent" du sac
		// on pose donc delta = poids - capacité > 0 en cas de débordement ; c'est un
		// choix
		// fait pour manipuler un delta >0.
		// ce delta étant nul si le sac n'est pas saturé ce qui implique dans ce cas
		// simplement un critère = à la fct objectif : valeur
		// on optimise en maximisation donc pénaliser la fonction objectif nécessite de
		// lui soustraire une fonction de delta f(delta) à valeurs positives.
		// test avec f(delta) = delta**2
		if (delta < 0) { // cas où capacité > poids, on prend pour critère y = valeur + 0 = valeur = fct
							// objectif
			return valeur;
		} else { // cas où la capa C est < au poids total des objets dans le sac
					// return valeur/(1+epsilon*epsilon);
			return valeur - delta * delta;
			// return 0;
		}
	}

	/*******************************************************************************************************************/
	/* GenererVoisin */
	/*******************************************************************************************************************/
	// voisinnage = ensemble des états qui ne diffèrent que de 1 bit par rapport à
	// xi donc #voisinnage = 2000.
	public void genererVoisin() {
		int indexI = generateur.nextInt(dimEtat); // on choisit un indice au hasard entre 0 et dimEtat-1
		X[indexI] = (X[indexI] + 1) % 2;// si X(i)= 0, 0+1=1%2 donne 1, si X[i]= 1, 1+1=2%2 donne 0
		oldIndexI = indexI; // on memorise l'index pour pouvoir faire un retour arriere le cas echeant
	}

	/*******************************************************************************************************************/
	/* Retour a la solution precedente */
	/*******************************************************************************************************************/
	public void comeBack() {
		X[oldIndexI] = (X[oldIndexI] + 1) % 2;
// nb : les valeurs et les poids totaux sont quant à eux remis à jours à partir du vecteur X "old" ci-dessus
// car dans la méthode refroidissement (dans la boucle for) calculCritere() est appelée qui utilise les X[i]
// pour le calcul de "poids" et "valeur". 
	}

	/*******************************************************************************************************************/
	/* Affichage */
	/*******************************************************************************************************************/
	public void afficherEtat() {
		System.out.println("poids sac = " + P + " valeur sac =  " + V);
	}
}
