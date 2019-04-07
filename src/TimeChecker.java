import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeChecker {
    Timer dbChecker;
    public TimeChecker(){
        // Instantiate a timer object.
        dbChecker = new Timer();

        // Assign timer task to timer object and schedule.
        // Delay: 0 means do not wait after instantiation.
        // Period: 1000 means check periodically at every 1000ms (1 sec.).
        dbChecker.scheduleAtFixedRate(new timerJob(), 0 , 1000);
    }
}


// This timer job's run() method executes every 1 second because of the scheduleAtFixedRate() method that is called.
class timerJob extends TimerTask {
    // Instantiate global-scope class objects.
    DatabaseOperations databaseOperations = new DatabaseOperations();
    MailSender mailSender = new MailSender();

    @Override
    public void run() {
        Date currentDate = new Date();

        // formatter object to parse incoming String data into Date.
        SimpleDateFormat formatter  = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            // Fetch the closest reminder from database, parse it into a Date object.
            Date closestReminderDate = formatter.parse(databaseOperations.getClosestReminder());
            System.out.println("\nChecked at: " + currentDate);
            System.out.println("Closest reminder is at: " + closestReminderDate);
            /** This 'if' operation needs a bit of explanation:
             *  1. getTime() method returns the Date object's value in Milliseconds.
             *  2. Divide getTime() result with 1000 to get value in Seconds.
             *  3. Subtraction operation means that, if reminder has come or passed a few seconds the result will be 0 or lower.
             *  4. In that case execute the following operations.
             * **/
            if ((closestReminderDate.getTime()/1000 - currentDate.getTime()/1000) < 0){
                // Get mail info to send mail on reminder time hit.
                ArrayList<String> reminderInfo = databaseOperations.getDataOfReminder(databaseOperations.getClosestReminder());

                // Send mail to registered reminder.
                mailSender.sendMail(reminderInfo.get(1), reminderInfo.get(2), reminderInfo.get(3));

                // Set current reminder's isSent flag to 1, to prevent double mailing.
                databaseOperations.setIsSent(reminderInfo.get(4));

                // For the sake of simulating sending sms notification:
                System.out.println("A SMS notification is sent to number : [with the same content of the mail]: " + reminderInfo.get(0));

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }



    }
}
