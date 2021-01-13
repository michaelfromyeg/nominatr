package qualtrics;

/*
 * A class running someone who is nominating someone else for a position
 */
public class Nominator {

  private String fullName;
  private String email;
  private String studentNumber;
  private String major;
  private String nominatingName;
  private String nominatingFor;

  public Nominator(String fullName, String email, String studentNumber, String major, String nominatingName, String nominatingFor) {
    this.fullName = fullName;
    this.email = email;
    this.studentNumber = studentNumber;
    this.major = major;
    this.nominatingName = nominatingName;
    this.nominatingFor = nominatingFor;
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

  public String getNominatingFor() {
    return this.nominatingFor;
  }

  public void setNominatingFor(String nominatingFor) {
    this.nominatingFor = nominatingFor;
  }
  
  // toString override
  @Override
  public String toString() {
    return "{" +
      " fullName='" + getFullName() + "'" +
      ", email='" + getEmail() + "'" +
      ", studentNumber='" + getStudentNumber() + "'" +
      ", major='" + getMajor() + "'" +
      ", nominatingName='" + getNominatingName() + "'" +
      ", nominatingFor='" + getNominatingFor() + "'" +
      "}";
  }

}
