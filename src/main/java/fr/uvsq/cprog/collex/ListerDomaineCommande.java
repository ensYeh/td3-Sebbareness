package fr.uvsq.cprog.collex;

import java.util.List;

/**
 * Commande pour lister les machines d'un domaine.
 */
public class ListerDomaineCommande implements Commande {
  private final Dns dns;
  private final DnsTUI tui;
  private final String domaine;
  private final boolean trierParIp;

  /**
   * Constructeur.
   *
   * @param dns la base de données DNS
   * @param tui l'interface utilisateur
   * @param domaine le nom de domaine
   * @param trierParIp true pour trier par IP (-a), false pour trier par nom
   */
  public ListerDomaineCommande(Dns dns, DnsTUI tui, String domaine, boolean trierParIp) {
    this.dns = dns;
    this.tui = tui;
    this.domaine = domaine;
    this.trierParIp = trierParIp;
  }

  @Override
  public void execute() throws DnsException {
    List<DnsItem> items = dns.getItems(domaine);
    tui.affiche(items, trierParIp);
  }
}
