package fr.uvsq.cprog.collex;

import java.util.List;
import java.util.Scanner;

/**
 * Interface utilisateur textuelle pour le DNS.

 */
public class DnsTUI {
  private final Scanner scanner;

  /**
   * Constructeur.
   */
  public DnsTUI() {
    this.scanner = new Scanner(System.in);
  }

  /**
   * Lit et analyse la prochaine commande saisie par l'utilisateur.
   *
   * @return la ligne de commande saisie 
   */
  public String nextCommande() {
    System.out.print("> ");
    String ligne = scanner.nextLine().trim();
    return ligne;
  }

  /**
   * Affiche un message à l'utilisateur.
   *
   * @param message le message à afficher
   */
  public void affiche(String message) {
    System.out.println(message);
  }

  /**
   * Affiche une erreur.
   *
   * @param message le message d'erreur
   */
  public void afficheErreur(String message) {
    System.out.println("ERREUR : " + message);
  }

  /**
   * Affiche un DnsItem.
   *
   * @param item l'item à afficher
   */
  public void affiche(DnsItem item) {
    if (item == null) {
      affiche("Non trouvé");
    } else {
      affiche(item.toString());
    }
  }

  /**
   * Affiche une liste de DnsItems (pour la commande ls).
   *
   * @param items la liste des items
   * @param trierParIp true pour trier par IP, false pour trier par nom
   */
  public void affiche(List<DnsItem> items, boolean trierParIp) {
    if (items.isEmpty()) {
      affiche("Aucune machine trouvée");
      return;
    }

    // Trier la liste
    if (trierParIp) {
      items.sort((item1, item2) -> 
          item1.getAdresseIp().compareTo(item2.getAdresseIp()));
    } else {
      items.sort((item1, item2) -> 
          item1.getNomMachine().compareTo(item2.getNomMachine()));
    }

    // Afficher chaque item
    for (DnsItem item : items) {
      affiche(item.toString());
    }
  }

  /**
   * Parse une ligne de commande et retourne l'objet Commande correspondant.
   *
   * @param ligne la ligne de commande
   * @param dns la base de données DNS
   * @return la commande à exécuter, ou null si commande invalide
   */
  public Commande parseCommande(String ligne, Dns dns) {
    if (ligne == null || ligne.isEmpty()) {
      return null;
    }

    String[] parties = ligne.split("\\s+");

    // Commande quit ou exit
    if (parties[0].equalsIgnoreCase("quit") || parties[0].equalsIgnoreCase("exit")) {
      return new QuitterCommande(this);
    }

    // Commande ls
    if (parties[0].equalsIgnoreCase("ls")) {
      if (parties.length < 2) {
        afficheErreur("Usage: ls [-a] domaine");
        return null;
      }
      
      boolean trierParIp = false;
      String domaine;
      
      if (parties[1].equals("-a")) {
        trierParIp = true;
        if (parties.length < 3) {
          afficheErreur("Usage: ls -a domaine");
          return null;
        }
        domaine = parties[2];
      } else {
        domaine = parties[1];
      }
      
      return new ListerDomaineCommande(dns, this, domaine, trierParIp);
    }

    // Commande add
    if (parties[0].equalsIgnoreCase("add")) {
      if (parties.length != 3) {
        afficheErreur("Usage: add adresse.ip nom.machine");
        return null;
      }
      return new AjouterCommande(dns, this, parties[1], parties[2]);
    }

    // Recherche par IP (format: xxx.xxx.xxx.xxx)
    if (parties[0].matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
      return new RechercherNomCommande(dns, this, parties[0]);
    }

    // Recherche par nom (contient au moins un point)
    if (parties[0].contains(".")) {
      return new RechercherIpCommande(dns, this, parties[0]);
    }

    afficheErreur("Commande non reconnue : " + ligne);
    return null;
  }

  /**
   * Ferme le scanner.
   */
  public void fermer() {
    scanner.close();
  }
}
