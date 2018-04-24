# TrueShot - 3D Sound Interface
TrueShot's goal is to give gunshot detection devices another interface for displaying their data to a user. It uses 3D sound to
convey coordinate locations and predictive information about the shooter.

The repository has been separated into two different modules: **lib** and **app**. Lib contains the meat of the 3D sound. Feel free to fork it if you want to apply
some 3D sound to your application, although at the moment it doesn't support much.


## Installation
*Must have maven installed prior
```
git init

git clone https://github.com/Luc-Olsthoorn/TrueShot.git

mvn clean -Dmaven.test.skip=true install

mvn exec:java
```
Go to localhost:8000 for stream

Go to localhost:9092 for admin panel

## Known errors
Sometimes the socket doesnt close properly and returns an error on startup; this is fine. Wait a little bit and retry, as the OS will free up the port.
