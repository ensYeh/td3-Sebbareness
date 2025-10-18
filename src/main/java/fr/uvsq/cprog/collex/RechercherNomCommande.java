package fr.uvsq.cprog.collex;

/**
 * Commande pour rechercher le nom de machine d'une adresse IP.
 */
public class RechercherNomCommande implements Commande {
  private final Dns dns;
  private final DnsTUI tui;
  private final String adresseIp;

  /**
   * Constructeur.
   *
   * @param dns la base de données DNS
   * @param tui l'interface utilisateur
   * @param adresseIp l'adresse IP à chercher
   */
  public RechercherNomCommande(Dns dns, DnsTUI tui, String adresseIp) {
    this.dns = dns;
    this.tui = tui;
    this.adresseIp = adresseIp;
  }

  @Override
  public void execute() throws DnsException {
    try {
      AdresseIP ip = new AdresseIP(adresseIp);
      DnsItem item = dns.getItem(ip);
      
      if (item == null) {
        tui.affiche("Non trouvé");
      } else {
        tui.affiche(item.getNomMachine().toString());
      }
    } catch (IllegalArgumentException e) {
      tui.afficheErreur("Adresse IP invalide : " + adresseIp);
    }
  }
}
