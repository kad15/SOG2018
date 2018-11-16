import java.util.Random;

public class Etat {

	public int dimEtat;
	public int[] vecteur;

	private static Random generateur = new Random(123);
	private int oldIndexI;
	private int oldIndexJ;

	/*******************************************************************************************************************/
	/* Methodes locales */
	/*******************************************************************************************************************/
	public void exchange(int I, int J) {
		int buffer;
		buffer = vecteur[I];
		vecteur[I] = vecteur[J];
		vecteur[J] = buffer;
	}

	/*******************************************************************************************************************/
	/* Constructeur */
	/*******************************************************************************************************************/
	public Etat(int dimEtat) {
		this.dimEtat = dimEtat;
		vecteur = new int[dimEtat];
	}

	/*******************************************************************************************************************/
	/* Initialisation aleatoire de l'etat */
	/*******************************************************************************************************************/
	public void initAleatEtat() {
		int ranIndex;
		int temp;
		for (int i = 0; i < dimEtat; ++i) {
			vecteur[i] = i;
		}
	}

	/*******************************************************************************************************************/
	/* Affichage */
	/*******************************************************************************************************************/

	public String afficherEtat() {
		return "";
	}

	/*******************************************************************************************************************/
	/* GenererVoisin */
	/*******************************************************************************************************************/

	public void genererVoisin() {

		int indexI = generateur.nextInt(dimEtat);
		int indexJ = generateur.nextInt(dimEtat);

		for (int i = 0; i < Math.ceil(Math.abs(indexI - indexJ) / 2); ++i) {
			exchange(Math.min(indexI, indexJ) + i, Math.max(indexI, indexJ) - i);
		}
		oldIndexI = indexI;
		oldIndexJ = indexJ;

		for (int i = 0; i < dimEtat - 1; ++i) {
			if (vecteur[i] <= i - 4 || vecteur[i] >= i + 4) {
				comeBack();
				break;
			}
		}
	}

	/*******************************************************************************************************************/
	/* Retour a la solution précédente */
	/*******************************************************************************************************************/
	public void comeBack() {
		for (int i = 0; i < Math.ceil(Math.abs(oldIndexI - oldIndexJ) / 2); ++i) {
			exchange(Math.min(oldIndexI, oldIndexJ) + i, Math.max(oldIndexI, oldIndexJ) - i);
		}
	}

	/*******************************************************************************************************************/
	/* Evaluation des objectifs */
	/*******************************************************************************************************************/
	public double calculCritere() {

		double cost = 0;
		double poids = 0;
		double dx, dy;

		for (int i = 0; i < dimEtat - 1; ++i) {
			cost += Data.separation[Data.tabAvions[vecteur[i]]][Data.tabAvions[vecteur[i + 1]]];
		}
		return cost;

	}

}
