package qualtrics;

import java.io.Serializable;

/**
 * A data class represneting someone who nominated someone else.
 *
*/
public class Nominator implements Serializable {

  /**
   * serialVersionUID for nominator.
   */
  private static final long serialVersionUID = -115924478268007616L;
  private String fullName;
  private String email;
  private String studentNumber;
  private String major;
  private String nominatingName;
  private String nominatingNameFirstName;
  private String nominatingNameLastName;
  private String nominatingFor;
  private boolean shouldEmail;
  private boolean isTallied;

  /**
   * Construct a nominator.
   * @param fullName their name
   * @param email their email
   * @param studentNumber their student number
   * @param major their major
   * @param nominatingName the person their nominating
   * @param nominatingFor what they're nomianting them for
   */
  public Nominator(String fullName,
      String email,
      String studentNumber,
      String major,
      String nominatingName,
      String nominatingFor)  {
    this.fullName = fullName;
    this.email = email;
    this.studentNumber = studentNumber;
    this.major = major;
    String[] names = nominatingName.split(" ");
    if (names.length == 3) {
      this.nominatingName = names[0] + " " + names[2];
      this.nominatingNameFirstName = names[0];
      this.nominatingNameLastName = names[2];
    } else if (names.length == 2) {
      this.nominatingName = names[0] + " " + names[1];
      this.nominatingNameFirstName = names[0];
      this.nominatingNameLastName = names[1];
    } else if (names.length == 1) {
      this.nominatingName = null;
      this.nominatingNameFirstName = nominatingName;
      this.nominatingNameLastName = nominatingName;
    } else {
      App.getLogger().warning("Tried to create a nominating nominating someone with "
          + "more than 3 names, or 0 names: " + fullName);
    }
    this.nominatingFor = nominatingFor;
    this.shouldEmail = true;
    this.isTallied = false;
  }

  public boolean getShouldEmail() {
    return this.shouldEmail;
  }

  public void setShouldEmail(boolean shouldEmail) {
    this.shouldEmail = shouldEmail;
  }

  public boolean getIsTallied() {
    return this.isTallied;
  }

  public void setIsTallied(boolean isTallied) {
    this.isTallied = isTallied;
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

  public String getNominatingName() {
    return this.nominatingName;
  }

  public void setNominatingName(String nominatingName) {
    this.nominatingName = nominatingName;
  }

  public String getNominatingNameFirstName() {
    return this.nominatingNameFirstName;
  }

  public void setNominatingNameFirstName(String nominatingNameFirstName) {
    this.nominatingNameFirstName = nominatingNameFirstName;
  }

  public String getNominatingNameLastName() {
    return this.nominatingNameLastName;
  }

  public void setNominatingNameLastName(String nominatingNameLastName) {
    this.nominatingNameLastName = nominatingNameLastName;
  }

  public String getNominatingFor() {
    return this.nominatingFor;
  }

  public void setNominatingFor(String nominatingFor) {
    this.nominatingFor = nominatingFor;
  }

  // toString override
  @Override
  public String toString() {
    return "{"
      + " fullName='" + getFullName() + "'"
      + ", email='" + getEmail() + "'"
      +  ", studentNumber='" + getStudentNumber() + "'"
      + ", major='" + getMajor() + "'"
      + ", nominatingName='" + getNominatingName() + "'"
      + ", nominatingFor='" + getNominatingFor() + "'"
      + ", shouldEmail='" + Boolean.toString(getShouldEmail()) + "'"
      + ", isTallied='" + Boolean.toString(getIsTallied()) + "'"
      + "}";
  }

}
