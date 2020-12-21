# File-Processor-Application
A file processor application with Swing GUI, created for a SAP task, given to students at FCST of TU-Sofia - you can see the task [here](https://github.com/mtsanovv/File-Processor-Application/blob/master/sap_task.png). A demo for the application is available [here](https://www.youtube.com/watch?v=Ft-SkyN-Lc8).

## Requirements
- Java 11 (this was created using OpenJDK 11.0.4)

## Running the app
1. Clone this repository.
2. Have Gradle process the configuration and load everything. After it's done, run ```gradlew run```.
3. There's a sample ```file.txt``` file in the main directory in order for you to be able to test the app.

## Building the app
- It's the same steps when running the app. However, instead of running ```gradlew run```, you have to run ```gradlew build```. The executable jar is in inside ```build/libs``` after that. However, sometimes you might have to run ```gradlew clean``` before you do ```gradlew build```.
- After you build the executable jar, you can run it the standard way, by opening a terminal, going to the build/libs directory of the project and running ```java -jar File-Processor-Application-X.X.X.jar```, where X.X.X is the version of your app, specified in the build.gradle configuration. **Make sure that you run the jar with Java 11 - to check which version you are using, run ```java -version``` before running the file.**

## Notes
- No need to build the app: it's already built with a sample ```file.txt``` file supplied in the jar location (```build/libs```).

*M. Tsanov, 2020*
