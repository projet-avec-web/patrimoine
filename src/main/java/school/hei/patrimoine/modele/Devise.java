package school.hei.patrimoine.modele;

import static java.lang.Math.max;
import static java.time.LocalDate.now;
import static java.time.Month.JULY;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;

public record Devise(String nom, int valeurEnAriary, LocalDate t, double tauxDappréciationAnnuel) {
	public static final Devise MGA = new Devise("ARIARY", 1, LocalDate.MIN, 0.0);
	//non-nommée.valeurEnAriary() = 1 pour la consistence, en pratique, non-nommee ne sera jamais
	// mixée avec d'autres devises, ainsi, les opérations entre valeurComptable de même devise restent les mêmes.
	public static final Devise NON_NOMMEE = new Devise("non-nommee", 1, LocalDate.MIN, 0.0);
	public static final Devise EUR = new Devise("EURO", 4821, LocalDate.of(2024, JULY, 3), -0.1);
	public static final Devise CAD = new Devise("CAD", 3286, LocalDate.of(2024, JULY, 8), -0.1);

	/**
	 * @param t date à laquelle nous voulons connaître la valeur de la devise
	 * @return valeur appréciée en Ariary à la date t si devise différente de NON_NOMMEE, sinon 1
	 */
	public double valeurEnAriary(LocalDate t) {
		if (this.equals(NON_NOMMEE)) {
			return 1;
		}
		var joursEcoules = DAYS.between(t, now());
		double valeurAjouteeJournaliere = valeurEnAriary * (tauxDappréciationAnnuel / 365.);
		return max(0, (int) (valeurEnAriary + valeurAjouteeJournaliere * joursEcoules));
	}
}