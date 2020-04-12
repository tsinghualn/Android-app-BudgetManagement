package com.example.badgerbudget.data.model;

import java.sql.*;


public class Connector {
    public Connection conn = null;

    /**
     * Constructor for accessing the MySQL Database. The method takes in a query and retrieves the data
     * and returns back to the function above sending the client the data
     * @param query
     */
    public Connector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Users?allowPublicKeyRetrieval=true&useSSL=false", "root", "Kangaroo1");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Will potentially create a user database from the users id that
     * contains all of their budget information.
     */
    public String createUserDB(String username) {
        String success = "Database Switched to BudgetInfo" + username;
        String failure = "Unable to switch database";
        try {
            Statement s = conn.createStatement();
            int result = s.executeUpdate("CREATE DATABASE IF NOT EXISTS BudgetInfo" + username);
            conn.setCatalog("BudgetInfo" + username);
            return success;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return failure;
        }

    }
    /**
     * Creates a table holding all of the users' login information
     * and personal information in the freshly created database for the user.
     */
    public String createUserTable() {
        try {
            String query = "CREATE TABLE IF NOT EXISTS `Users` (\n" +
                    "  `username` varchar(26) NOT NULL,\n" +
                    "  `password` varchar(26) NOT NULL,\n" +
                    "  `name` varchar(26) NOT NULL,\n" +
                    "  `birthday` varchar(10) NOT NULL,\n" +
                    "  `asnwer1` varchar(100) NOT NULL,\n" +
                    "  `answer2` varchar(100) NOT NULL,\n" +
                    "  `id` int(99) NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Table that will hold all of the user information';";
            Statement stmt = conn.createStatement();
            int resultSet = stmt.executeUpdate(query);
            System.out.println("User Table successfully created");
            System.out.println(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Could not create user table";
        }
        return "Could not create user table";
    }
    /**
     * Inserts a new user into the Users table iff the user name hasn't already been taken.
     */
    public String insertUser(String username , String password, String sq1, String sq2, String sq3, String birthday, String name) {
        try {
            boolean exists = userExists(username);
            if (!exists) { // Checks to see if the query returned that user name or not
                //If the query didn't return anything, the user hasn't been taken yet
                Statement stmt = conn.createStatement();
                String insertUser = "INSERT INTO `Users`.`users` (`username`, `password`, `securityquestion1`, `securityquestion2`, `securityquestion3`, `birthday`, `name`) VALUES ('"+ username +"', '"+ password +"', '" + sq1+"', '"+ sq2+ "', '" + sq3+ "', '" + birthday +"', '" + name + "');";
                int rs = stmt.executeUpdate(insertUser); //User is added to the database
                return "User successfully inserted";
            } else {
                return ("username already exists!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ("username already exists!");
    }
    /**
     * Checks to see if the user name exists in the table
     * @return true: if the user name exists
     * 		   false: user doesn't exist
     */
    public boolean userExists(String username) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT username FROM Users WHERE username = " + "'" + username+ "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getString(1).equals(username)) {
                    return true;
                }
            }
            return false; //Return false if it doesn't find the user in the database
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a user name and password exist in the table. This method will be used for login
     * purposes. We will be storing the hash of the correct password in the table, so we will need
     * to repeat the hash process and compare hashes.
     * @return
     */
    public String passwordCheck(String username, String password) {
        //TODO: Implement Password Hashing for next iteration
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM Users WHERE username = " + "'" + username + "' and password ='" + password + "';";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                if (rs.getString(2).equals(username) && rs.getString(3).equals(password)) {
                    return "true";
                }
            } return "Invalid Combination of Username and Password";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Could not validate information";
        }
    }


    /**
     * Will change an existing user's password, and will return false if the new password's
     * hash is the same as the existing hash in our database
     * @return
     */
    public String changePassword(String id, String password) {
        try {
            PreparedStatement ps  = conn.prepareStatement("UPDATE users SET password = ? where userid =" + id);
            ps.setString(1, password);
            ps.executeUpdate();
            ps.close();
            return "Password successfully changed";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Could not change password";
    }
    /**
     * Returns a list of the security questions from the user table
     * @return
     */
    public String getSecurityQuestions(String username, String password) {
        String questions = "";
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM Users WHERE username = " + "'" + username + "' and password ='" + password + "';";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Gets all of the security question answers into a single string making it easy to parse.
                questions += (rs.getString(4) + ";" + rs.getString(5) + "; " + rs.getString(6));

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return questions;
    }
    /**
     * Creates a table for a specific user of all their transactions
     */
    public String createUserTransactionTable() {
        String query = "CREATE TABLE IF NOT EXISTS `Transaction` (\n" +
                "  `orderId` int(10) NOT NULL AUTO_INCREMENT,\n" +
                "  `type` varchar(25) NOT NULL,\n" +
                "  `amount` varchar(100) NOT NULL,\n" +
                "  `date` varchar(15) NOT NULL,\n" +
                "  `month` varchar(15) NOT NULL,\n" +
                "	PRIMARY KEY(orderId)\n"+
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
        try {
            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(query);
            return "User Transaction Table Created";
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Unable to create User Transaction Table";
    }

    /**
     * Inserts a transaction into a specific user's transaction table
     */
    public String insertTransaction(String username, String type, String amount, String date, String month) {
        String insertTrans = "INSERT INTO `BudgetInfo" + username +"`.`transaction` ( `type`, `amount`, `date`, `month`) VALUES ('"+ type +"', '" + amount+"', '"+ date+ "', '" + month+ "');";
        try {
            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(insertTrans);
            return "Transaction inserted";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Could not insert transaction";
    }
    /**
     * Gets the list of transactions that the user has in the database
     */
    public String getTransactionList(String username) {
        String transList = "";
        String query = "SELECT * FROM Transaction;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                for (int i = 1 ; i <= 5; i++) {
                    if (i <=4) {
                        transList += rs.getString(i) + " ";
                    } else if (i == 5) {
                        transList += rs.getString(i) + ";";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transList;
    }
    /**
     * Creates a table for a specific user for all the categories that make
     * up their budget
     * @param username
     */
    public String createUserCategoryTable() {
        String query = "CREATE TABLE IF NOT EXISTS `Category` (\n" +
                "  `categoryName` varchar(25) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
        try {
            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(query);
            return "User Category Table created";
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Unable to Create User Category Table";
    }
    /**
     * Checks to make sure that the category isn't already in the database
     * @param category
     * @return
     */
    public boolean categoryExists( String category) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT categoryName FROM Category";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getString(1).equals(category)) {
                    return true;
                }
            }
            return false; //Return false if it doesn't find the user in the database
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }
    /**
     * Inserts a category into a specific user's category table
     * @param username
     * @param category
     */
    public String insertCategory(String username, String categoryName) {
        String insertCategory = "INSERT INTO `BudgetInfo" + username +"`.`Category` ( `categoryName`) VALUES ('"+ categoryName +"');";
        try {
            if (!categoryExists(categoryName)) { //Category doesn't already exist
                Statement stmt = conn.createStatement();
                int rs = stmt.executeUpdate(insertCategory);
                return "Category Successfully Added";
            } else {
                return  ("Category Already Exists. Pick a different name");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "Unable to Add Category";
        }

    }
    /**
     * Gets array representation of the categories in a user's budget
     */
    public String getCategory() {
        String categories = "";
        String query = "SELECT categoryName FROM Category";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Gets all of the users categories into a single string and returns them
                categories += rs.getString(1) +";";
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Creates a table for a specific user's goals
     */
    public String createGoalsTable() {
        String query = "CREATE TABLE IF NOT EXISTS `Goal` (\n" +
                "  `name` varchar(25) NOT NULL,\n" +
                "  `month` varchar(25) NOT NULL,\n" +
                "  `amount` varchar(25) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
        try {
            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(query);
            return "Goals Table Created";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Could not create goals table";
    }
    /**
     * Checks to see whether or not the goal the user is entering exists or not.
     * This method will be used by the insertGoal() method to make sure the user
     * can't enter the same goal twice.
     * @param goalName
     * @return
     */
    public boolean goalExists(String goalName) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT name FROM Goal";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getString(1).equals(goalName)) {
                    return true;
                }
            }
            return false; //Return false if it doesn't find the user in the database
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserts a new goal into the user's goal table
     */
    public String insertGoal(String username, String goalName, String month, String amount) {
        String insertGoal = "INSERT INTO `BudgetInfo" + username +"`.`goal` ( `name`, `month`, `amount`) VALUES ('"+ goalName +"', '" + month+"', '"+ amount+ "');";
        try {
            if (!goalExists(goalName)) {
                Statement stmt = conn.createStatement();
                int rs = stmt.executeUpdate(insertGoal);
                return "Goal Successfully Added";
            } else {
                return ("Goal Already Exists. Pick a Different Goal Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Unable to add goal";
        }
    }
    /**
     * Returns a list of the goals for a specific user
     */
    public String getGoalsList() {
        String goals = "";
        String query = "SELECT * FROM Goal";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                for (int i = 1; i < 4; i++) {
                    if (i < 3) {	goals += rs.getString(i) + " "; }
                    else {goals += rs.getString(i) + ";";}
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goals;
    }
}
