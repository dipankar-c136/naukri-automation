Feature: Naukri Login and Resume Management

  Scenario: Login to Naukri and manage resume
    Given I have the Naukri login credentials from the Excel file
    And I open the Naukri login page
    When I enter my credentials and login
    And I clicked on the <View Profile> button
    Then I should see my profile page
    And I navigate to the resume management section
    When I remove the already uploaded resume
    And I upload my resume again
    #Then I should see a confirmation that the resume has been uploaded successfully
    #And I receive an email notification about the successful job run
    Then I close the browser