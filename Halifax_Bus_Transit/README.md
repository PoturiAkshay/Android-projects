
# Halifax Transit
Halifax Transit is an application that allows the user to see the maps and determine when their bus is coming in real time is implemented.

# Description
This is a native android application which is implemented in Java which meets the below requirements:

1) On launching the app, a map should be displayed with all the indicators showing the current position of buses in halifax.
2) The marker for each bus will show the route number, trip number and delay time of the bus.
3) The positions of buses will updated for every 10 seconds. the realtime data of the buses are extracted from Halifax gtfs website.
4) when the user clicks on current location button, maps will show the marker for user's current location.
5) The user can zoom in/zoom out the map freely to any position on the map.
6) when the device is rotated to any position, the information will be retained and markers with current bus locations will be displayed.
7) when the application is closed and re-opened the maps will show the same region it was showing earlier when the application is closed.
8) The user can filter the buses to be displayed in maps and select the required bus by clicking filter the buses button.

# Design and workflow
The design and workflow of the application is given below:

1) The application is designed with two screens in which one of the screens allows the user to see their current location, the current location of buses in halifax (the location of buses will be updated for every 10 seconds) along with the route number, trip id, delay time of the bus as per the schedule and other screen allows the user to filter the bus he/she wants to display in the maps.
2) In the first screen, the current location of the buses will be displayed immediately after opening the application and the location of buses will be updated for every 10 seconds. The user can zoom in/zoom out of the maps and see any region on the maps.
3) The user can also go to their current location by clicking on the current location button which is present on the top right corner of the maps.
4) when the user closes the app and reopens, it will show the region which was present before closing the map and all the markers of current locations of buses.
5) Everytime when the first screen is opening, if loading the maps is taking time, the app will diplay a message saying "Loading, please wait for a while.." until the maps are loaded.
6) A button "Filter the buses" is given for the user so that it navigates to the next screen on click of the button.
7) Once the second screen appears, user can give the bus number(route number) whose realtime information the user wants to see on the maps. A button is given for the user on click of which, the first screen will be redirected with position of bus(route number provided by the user in second screen). 

# Known bugs
I have tested the application with multiple scenarios and all the functionalities were working properly and no issues were observed.


# References
[1]"Get Started  |  Maps SDK for Android  |  Google Developers", Google Developers, 2019. [Online]. Available: https://developers.google.com/maps/documentation/android-sdk/start. [Accessed: 15- Jul- 2019].

[2]Gtfs.org, 2019. [Online]. Available: https://gtfs.org/reference/static/. [Accessed: 15- Jul- 2019].

[3]Gtfs.org, 2019. [Online]. Available: https://gtfs.org/reference/realtime/v2/. [Accessed: 15- Jul- 2019].

[4]Gtfs.halifax.ca, 2019. [Online]. Available: http://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb. [Accessed: 15- Jul- 2019].

[5]"MobilityData/gtfs-realtime-bindings", GitHub, 2019. [Online]. Available: https://github.com/MobilityData/gtfs-realtime-bindings. [Accessed: 15- Jul- 2019].

[6]Dal.brightspace.com, 2019. [Online]. Available: https://dal.brightspace.com/d2l/le/content/97458/viewContent/1347182/View. [Accessed: 15- Jul- 2019].
