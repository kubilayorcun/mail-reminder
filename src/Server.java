import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.TimerTask;

public class Server {
    BufferedReader reader;
    PrintWriter writer;
    ServerSocket serverSocket;
    DatabaseOperations databaseOperations;
    TimeChecker timeChecker;
    public Server(){
        try {
            timeChecker = new TimeChecker();

            databaseOperations = new DatabaseOperations();
            serverSocket = new ServerSocket(5000);
            while (true){
                Socket socket = serverSocket.accept();
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream());
                ServerThread serverThread = new ServerThread(reader , writer , socket);
                serverThread.start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Server();
    }
}
