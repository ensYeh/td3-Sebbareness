package fr.uvsq.cprog.collex;

import java.util.Objects;

/**
 * Représente un nom qualifié de machine.
 */
public class NomMachine implements Comparable<NomMachine> {
  private final String nomComplet;

  /**
   * Constructeur d'un nom de machine.
   *
   * @param nomComplet le nom qualifié complet
   * @throws IllegalArgumentException si le nom n'est pas valide
   */
  public NomMachine(String nomComplet) {
    if (nomComplet == null || nomComplet.isEmpty() || !nomComplet.contains(".")) {
      throw new IllegalArgumentException("Nom de machine invalide : " + nomComplet);
    }
    this.nomComplet = nomComplet;
  }

  /**
   * Retourne le nom de la machine (avant le premier point).
   *
   * @return le nom de la machine
   */
  public String getNomMachine() {
    return nomComplet.substring(0, nomComplet.indexOf('.'));
  }

  /**
   * Retourne le nom de domaine (après le premier point).
   *
   * @return le nom de domaine
   */
  public String getNomDomaine() {
    return nomComplet.substring(nomComplet.indexOf('.') + 1);
  }

  public String getNomComplet() {
    return nomComplet;
  }

  @Override
  public String toString() {
    return nomComplet;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NomMachine that = (NomMachine) o;
    return Objects.equals(nomComplet, that.nomComplet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nomComplet);
  }

  @Override
  public int compareTo(NomMachine autre) {
    return this.nomComplet.compareTo(autre.nomComplet);
  }
}
