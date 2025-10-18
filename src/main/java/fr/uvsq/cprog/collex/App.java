package fr.uvsq.cprog.collex;

/**
 * Application principale DNS.
 */
public class App {
  private final Dns dns;
  private final DnsTUI tui;
  private boolean continuer;

  /**
   * Constructeur.
   *
   * @throws DnsException si erreur lors de l'initialisation
   */
  public App() throws DnsException {
    this.dns = new Dns();
    this.tui = new DnsTUI();
    this.continuer = true;
  }

  /**
   * Lance l'application.
   */
  public void run() {
    tui.affiche("Bienvenue dans le DNS !");
    tui.affiche("Tapez 'quit' pour quitter");
    tui.affiche("");

    while (continuer) {
      try {
        String ligne = tui.nextCommande();
        
        if (ligne.isEmpty()) {
          continue;
        }

        Commande cmd = tui.parseCommande(ligne, dns);
        
        if (cmd != null) {
          // Vérifier si c'est la commande quitter
          if (cmd instanceof QuitterCommande) {
            continuer = false;
          }
          
          cmd.execute();
        }
        
      } catch (DnsException e) {
        tui.afficheErreur(e.getMessage());
      } catch (Exception e) {
        tui.afficheErreur("Erreur inattendue : " + e.getMessage());
      }
    }
  }

  /**
   * Point d'entrée de l'application.
   *
   * @param args arguments de ligne de commande (non utilisés)
   */
  public static void main(String[] args) {
    try {
      App app = new App();
      app.run();
    } catch (DnsException e) {
      System.err.println("ERREUR FATALE : " + e.getMessage());
      System.exit(1);
    }
  }
}
