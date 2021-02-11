package qualtrics;

import java.util.ArrayList;
import java.util.List;

/**
 * An object representing someone who is running for a position.
 */
public class Nominee {

  private String fullName;
  private String email;
  private String studentNumber;
  private String major;
  private String runningFor;
  private int nominationTally;
  private String runningForPositionName;
  private List<Nominator> nominators;

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
    this.fullName = fullName;
    this.email = email;
    this.studentNumber = studentNumber;
    this.major = major;
    this.runningFor = runningFor;
    this.nominationTally = 0;
    this.nominators = new ArrayList<>();
  }

  public void incrementTally() {
    this.nominationTally += 1;
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
      + "}";
  }

}
