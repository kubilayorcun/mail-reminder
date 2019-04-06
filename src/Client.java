import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    public Client(ArrayList<String> cliArgs){
        try {
            socket = new Socket("localhost" , 5000);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            StringBuilder reminderInfo = new StringBuilder();

            // Concatenate input info to send all info at once.
            for (String argument : cliArgs){
                reminderInfo.append(argument).append("-");
            }
            writer.println(reminderInfo);
            writer.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> reminderInfo = new ArrayList<>();
        System.out.println("Please provide the reminder data as follows:");
        System.out.println("Mail Address: ");
        reminderInfo.add(scan.next());
        System.out.println("Subject: ");
        reminderInfo.add(scan.next());
        System.out.println("Content: ");
        reminderInfo.add(scan.next());
        System.out.println("Reminder date: [format: dd/MM/yyyy HH:mm:ss]");
        reminderInfo.add(scan.nextLine());
        new Client(reminderInfo);

    }
}
