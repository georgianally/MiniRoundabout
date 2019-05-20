# MiniRoundabout
Java program of an automated mini roundabout using multi-threading and Java animation

Using the MVC architectural pattern helped with designing the program; dividing purposes and containing those within their files. For example, three views were used to keep different GUIs easily maintainable and reusable. The model also helped to encapsulate car’s data. 

With this encapsulation, the observer pattern enabled communication between the view and car: when a car does something, two visual views (text and animation) are notified and updated.

Once a car is created, the car runs its entire lifecycle, alongside many other cars, by using multithreading. Multithreading helped by allowing multiple objects of cars run their methods individually without interference. Further, it allowed cars to interact with each other by checking other car objects for collision detection and sleeping threads when needing to wait for each other.

![image](https://github.com/georgianally/MiniRoundabout/blob/52418acf7f060898cd593869b87656bbbbe1156a/Capture.PNG)
