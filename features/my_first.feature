Feature: Diary feature

  Scenario: As a user I can add entries in my diary
    When I press "Diary"
    And I press "New Entry"
    And I press view with id "editTime"
    And I set the time to "13:37" on TimePicker with index "1"
    And I press "Done"
    And I press view with id "editDate"
    And I set the date to "20-12-2013" on DatePicker with index "1"
    And I press "Done"
    And I press "Save Entry"
    Then I see "New entry"
