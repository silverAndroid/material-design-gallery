# JSON Gallery

JSON Gallery is an Android application that loads albums and images from the REST API, [JSONPlaceHolder](http://jsonplaceholder.typicode.com/) and saves it locally in an SQLite database. It uses a Fragment-based approach which allows for the views to be decoupled from the Activity and allow for easier transitioning.

### Tech

JSON Gallery uses a number of (mostly open-source) libraries to work:
* [Android Support Library](https://developer.android.com/topic/libraries/support-library/index.html) - Support library that offers backward-compatible versions of new features, and provides useful UI elements
* [RecyclerView](https://developer.android.com/topic/libraries/support-library/features.html#v7-recyclerview) - A widget that is a container for displaying large data sets that can be scrolled through efficiently
* [Retrofit](https://square.github.io/retrofit/) - Type-safe HTTP client for Android and Java
* [Fresco](http://frescolib.org) - A memory-efficient image loader which can load images locally or from the network
* [Hawk](https://github.com/orhanobut/hawk) - Secure, simple key-value storage for Android
* [Schematic](https://github.com/SimonVT/schematic/) - Automatically generates a ContentProvider backed by a SQLite database (removes a lot of boilerplate)
