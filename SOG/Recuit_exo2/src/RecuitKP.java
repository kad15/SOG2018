
public class RecuitKP {
	// recuit pour le problÃ¨me du sac Ã  dos
	//*************************************************************
	// réponse question knapsack
	//  la valeur V = somme des xi*vi est maximoisée
	// s.t. P = somme des xi*pi < C , C capacité du sac
	// critère pour relaxation : 
	// y = V - delta**2 avec delta = P-C
	// if(P>C) delta = P-C
	// else delta = 0
	
	/*******************************************************/
	  /* Parametres du recuit */
	private static final int nbTransitions = 2000;
	private static final double alpha = 0.995;
	private static final boolean minimisation = false;
	
	  /*******************************************************/
	  /* dimension du probleme */
	private static final int DIMENSION = 100;
	
	 // principe d'acceptation
		private boolean accept(double yi, double yj, double T, boolean min) {
			boolean isAccepted = false;
			double dE = yj-yi;
			double proba = Math.exp(-Math.abs(dE)/T);
			double tirage = Math.random();
			
			if(min) {
				if(dE<0) isAccepted = true;
				else if (tirage <= proba) isAccepted=true;//acceptation frequente si T grand car alors proba proche de 1	
			} else {// opt en maximisation
				if(dE>0) isAccepted = true;
				else if (tirage <= proba) isAccepted=true;
			}
			return isAccepted;
		}
	 // **************************************
	  // Chauffage
	  // *************************************
	  public double chauffage() { 
		  int nbAccept = 0;
		  double yi; // critere courant
		  double yj; //critere voisin
		  double T=0.01; 
		  double tauxAccept=0.0;
		  EtatKP xi = new EtatKP(DIMENSION);
		  
		  do {
			  nbAccept=0;
			  for (int i = 0; i < nbTransitions; i++) {
				  // generation d'un point de l'espace d'etat
				  xi.initAleatEtat();
				  yi = xi.calculCritere();
				  // generation d'un voisin
				  xi.genererVoisin();
				  yj = xi.calculCritere();
			       
				  if(accept(yi,yj,T,minimisation)) nbAccept++;
					  
				  tauxAccept= (double)nbAccept / (double)nbTransitions;
				 }
			  T=1.1*T;
			System.out.println("T = "+ T + " Taux acceptation "+ tauxAccept);	  
			  
		  }while (tauxAccept <0.8);
		  return T;		  
	  }//fin chauffage
	  
	// *****************************************
	  // Refroidissement
	  // *****************************************
	  public EtatKP refroidissement(double Tinit) {
		double yi = 0.0, yj = 0.0; // criteres courant yi et critere voisin mis e 0
	    double T = Tinit;
	    EtatKP xi = new EtatKP(DIMENSION);

	    xi.initAleatEtat();
	    yi = xi.calculCritere();
	    
	    do {
	    	
	    for(int i =0; i< nbTransitions;i++) {
	    	xi.genererVoisin();
	    	yj = xi.calculCritere();
	    	if(accept(yi, yj, T, minimisation)) {
	    		yi = yj;
	    	}else {
	    		xi.comeBack();
	    	}
	    }
	    T = T * alpha;
	    System.out.println("T = "+ T + " valeur critere " + yi);
	  //  System.out.println(xi.afficherEtat());
	    xi.afficherEtat();
	
	    } while (T > 0.0001 * Tinit || xi.getP()>2000 || xi.getP()<1990);
	    xi.afficherEtat();
//	    for(int i = 0; i<DIMENSION;i++)
//	    	System.out.println(i + " : "+xi.X[i]+ " ");
	    return xi;
	  }//fin refroidt
	  	  
	  // *******************************************
	  // MAIN
	  // *******************************************
	  public static void main(String args[]) {
		  double temperature;
		  RecuitKP monRecuit = new RecuitKP();
		  // generation des donnees
		    System.out.println("***********************Generation des donnees  ***************");
		    Data.genererObjets(DIMENSION);
		    
		    System.out.println("***************************Chauffage ***************");
		    temperature = monRecuit.chauffage();  // on recupere la temperature apres chauffage 
		    // i.e. T lorsque le taux d'acceptation est de 0.8
		    
		    System.out.println("=======================Refroidissement =============");
		    monRecuit.refroidissement(temperature);		    
	  }//fin main
}//fin class Recuit2
