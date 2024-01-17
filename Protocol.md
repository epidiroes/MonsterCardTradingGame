# Monster Trading Card Game Protocol

Laura Engleitner, if21b194@technikum-wien.at


## Design

The project is structured into two parts: Application and server.
The server is responsible for receiving the requests and formatting the responses.
It translates the http requests into Request class objects.
Those are passed on to the correct controller to handle.
The Response class object that gets returned is then translated to a http response and sent.

The application is structured into three layers: Controllers (mentioned above), Services and Repositories
with additional Entities representing the database objects.

### Controller

An abstract class "Controller" is defined with the abstract methods supports(String route), 
which returns a boolean depending on if the given route is supported, and handle(Request request), 
which matches the route and handles the request according to the given HttpMethod (GET, POST, PUT or DELETE).

Every route has a corresponding controller that extends the abstract controller class.
After the RequestHandler in the server part of the application calls the handle() method of the controller,
the controller calls its according method to handle the request.
They are then responsible for parsing the request body's content to dtos or entity classes
and calling services to process the request.
The authorization of the users also happens here, except in the UserController.


### Services

The controllers call on services to process data or access the database.
They in turn call the correct repository methods to interact with the database.
Some services don't have their own repositories. 
Such as the AuthorizationService, which calls on the UserRepository,
to check if a user exists and the authorization matches.

### Repositories

Every table in the database has its own repository.
Repositories contain the methods used for interacting with the database, such as "SELECT", "INSERT INTO", "UPDATE" and "DELETE".
SQL injections are being prevented by the use of prepared statements. 


## Tests 

Unit tests where used to test the Card entity, BattleLogic fight method and the HttpMapper methods.
The tests are structured into three steps "Arrange", "Act" and "Assert".
In the first step all necessary steps are taken to create the objects that are used in the test.
In "Act" the method that is being tested is called and the result saved into a variable.
In the last step the result gets compared to the expected value and their equality is asserted.

A provided curl script was used for integration tests.

## Time spent

Approximately 50 hours were spent on planing out, writing and testing this project,
not counting the hours spent during class.


## Git link

https://github.com/epidiroes/MonsterCardTradingGame.git
