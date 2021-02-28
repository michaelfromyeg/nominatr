package qualtrics;

/**
* A class representing a raw response from a CSV.
*/
public class Response {

  private String startDate;
  private String endDate;
  private String responseType;
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
  private String nomineeOrNominator; // Nominee or nominator

  // NOMINEE
  private String nomineeFullName; // Full name
  private String nomineeStudentNumber; // Student number
  private String nomineeEmail; // Email
  private String nomineePhoneNumber; // Phone number
  private String nomineeYear; // Year (eg: 1,2,3,4)
  private String nomineeMajor; // Major/department
  private String nomineeRunningFor; // Running for...

  // NOMINATOR
  private String nominatorFullName; // Full name
  private String nominatorStudentNumber; // Student number
  private String nominatorEmail; // Email
  private String nominatorMajor; // Major
  private String nominatorNominatingName; // I am nominating...
  private String nominatorNominatingPosition; // For the position of...

  /**
   *  Return an empty instance that will need to be filled via setters.
   */
  public Response() {
    // Empty constructor
  }

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
   * @param nomineeOrNominator q1
   * @param nomineeFullName q4
   * @param nomineeStudentNumber q5
   * @param nomineeEmail q6
   * @param nomineePhoneNumber q7
   * @param nomineeYear q8
   * @param nomineeMajor q9
   * @param nomineeRunningFor q10
   * @param nominatorFullName q11
   * @param nominatorStudentNumber q12
   * @param nominatorEmail q19
   * @param nominatorMajor q13
   * @param nominatorNominatingName q15
   * @param nominatorNominatingPosition q16
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
      String nomineeOrNominator,
      String nomineeFullName,
      String nomineeStudentNumber,
      String nomineeEmail,
      String nomineePhoneNumber,
      String nomineeYear,
      String nomineeMajor,
      String nomineeRunningFor,
      String nominatorFullName,
      String nominatorStudentNumber,
      String nominatorEmail,
      String nominatorMajor,
      String nominatorNominatingName,
      String nominatorNominatingPosition) {
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
    this.nomineeOrNominator = nomineeOrNominator;
    this.nomineeFullName = nomineeFullName;
    this.nomineeStudentNumber = nomineeStudentNumber;
    this.nomineeEmail = nomineeEmail;
    this.nomineePhoneNumber = nomineePhoneNumber;
    this.nomineeYear = nomineeYear;
    this.nomineeMajor = nomineeMajor;
    this.nomineeRunningFor = nomineeRunningFor;
    this.nominatorFullName = nominatorFullName;
    this.nominatorStudentNumber = nominatorStudentNumber;
    this.nominatorEmail = nominatorEmail;
    this.nominatorMajor = nominatorMajor;
    this.nominatorNominatingName = nominatorNominatingName;
    this.nominatorNominatingPosition = nominatorNominatingPosition;
  }

  /**
   * Match a value to a field by a string-form field name key.
   *
   * @param value a field name, as a string
   * @throws Exception if value not found in valid field names
   */
  public void setByFieldNameString(String fieldName, String value) throws Exception {
    if (fieldName == null) {
      return;
    }
    switch (fieldName) {
      case "startDate":
        this.setStartDate(value);
        break;
      case "endDate":
        this.setEndDate(value);
        break;
      case "responseType":
        this.setResponseType(value);
        break;
      case "ipAddress":
        this.setIpAddress(value);
        break;
      case "progress":
        this.setProgress(value);
        break;
      case "duration":
        this.setDuration(value);
        break;
      case "finished":
        this.setFinished(value);
        break;
      case "recordedDate":
        this.setRecordedDate(value);
        break;
      case "responseId":
        this.setResponseId(value);
        break;
      case "lastName":
        this.setLastName(value);
        break;
      case "firstName":
        this.setFirstName(value);
        break;
      case "recipientEmail":
        this.setReceipientEmail(value);
        break;
      case "externalReference":
        this.setExternalReference(value);
        break;
      case "locationLatitude":
        this.setLocationLatitude(value);
        break;
      case "locationLongitude":
        this.setLocationLongitude(value);
        break;
      case "distributionChannel":
        this.setDistributionChannel(value);
        break;
      case "userLang":
        this.setUserLang(value);
        break;
      case "nomineeOrNominator":
        this.setNomineeOrNominator(value);
        break;
      case "nomineeFullName":
        this.setNomineeFullName(value);
        break;
      case "nomineeStudentNumber":
        this.setNomineeStudentNumber(value);
        break;
      case "nomineeEmail":
        this.setNomineeEmail(value);
        break;
      case "nomineePhoneNumber":
        this.setNomineePhoneNumber(value);
        break;
      case "nomineeYear":
        this.setNomineeYear(value);
        break;
      case "nomineeMajor":
        this.setNomineeMajor(value);
        break;
      case "nomineeRunningFor":
        this.setNomineeRunningFor(value);
        break;
      case "nominatorFullName":
        this.setNominatorFullName(value);
        break;
      case "nominatorStudentNumber":
        this.setNominatorStudentNumber(value);
        break;
      case "nominatorEmail":
        this.setNominatorEmail(value);
        break;
      case "nominatorMajor":
        this.setNominatorMajor(value);
        break;
      case "nominatorNominatingName":
        this.setNominatorNominatingName(value);
        break;
      case "nominatorNominatingPosition":
        this.setNominatorNominatingPosition(value);
        break;
      default:
        throw new Exception("Invalid machine name passed to setByFieldNameString");
    }
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

  public void setResponseType(String responseType) {
    this.responseType = responseType;
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

  public String getNomineeOrNominator() {
    return this.nomineeOrNominator;
  }

  public void setNomineeOrNominator(String nomineeOrNominator) {
    this.nomineeOrNominator = nomineeOrNominator;
  }

  public String getNomineeFullName() {
    return this.nomineeFullName;
  }

  public void setNomineeFullName(String nomineeFullName) {
    this.nomineeFullName = nomineeFullName;
  }

  public String getNomineeStudentNumber() {
    return this.nomineeStudentNumber;
  }

  public void setNomineeStudentNumber(String nomineeStudentNumber) {
    this.nomineeStudentNumber = nomineeStudentNumber;
  }

  public String getNomineeEmail() {
    return this.nomineeEmail;
  }

  public void setNomineeEmail(String nomineeEmail) {
    this.nomineeEmail = nomineeEmail;
  }

  public String getNomineePhoneNumber() {
    return this.nomineePhoneNumber;
  }

  public void setNomineePhoneNumber(String nomineePhoneNumber) {
    this.nomineePhoneNumber = nomineePhoneNumber;
  }

  public String getNomineeYear() {
    return this.nomineeYear;
  }

  public void setNomineeYear(String nomineeYear) {
    this.nomineeYear = nomineeYear;
  }

  public String getNomineeMajor() {
    return this.nomineeMajor;
  }

  public void setNomineeMajor(String nomineeMajor) {
    this.nomineeMajor = nomineeMajor;
  }

  public String getNomineeRunningFor() {
    return this.nomineeRunningFor;
  }

  public void setNomineeRunningFor(String nomineeRunningFor) {
    this.nomineeRunningFor = nomineeRunningFor;
  }

  public String getNominatorFullName() {
    return this.nominatorFullName;
  }

  public void setNominatorFullName(String nominatorFullName) {
    this.nominatorFullName = nominatorFullName;
  }

  public String getNominatorStudentNumber() {
    return this.nominatorStudentNumber;
  }

  public void setNominatorStudentNumber(String nominatorStudentNumber) {
    this.nominatorStudentNumber = nominatorStudentNumber;
  }

  public String getNominatorEmail() {
    return this.nominatorEmail;
  }

  public void setNominatorEmail(String nominatorEmail) {
    this.nominatorEmail = nominatorEmail;
  }

  public String getNominatorMajor() {
    return this.nominatorMajor;
  }

  public void setNominatorMajor(String nominatorMajor) {
    this.nominatorMajor = nominatorMajor;
  }

  public String getNominatorNominatingName() {
    return this.nominatorNominatingName;
  }

  public void setNominatorNominatingName(String nominatorNominatingName) {
    this.nominatorNominatingName = nominatorNominatingName;
  }

  public String getNominatorNominatingPosition() {
    return this.nominatorNominatingPosition;
  }

  public void setNominatorNominatingPosition(String nominatorNominatingPosition) {
    this.nominatorNominatingPosition = nominatorNominatingPosition;
  }
}
