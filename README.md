# BudgetManagementApplication

# Implement the server

First, you need to implement the server.

1. Download MySQL

a) Select your OS from the dropdown bar: https://dev.mysql.com/downloads/mysql/

b) When you download MySQL, make sure it’s started and that you create a password for the root user of your database. Make sure to remember that password as you’ll need to insert it into line 15 of the Connector class in the string right after “root”. 

2. Download MySQL Workbench

a) Select your OS from the dropdown bar: https://dev.mysql.com/downloads/workbench/

3. Download the MySQL Driver so that the connector can run properly on your machine. 

a) https://dev.mysql.com/downloads/connector/j/5.1.html

b) Select Platform Independent here as we’re putting it in Eclipse.

c) In addition, we'll need jbcrypt in Eclipse as well as we're hashing the passwords for a more secure login. 

d) To download click the link: https://jar-download.com/artifacts/org.mindrot/jbcrypt/0.4/source-code

4. Once the download is complete, the files we need to put into Eclipse are called: 
Mysql-connector-java-5.1.48-bin.jar and jbcrypt-0.4.jar

5. Afterwards, since we need to run the server separately from the Android Studio Project, we need to place the connector in a separate IDE, we used Eclipse, along with the Server and Connector classes. 

a) Since we used Eclipse, here’s how to do it in Eclipse: 

b) First, we need the server and connector classes from Github. Grab those and make a new Eclipse project with them. The repo for the Server and Connector classes can be found here: https://github.com/AndySupreme/ServerClient

c) After we have a project in Eclipse right click on your project go to ‘Build Path’ and click ‘Configure Build Path’. Click on ‘Classpath’ to highlight it and then click on ‘Add External JARS…’ add both of the files specified above as References Libraries

d) Just as a check before you start the server, make sure that the port is set to “6868” as that is what we used for the client in Android Studio. The client 10.0.2.2, which is the local emulator host for Android Studio. 

e) Make sure that the server is already running when you run the Android Studio project. 

f) Should be able to access the app with database after this.

6. Before you run it, since you’re required to log into MySQL with a database, we need to make one. 

7. Well call it ‘User’. Click the create database button in MySQL and create the new schema. Then we need tables for userID, username, password, security question 1, security question 2, security question 3, birthday, and name. After that we’re good to go.  

8. You now should be able to start the server in Eclipse or another IDE of your choice with the Driver in the classpath and then run the Android Studio app. 

# Run the app

Here are some instructions on how to run our application! After you have cloned our repository...
1. Download Android Studio, if you do not already. You can download Android Studio from the following link: https://developer.android.com/studio
2. Open the project titled "BadgerBudget" in Android Studio. You should be able to see the code for our application that we have completed at this point. There are 2 "com.example.badgerbudget" folders, one with just the file "BuildConfig" in it, and the other with our code. You can navigate to the second folder via the path app > java > com.example.badgerbudget. 
3. To build our application, go to Build -> Make Project. This should run without any issues.
4. Now to run our application, you will need an Android Virtual Device (AVD). Follow the instructions located at https://developer.android.com/studio/run/managing-avds to create an AVD. 
5. Now, you should be able to hit the green triangle near the top left of the screen in Android Studio, and it can run on the AVD that you created earlier. You should be able to see the first page of our application, the login page.

6. The only other thing that is needed to be done is for our UI Testing located in the filepath java/com.example.badgerbudget (androidTest)/ui.login is change the username and password for all the tests to a user you have created. Alternatively, you can also create a user in the application with username and password that are in the test cases. After one of these is done, the UI Tests should run smoothly. 

# Testings
We used Espresso and JUnit testing to test the application and server.

If you want to try running Espresso UI testing,

1. Create a new user: 
username: testuser, password: testuser
	
2. Create a new user with sample data
- Username: user00, password: pass0000
- Sample data can be imported by running the following queries on MySQL Workbench
- QUERIES:



Make sure you are running the server before running the tests.

You can find the summary of code coverage in:
BudgetManagementApplication\app\build\reports\coverage\debug\index.html

The summary of Espresso UI Test

The summary of JUnit Server Test

The summary of JUnit Backend Test



