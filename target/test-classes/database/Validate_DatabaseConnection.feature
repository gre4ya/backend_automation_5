Feature: Validate database connection

  @db
  Scenario Outline: Validate the minimum salary
    Given user is able to connect to the database
    When user send "<query>" to the database
    Then Validate the <salary>

    Examples:
      | query                             | salary |
      | select min(salary) from employees | 2100   |