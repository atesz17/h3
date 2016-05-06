Chat Application
-------

Description:
  Sample home work application for using code instrumentation, logging, etc.

Author:
  Benedek Horváth
  Budapest University of Technology and Economics
  Intelligent System Management course (VIMIA370)
  https://www.inf.mit.bme.hu/edu/bsc/irf

History
  Last updated: 2016.03.13.

Usage:
  0. Requirements: Java 1.7
  
  1. Running the application:
  java -jar ChatApplication.jar <port> <configxml>
  
  2. Login requires a username and a password. Both of them should be typed 
  in the console as plain text. Default password for the users, already stored
  in the config.xml, is the lowercase monogram of their names.
  
  3. Registered users and passwords are stored in the config.xml file. 
  Please be advised, that the passwords should be Base64 encoded, 
  so if you add extra users, make sure their passwords are so!
  
Development
  Include the dist/util.jar to the project in the IDE (e.g. Eclipse, NetBeans) 
  you are using, because the required libraries are there.