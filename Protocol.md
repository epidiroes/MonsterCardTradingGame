#Monster Trading Card Game Protocol

Laura Engleitner if21b194


##Design

This server application is structued into three layers: Contorllers, Services and Repositories with additional Entities representing the database objects.

###Controller

An abstract class "Controller" is defined with the abstract methods supports(String route), which returns a boolean depending on if the given route is supported, and handle(Request request), which matches the route and handles the request according to the given HttpMethod (GET, POST, PUT or DELETE).
Every route has a corresponding controller that extends the abstract controller class.
They are also responsible for parsing the request body's content to dtos or entity classes. 
The authorization of the users also happens here, except in the UserController.


###Service

The logic layer which calls the correct repository methods to interact with the database.

###Repository

Contains the methods used for interacting with the database, such as "SELECT", "INSERT INTO", "UPDATE" and "DELETE".
Every table in the database has its own repository.


##Tests 

Unit tests where used to test the Card entity, BattleLogic fight method and the HttpMapper methods.
The tests are structured into three steps "Arrange", "Act" and "Assert".
In the first step all necessary steps are taken to create the objects that are used in the test.
In "Act" the method that is beeing tested is called and the result saved into a variable.
In the last step the result gets compared to the expected value and their equality is asserted.


##Time spent

Approximatily 55 hours


##Git link

https://github.com/epidiroes/MonsterCardTradingGame.git
