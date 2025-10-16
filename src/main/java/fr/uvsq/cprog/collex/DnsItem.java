package fr.uvsq.cprog.collex;

import java.util.Objects;

/**
 * Représente une entrée DNS (association IP - Nom de machine).
 */
public class DnsItem {
  private final AdresseIP adresseIp;
  private final NomMachine nomMachine;

  /**
   * Constructeur d'un item DNS.
   *
   * @param adresseIp l'adresse IP
   * @param nomMachine le nom de machine
   */
  public DnsItem(AdresseIP adresseIp, NomMachine nomMachine) {
    this.adresseIp = adresseIp;
    this.nomMachine = nomMachine;
  }

  public AdresseIP getAdresseIp() {
    return adresseIp;
  }

  public NomMachine getNomMachine() {
    return nomMachine;
  }

  @Override
  public String toString() {
    return adresseIp + " " + nomMachine;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DnsItem dnsItem = (DnsItem) o;
    return Objects.equals(adresseIp, dnsItem.adresseIp)
        && Objects.equals(nomMachine, dnsItem.nomMachine);
  }

  @Override
  public int hashCode() {
    return Objects.hash(adresseIp, nomMachine);
  }
}
