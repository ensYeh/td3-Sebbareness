package fr.uvsq.cprog.collex;

/**
 * Interface pour le pattern Command.
 * Chaque commande implémente cette interface.
 */
public interface Commande {
  
  /**
   * Exécute la commande.
   *
   * @throws DnsException si erreur lors de l'exécution
   */
  void execute() throws DnsException;
}
