package qualtrics;

import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * A class of utility methods for the Qualtrics project.
 */
public final class Utils {

  /**
   * Threshold variable for fuzzyEquals method.
   */
  private static final int FUZZY_THRESHHOLD = 3;

  private Utils() {
    // Empty constructor for utility class
  }

  /**
   * Fuzzy compares two strings.
   *
   * @param a the first string to compare
   * @param b the second string to compare
   * @return whether strings have a dist of less than FUZZY_THRESHHOLD
   */
  public static boolean fuzzyEquals(final String a, final String b) {
    if (a == null || b == null) {
      return false;
    }
    String asLower = a.toLowerCase();
    String bsLower = b.toLowerCase();
    LevenshteinDistance levInstance = new LevenshteinDistance();
    int dist = levInstance.apply(asLower, bsLower);
    return dist <= FUZZY_THRESHHOLD;
  }

}
