Feature:
    Verify different POST operations

    Background:
       Given I have set up the ability to create products

@success
    Scenario: Verify creating a product on pet store
        Given I perform POST with id as "1234"
        Then I should see the product created with the id "1234"

    Scenario: Verify creating a product on pet store with all valid data
        Given I perform POST with all valid data
        Then I should see the product created successfully

    Scenario: Verify creating a product on pet store with status set to availible
        Given I perform POST with status as "availible"
        Then I should see the product created with the product status "availible"

    Scenario: Verify creating a product on pet store with status set to pending
        Given I perform POST with status as "pending"
        Then I should see the product created with the product status "pending"

    Scenario: Verify creating a product on pet store with status set to sold
        Given I perform POST with status as "sold"
        Then I should see the product created with the product status "sold"


@negative
    Scenario: Verify creating a product on pet store with incorrect id
        Given I perform POST with id as "!@£@£$$"
        Then I should see an error response with status code "500"

    Scenario: Verify creating a product on pet store with status set to Processed
        Given I perform POST with status as "processed"
        Then I should see an error response with status code "405"

    Scenario: Verify creating a product on pet store with invalid id
        Given I perform POST with id as "9223372036854775808"
        Then I should see an error response with status code "405"







