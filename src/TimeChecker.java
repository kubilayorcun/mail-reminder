import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeChecker {
    DatabaseOperations databaseOperations;
    Timer dbChecker;
    public TimeChecker(){
        databaseOperations = new DatabaseOperations();
        dbChecker = new Timer();
        dbChecker.scheduleAtFixedRate(new timerJob(), 0 , 1000);
    }
}



class timerJob extends TimerTask {
    DatabaseOperations databaseOperations = new DatabaseOperations();
    MailSender mailSender = new MailSender();

    @Override
    public void run() {
        Date currentDate = new Date();
        Thread.currentThread().setName("Reminder checker");
        System.out.println(Thread.currentThread().getName() + " checked the database at: " + currentDate);
        SimpleDateFormat formatter  = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            System.out.println("Closest reminder: "+databaseOperations.getClosestReminder());
            Date closestReminderDate = formatter.parse(databaseOperations.getClosestReminder());

            // Check if reminder's time has came or not :
            if ((closestReminderDate.getTime()/1000 - currentDate.getTime()/1000) < 0){
                // Get mail info to send mail on reminder time hit.
                ArrayList<String> reminderInfo = databaseOperations.getDataOfReminder(databaseOperations.getClosestReminder());

                // Send mail to registered reminder.
                mailSender.sendMail(reminderInfo.get(0), reminderInfo.get(1), reminderInfo.get(2));

                // Delete reminder to prevent double mailing.
                databaseOperations.deleteReminder(databaseOperations.getClosestReminder());

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }



    }
}

class ReminderThread extends Thread {
        DatabaseOperations dbOperations = new DatabaseOperations();
        MailSender mailSender = new MailSender();

    @Override
    public void run() {

    }
}
