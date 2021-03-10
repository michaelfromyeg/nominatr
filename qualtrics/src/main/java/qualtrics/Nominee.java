package qualtrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An object representing someone who is running for a position.
 */
public class Nominee implements Serializable {

  /**
   * serialVersionUID for nominee.
   */
  private static final long serialVersionUID = -2372727601561399983L;
  private String fullName;
  private String firstName;
  private String lastName;
  private String email;
  private String studentNumber;
  private String major;
  private String runningFor;
  private int nominationTally;
  private String runningForPositionName;
  private List<Nominator> nominators;
  private boolean shouldEmail;

  /**
   * An all-field constructor for a nominee.
   *
   * @param fullName their name
   * @param email their email
   * @param studentNumber their student number
   * @param major their major
   * @param runningFor what they're running for
   */
  public Nominee(String fullName,
      String email,
      String studentNumber,
      String major,
      String runningFor) {
    // Scrub middle names
    String[] names = fullName.split(" ");
    if (names.length == 3) {
      this.fullName = names[0] + " " + names[2];
      this.firstName = names[0];
      this.lastName = names[2];
    } else if (names.length == 2) {
      this.fullName = names[0] + " " + names[1];
      this.firstName = names[0];
      this.lastName = names[1];
    } else if (names.length == 1) {
      this.fullName = null;
      this.firstName = fullName;
    } else {
      App.getLogger().warning("Tried to create a nominee with more than 3 names, "
          + "or 0 names: " + fullName);
    }
    this.email = email;
    this.studentNumber = studentNumber;
    this.major = major;
    this.runningFor = runningFor;
    this.nominationTally = 0;
    this.nominators = new ArrayList<>();
    this.shouldEmail = true;
  }

  public void incrementTally() {
    this.nominationTally += 1;
  }

  public boolean getShouldEmail() {
    return this.shouldEmail;
  }

  public void setShouldEmail(boolean shouldEmail) {
    this.shouldEmail = shouldEmail;
  }

  /**
   * Get a nominator by their name.
   *
   * @param name their name
   * @return a Nominator object
   */
  public Nominator getNominatorByName(String name) {
    for (Nominator n : this.nominators) {
      if (name.equals(n.getFullName())) {
        return n;
      }
    }
    return null;
  }

  public List<Nominator> getNominators() {
    return this.nominators;
  }

  public void addNominator(Nominator n) {
    this.nominators.add(n);
  }

  public void setTally(int tally) {
    this.nominationTally = tally;
  }

  public int getTally() {
    return this.nominationTally;
  }

  public void setRunningForPositionName(String position) {
    this.runningForPositionName = position;
  }

  public String getRunningForPositionName() {
    return this.runningForPositionName;
  }

  // Getters and setters

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStudentNumber() {
    return this.studentNumber;
  }

  public void setStudentNumber(String studentNumber) {
    this.studentNumber = studentNumber;
  }

  public String getMajor() {
    return this.major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getRunningFor() {
    return this.runningFor;
  }

  public void setRunningFor(String runningFor) {
    this.runningFor = runningFor;
  }

  // toString override
  @Override
  public String toString() {
    return "{"
      + " fullName='" + getFullName() + "'"
      + ", email='" + getEmail() + "'"
      + ", studentNumber='" + getStudentNumber() + "'"
      + ", major='" + getMajor() + "'"
      + ", runningFor='" + getRunningFor() + "'"
      + ", tally='" + getTally() + "'"
      + ", shouldEmail='" + Boolean.toString(getShouldEmail()) + "'"
      + "}";
  }

}
