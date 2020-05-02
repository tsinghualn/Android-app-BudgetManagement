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
```
SELECT * FROM users.users;
# INSERT USER Mannually in the application (to make a password encrypted)

###############DONT FORGET TO CHANGE USERNAME!!! budgetinfo_USERID_ ####################
###############user00 -> your userID #######################

#category table
SELECT * FROM budgetinfouser00.category;
# INSERT INTO budgetinfouser00.category (categoryName, budget, month, year) VALUES ();
INSERT INTO budgetinfouser00.category (categoryName, budget, month, year) VALUES ("Book", "100", "November", "2019"), ("Coffee", "100", "November", "2019"), 
("salary", "0", "November", "2019"), ("Transportation", "100", "November", "2019");

# transaction table 
SELECT * FROM budgetinfouser00.transaction;
# INSERT INTO budgetinfouser00.transaction (type, amount, date, month, year, category, note) VALUES ();
# DELETE FROM budgetinfouser00.transaction WHERE orderId > 100 ;
# transaction for November 19
## both income and expense transaction exsit
INSERT INTO budgetinfouser00.transaction (type, amount, date, month, year, category, note) VALUES ("Expense", "24.56", "11/1/19", "November", "2019", "Food", "Lunch"),
("Expense", "5.42", "11/3/19", "November", "2019", "Coffee", "NULL"), ("Expense", "12.45", "11/5/19", "November", "2019", "Transportation", "Uber"),
("Expense", "50.23", "11/5/19", "November", "2019", "Clothes", "Pants"), ("Income", "-200", "11/14/19", "November", "2019", "salary", "extraSalary"),
("Expense", "60.59", "11/16/19", "November", "2019", "Book", "English"), ("Expense", "20.21", "11/20/19", "November", "2019", "Food", "Dinner"), 
("Expense", "10.25", "11/25/19", "November", "2019", "Book", "NULL"), ("Income", "-100", "11/28/19", "November", "2019", "salary", "extraSalary");


# transaction for December 19
## both income and expense transaction exsit
## no expense on book category
# 
INSERT INTO budgetinfouser00.transaction (type, amount, date, month, year, category, note) VALUES ("Expense", "21.26", "12/1/19", "December", "2019", "Food", "Lunch"),
("Expense", "8.42", "12/3/19", "December", "2019", "Coffee", "NULL"), ("Expense", "24.45", "12/5/19", "December", "2019", "Transportation", "Uber"),
("Expense", "89.23", "12/5/19", "December", "2019", "Clothes", "Pants"), ("Income", "-250", "12/10/19", "December", "2019", "salary", "extraSalary"), 
("Expense", "32.59", "12/16/19", "December", "2019", "Food", "Steak"), ("Expense", "18.21", "12/20/19", "December", "2019", "Food", "Dinner"), 
("Expense", "24.32", "12/21/19", "December", "2019", "Coffee", "powder"), ("Expense", "8.21", "12/21/19", "December", "2019", "Transportation", "Bus"), 
("Expense", "39.25", "12/25/19", "December", "2019", "Groceries", "NULL"), ("Income", "-100", "12/28/19", "December", "2019", "salary", "extraSalary");

# transaction for January 20
## Income type transaction does not exist
INSERT INTO budgetinfouser00.transaction (type, amount, date, month, year, category, note) VALUES ("Income", "-300", "01/10/20", "January", "20", "salary", "extraSalary"),
("Expense", "23.32", "01/16/20", "January", "2020", "Food", "Steak"), ("Expense", "36.21", "01/20/20", "January", "2020", "Food", "Dinner"), 
("Expense", "10.24", "01/21/20", "January", "2020", "Coffee", "powder"), ("Expense", "2.21", "01/21/20", "January", "2020", "Transportation", "Bus");


# transaction for February 20
INSERT INTO budgetinfouser00.transaction (type, amount, date, month, year, category, note) VALUES ("Income", "-150", "02/10/20", "February", "20", "salary", "extraSalary"),
("Expense", "23.32", "02/16/20", "February", "2020", "Food", "Steak"), ("Expense", "36.21", "02/20/20", "February", "2020", "Food", "Dinner"), 
("Expense", "10.24", "02/21/20", "February", "2020", "Coffee", "powder"), ("Expense", "2.21", "02/21/20", "February", "2020", "Transportation", "Bus");


# transaction for March 20
## both income and expense transaction exsit
INSERT INTO budgetinfouser00.transaction (type, amount, date, month, year, category, note) VALUES ("Expense", "50.24", "03/1/20", "March", "2020", "Food", "Lunch"),
("Expense", "9.42", "03/3/20", "March", "2020", "Coffee", "NULL"), ("Expense", "35.62", "03/5/20", "March", "2020", "Transportation", "Uber"),
("Expense", "150.2", "03/5/20", "March", "2020", "Clothes", "Pants"), ("Income", "-50", "03/10/20", "March", "2020", "salary", "extraSalary"), 
("Expense", "23.32", "03/16/20", "March", "2020", "Food", "Steak"), ("Expense", "36.21", "03/20/20", "March", "2020", "Food", "Dinner"), 
("Expense", "10.24", "03/21/20", "March", "2020", "Coffee", "powder"), ("Expense", "2.21", "03/21/20", "March", "2020", "Transportation", "Bus"), 
("Expense", "60.66", "03/25/20", "March", "2020", "Groceries", "NULL"), ("Income", "-200", "03/28/20", "March", "2020", "salary", "extraSalary");

# transaction for April 20
## both income and expense transaction exsit
INSERT INTO budgetinfouser00.transaction (type, amount, date, month, year, category, note) VALUES ("Expense", "52.35", "04/1/20", "April", "2020", "Food", "Lunch"),
("Expense", "3.41", "04/3/20", "April", "2020", "Coffee", "NULL"), ("Expense", "25.63", "04/5/20", "April", "2020", "Transportation", "Uber"),
("Expense", "50.2", "04/5/20", "April", "2020", "Clothes", "Pants"), ("Income", "-100", "04/10/20", "April", "2020", "salary", "extraSalary"), 
("Expense", "13.32", "04/16/20", "April", "2020", "Food", "Steak"), ("Expense", "48.21", "04/20/20", "April", "2020", "Food", "Dinner"), 
("Expense", "24.24", "04/21/20", "April", "2020", "Coffee", "powder"), ("Expense", "10.21", "04/21/20", "April", "2020", "Transportation", "Bus"), 
("Expense", "88.66", "04/25/20", "April", "2020", "Groceries", "NULL"), ("Expense", "13.31", "04/26/20", "April", "2020", "Coffee", "NULL"),
("Income", "-250", "04/27/20", "April", "2020", "salary", "extraSalary"), ("Expense", "43.13", "04/28/20", "April", "2020", "Clothes", "NULL"),
("Expense", "38.24", "04/29/20", "April", "2020", "Book", "Textbook"), ("Expense", "8.21", "04/29/20", "April", "2020", "Food", "Restaurant"),
("Expense", "24.13", "04/30/20", "April", "2020", "Book", "Textbook2"), ("Expense", "4.21", "04/30/20", "April", "2020", "Coffee", "Starbucks");
```

Make sure you are running the server before running the tests.

You can find the summary of code coverage in:
BudgetManagementApplication\app\build\reports\coverage\debug\index.html

The summary of Espresso UI Test
![UItestSummary](https://user-images.githubusercontent.com/50937419/80853405-468bbc80-8bf6-11ea-8810-9b05be207d5d.JPG)

The summary of JUnit Server Test
![ServerClientTestSummary](https://user-images.githubusercontent.com/50937419/80853374-ff9dc700-8bf5-11ea-802a-f24048b3e994.JPG)


The summary of JUnit Backend Test: tests that are not linked with the interface
![BackendTestSummary](https://user-images.githubusercontent.com/50937419/80853431-7aff7880-8bf6-11ea-9fe4-d3c92f13a609.JPG)



