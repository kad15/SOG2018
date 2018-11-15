import java.util.Random;
// recuit simple
public class EtatSimple {
	
	public int dimEtat;
	public int[] vecteur;
	
	private static Random generateur = new Random(999);
	private int oldIndexI; //, oldIndexJ;
	
	 /*******************************************************************************************************************/
	  /* Methodes locales */
	  /*******************************************************************************************************************/
	//�change deux valeurs du vecteur d'�tat  
	public void swap(int a, int b) {
		  int tmp;
		  tmp = vecteur[a];
		  vecteur[a]=vecteur[b];
		  vecteur[b]=tmp;
		  
	  }
	
	/*******************************************************************************************************************/
	  /* Constructeur */
	  /*******************************************************************************************************************/
	  public EtatSimple(int dimEtat) {
		  this.dimEtat = dimEtat;
		  vecteur = new int[dimEtat];
	  }

	  /*******************************************************************************************************************/
	  /* Initialisation aleatoire de l'etat */
	  /*******************************************************************************************************************/
	  public void initAleatEtat() {
		  for(int i= 0 ; i<dimEtat;i++)
			  vecteur[i]=generateur.nextInt(2);
	  }
	  
	  /*******************************************************************************************************************/
	  /* Evaluation des objectifs */
	  /*******************************************************************************************************************/
	  public double calculCritere() {
		  double cost = 0;
		  for(int i =0;i<dimEtat;i++)
			  cost +=vecteur[i];
		  return cost;
	  }
	  /*******************************************************************************************************************/
	  /* GenererVoisin */
	  /*******************************************************************************************************************/

	  public void genererVoisin() {
		  int indexI = generateur.nextInt(dimEtat); // on choisit un indice au hasard entre 0 et 999
		  vecteur[indexI]=(vecteur[indexI] + 1) % 2;// si vecteur(i) contient 0, 0+1=1%2 donne 1, si vec contient 1, 1+1=2%2 donne 0
		  oldIndexI=indexI; //on m�morise l'index pour pouvoir faire un retour arri�re le cas �ch�ant
	  }

	  
	  /*******************************************************************************************************************/
	  /* Retour a la solution pr�c�dente */
	  /*******************************************************************************************************************/
	  public void comeBack() {
		  vecteur[oldIndexI] = (vecteur[oldIndexI] + 1)%2;
	  }
	  
	  /*******************************************************************************************************************/
	  /* Affichage */
	  /*******************************************************************************************************************/

	  public String afficherEtat() {
	    return "";

	  }
	  
}
