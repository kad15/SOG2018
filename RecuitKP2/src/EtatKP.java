import java.util.Random;
// recuit pour le problème du sac à dos
public class EtatKP {
	
	public int dimEtat;
	public int[] X;
	
	private static Random generateur = new Random(999);
	private int oldIndexI;//, oldIndexJ;
	//private double oldPoids;
	private double capacite = 2000;
	private double P, V;
	
	 /*******************************************************************************************************************/
	  /* Methodes locales */
	  /*******************************************************************************************************************/
	//echange deux valeurs du vecteur d'etat X  
	public void swap(int a, int b) {
		  int tmp;
		  tmp = X[a];
		  X[a]=X[b];
		  X[b]=tmp;	  
	  }
	
	public double getP()
	{ return P;}	
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
		  for(int i= 0 ; i<dimEtat;i++)
			  X[i]=generateur.nextInt(2);
	  }
	  /*******************************************************************************************************************/
	  /* Evaluation des objectifs */
	  /*******************************************************************************************************************/
	  public double calculCritere() {
		  double valeur = 0;
		  double poids = 0;
		  
		  for (int i=0; i<dimEtat; ++i){
	    		valeur += X[i] * Data.tabValeurs[i];
	    		poids  += X[i] * Data.tabPoids[i];
	    	}
		  V = valeur;
		  P = poids;
		  double delta = poids - capacite;
		  double epsilon = delta / capacite;
	    	if (delta <= 0){
	    		return valeur;
	    	}
	    	else{
	    		//return valeur/(1+epsilon*epsilon);
	    		return valeur -10*delta;
	    		//return 0;
	    	}
	  }
	  /*******************************************************************************************************************/
	  /* GenererVoisin */
	  /*******************************************************************************************************************/
	  public void genererVoisin() {
		  int indexI = generateur.nextInt(dimEtat); // on choisit un indice au hasard entre 0 et dimEtat-1
		  X[indexI]=(X[indexI] + 1) % 2;// si X(i)= 0, 0+1=1%2 donne 1, si X[i]= 1, 1+1=2%2 donne 0
		  oldIndexI=indexI; //on memorise l'index pour pouvoir faire un retour arriere le cas echeant
	  }  
	  /*******************************************************************************************************************/
	  /* Retour a la solution precedente */
	  /*******************************************************************************************************************/
	  public void comeBack() {
		  X[oldIndexI] = (X[oldIndexI] + 1) % 2;
	  }  
	  /*******************************************************************************************************************/
	  /* Affichage */
	  /*******************************************************************************************************************/
	  public void afficherEtat() {
		  System.out.println("poids sac = " + P +" valeur sac =  " + V);
	  }  
}
