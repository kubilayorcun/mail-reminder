import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // For in-class use, initialize global references.
    private BufferedReader reader;
    private ServerSocket serverSocket;
    private DatabaseOperations databaseOperations;
    private TimeChecker timeChecker;

    public Server(){
        try {
            // Start timeChecker thread in order to start checking reminders in database table 'Reminders'.
            timeChecker = new TimeChecker();

            databaseOperations = new DatabaseOperations();
            serverSocket = new ServerSocket(5000);
            while (true){

                // Server instance gets stuck at the accept() method. If any connection occurs
                // moves on to execute the next line. After that because of the while loop, comes back to accept() for waiting for new incoming connections.
                Socket socket = serverSocket.accept();

                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Instantiate a serverThread object with the respective streams and socket. Which will listen for one client.
                ServerThread serverThread = new ServerThread(reader , socket);
                // Invoke serverThread
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
