package exceptions;

public class ExceptionUtilisateur extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionUtilisateur() {
		super("Utilisateur non trouvé");
	}
}
