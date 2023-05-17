Feature: Automatic Handling

    Automatic handling checks that the equity capital in an application is at least 40% and not over 200%

    Scenario: Equity Capital too low
        Given Equity capital in an application is too low
        When The application goes to automatic handling
        Then The application will not be approved

    Scenario: Equity Capital high enough
        Given Equity capital in an application is high enough
        When The application goes to automatic handling
        Then The application will be approved

    Scenario: Equity Capital is 2 times as high as the credit amount
        Given Equity capital in an application is 2 times or higher than the credit amount 
        When The application goes to automatic handling
        Then The application will not be approved