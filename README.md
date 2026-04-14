# course-enrollment-system-tests

Course Enrollment System API tests.

## Frameworks/Language

* Maven
* Java 26
* JUnit 5
* RestAssured

## How to run

To run the tests you will need the following data set as environment variables:

* `APP_URL` - this is the host of the service with no paths. Paths are handled within the Helper class in the framework.
* `CANDIDATE_ID` - this is the candidate/application id that you were supplied.
* `PASSWORD` - this is the password you were supplied.

Setting environment variables enables these parameters to be set securely within pipeline via secrets so they're not
exposed.

Maven command:

```aiignore
APP_URL=<host with no paths> CANDIDATE_ID=<candidate/applicant id> PASSWORD=<password> mvn surefire-report:report test
```

When running JUnit Test within an IDE make sure to set the environment variables within the configuration of the test.

## Reports

This framework uses Maven SureFire Report plugin. When you run the test with arg `surefire-report:report` it will
generate a basic report in the `target/report` folder.
