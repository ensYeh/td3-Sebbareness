package fr.uvsq.cprog.collex;

/**
 * Exception levée lors d'erreurs liées au DNS.
 */
public class DnsException extends Exception {
  
  /**
   * Constructeur avec message.
   *
   * @param message le message d'erreur
   */
  public DnsException(String message) {
    super(message);
  }

  /**
   * Constructeur avec message et cause.
   *
   * @param message le message d'erreur
   * @param cause la cause de l'exception
   */
  public DnsException(String message, Throwable cause) {
    super(message, cause);
  }
}
