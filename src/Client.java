import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Client {
    private PrintWriter writer;
    private Socket socket;
    private ArrayList<String> clientCredentials;

    public Client(ArrayList<String> clientCredentials) {
        // Get client credentials and assign them to previously defined global variable.
        this.clientCredentials = clientCredentials;

        try {
            Scanner scan = new Scanner(System.in);
            socket = new Socket("localhost", 5000);
            writer = new PrintWriter(socket.getOutputStream());
            ArrayList<String> reminderInfoArray = new ArrayList<>();

            // Infinite loop to keep the process going.
            while (true) {

                // Clear reminderInfoArray to prevent any previously added data.
                reminderInfoArray.clear();

                // Add clientCredentials that are coming from login phase to infoArray.
                reminderInfoArray.addAll(clientCredentials);

                // Inputting reminder data.
                System.out.println("\nPlease provide the reminder data as follows:");
                System.out.print("Subject: ");
                reminderInfoArray.add(scan.next());
                System.out.print("Content: ");
                reminderInfoArray.add(scan.next());
                scan.nextLine();
                System.out.print("Reminder date [format: dd/MM/yyyy HH:mm:ss]: ");
                String dateInput = scan.nextLine();

                // Date format check
                if (isValidDate(dateInput)) {

                    reminderInfoArray.add(dateInput);

                    StringBuilder reminderInfo = new StringBuilder();



                    // Concatenate input info to send all info at once.
                    for (String argument : reminderInfoArray) {
                        // This minus sign is there, in order to parse the incoming string at the server side.
                        reminderInfo.append(argument).append("-");
                    }

                    // Send concatenated info through TCP socket.
                    writer.println(reminderInfo);
                    writer.flush();

                } else {
                    System.out.println("Invalid date format. Format should be: dd/MM/yyyy HH:mm:ss\nExample: 30/12/2019 12:45:59");
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Validate the inputted date format.
    private boolean isValidDate(String value) {
        Date date;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            date = simpleDateFormat.parse(value);
        } catch (ParseException e) {
            // If inputted string is not properly written, parse() method will throw an exception.
            date = null;
        }
        return date != null;

    }
    
}
