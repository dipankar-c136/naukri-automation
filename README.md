# Naukri Automation Project

This project automates the process of logging into the Naukri website, managing resumes, and running tests using TestNG and Cucumber.

## Project Structure

```
naukri-automation
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com
│   │   │   │   └── naukri
│   │   │   │       ├── pages
│   │   │   │       │   └── LoginPage.java
│   │   │   │       ├── utils
│   │   │   │       │   ├── ExcelUtils.java
│   │   │   │       │   └── ConfigReader.java
│   │   │   │       └── base
│   │   │   │           └── BaseTest.java
│   │   └── resources
│   │       ├── config.properties
│   │       └── testdata.xlsx
│   ├── test
│       ├── java
│       │   ├── com
│       │   │   └── naukri
│       │   │       ├── stepdefinitions
│       │   │       │   └── LoginSteps.java
│       │   │       ├── runners
│       │   │       │   └── TestRunner.java
│       │   │       └── hooks
│       │   │           └── Hooks.java
│       └── resources
│           └── features
│               └── Login.feature
├── pom.xml
├── Jenkinsfile
└── README.md
```

## Setup Instructions

1. **Clone the Repository**: Clone this repository to your local machine.
   
2. **Install Dependencies**: Navigate to the project directory and run the following command to install the required dependencies:
   ```
   mvn clean install
   ```

3. **Configure Credentials**: Update the `src/main/resources/testdata.xlsx` file with your Naukri login credentials.

4. **Configure Properties**: Modify the `src/main/resources/config.properties` file to set the necessary configuration settings such as the Naukri website URL and browser type.

## Running the Tests

To run the tests, you can use the following command:
```
mvn test
```

Alternatively, you can set up a Jenkins job to run the tests daily at 9:15 AM IST using the provided `Jenkinsfile`.

## Reporting

The project includes reporting capabilities to notify you via email about the test execution results. Ensure that your email settings are configured correctly in the `pom.xml` file.

## Features

- Automated login to the Naukri website.
- Resume management: remove existing resumes and upload new ones.
- BDD approach using Cucumber for defining test scenarios.

## Contributing

Feel free to contribute to this project by submitting issues or pull requests. Your contributions are welcome!

## License

This project is licensed under the MIT License. See the LICENSE file for details.