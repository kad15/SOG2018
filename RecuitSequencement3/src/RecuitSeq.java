// sequencement
public class RecuitSeq {
	
	/* TP SOG EXO 4 Sequence d'atterrissage
	 Binome FEJRI-BELDJILALI IENAC 2018
	
	 OBJECTIF : minimiser la somme des distances de s�paration
	 sur l'ensemble du segment de la finale.
	 on la matrice S des dist de s�paration telle que Sij d�signe
	 la distance la dist de separation entre un avion de classe i
     et un avion de classe j. avec i,j dans {0,1,2} correspondant
	 aux classe heavy, medium et light
	 =>
	 On minimise donc la somme S(Ck,Ck+1) entre k = 0 et k=n-2 (n-2 car tableau java de 0 � n-1)
	 n : nombre d'avions, Ck classe de l'avion k (donc Ck = 0, 1 ou 2 et S(Ck,Ck+1)
	repr�sente la distance min entre l'avion k et l'avion k+1 ie l'avion qui le suit
	dans la sequence d'atterrissage
	
	fonction objectif traduite sous forme java : on cherche min z telle
	que somme S[   c[x[i]]   ][  c[  x[i+1]  ]     ] de 0 � n-2
	
	 ETAT : vecteur des identifiants des avions X = [xk, ...,xi,... , xn]
	 les avions sont num�rot�s de 0 � n-1  ie les xk sont dans 0, n-1
	 L'index du tableau, quant � lui, donne le rang d'atterissage
	 ainsi l'avion d'identifiant xk atterrira en premier, etc.
	 On se ram�ne donc � trouver une permutation optimale comme pour le TSP
	 mais ici les �tats admissibles sont davantage restreints par les contraintes
	 de vitesses traduite par l'horizon +/- 4 dans le changement des positions 
	 
	 L'espace d'etat du TSP a un cardinal :  |TSP| = n!
	 L'espace d'�tat du s�quencement  a un cardinal major� par n*9!
	 On a en effet pour un avion donn� 2*4 = 8 avions qui peuvent �tre permut�s
	 le cas �ch�ant avec le dit avion donc pour un total de 9. donc 9! permutations
	 sauf aux extremites du tableau. et on a n avions donc |Seq| = n*(9!) = 100*9! ~ 36 millions
	 remarque : �a reste assez faible par rapport au TSP => { 100! = 93326215443944152681699238856
	 266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758
	 251185210916864000000000000000000000000 }
	 La solution du s�quencement est peut-�tre abordable par brute force compte tenu des capacit� informatiques
	 actuelle, ceci pour avoir une solution exacte !
	 
	 Taille du voisinnage : on choisit au hasard un avion entre 0 et 99
	 puis on permute sym�triquement les 4 avions voisins par rapport � cet avion.
	 Ainsi l'�tat [..., vois+4 vois+3 vois+2 vois+1 avion_choisi vois-1 vois-2 vois-3 vois-4, ...]
	 aura pour voisin [vois-4 vois-3 vois-2 vois-1 avion_choisi vois+1 vois+2 vois+3 vois+4]
	 
	 la taille du voisinnage est donc n = 100 puisqu'on a 100 �tats accessible via le choix al�atoire
	 de l'avion_choisi entre 0 et 99 ceci compte tenu du codage effectue dans la m�thode g�n�rer voisin.
	 
	  
	 
	 
	*/
	/*******************************************************/
	/* Parametres du recuit */
	private static final int nbTransitions = 2000;
	private static final double alpha = 0.995;
	private static final boolean minimisation = true;
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
	// Heat Up
	// *************************************
	public double heatUpLoop() { // HeatUp heat = new HeatUp();
		int acceptCount = 0;
		double yi = 0, yj;
		double T = 0.01, tauxAccept = 0.0;
		EtatSeq xi = new EtatSeq(DIMENSION);

		do {
			acceptCount = 0;
			for (int i = 0; i < nbTransitions; i++) {

				// generation d'un point de l'espace d'etat
				xi.initAleatEtat();
				yi = xi.calculCritere();

				// generation d'un voisin
				xi.genererVoisin();
				yj = xi.calculCritere();

				if (accept(yi, yj, T, minimisation))
					acceptCount++;
				tauxAccept = (double) acceptCount / (double) nbTransitions;
			}
			T = T * 1.1;
			System.out.println("T= " + T + " tauxAccept= " + tauxAccept + " currentCost= " + yi);
		} while (tauxAccept < 0.8);
		return T;
	}
	// *****************************************
	// COOLING
	// *****************************************
	public EtatSeq coolingLoop(double Tinit) { // HeatUp heat = new HeatUp();
		double yi = 0.0, yj = 0.0;
		double T = Tinit;
		EtatSeq xi = new EtatSeq(DIMENSION);

		xi.initAleatEtat();
		yi = xi.calculCritere();
		do {
			for (int i = 0; i < nbTransitions; i++) {
				xi.genererVoisin();
				yj = xi.calculCritere();
				if (accept(yi, yj, T, minimisation)) {
					yi = yj;
				} else {
					xi.comeBack();
				}
			}
			T = T * alpha;
			System.out.println("T= " + T + " valeur critere " + yi);
		} while (T > 0.0001 * Tinit);
		xi.afficherEtat();
		//Data.afficherAvions();
		return xi;
	}
	// *******************************************
	// MAIN
	// *******************************************
	public static void main(String args[]) {
		double temperature;
		RecuitSeq monRecuit = new RecuitSeq();
		// generation des donnees
		System.out.println("***********************Generation des donnee  ***************");
		Data.genererAvions(DIMENSION);

		System.out.println("***************************Chauffage ***************");
		temperature = monRecuit.heatUpLoop();
		System.out.println("=======================Refroidissement =============");
		monRecuit.coolingLoop(temperature);
	}// end main
}// End class HeatUp2
