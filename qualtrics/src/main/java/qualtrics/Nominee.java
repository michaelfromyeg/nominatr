package qualtrics;

/*
 * A class representing someone who is running for a position
 */
public class Nominee {

  private String fullName;
  private String email;
  private String studentNumber;
  private String major;
  private String runningFor;

  public Nominee(String fullName, String email, String studentNumber, String major, String runningFor) {
    this.fullName = fullName;
    this.email = email;
    this.studentNumber = studentNumber;
    this.major = major;
    this.runningFor = runningFor;
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
    return "{" +
      " fullName='" + getFullName() + "'" +
      ", email='" + getEmail() + "'" +
      ", studentNumber='" + getStudentNumber() + "'" +
      ", major='" + getMajor() + "'" +
      ", runningFor='" + getRunningFor() + "'" +
      "}";
  }

}
