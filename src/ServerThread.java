import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
    BufferedReader reader;
    Socket socket;
    DatabaseOperations databaseOperations;
    public ServerThread(BufferedReader reader , Socket socket){
        this.reader = reader;

        this.socket = socket;
        databaseOperations = new DatabaseOperations();
    }

    @Override
    public void run() {

        String mailInfo = "";
        try {
            while ((mailInfo = reader.readLine()) != null){
                // Parse incoming string data using '-' as regex.
                String[] mailInfoArray = mailInfo.split("-");

                // Insert incoming info to database @Reminders table.
                databaseOperations.connectToDatabase();
                databaseOperations.insertReminder(mailInfoArray[0] , mailInfoArray[1] , mailInfoArray[2] , mailInfoArray[3] , mailInfoArray[4]);
                databaseOperations.closeConnection();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
