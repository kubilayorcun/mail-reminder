## Mail Reminder
### Definition
This project is developed for the 'Atölye15 Internship Camp'. MailReminder's main function is 
to provide the functionality of setting custom reminders to the user. Which will be sent to
that specific user when the reminder time comes. Content of the reminder mail and the subject is solely
dependant on the user's preference. Also this application provides a 'User Registration/Login' structure 
which aims to save the user from trouble of inputting mail and phone number each time user wants to create a reminder.

#### Structural Components

- This project demonstrates a multithreaded server-client paradigm. 
- As a persistent storage MailReminder relies on Sqlite which provides a light-weight relational database structure.

#### Under the hood

- There are two core concepts in this project which are implemented through 'Client' and 'Server'.
  - Server: Server is responsible for checking previously set reminders every one second
 and sends the mail respectively. After sending the mail, server also sets that specific reminder's
 flag (which is held in the database) to 1 (which represents the executedFlag). After these operations server also 
 keeps checking the reminders every one second. All those operations done by a 'CheckerThread' 
 that is instantiated and invoked at the server side right after project ran.
   - Client: Client will be welcomed with a login page which asks to choose one of two options (Login/Register).
     - Login: In the login sequence user is asked to enter his/her credentials. Authentication
     occurs in the client side for the sake of simplicity. 
     - Registration: In the registration process user is asked to enter his/her credential one by one.
     After inputting all necessary credentials, a confirmation mail with a verification number is sent
     to the user, then user is asked to input the verification number that has been sent. If the input can be verified
     then user is redirected to the initial login/register process and the credentials are registered successfully,
     otherwise user will be prompted with a error message.
