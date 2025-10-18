package fr.uvsq.cprog.collex;

/**
 * Commande pour ajouter une entrée dans la base DNS.
 */
public class AjouterCommande implements Commande {
  private final Dns dns;
  private final DnsTUI tui;
  private final String adresseIp;
  private final String nomMachine;

  /**
   * Constructeur.
   *
   * @param dns la base de données DNS
   * @param tui l'interface utilisateur
   * @param adresseIp l'adresse IP à ajouter
   * @param nomMachine le nom de machine à ajouter
   */
  public AjouterCommande(Dns dns, DnsTUI tui, String adresseIp, String nomMachine) {
    this.dns = dns;
    this.tui = tui;
    this.adresseIp = adresseIp;
    this.nomMachine = nomMachine;
  }

  @Override
  public void execute() throws DnsException {
    try {
      AdresseIP ip = new AdresseIP(adresseIp);
      NomMachine nom = new NomMachine(nomMachine);
      
      dns.addItem(ip, nom);
      tui.affiche("Entrée ajoutée avec succès");
    } catch (IllegalArgumentException e) {
      tui.afficheErreur("Format invalide : " + e.getMessage());
    }
  }
}
