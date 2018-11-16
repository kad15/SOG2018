import java.util.Random;
//séquencement
public class EtatSeq {

	public int dimEtat;
	public int[] idAvions;
	private static Random generateur = new Random(123);
	private int oldIndexI;
	private int oldIndexJ;
	/*******************************************************************************************************************/
	/* Methodes locales */
	/*******************************************************************************************************************/
	public void exchange(int I, int J) {
		int buffer;
		buffer = idAvions[I];
		idAvions[I] = idAvions[J];
		idAvions[J] = buffer;
	}
	/*******************************************************************************************************************/
	/* Constructeur */
	/*******************************************************************************************************************/
	public EtatSeq(int dimEtat) {
		this.dimEtat = dimEtat;
		idAvions = new int[dimEtat];
	}
	/*******************************************************************************************************************/
	/* Initialisation aleatoire de l'etat */
	/*******************************************************************************************************************/
	public void initAleatEtat() {
		for (int i = 0; i < dimEtat; ++i) {
			idAvions[i] = i;
		}
	}
	/*******************************************************************************************************************/
	/* Affichage sequence avions
	 * */
	/*******************************************************************************************************************/
	public void afficherEtat() {
		System.out.println("rang d'atterrissage ; id avion");
		for (int i = 0; i < idAvions.length; i++) {
			System.out.println(i+1 + " ; "+idAvions[i]);
		}
	}
	/*******************************************************************************************************************/
	/* GenererVoisin */
	/*******************************************************************************************************************/
//	public void genererVoisin2() {
//		int indexI = generateur.nextInt(dimEtat); // on prends deux index au hasard entre 0 et 99
//		int indexJ = generateur.nextInt(dimEtat);
//        // on permute les avions i.e leur ordre d'atterrissage compris entre ces deux index de maniere symetrique
//		for (int i = 0; i < Math.ceil(Math.abs(indexI - indexJ) / 2); ++i) {
//			exchange(Math.min(indexI, indexJ) + i, Math.max(indexI, indexJ) - i);
//		}
//		oldIndexI = indexI;
//		oldIndexJ = indexJ;
//
//		for (int i = 0; i < dimEtat - 1; ++i) {
//			if (idAvions[i] <= i - 4 || idAvions[i] >= i + 4) {
//				comeBack();
//				break;
//			}
//		}
//	}
	
	public void genererVoisin() {
		int indexI = generateur.nextInt(dimEtat); // on prends un index au hasard entre 0 et 99 (dimEtat=100=DIMENSION)
		int indexJ = indexI - 4 + generateur.nextInt(9); // on génère un indexJ dans indexI +/- 4 pour tenir compte de la
		//contrainte de vitesse ; un avion ne peut changer de rang au délà de +/- 4/
		if (indexJ<0) indexJ = 0;
		if(indexJ>=dimEtat) indexJ=dimEtat-1; 
        // on permute les avions i.e leur ordre d'atterrissage compris entre ces deux index de maniere symetrique
		for (int i = 0; i < Math.ceil(Math.abs(indexI - indexJ) / 2); ++i) {
			exchange(Math.min(indexI, indexJ) + i, Math.max(indexI, indexJ) - i);
		}
		oldIndexI = indexI;
		oldIndexJ = indexJ;
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
		// tabClasseAvions contient les classes des 100 avions (0 : Heavy, 1 : Medium, 2 : Light),
		// l'index de tabAvions correspond à l'identifiant avion de 0 à 99
		//idAvions contient les avions identifiés par un chiffre de 0 à 99
		// les index du tableau idAvions donne l'ordre d'atterrissage : idAvion[0] donne l'id de l'avion qui atterrit en premier.
		for (int i = 0; i < dimEtat - 1; ++i) {
			cost += Data.separation[Data.tabClasseAvions[idAvions[i]]][Data.tabClasseAvions[idAvions[i + 1]]];
		}
		return cost;
	}
}
