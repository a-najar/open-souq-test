# open-souq-test

The test is build with clean arch 
and the normal xml ui with ViewModel 

the project structure will be domain wich contain 
- entities
- repositories
- and use cases 

the presentation will contain the UI Part 

- MainActivity 
. contain the navigaiton component for single activity applicaiton
. the weather fragment will contain all the ui with it's view model for each screen 
the pattern used in the presentaiton is MVVM.

- unit test are added only for the domain layer for the use case 
- the domain have only one use case will fetch the country by it's name
