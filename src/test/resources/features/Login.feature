@login
Feature:  User Login
  user should be able to login with his credentials

	Background: navigate to application
		Given User should be on the loginpage
			|"https://the-internet.herokuapp.com/login"|
			|"http://www.amazon.com"  								 |
			|"https://www.flipkart.com"                |
	
  @valid_login @positive @smoke
  Scenario Outline: user login with valid credentials "<username>" 
    When User submits his "<username>" and "<password>"
    Then User should see the message "<msg>"
    
    #And User logs out of the secure area
    #Then User should see the message "You logged out of the secure area!"
     
      Examples: 
      | username  | password 						 		| msg  																|
      | tomsmith  | SuperSecretPassword! 		|  You logged into a secure area!     |
      #| saikiran  |  SuperSecretPassword!   |   Your username is invalid!   			|
      #| user3  		|  password!              |   Your username is invalid!   			| 

  #@invalid_login @nagative @smoke
  #Scenario: user login with invalid credentials
    #When User submits his "username" and "passsword"
    #Then User should see the message "Your username is invalid!"

  #@tag2
  #Scenario Outline: Title of your scenario outline
    #Given I want to write a step with <name>
    #When I check for the <value> in step
    #Then I verify the <status> in step
#
    #Examples: 
      #| name  | value | status  |
      #| name1 |     5 | success |
      #| name2 |     7 | Fail    |
