# AndroidApp
An Android app which Ingests a json feed from here.


We add permissions to use internet in the manifest file to allow to make network call to server to read the json url.

if phone is not connected to internet we display a toast message. We are using an anko kotlin library for displaying a toast message
implementation 'org.jetbrains.anko:anko-common:0.9'

The content is displayed in a ListView with title, description and image as the fields.

The title in the ActionBar gets updated from the json data by calling getSupportActionBar().

Each row is dynamically sized to display its content, no clipping, no extraneous white-space etc.

The images are loaded lazily using Glide.GLide is used by importing the below library in gradle build.

implementation 'com.github.bumptech.glide:glide:4.9.0'

A Refresh click listener is added to thr action bar to refresh the contents of the list view. On Click it fetches the json data from the json URl.
Every data before the data is fetched the array list used to store the data is cleared everytime to avoid redundant storage.
