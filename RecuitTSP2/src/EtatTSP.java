import java.util.*;
// TSP
public class EtatTSP {
    public int dimEtat;
    public int[] vecteur;

    private static Random generateur = new Random(123);
    private int oldIndexI; 
    private int oldIndexJ; 
    private static final int methode =2;
    /*******************************************************************************************************************/
    /*                                Methodes locales                                                                 */
    /*******************************************************************************************************************/
    public void exchange(int I, int J){
    	int buffer;
    	buffer = vecteur[I];
    	vecteur[I] = vecteur[J];
    	vecteur[J] = buffer;	
    }
    /*******************************************************************************************************************/
    /*                                Constructeur                                                                     */
    /*******************************************************************************************************************/
    public EtatTSP(int dimEtat) 
    {
		this.dimEtat=dimEtat;
		vecteur = new int[dimEtat];
    }
    /*******************************************************************************************************************/
    /*                           Initialisation aleatoire de l'etat                                                    */
    /*******************************************************************************************************************/
    public void initAleatEtat()
    {
    	for (int i=0; i<dimEtat; ++i){
    		vecteur[i] = i;
    	}
    }   
    /*******************************************************************************************************************/
    /*                                Affichage                                                                        */
    /*******************************************************************************************************************/
    public String afficherEtat()
    {
    	return ""; 
    }
    /*******************************************************************************************************************/
    /*                               GenererVoisin                                                                         */
    /*******************************************************************************************************************/
    public void genererVoisin()
    {
    	int indexI = generateur.nextInt(dimEtat);
    	int indexJ = generateur.nextInt(dimEtat);
    	
    	if(methode ==1)
    	    exchange(indexI, indexJ); //***1er methode pour changer la position aleatoirement
    	if (methode ==2){
	    	for (int i=0; i<Math.ceil(Math.abs(indexI-indexJ)/2); ++i){
	    		exchange(Math.min(indexI, indexJ)+i, Math.max(indexI, indexJ)-i);
	    		//System.out.println(i+ " " + (Math.min(indexI, indexJ)+i) + " " + (Math.max(indexI, indexJ)-i) );
	    	} 
    	}
    	oldIndexI = indexI;
    	oldIndexJ = indexJ;
    }
   /*******************************************************************************************************************/
    /*                               Retour a la solution précédente                                                                       */
    /*******************************************************************************************************************/
    public void comeBack()
    {
    	if(methode ==1) exchange(oldIndexI, oldIndexJ); //**1er methode pour changer la position aleatoirement
    	if (methode ==2){
	    	for (int i=0; i<Math.ceil(Math.abs(oldIndexI-oldIndexJ)/2); ++i){
	    		exchange(Math.min(oldIndexI, oldIndexJ)+i, Math.max(oldIndexI, oldIndexJ)-i);
	    	}
    	}
    }
    /*******************************************************************************************************************/
    /*                                Evaluation des objectifs                                                         */
    /*******************************************************************************************************************/
    public double calculCritere(){
    	double cost=0;
    	double dx,dy;	
    	for (int i=0; i<dimEtat-1; ++i){
    		dx = Data.tabVilles[vecteur[i+1]][0]-Data.tabVilles[vecteur[i]][0];
    	    dy = Data.tabVilles[vecteur[i+1]][1]-Data.tabVilles[vecteur[i]][1];
    		cost += Math.sqrt(dx*dx+dy*dy);
    	}
    	dx = Data.tabVilles[vecteur[dimEtat-1]][0]-Data.tabVilles[vecteur[0]][0];
    	dy = Data.tabVilles[vecteur[dimEtat-1]][1]-Data.tabVilles[vecteur[0]][1];
    	cost += Math.sqrt(dx*dx+dy*dy);
    	return cost;
    }
}//fin EtatTSP


