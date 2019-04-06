import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    DatabaseOperations databaseOperations;
    public ServerThread(BufferedReader reader , PrintWriter writer , Socket socket){
        this.reader = reader;
        this.writer = writer;
        this.socket = socket;
        databaseOperations = new DatabaseOperations();
    }

    @Override
    public void run() {

        String mailInfo = "";
        try {
            while (true){
                mailInfo = reader.readLine();
                String[] mailInfoArray = mailInfo.split("-");

                // insert incoming info to database @Reminders table.
                databaseOperations.connectToDatabase();
                databaseOperations.insertReminder(mailInfoArray[0] , mailInfoArray[1] , mailInfoArray[2] , mailInfoArray[3] , mailInfoArray[4]);
                databaseOperations.closeConnection();
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
