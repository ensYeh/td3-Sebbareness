package fr.uvsq.cprog.collex;

/**
 * Commande pour quitter l'application.
 */
public class QuitterCommande implements Commande {
  private final DnsTUI tui;

  /**
   * Constructeur.
   *
   * @param tui l'interface utilisateur
   */
  public QuitterCommande(DnsTUI tui) {
    this.tui = tui;
  }

  @Override
  public void execute() {
    tui.affiche("Au revoir !");
    tui.fermer();
  }
}
