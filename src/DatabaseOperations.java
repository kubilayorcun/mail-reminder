import java.sql.*;
import java.util.ArrayList;

public class DatabaseOperations {

    private Connection connection;

    public DatabaseOperations() {
        connectToDatabase();
        String createQuestionsTable = "CREATE TABLE IF NOT EXISTS Reminders (\n"
                + " id integer primary key,\n"
                + " phone text not null,\n"
                + " mail text not null,\n"
                + " subject text not null,\n"
                + " content text not null,\n"
                + " reminder text not null,\n"
                + " isSent text default '0'\n"
                + ");";

        String createUsersTable = "CREATE TABLE IF NOT EXISTS Users (\n"
                + " id integer primary key,\n"
                + " mail text not null,\n"
                + " phone text not null,\n"
                + " password text not null\n"
                + ");";
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute(createQuestionsTable);
            statement.execute(createUsersTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean connectToDatabase() {
        try {
            String connectionUrl = "jdbc:sqlite:mailReminderApp.db";
            connection = DriverManager.getConnection(connectionUrl);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void insertReminder(String phone, String mail , String subject, String content, String reminder) {
        String sql = "INSERT INTO Reminders(phone,mail,subject,content,reminder) VALUES(?,?,?,?,?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, phone);
            pstmt.setString(2, mail);
            pstmt.setString(3, subject);
            pstmt.setString(4, content);
            pstmt.setString(5, reminder);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getClosestReminder() {
        String query = "SELECT reminder from Reminders WHERE isSent = '0' ORDER BY reminder DESC ";
        String closestDate = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                closestDate = resultSet.getString("reminder");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return closestDate;
        }
    }


    public ArrayList<String> getDataOfReminder(String reminder){
        ArrayList<String> reminderInfo = new ArrayList<>();
        String query = "SELECT * FROM Reminders WHERE reminder = ? AND isSent = '0'";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,reminder);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                reminderInfo.add(resultSet.getString("phone"));
                reminderInfo.add(resultSet.getString("mail"));
                reminderInfo.add(resultSet.getString("subject"));
                reminderInfo.add(resultSet.getString("content"));
                reminderInfo.add(Integer.toString(resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reminderInfo;
    }

    public void setIsSent(String id){
        String query = "UPDATE Reminders SET isSent = '1' WHERE id = ?";

        try {
            PreparedStatement preparedStatement  = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String mail , String password){
        String query="SELECT password FROM Users WHERE mail = ?";
        String registeredPassword = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1 , mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                registeredPassword = resultSet.getString("password");
            }

            if (registeredPassword.equals(password)) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public String getUserPhone(String mail){
        String query = "SELECT phone FROM Users WHERE mail = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,mail);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                return resultSet.getString("phone");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
