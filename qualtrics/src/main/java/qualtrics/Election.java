package qualtrics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The election object, for processing election data.
 */
public class Election {

  /**
   * List of nominees.
   */
  private List<Nominee> nominees;


  /**
   * List of nominators.
   */
  private List<Nominator> nominators;

  /**
   * Key-value pairs representing qualtrics ID-positon name.
   */
  private Map<Integer, String> positions;


  /**
   * Comma-separated list of names of people who cannot be nominators.
   */
  private String blacklist;

  /**
   * Array of names of people who cannot be nominators.
   */
  private String[] blacklistNames;

  /**
   * Last response processed, so we don't read in duplicates.
   */
  private int responsesProcessed;

  /**
   * Create a new election object.
   */
  public Election() {
    nominees = new ArrayList<Nominee>();
    nominators = new ArrayList<Nominator>();
    positions = new HashMap<Integer, String>();
    blacklist = App.getDotenv().get("NOMIANTOR_BLACKLIST");
    blacklistNames = blacklist.split(",");
  }

  /**
   * Build positions map.
   *
   * @param toAdd list of positions to add to map
   */
  public final void buildPositions(final String[] toAdd) {
    for (int i = 0; i < toAdd.length; i++) {
      positions.put(i, toAdd[i]);
    }
  }

  /**
   * Process nominations; add to nominees, nominators lists.
   *
   * @param responses list of responses from the survey data
   */
  public final void processNominations(final List<Response> responses) {

    // Read in old serialized data
    try {
      FileInputStream fis = new FileInputStream(
          App.getFilePath() + "/nominator-data.ser");
      ObjectInputStream ois
          = new ObjectInputStream(fis);
      @SuppressWarnings("unchecked")
      List<Nominator> deserializedNominators  = (List<Nominator>) ois.readObject();
      nominators = deserializedNominators;
      fis.close();
      ois.close();
    } catch (IOException | ClassNotFoundException e) {
      // e.printStackTrace();
    }

    try {
      FileInputStream fis2 = new FileInputStream(
          App.getFilePath() + "/nominee-data.ser");
      ObjectInputStream ois2
          = new ObjectInputStream(fis2);
      @SuppressWarnings("unchecked")
      List<Nominee> deserializedNominees  = (List<Nominee>) ois2.readObject();
      nominees = deserializedNominees;
      fis2.close();
      ois2.close();
    } catch (IOException | ClassNotFoundException e) {
      // e.printStackTrace();
    }

    try {
      FileInputStream fis3 = new FileInputStream(
          App.getFilePath() + "/responses.ser");
      ObjectInputStream ois3
          = new ObjectInputStream(fis3);
      int deserializedResponseCount  = (Integer) ois3.readObject();
      responsesProcessed = deserializedResponseCount;
      fis3.close();
      ois3.close();
    } catch (IOException | ClassNotFoundException e) {
      // e.printStackTrace();
    }

    for (int i = responsesProcessed; i < responses.size(); i++) {
      // System.out.println("Processing response " + i);
      Response r = responses.get(i);
      if ((r.getNominatorFullName().equals("")
            && r.getNomineeFullName().equals(""))
            || (r.getNominatorNominatingPosition().equals("")
            && r.getNomineeRunningFor().equals(""))) {
        continue;
      }
      if (r.getNomineeOrNominator().toCharArray()[0] == '1') {
        // Response is probably blank, continue
        if (r.getNomineeRunningFor().equals("")) {
          App.getLogger().warning(r.getNomineeFullName()
              + "did not enter a position they are running for.");
          continue;
        } else {
          String position = positions.get(Integer.parseInt(r.getNomineeRunningFor()));
          // App.getLogger().info(r.getNomineeFullName() + " is running for " + position);
          Nominee n = new Nominee(r.getNomineeFullName().trim(), r.getNomineeEmail().trim(),
              r.getNomineeStudentNumber().trim(), r.getNomineeMajor().trim(),
              r.getNomineeRunningFor().trim());
          n.setRunningForPositionName(position);
          // if they filled out the form twice
          boolean shouldAdd = true;
          for (Nominee nee : nominees) {
            if (nee.getFullName() != null && nee.getFullName().equals(n.getFullName())) {
              shouldAdd = false;
              break;
            }
          }
          if (shouldAdd && nominees.contains(n)) {
            shouldAdd = false;
          }
          if (shouldAdd) {
            nominees.add(n);
          }
        }
      }
      if (r.getNomineeOrNominator().toCharArray()[0] == '2') {
        if (r.getNominatorNominatingPosition().equals("")) {
          // Response is probably blank, continue
          App.getLogger().warning(r.getNominatorFullName()
              + "did not enter a position they are running for.");
          continue;
        } else {
          // App.getLogger().info(r.getNominatorFullName() + " nominated "
          //     + r.getNominatorNominatingName()
          //     + " for the position of "
          //     + positions.get(Integer.parseInt(r.getNominatorNominatingPosition())));
          if (Arrays.asList(blacklistNames).contains(r.getNominatorFullName().trim())) {
            App.getLogger().warning(
                "A blacklisted individual named tried to nominate someone: "
                + r.getNominatorFullName()
                + ". They were probably a SUS Candidate last year.");
            continue;
          } else {
            nominators.add(new Nominator(r.getNominatorFullName().trim(),
                r.getNominatorEmail().trim(),
                r.getNominatorStudentNumber().trim(),
                r.getNominatorMajor().trim(),
                r.getNominatorNominatingName().trim(),
                r.getNominatorNominatingPosition().trim()));
          }
        }
      }
    }
    responsesProcessed = responses.size();
  }

  // TODO: write function to verify one nominator
  // didn't nominate two or more nominees for the same position

  /**
   * Compute the total number of nominators for each candidate.
   */
  public final void totalNominations() {
    for (Nominator nom : nominators) {
      boolean nominationValid = false;
      if (nom.getIsTallied()) {
        nominationValid = true;
        continue;
      }
      for (Nominee nee : nominees) {
        // App.getLogger().info("Comparing: "
        //     + nom.getNominatingName()
        //     + " " + nee.getFullName());
        if (nom.getNominatingName() == null) {
          continue;
        }
        if (nom.getNominatingName().equals(nee.getFullName())) {
          if (nominationValid) {
            break;
          }
          nee.incrementTally();
          nee.addNominator(nom);
          nom.setIsTallied(true);
          nominationValid = true;
          break;
        }
      }
      // If no nominee was found, try a fuzzy match instead
      if (!nominationValid) {
        // App.getLogger().info("No nomination found... trying again!");
        for (Nominee nee : nominees) {
          if (Utils.fuzzyEquals(nom.getNominatingName(), nee.getFullName())) {
            if (nominationValid) {
              break;
            }
            // App.getLogger().info("Found a fuzzy match for "
            //     + nom.getNominatingName()
            //     + " " + nee.getFullName());
            nee.incrementTally();
            nee.addNominator(nom);
            nom.setIsTallied(true);
            nom.setNominatingName(nee.getFullName()
                + " (corrected from " + nom.getNominatingName() + ")");
            nominationValid = true;
            break;
          }
        }
      }
      // Try first name
      if (!nominationValid) {
        for (Nominee nee : nominees) {
          if (nom.getNominatingNameFirstName().equals(nee.getFirstName())) {
            if (nominationValid) {
              break;
            }
            nee.incrementTally();
            nee.addNominator(nom);
            nom.setIsTallied(true);
            nom.setNominatingName(nee.getFullName()
                + " (assumed full name from first name " + nom.getNominatingNameFirstName() + ")");
            nominationValid = true;
            break;
          } else if (nom.getNominatingNameLastName().equals(nee.getLastName())) {
            if (nominationValid) {
              break;
            }
            nee.incrementTally();
            nee.addNominator(nom);
            nom.setIsTallied(true);
            nom.setNominatingName(nee.getFullName()
                + " (assumed full name from last name " + nom.getNominatingNameLastName() + ")");
            nominationValid = true;
            break;
          }
        }
      }
      if (!nominationValid) {
        if (nom.getNominatingName() == null) {
          App.getLogger().warning(nom.getFullName() + " did not nominate anyone. "
              + "They tried to nominate " + nom.getNominatingNameFirstName()
              + " / " + nom.getNominatingNameLastName());
        } else {
          App.getLogger().warning(nom.getFullName() + " did not nominate anyone. "
              + "They tried to nominate " + nom.getNominatingName());
        }
      }
    }
  }

  /**
   * Create serialized object of nominee data.
   */
  public void saveNominations() {
    try {
      FileOutputStream fos
          = new FileOutputStream(App.getFilePath() + "/nominee-data.ser", false);
      ObjectOutputStream oos
          = new ObjectOutputStream(fos);
      oos.writeObject(nominees);
      oos.close();
      fos.close();
      // System.out.println("Nominees serialized!");
      FileOutputStream fos2
          = new FileOutputStream(App.getFilePath() + "/nominator-data.ser", false);
      ObjectOutputStream oos2
          = new ObjectOutputStream(fos2);
      oos2.writeObject(nominators);
      oos2.close();
      FileOutputStream fos3
          = new FileOutputStream(App.getFilePath() + "/responses.ser", false);
      ObjectOutputStream oos3
          = new ObjectOutputStream(fos3);
      oos3.writeObject(responsesProcessed);
      oos3.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * Export the nominees, by position, to a CSV file.
   */
  public void exportNominees(boolean shouldPrint) {
    Map<String, List<Nominee>> nomineesMap = new HashMap<>();
    for (Entry<Integer, String> position : positions.entrySet()) {
      Integer positionIndex = position.getKey();
      if (positionIndex == 0) { continue; }
      String positionName = position.getValue();
      List<Nominee> nomineesForPosition = new ArrayList<>();
      for (Nominee n : nominees) {
        if (Integer.parseInt(n.getRunningFor()) == positionIndex) {
          nomineesForPosition.add(n);
        }
      }
      nomineesMap.put(positionName, nomineesForPosition);
    }

    try (PrintWriter pw = new PrintWriter(new File(App.getFilePath() + "/positions.csv"))) {
      StringBuilder sb = new StringBuilder();
      String header = "Position,Names";
      sb.append(header + "\n");
      for (Entry<String, List<Nominee>> entry : nomineesMap.entrySet()) {
        List<String> names = new ArrayList<>();
        for (Nominee n : entry.getValue()) {
          if (n.getFullName() == null || n.getTally() < App.getNominationsRequired()) {
            continue;
          } else {
            names.add(n.getFullName());
          }
        }
        if (shouldPrint) {
          App.getLogger().info(entry.getKey() + ": " + String.join("; ", names));
        }
        String formattedPosition = entry.getKey().replace("Vice President,", "VP");
        sb.append(formattedPosition + "," + String.join("; ", names) + "\n");
      }
      App.getLogger().info(sb.toString());
      pw.print(sb.toString());
      pw.close();
      System.out.println("Done!");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {

    }
  }

  /**
   * Find a time for all candidates to meet, via a Doodle poll.
   */
  public void scheduleMeeting() {
    System.out.println("Not implemented yet!");
  }

  /**
   * Find a time for each position to meet, via a Doodle poll.
   */
  public void scheduleMeetingByPosition() {
    System.out.println("Not implemented yet!");
  }

  /**
   * Print all nominees and nominators participating in the election.
   */
  public void printResults(boolean printNominators, boolean printNominees) {
    if (printNominators) {
      for (Nominator n : nominators) {
        System.out.println(n.toString());
      }
    }
    if (printNominees) {
      for (Nominee n : nominees) {
        System.out.println(n.toString());
      }
    }
  }

  public final void addNominee(final Nominee n) {
    nominees.add(n);
  }

  public final void addNominator(final Nominator n) {
    nominators.add(n);
  }

  public final List<Nominee> getNominees() {
    return nominees;
  }

  public final List<Nominator> getNominators() {
    return nominators;
  }
}
