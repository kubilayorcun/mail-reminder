# Mail Reminder

:exclamation: This README document contains language choice:
  - English [Starts with english section]
  - Turkish [After english section ends, turkish will continue]

:exclamation: There can be contextual inconsistencies between languages, but in overall
the core concepts and fundamental parts are translated carefully.
## Definition
This project is developed for the 'Atölye15 Internship Camp'. MailReminder's main function is 
to provide the functionality of setting custom reminders to the user. Which will be sent to
that specific user when the reminder time comes. Content of the reminder mail and the subject is solely
dependant on the user's preference. Also this application provides a 'User Registration/Login' structure 
which aims to save the user from trouble of inputting mail and phone number each time user wants to create a reminder.

### Structural Components

- This project demonstrates a multithreaded server-client paradigm. 
- As a persistent storage MailReminder relies on Sqlite which provides a light-weight relational database structure.

### Under the hood

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

#### Project Critics
##### What is done
 - Reminder check at realtime (every 1 sec.) is done properly.
 - Mail client is up and running.
 - Login/register process is added.
 - Concurrency is achieved through using multithreaded approach.
 - Multiuser is supported by handling each user with a seperate thread.
 - Mail validation at registration step is added.
 - Server always listening for new client instances that may connect.
 - Server checks for reminders in a seperate thread which runs indefinitely.

##### What could be done more
 - Passwords are stored as plain text which is not a proper way to do this.
   - Solution: This could be done by using a symmetric or asymmetric encryption algorithm (AES,
    DES, RSA).
 - The thread that checks for reminders every second is querying database table 
 every second which is not an ideal solution in a case like this. For the sake of
 demonstaring database management this is done in a way that is not so efficient. 
   - Solution: Instead of registering reminders to database they could be held dynamically
   in the memory as long as server is up running. Then if server crashes or shuts down 
   the dynamically held data could be saved to a persistent storage area which can also be retrieved if server becomes up and running again.
- If there are reminders that have exactly same date and time, because of single thread handling 
those reminder checks those reminders' mails will be sent no matter what but a few 
seconds of delay can occur since they are treated in an ordered fashion. 
  - Solution: Instead of sending those reminders' mails by the very same thread that is checking the reminders every second, 
  when a reminder is hit, that checker thread could invoke another thread and delegate the
  job of sending the mail to that very specific newly created thread.   
