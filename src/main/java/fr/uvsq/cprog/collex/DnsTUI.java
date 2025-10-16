package fr.uvsq.cprog.collex;

import java.util.List;
import java.util.Scanner;

/**
 * Interface utilisateur textuelle pour le DNS.
 * TUI = Text User Interface
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
   * @return la ligne de commande saisie (sera transformée en Commande en Q4)
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
   * Ferme le scanner.
   */
  public void fermer() {
    scanner.close();
  }
}
