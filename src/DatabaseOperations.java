import java.sql.*;
import java.util.ArrayList;

public class DatabaseOperations {

    private Connection connection;

    public DatabaseOperations() {
        connectToDatabase();
        String createQuestionsTable = "CREATE TABLE IF NOT EXISTS Reminders (\n"
                + " id integer primary key,\n"
                + " mail text not null,\n"
                + " subject text not null,\n"
                + " content text not null,\n"
                + " reminder text not null\n"
                + ");";
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute(createQuestionsTable);
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

    public void insertReminder(String mail , String subject, String content, String reminder) {
        String sql = "INSERT INTO Reminders(mail,subject,content,reminder) VALUES(?,?,?,?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, mail);
            pstmt.setString(2, subject);
            pstmt.setString(3, content);
            pstmt.setString(4, reminder);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getClosestReminder() {
        String query = "SELECT reminder from Reminders ORDER BY reminder DESC ";
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

    public void deleteReminder(String reminder){
        String query="DELETE FROM Reminders WHERE reminder = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1 , reminder);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getDataOfReminder(String reminder){
        ArrayList<String> reminderInfo = new ArrayList<>();
        String query = "SELECT * FROM Reminders WHERE reminder = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,reminder);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                reminderInfo.add(resultSet.getString("mail"));
                reminderInfo.add(resultSet.getString("subject"));
                reminderInfo.add(resultSet.getString("content"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reminderInfo;
    }


}
