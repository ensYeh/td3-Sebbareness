package fr.uvsq.cprog.collex;

/**
 * Commande pour rechercher l'adresse IP d'un nom de machine.
 */
public class RechercherIpCommande implements Commande {
  private final Dns dns;
  private final DnsTUI tui;
  private final String nomMachine;

  /**
   * Constructeur.
   *
   * @param dns la base de données DNS
   * @param tui l'interface utilisateur
   * @param nomMachine le nom de la machine à chercher
   */
  public RechercherIpCommande(Dns dns, DnsTUI tui, String nomMachine) {
    this.dns = dns;
    this.tui = tui;
    this.nomMachine = nomMachine;
  }

  @Override
  public void execute() throws DnsException {
    try {
      NomMachine nom = new NomMachine(nomMachine);
      DnsItem item = dns.getItem(nom);
      
      if (item == null) {
        tui.affiche("Non trouvé");
      } else {
        tui.affiche(item.getAdresseIp().toString());
      }
    } catch (IllegalArgumentException e) {
      tui.afficheErreur("Nom de machine invalide : " + nomMachine);
    }
  }
}
