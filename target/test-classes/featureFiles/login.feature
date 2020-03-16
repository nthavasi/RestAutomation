Feature: Login Functionality

Scenario Outline: Check the API response
Given Read test Data from Excel for "<TestScenarioName>" and "<TestCaseName>"
When Request is sent and the Response is generated
And Validate the response status code
Examples:
|TestScenarioName|TestCaseName|
|Check the API response|test1|
|Check the API response|test2|
|Check the API response|test3|
|Check the API response|test4|

Scenario Outline: Success-Login-2
Given Read test Data from Excel for "<TestScenarioName>" and "<TestCaseName>"
When Request is sent and the Response is generated
And Validate the response status code
Examples:
|TestScenarioName|TestCaseName|
|Success-Login-2|test6|
|Success-Login-2|test7|