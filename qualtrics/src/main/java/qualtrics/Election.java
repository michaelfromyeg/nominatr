package qualtrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    for (Response r : responses) {
      if (r.getNomineeOrNominator().toCharArray()[0] == '1') {
        String position = positions.get(Integer.parseInt(r.getNomineeRunningFor()));
        App.getLogger().info(r.getNomineeFullName() + " is running for " + position);
        Nominee n = new Nominee(r.getNomineeFullName().trim(), r.getNomineeEmail().trim(),
            r.getNomineeStudentNumber().trim(), r.getNomineeMajor().trim(),
            r.getNomineeRunningFor().trim());
        n.setRunningForPositionName(position);
        nominees.add(n);
      }
      if (r.getNomineeOrNominator().toCharArray()[0] == '2') {
        App.getLogger().info(r.getNominatorFullName() + " nominated "
            + r.getNominatorNominatingName()
            + " for the position of "
            + positions.get(Integer.parseInt(r.getNominatorNominatingPosition())));
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

  /**
   * Compute the total number of nominators for each candidate.
   */
  public final void totalNominations() {
    for (Nominator nom : nominators) {
      boolean nominationValid = false;
      for (Nominee nee : nominees) {
        App.getLogger().info("Comparing: "
            + nom.getNominatingName()
            + " " + nee.getFullName());
        if (nom.getNominatingName().equals(nee.getFullName())) {
          if (nominationValid) {
            break;
          }
          nee.incrementTally();
          nee.addNominator(nom);
          nominationValid = true;
          break;
        }
      }
      // If no nominee was found, try a fuzzy match instead
      if (!nominationValid) {
        App.getLogger().info("No nomination found... trying again!");
        for (Nominee nee : nominees) {
          if (Utils.fuzzyEquals(nom.getNominatingName(), nee.getFullName())) {
            if (nominationValid) {
              break;
            }
            App.getLogger().info("Found a fuzzy match for "
                + nom.getNominatingName()
                + " " + nee.getFullName());
            nee.incrementTally();
            nee.addNominator(nom);
            nom.setNominatingName(nee.getFullName()
                + " (corrected from " + nom.getNominatingName() + ")");
            nominationValid = true;
            break;
          }
        }
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
