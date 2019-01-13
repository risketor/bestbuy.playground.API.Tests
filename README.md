# Bender - REST Assured Test Suite for Best Buy API Playground 
=======

Test suite covering the RESTful API in Best Buy API playground. 

## Solution
Simple solution in Java using REST Assured tool for testing the Best Buy API Playground dataset of products, categories, and stores exposed over HTTP via a simple REST API. It is including 39 tests, covering http response codes and returned fields. 

## Getting Started
1. Open a terminal window/command prompt
2. Clone this project.
3. Run ``` mvn clean verify``` in the top level directory of the project 
4. All dependencies should now be downloaded and the test will have run successfully.

### Prerequisites
1. Java JDK v1.8
2. Set up Best Buy API playground local environments as explained in the guide https://github.com/BestBuy/api-playground
3. Maven: to have the command-line tool running: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

## Running the tests

Run 'mvn clean test' will run all the test successfully.
```
mvn clean test
```

## Built With
* REST Assured open-source tool.
* [Maven](https://maven.apache.org/) - Dependency Management.

## Improvements
Improvements to be done with more time:
* Assert fields in body responses.
* Test the functionality to filter, limit, skip, sort, etc. with the endpoints. 
* Having different files for test user data, environment configuration, runner, etc.
* Test reports in files.

## Contact
If you have any questions about this repo, please do not hesitate to contact me.
