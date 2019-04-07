import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Client {
    // TODO : @kubilay create users and authentication
    /** AND THEN YOU ARE DONE **/
    PrintWriter writer;
    Socket socket;
    ArrayList<String> clientCredentials;
    public Client(ArrayList<String> clientCredentials) {
        this.clientCredentials = clientCredentials;
        try {
            Scanner scan = new Scanner(System.in);
            socket = new Socket("localhost", 5000);
            writer = new PrintWriter(socket.getOutputStream());
            ArrayList<String> reminderInfoArray = new ArrayList<>();


            while (true) {
                reminderInfoArray.clear();
                reminderInfoArray.addAll(clientCredentials);
                System.out.println("Please provide the reminder data as follows:");
                System.out.println("Subject: ");
                reminderInfoArray.add(scan.next());
                System.out.println("Content: ");
                reminderInfoArray.add(scan.next());
                scan.nextLine();
                System.out.println("Reminder date: [format: dd/MM/yyyy HH:mm:ss]");
                String dateInput = scan.nextLine();

                // Date format check
                if (isValidDate(dateInput)) {

                    System.out.println("good format");

                    reminderInfoArray.add(dateInput);
                    StringBuilder reminderInfo = new StringBuilder();
                    System.out.println(reminderInfoArray);
                    // Concatenate input info to send all info at once.
                    for (String argument : reminderInfoArray) {
                        reminderInfo.append(argument).append("-");
                    }

                    System.out.println("Concat reminder info: " + reminderInfo);
                    writer.println(reminderInfo);
                    writer.flush();

                } else {
                    System.out.println("Bad format");
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isValidDate(String value) {
        Date date;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            date = simpleDateFormat.parse(value);
        } catch (ParseException e) {
            date = null;
        }
        if (date == null) return false;
        else return true;

    }
    
}
