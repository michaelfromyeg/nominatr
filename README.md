# nominatr

A tool for UBC students to interact with Qualtrics survey data in real time and send emails based on that data. Currently being used for managing SUS' 2021 Spring Elections.

## TODOs

- [x] Send daily email to SUS EAs about current election status (with logs, raw data)
- [x] Send daily email to nominees until they have enough nominations
- [x] Send email receipt to nominators for who they nominated
- [ ] Verify candidates to not get repeat emails
- [ ] Provide option for candidates to opt-out of emails
- [x] Add code formatting
- [ ] Configure mock-CMS to allow some data to be changed "on the fly"
- [ ] Create CI/CD pipeline and deploy to AWS (ca-central)
- [ ] Fill in Javadoc and improve general documentation
- [ ] Write README and contributing guidelines; provide use-case for other student elections
- [ ] Validate that one person doesn't nominate 2 people for the same position

## Notes

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
