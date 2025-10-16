package fr.uvsq.cprog.collex;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Gère la base de données DNS.
 */
public class Dns {
  private final Map<AdresseIP, DnsItem> itemsParIp;
  private final Map<NomMachine, DnsItem> itemsParNom;
  private final Path fichierDatabase;

  /**
   * Constructeur qui charge la base de données depuis le fichier.
   *
   * @throws DnsException si erreur lors du chargement
   */
  public Dns() throws DnsException {
    this.itemsParIp = new HashMap<>();
    this.itemsParNom = new HashMap<>();
    
    // Charger le nom du fichier depuis config.properties
    Properties props = new Properties();
    try (InputStream input = getClass().getClassLoader()
        .getResourceAsStream("config.properties")) {
      if (input == null) {
        throw new DnsException("Fichier config.properties introuvable");
      }
      props.load(input);
    } catch (IOException e) {
      throw new DnsException("Erreur lors du chargement de config.properties", e);
    }

    String nomFichier = props.getProperty("dns.database.file");
    if (nomFichier == null) {
      throw new DnsException("Propriété dns.database.file non trouvée");
    }

    this.fichierDatabase = Paths.get(nomFichier);
    chargerDatabase();
  }

  /**
   * Charge les données depuis le fichier.
   *
   * @throws DnsException si erreur lors du chargement
   */
  private void chargerDatabase() throws DnsException {
    if (!Files.exists(fichierDatabase)) {
      // Créer un fichier vide si inexistant
      try {
        Files.createFile(fichierDatabase);
      } catch (IOException e) {
        throw new DnsException("Impossible de créer le fichier de base de données", e);
      }
      return;
    }

    try {
      List<String> lignes = Files.readAllLines(fichierDatabase);
      for (String ligne : lignes) {
        ligne = ligne.trim();
        if (ligne.isEmpty() || ligne.startsWith("#")) {
          continue;
        }

        String[] parties = ligne.split("\\s+");
        if (parties.length != 2) {
          throw new DnsException("Format de ligne invalide : " + ligne);
        }

        NomMachine nom = new NomMachine(parties[0]);
        AdresseIP ip = new AdresseIP(parties[1]);
        DnsItem item = new DnsItem(ip, nom);

        itemsParIp.put(ip, item);
        itemsParNom.put(nom, item);
      }
    } catch (IOException e) {
      throw new DnsException("Erreur lors de la lecture du fichier", e);
    }
  }

  /**
   * Sauvegarde la base de données dans le fichier.
   *
   * @throws DnsException si erreur lors de la sauvegarde
   */
  private void sauvegarderDatabase() throws DnsException {
    List<String> lignes = itemsParNom.values().stream()
        .map(item -> item.getNomMachine() + " " + item.getAdresseIp())
        .collect(Collectors.toList());

    try {
      Files.write(fichierDatabase, lignes, StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      throw new DnsException("Erreur lors de la sauvegarde du fichier", e);
    }
  }

  /**
   * Recherche un item par son adresse IP.
   *
   * @param ip l'adresse IP à rechercher
   * @return le DnsItem correspondant, ou null si non trouvé
   */
  public DnsItem getItem(AdresseIP ip) {
    return itemsParIp.get(ip);
  }

  /**
   * Recherche un item par son nom de machine.
   *
   * @param nom le nom de machine à rechercher
   * @return le DnsItem correspondant, ou null si non trouvé
   */
  public DnsItem getItem(NomMachine nom) {
    return itemsParNom.get(nom);
  }

  /**
   * Retourne tous les items d'un domaine donné.
   *
   * @param domaine le nom de domaine
   * @return la liste des items du domaine
   */
  public List<DnsItem> getItems(String domaine) {
    List<DnsItem> resultat = new ArrayList<>();
    for (DnsItem item : itemsParNom.values()) {
      if (item.getNomMachine().getNomDomaine().equals(domaine)) {
        resultat.add(item);
      }
    }
    return resultat;
  }

  /**
   * Ajoute un nouvel item dans la base de données.
   *
   * @param ip l'adresse IP
   * @param nom le nom de machine
   * @throws DnsException si l'IP ou le nom existe déjà
   */
  public void addItem(AdresseIP ip, NomMachine nom) throws DnsException {
    if (itemsParIp.containsKey(ip)) {
      throw new DnsException("L'adresse IP existe déjà !");
    }
    if (itemsParNom.containsKey(nom)) {
      throw new DnsException("Le nom de machine existe déjà !");
    }

    DnsItem item = new DnsItem(ip, nom);
    itemsParIp.put(ip, item);
    itemsParNom.put(nom, item);

    // Sauvegarder immédiatement dans le fichier
    sauvegarderDatabase();
  }
}
