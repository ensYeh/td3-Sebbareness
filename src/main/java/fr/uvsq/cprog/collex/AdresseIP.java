package fr.uvsq.cprog.collex;

import java.util.Objects;

/**
 * Représente une adresse IP (IPv4).
 */
public class AdresseIP implements Comparable<AdresseIP> {
  private final String adresse;

  /**
   * Constructeur d'une adresse IP.
   *
   * @param adresse l'adresse IP au format String
   * @throws IllegalArgumentException si l'adresse n'est pas valide
   */
  public AdresseIP(String adresse) {
    if (!estValide(adresse)) {
      throw new IllegalArgumentException("Adresse IP invalide : " + adresse);
    }
    this.adresse = adresse;
  }

  /**
   * Vérifie si une adresse IP est valide.
   *
   * @param adresse l'adresse à vérifier
   * @return true si valide, false sinon
   */
  private boolean estValide(String adresse) {
    if (adresse == null || adresse.isEmpty()) {
      return false;
    }
    String[] parties = adresse.split("\\.");
    if (parties.length != 4) {
      return false;
    }
    for (String partie : parties) {
      try {
        int valeur = Integer.parseInt(partie);
        if (valeur < 0 || valeur > 255) {
          return false;
        }
      } catch (NumberFormatException e) {
        return false;
      }
    }
    return true;
  }

  public String getAdresse() {
    return adresse;
  }

  @Override
  public String toString() {
    return adresse;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdresseIP adresseIp = (AdresseIP) o;
    return Objects.equals(adresse, adresseIp.adresse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(adresse);
  }

  @Override
  public int compareTo(AdresseIP autre) {
    String[] parties1 = this.adresse.split("\\.");
    String[] parties2 = autre.adresse.split("\\.");
    for (int i = 0; i < 4; i++) {
      int val1 = Integer.parseInt(parties1[i]);
      int val2 = Integer.parseInt(parties2[i]);
      if (val1 != val2) {
        return Integer.compare(val1, val2);
      }
    }
    return 0;
  }
}
