
public enum codeErreur {
	COORDS("Veuillez entrez a nouveau les coordonn�es"),
	PAS_LIBRE(""),
	CAMP_ADV("Ce ne sont pas vos billes !"),
	HORS_TAB("Vous allez hors du plateau"),
	PAS_DE_BILLE("Il n'y a pas de bille ici !"),
	MEME_CAMP("Vous tentez d'�jecter une de vos billes !"),
	CONFIG("Vous essayez de reproduire la configuration du tour pr�c�dent ! "),
	OPPOSE("Le point de pouss�e n'est pas libre !"),
	STOP(""),
	CONTINUE(""),
	EJECT("Une bille adverse a �t� �ject�e du plateau ! Rejouer ? "),
	EJECT_ROUGE("Une bille rouge a �t� �ject�e du plateau ! Rejouer ? "),
	OK("");
	
	private String message;
	
	private codeErreur(String message)
	{
		this.message = message;
	}
		
	public String toString()
	{
		return message;
	}
}
