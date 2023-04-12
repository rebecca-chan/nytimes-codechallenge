## REPOS

For this project I tried to focus mainly on architecture with MVVM so it can be expanded later

Things I would have done with more time:
Add tests to SearchViewModel - I started on this, but ran into an error with the coroutine scope
inside initialization method
Added tests for Composables
Add a way to refresh results, like swipe to refresh. If there's no network connection you'll just
see an error message, but if you
reconnect to a network, the results won't refresh automatically

## Build tools & versions used

Built with Android Studio Electric Eel | 2022.1.1 Patch 2

## Steps to run the app

Run on Android Studio or ./gradlew installDebug

For testing:
./gradlew test

