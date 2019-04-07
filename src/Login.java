import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
        DatabaseOperations databaseOperations = new DatabaseOperations();
        Scanner scan = new Scanner(System.in);
        MailSender mailSender = new MailSender();
        while (true) {

            // Greet the user.
            System.out.println("\nWELCOME TO MAIL REMINDER");
            System.out.println("________________________");
            System.out.println("\nPlease choose one option below: [Enter the number and press enter]");
            System.out.print("1)Login\n2)Register\nOption: ");

            // Get the option preference.
            int option = scan.nextInt();

            if (option == 1) {
                System.out.println("Provide necessary credentials as prompted: ");
                System.out.print("E-mail: ");
                String mail = scan.next();
                System.out.print("Password: ");
                String password = scan.next();

                // Connect to database
                databaseOperations.connectToDatabase();

                // Check user info / Authentication
                if (databaseOperations.authenticateUser(mail, password)) {
                    ArrayList<String> userCredentials = new ArrayList<>();

                    // Retrieve user's respective phone and add it to credentials.
                    userCredentials.add(databaseOperations.getUserPhone(mail));

                    // Close database connection in order not to cause any locks on database connection.
                    databaseOperations.closeConnection();

                    // Add mail info to credentials.
                    userCredentials.add(mail);

                    // Pass the userCredentials to Client object's constructor in order to use it in Client class.
                    new Client(userCredentials);
                }

            } else if (option == 2) {
                // Registration process invoked.
                System.out.println("Provide necessary credentials as prompted: ");
                System.out.print("Phone: ");
                String phone = scan.next();
                System.out.print("Email: ");
                String mail = scan.next();
                System.out.print("Password: ");
                String password = scan.next();

                System.out.print("Enter the verification code that has been sent to " + mail+"\n[It may take few seconds for you to get the e-mail.]: ");

                // Send confirmation mail to check if mail is valid.
                mailSender.sendConfirmationMail(mail);

                // Retrieve the verification that has been mailed to user..
                String sentVerificationCode = mailSender.getVerificationCode();

                // Get the verification code input from user.

                String inputtedVerificationCode = scan.next();

                // Check if verification codes hold.
                if (inputtedVerificationCode.equals(sentVerificationCode)) {
                    // Register new user
                    databaseOperations.insertUser(mail, phone, password);
                    databaseOperations.closeConnection();
                    System.out.println("Now you can login with your credentials, redirecting you to login process.");
                } else {
                    System.out.println("VERIFICATION CODE IS INVALID [!]");
                }
            } else {
                System.out.println("INVALID OPTION [!]");
            }
        }

    }
}
