import java.util.Random;
import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("WELCOME TO MAIL REMINDER");
        System.out.println("________________________");
        System.out.println("Please choose one option below: [Enter the number and press enter]");
        System.out.println("1- Login \n 2- Register");
        int option = scan.nextInt();
        if (option == 1){
            // TODO : @kubi -> Get user credentials - Encrypt password - Authentication with database - Login the user - Initialize new client which in turn instantiates a serverThread instance.
        }
        else if(option == 2){
            // TODO : @kubi -> Get user credentials - Send verification code - Authenticate verification code - if success [Register user] - else [Prompt error message send to beginning].
        }
        else {
            System.out.println("INVALID OPTION [!]");
        }
    }
}
