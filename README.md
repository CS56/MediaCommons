# MediaCommons
A collection of useful utilities written in the Java programming language.

[![Media Commons](misc/background.png)](https://github.com/CS56/MediaCommons/releases)

## Overview
This repository was written for a Spring Java Programming class. The goal of the project was to provide useful features the end user could use (security, weather, information etc).

## Features
The version featured on this repository is missing a few features. These were removed as private Developer API keys or Passwords were used, and due to possible abuse have been removed. Some of the information was encrypted, but then it would serve no purpose as it would be still unusable.

All features in this program are divided into tabs. Each of these tabs may, or may not have an associated "module". Modules handle the API implementation, or relevant utilities.

**Overview**
* About Panel - Features project information
* System Panel - Information about runtime/network stats
* Weather Panel - Displays real time weather stats
* Passwords Panel - Helps determine password strength
* Pastebin Panel - [REMOVED] - May have config for end users
* Checksums Panel - Checks file integrity of files using MD5 and SHA-1
* Dictionary Panel - Displays info of a word against a local .db file
* Twitter Panel - [REMOVED] - May have config for end users
* Security Panel - Encrypt and Decrypt password protected text
* Units Panel - Convert between the Metric and Imperial system

## APIs/Libraries Used
* [Project Lombok](https://projectlombok.org/)
* [YahooWeatherAPI](https://github.com/fedy2/yahoo-weather-java-api)
* [Xerial SQLite](https://bitbucket.org/xerial/sqlite-jdbc)
* [JFree PieChart](http://www.jfree.org/jfreechart/)
* [Twitter4J](http://twitter4j.org/en/)
* [Jasypt](http://www.jasypt.org/)
* [ApacheCommons](https://commons.apache.org/)
* [CommonsCodec](https://commons.apache.org/proper/commons-codec/)
* [ZIP4J](http://www.lingala.net/zip4j/)
* [Rome](http://rometools.github.io/rome/)
* [JScience](http://jscience.org/)

## Downloads
See the "Releases" tab for compiled and ready to run versions of this software.

## Running the App
To run the application, simply double click the JAR file, or use the Java command: "java -jar MediaCommons.jar"

## Compilation
To compile your own version of MediaCommons, follow these steps:

* Download the source using Git Bash/Github etc
* Import into your IDE as a "Maven" project, or run the POM.xml from a Git command line
* Use the goal "clean package" to compile

### Requirements
* In order to successfully compile through an IDE, you must have the Lombok plugin installed
* Windows command prompt is different from Git Bash, so running Maven commands through there will not work out of the box

## License
See the "LICENSE"  file to see the license this program is written in.