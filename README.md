# revolut-app

Sample application written in Kotlin for fetching and converting currencies simultaneously. App downloads and updates rates every 1 second using Revolut API. User can select base currency and input rate value which calculates other currencies in real time. App works also in offline mode and provides last fetched rates. Project is implemented using MVP software design pattern.

## Technology Stack:
- RxJava2
- Retrofit2
- Dagger2
- ReactiveNetwork

## Tests:
- unit tests for presenters and other business-logic components
- tests implemented in BDD style
- used frameworks: JUnit & Mockito/BDDMockito

## App structure:
- data/ - contains data access code, including models, remote endpoints and local storage (repository)
- extensions/ - set of extensions used within app scope
- injection/ - dagger component & modules definiton
- interactor/ - helpers used by presenters
- service/ - contains logic for fetching rates periodically
- ui/ - presentation logic based on MVP pattern 
- util/ - package of utility classes

