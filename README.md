# BudgetManagementApplication

Here are some instructions on how to run our application! After you have cloned our repository...
1. Download Android Studio, if you do not already. You can download Android Studio from the following link: https://developer.android.com/studio
2. Open the project titled "BadgerBudget" in Android Studio. You should be able to see the code for our application that we have completed at this point. There are 2 "com.example.badgerbudget" folders, one with just the file "BuildConfig" in it, and the other with our code. You can navigate to the second folder via the path app > java > com.example.badgerbudget. 
3. To build our application, go to Build -> Make Project. This should run without any issues.
4. Now to run our application, you will need an Android Virtual Device (AVD). Follow the instructions located at https://developer.android.com/studio/run/managing-avds to create an AVD. 
5. Now, you should be able to hit the green triangle near the top left of the screen in Android Studio, and it can run on the AVD that you created earlier. You should be able to see the first page of our application, but not much else, as are are currently in early development of the application!
6. To see the other UI pages that we have developed but not linked to the application, navigate to the "layout" folder via the following path: app > resources > layout. This will contain xml files that when opened in Android Studio will show you what the other UI pages that we have developed.
To get the backend started, download MySQL
Select your OS from the dropdown bar: https://dev.mysql.com/downloads/mysql/
Download MySQL Workbench
Select your OS from the dropdown bar: https://dev.mysql.com/downloads/workbench/
Download the MySQL Driver so that the connector can run properly on your machine. 
https://dev.mysql.com/downloads/connector/j/5.1.html
Select Platform Independent here as we’re putting it in Android Studio. 
Once the download is complete, the file we need to put into Android Studio is called: Mysql-connector-java-5.1.48-bin.jar
Afterwards, since we need to run the server separately from the Android Studio Project, we need to place the connector in a separate IDE, we used Eclipse, along with the Server and Connector classes. 
Since we used Eclipse, here’s how to do it in Eclipse: 
First, we need the server and connector classes from github. Grab those and make a new Eclipse project with them.
After we have a project in Eclipse right click on your project go to ‘Build Path’ and click ‘Configure Build Path’. Click on ‘Classpath’ to highlight it and then click on ‘Add External JARS…’
Just as a check before you start the server, make sure that the port is set to “6868” as that is what we used for the client in Android Studio. 
Make sure that the server is already running when you run the Android Studio project. 
Should be able to access the app with database after this.
