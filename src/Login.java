import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
        DatabaseOperations databaseOperations = new DatabaseOperations();
        Scanner scan = new Scanner(System.in);
        MailSender mailSender = new MailSender();
        while (true){

            System.out.println("WELCOME TO MAIL REMINDER");
            System.out.println("________________________");
            System.out.println("Please choose one option below: [Enter the number and press enter]");
            System.out.println("1- Login \n 2- Register");

            int option = scan.nextInt();

            if (option == 1){
                // TODO : @kubi -> Get user credentials - Encrypt password - Authentication with database - Login the user - Initialize new client which in turn instantiates a serverThread instance.
                System.out.println("Provide necessary credentials as prompted: ");
                System.out.print("E-mail: ");
                String mail = scan.next();
                System.out.println("Password: ");
                String password = scan.next();
                databaseOperations.connectToDatabase();

                if (databaseOperations.authenticateUser(mail , password)){
                    ArrayList<String> userCredentials = new ArrayList<>();
                    userCredentials.add(databaseOperations.getUserPhone(mail));
                    databaseOperations.closeConnection();
                    userCredentials.add(mail);
                    // Login user
                    new Client(userCredentials);
                }

            }

            else if(option == 2){
                // TODO : @kubi -> Get user credentials - Send verification code - Authenticate verification code - if success [Register user] - else [Prompt error message send to beginning].
                System.out.println("Provide necessary credentials as prompted: ");
                System.out.println("Phone: ");
                String phone = scan.next();
                System.out.println("Email");
                String mail = scan.next();
                System.out.println("Password");
                String password = scan.next();

                // Send confirmation mail to check if mail is valid.
                mailSender.sendConfirmationMail(mail);

                String sentVerificationCode = mailSender.getVerificationCode();

                System.out.println("Enter the verification code that has been sent to: " + mail);
                String inputtedVerificationCdde = scan.next();

                if (inputtedVerificationCdde.equals(sentVerificationCode)){
                    // Register new user
                    databaseOperations.insertUser(mail , phone , password);
                    databaseOperations.closeConnection();
                    System.out.println("Now you can login with your credentials, redirecting you to login process.");
                }
                else {
                    System.out.println("Verification code is invalid");
                }
            }

            else {
                System.out.println("INVALID OPTION [!]");
            }
        }

    }
}
