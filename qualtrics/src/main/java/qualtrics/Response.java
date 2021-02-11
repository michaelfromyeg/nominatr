package qualtrics;

/**
* A class representing a raw response from a CSV.
*/
public class Response {

  private String startDate;
  private String endDate;
  private String status;
  private String ipAddress;
  private String progress;
  private String duration;
  private String finished;
  private String recordedDate;
  private String responseId;
  private String lastName;
  private String firstName;
  private String receipientEmail;
  private String externalReference;
  private String locationLatitude;
  private String locationLongitude;
  private String distributionChannel;
  private String userLang;
  private String q1; // Nominee or nominator

  // NOMINEE
  private String q4; // Full name
  private String q5; // Student number
  private String q6; // Email
  private String q7; // Phone number
  private String q8; // Year (eg: 1,2,3,4)
  private String q9; // Major/department
  private String q10; // Running for...

  // NOMINATOR
  private String q11; // Full name
  private String q12; // Student number
  private String q19; // Email
  private String q13; // Major
  private String q15; // I am nominating...
  private String q16; // For the position of...

  /**
   * Build a Qualtrics response object.
   *
   * @param startDate startDate
   * @param endDate endDate
   * @param status status
   * @param ipAddress ipAddress
   * @param progress progress
   * @param duration duration
   * @param finished finished
   * @param recordedDate recordedDate
   * @param responseId responseId
   * @param lastName lastName
   * @param firstName firstName
   * @param receipientEmail receipientEmail
   * @param externalReference externalReference
   * @param locationLatitude locationLatitude
   * @param locationLongitude locationLongitude
   * @param distributionChannel distributionChannel
   * @param userLang userLang
   * @param q1 q1
   * @param q4 q4
   * @param q5 q5
   * @param q6 q6
   * @param q7 q7
   * @param q8 q8
   * @param q9 q9
   * @param q10 q10
   * @param q11 q11
   * @param q12 q12
   * @param q19 q19
   * @param q13 q13
   * @param q15 q15
   * @param q16 q16
   */
  public Response(String startDate,
      String endDate,
      String status,
      String ipAddress,
      String progress,
      String duration,
      String finished,
      String recordedDate,
      String responseId,
      String lastName,
      String firstName,
      String receipientEmail,
      String externalReference,
      String locationLatitude,
      String locationLongitude,
      String distributionChannel,
      String userLang,
      String q1,
      String q4,
      String q5,
      String q6,
      String q7,
      String q8,
      String q9,
      String q10,
      String q11,
      String q12,
      String q19,
      String q13,
      String q15,
      String q16) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
    this.ipAddress = ipAddress;
    this.progress = progress;
    this.duration = duration;
    this.finished = finished;
    this.recordedDate = recordedDate;
    this.responseId = responseId;
    this.lastName = lastName;
    this.firstName = firstName;
    this.receipientEmail = receipientEmail;
    this.externalReference = externalReference;
    this.locationLatitude = locationLatitude;
    this.locationLongitude = locationLongitude;
    this.distributionChannel = distributionChannel;
    this.userLang = userLang;
    this.q1 = q1;
    this.q4 = q4;
    this.q5 = q5;
    this.q6 = q6;
    this.q7 = q7;
    this.q8 = q8;
    this.q9 = q9;
    this.q10 = q10;
    this.q11 = q11;
    this.q12 = q12;
    this.q19 = q19;
    this.q13 = q13;
    this.q15 = q15;
    this.q16 = q16;
  }

  // Getters and setters

  public String getStartDate() {
    return this.startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return this.endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getIpAddress() {
    return this.ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getProgress() {
    return this.progress;
  }

  public void setProgress(String progress) {
    this.progress = progress;
  }

  public String getDuration() {
    return this.duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getFinished() {
    return this.finished;
  }

  public void setFinished(String finished) {
    this.finished = finished;
  }

  public String getRecordedDate() {
    return this.recordedDate;
  }

  public void setRecordedDate(String recordedDate) {
    this.recordedDate = recordedDate;
  }

  public String getResponseId() {
    return this.responseId;
  }

  public void setResponseId(String responseId) {
    this.responseId = responseId;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getReceipientEmail() {
    return this.receipientEmail;
  }

  public void setReceipientEmail(String receipientEmail) {
    this.receipientEmail = receipientEmail;
  }

  public String getExternalReference() {
    return this.externalReference;
  }

  public void setExternalReference(String externalReference) {
    this.externalReference = externalReference;
  }

  public String getLocationLatitude() {
    return this.locationLatitude;
  }

  public void setLocationLatitude(String locationLatitude) {
    this.locationLatitude = locationLatitude;
  }

  public String getLocationLongitude() {
    return this.locationLongitude;
  }

  public void setLocationLongitude(String locationLongitude) {
    this.locationLongitude = locationLongitude;
  }

  public String getDistributionChannel() {
    return this.distributionChannel;
  }

  public void setDistributionChannel(String distributionChannel) {
    this.distributionChannel = distributionChannel;
  }

  public String getUserLang() {
    return this.userLang;
  }

  public void setUserLang(String userLang) {
    this.userLang = userLang;
  }

  public String getQ1() {
    return this.q1;
  }

  public void setQ1(String q1) {
    this.q1 = q1;
  }

  public String getQ4() {
    return this.q4;
  }

  public void setQ4(String q4) {
    this.q4 = q4;
  }

  public String getQ5() {
    return this.q5;
  }

  public void setQ5(String q5) {
    this.q5 = q5;
  }

  public String getQ6() {
    return this.q6;
  }

  public void setQ6(String q6) {
    this.q6 = q6;
  }

  public String getQ7() {
    return this.q7;
  }

  public void setQ7(String q7) {
    this.q7 = q7;
  }

  public String getQ8() {
    return this.q8;
  }

  public void setQ8(String q8) {
    this.q8 = q8;
  }

  public String getQ9() {
    return this.q9;
  }

  public void setQ9(String q9) {
    this.q9 = q9;
  }

  public String getQ10() {
    return this.q10;
  }

  public void setQ10(String q10) {
    this.q10 = q10;
  }

  public String getQ11() {
    return this.q11;
  }

  public void setQ11(String q11) {
    this.q11 = q11;
  }

  public String getQ12() {
    return this.q12;
  }

  public void setQ12(String q12) {
    this.q12 = q12;
  }

  public String getQ19() {
    return this.q19;
  }

  public void setQ19(String q19) {
    this.q19 = q19;
  }

  public String getQ13() {
    return this.q13;
  }

  public void setQ13(String q13) {
    this.q13 = q13;
  }

  public String getQ15() {
    return this.q15;
  }

  public void setQ15(String q15) {
    this.q15 = q15;
  }

  public String getQ16() {
    return this.q16;
  }

  public void setQ16(String q16) {
    this.q16 = q16;
  }
}
