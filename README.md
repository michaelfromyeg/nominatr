# nominatr

A tool for UBC students to interact with Qualtrics survey data in real time and send emails based on that data. Currently being used for managing SUS' 2021 Spring Elections.

The app:

* Downloads Qualtrics nomination data using the Qualtrics Java SDK
* Processes that data and creates a list of generic response objects
* Creates lists of nominators and nominees
* Tallies nominations, using additional logic to verify nominations and fuzzy match names
* Caches data from Qualtrics to be re-used on subsequent runs, and track who needs to be emailed still
* Emails updates to the EA team, nominees, and nominators (on a cron schedule)

## Notes

Just general notes for understanding the code base.

### Sending Emails

When we build data for emails, it comes in the following format:

- Elections team Key-value of key=table (nominees, nominators), value=list (to render in table, need key-value again)
- Nominees List of key-value that is key=email (nominee's email), value=data; data consists of key=table (summary, nominators) value=list (key-value again)
- Nominators List of key-value that is key=email (nominator's email), value=data; data consits of key=nominee, value=position

## Build

TODO

### Maven

TODO

### Gradle

TODO

## TODOs

* [x] Send daily email to SUS EAs about current election status (with logs, raw data)
* [x] Send daily email to nominees until they have enough nominations
* [x] Send email receipt to nominators for who they nominated
* [x] Verify candidates to not get repeat emails
* [ ] Provide option for candidates to opt-out of emails
* [x] Add code formatting
* [ ] Configure mock-CMS to allow some data to be changed "on the fly"
* [x] Create CI/CD pipeline and deploy to AWS (ca-central)
* [x] Fill in Javadoc and improve general documentation
* [ ] Write README and contributing guidelines; provide use-case for other student elections
* [ ] Validate that one person doesn't nominate 2 people for the same position
