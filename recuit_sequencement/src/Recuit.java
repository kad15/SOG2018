import java.util.Random;

public class Recuit {
	private static Random generateur = new Random(123);

	/*******************************************************/
	/* Parametres du recuit */
	private static final int nbTransitions = 2000;
	private static final double alpha = 0.995;
	private static final boolean minimisation = true;
	/*******************************************************/

	/* dimension du probleme */
	private static final int DIMENSION = 100;

	// principe d'acceptation en maximisation
	private boolean accept(double yi, double yj, double temp, boolean minimiser) {
		boolean res = false;
		double proba, tirage;
		if (minimiser) {
			if (yj < yi) {
				res = true;
			} else {
				proba = Math.exp((yi - yj) / temp);
				tirage = Math.random(); // 0-1
				if (tirage < proba)
					res = true;
			}
		} else {
			if (yj > yi) {
				res = true;
			} else {
				proba = Math.exp((yj - yi) / temp);
				tirage = Math.random(); // 0-1
				if (tirage < proba)
					res = true;
			}
		}
		return res;
	}

	// **************************************
	// Heat Up
	// *************************************
	public double heatUpLoop() { // HeatUp heat = new HeatUp();
		int acceptCount = 0;
		double yi = 0, yj;
		double T = 0.01, tauxAccept = 0.0;
		Etat xi = new Etat(DIMENSION);

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

	public Etat coolingLoop(double Tinit) { // HeatUp heat = new HeatUp();
		double yi = 0.0, yj = 0.0, proba;
		double T = Tinit;
		Etat xi = new Etat(DIMENSION);

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
			System.out.println(xi.afficherEtat());

		} while (T > 0.0001 * Tinit);
		return xi;
	}

	// *******************************************
	// MAIN
	// *******************************************
	public static void main(String args[]) {
		double temperature;
		Recuit monRecuit = new Recuit();
		// generation des donnees
		System.out.println("***********************Generation des donnee  ***************");
		Data.genererAvions(DIMENSION);

		System.out.println("***************************Chauffage ***************");
		temperature = monRecuit.heatUpLoop();
		System.out.println("=======================Refroidissement =============");
		monRecuit.coolingLoop(temperature);

	}// end main
}// End class HeatUp2
