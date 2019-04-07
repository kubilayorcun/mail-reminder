:exclamation: This README document contains language choice:
  - English [Starts with english section]
  - Turkish [After english section ends, turkish will continue]

:exclamation: There can be contextual inconsistencies between languages, but in overall
the core concepts and fundamental parts are translated carefully.

:exclamation: Feel free to check the code, you will see lots of detailed comment lines, telling about almost each line.

# Mail Reminder

## Definition
This project is developed for the 'Atölye15 Internship Camp'. MailReminder's main function is 
to provide the functionality of setting custom reminders to the user. Which will be sent to
that specific user when the reminder time comes. Content of the reminder mail and the subject is solely
dependant on the user's preference. Also this application provides a 'User Registration/Login' structure 
which aims to save the user from trouble of inputting mail and phone number each time user wants to create a reminder.

### Structural Behaviour

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
- Authentication is done at the client-side which is not a realistic scenario in the real life. 
  - Solution: Credentials could be sent to the server over a socket within a TCP segment (to prevent data loss and corruption since TCP is a reliable, connection oriented protocol.), which then can be authenticated and the 
  result could be sent back to the client-side.

----------------------------------------------------------------------------
# E-posta Hatırlatıcı

## Tanım
Bu proje Atölye15 Staj Kampı 2019 kapsamında verilmiş olan görevlerden birine ithafen geliştirilmiştir. Bu hatırla
tıcının ana fonksiyonlarından birisi kullanıcının kendi tercihine göre hatırlatıcılar yaratabilmesidir. Bu hatırlatıcılar
 belirlenen zaman geldiği zaman sunucu tarafından otomatik olarak hatırlatıcıyı kaydetmiş olan 
 kullanıcıya gönderilir. Hatırlatıcını e-postanın başlığı ve içeriği tamamen kullanıcının girdisi ile oluşturulur.
 . Aynı zamanda bu uygulama kullanıcıyı her hatırlatıcı kurmak istediğinde tekrar e-posta adresi ve telefon girme zahmetinden
 kurtarmak adına bir Giriş/Kayıt sistemi de içermektedir.

### Yapısal Davranış

- Bu proje çok parçacıklı (multithreaded) bir istemci-sunucu (client-server) paradigmasını uygulamaktadır.
- Kalıcı (non-volatile) veri depolamak adına bu projede herhangi bir sunucu yapısına sahip olmamasından dolayı daha hafif olduğu için
Sqlite kullanılmıştır.

### Ayrıntılı Anlatım

- Bu projede 'Sunucu' ve 'İstemci' olmak üzere iki ana konsept fonksiyonları yerine getirmekten sorumludur.
  - Sunucu: Sunucu daha önce kaydedilmiş olan hatırlatıcıları her 1 saniyede kontrol etmekle sorunludur, eğer bir hatırlatıcının
  vakti gelirse sunucu o hatırlatıcının kaydında bulunan e-posta adresine gerekli e-postayı gönderir. E-postayı gönderdikten sonra 
  sunucu kullanılmış olan hatırlatıcının bulunduğu satırın 0/1 olarak tutulan değişkenini 1 (gönderildi) olarak günceller.
  Bütün bu operasyonlardan sonra sunucu veritabanındaki hatırlatıcıları kontrol etmeye devam eder. Aynı zamanda bütün bu sunucu tara
  fında gerçekleşen işlevsellikler, sunucu başlatıldığında yine sunucu tarafından başlatılan bir iş parçacığı (thread) tarafından 
  gerçekleştirilir.
  
   - İstemci: İstemci çalıştırıldığından karşına 2 seçenek (Giriş/Kayıt) sunulur.
     - Giriş: Giriş işlemi sırasında kullanıcıdan giriş bilgileri istenir. Bilgiler veritabanı ile karşılaştırılarak kontrol edilir. 
     Eğer işlem sonucunda bir tutarsızlık olmazsa kullanıcı hatırlatıcı girebileceği kısma yönlendirilir. Bütün bunlar istemci-tarafında 
     gerçekleştirilir.
     - Kayıt: Kayıt işlemi sırasında kullanıcıdan bilgileri tek tek alınır. Bütün bilgiler alındıktan sonra, bu bilgiler arasında olan
     kullanıcının e-postasına bir adet 'Onay E-postası' gönderilir. Eğer sonrasında kullanıcı bu e-postanın içerisinde almış olmuş
     onay kodunu doğru bir şekilde girerse kullanıcı sisteme kaydedilir ve uygulamanın en başına yönlendirilir.

#### Proje Eleştirileri
##### Yapılanlar
 - Her saniye hatılatıcılar kontrol ediliyor.
 - E-posta servisi sorunsuz bir şekilde çalışıyor.
 - Giriş/Kayıt aşamaları eklendi.
 - Çoklu işparçacığı (multithreaded) bir yapı kullanılarak paralellik sağlanmış oldu.
 - Çoklu kullanıcı desteği her bir kullanıcıya bir iş parçacığı (thread) atayarak sağlandı.
 - Kayıt sırasında e-posta ile doğrulama sistemi eklendi. 
 - Sunucu açık olduğu sürece yeni bağlanma ihtimali olan kullanıcıları dinlemeye devam ediyor.
 - Sunucu tarafında hatırlatıcı zamanlarının kontrolü yine sunucu tarafından başlatılan bir iş parçacığına (thread) atandı. 

##### Geliştirilebilecekler
 - Kullanıcı parolaları olduğu gibi açık halde veritabanınca tutuluyor. Güvenlik açısından uygun bir yöntem değil.
   - Çözüm: Parolaları şifreleyerek veritabanına kaydetmek bir çözüm. Bu şifreleme işlemi ise simetrik (DES,AES) veya
   asimetrik (RSA) şifreleme algoritmalatı kullanılarak uygulanabilir.
 - Hatırlatıcıları her saniye kontrol eden iş parçacığı (thread) bu kontrolü her seferinde veritabanına sorgu atarak yapmakta,
 bu yol çözümler arasında en iyilerden değil. Ancak veritabanı yönetimi ve sorgusu gibi işlemleri de gösterebilmek adına bu proje
 kapsamında bu şekilde gerçekleştirildi.
   - Çözüm: Bütün hatırlatıcıların her yeni hatırlatıcı geldiğinde veritabanına kaydedilmesi yerine dinamik şekilde bilgisayarın hafızasında
   tutulabilirler. Eğer bu durumda sunucu kapanırsa veya kapatılırsa dinamik verilerin tamamı kalıcı bir veritabanına veya dosyaya 
   yazılabilir, böylece hiçbir kullanıcı sunucu minör çöküşler yaşasa dahi daha önce kurdukları hatırlatıcılarını kaybetmemiş olurlar.
- Eğer veritabanında tam olarak aynı gün ve saatte ayaarlanmış olan hatırlatıcılar varsa bu hatırlatıcıların e-postaları gönderilmeye
çalışıldığı sırada, sıranın devamında gelmekte olan aynı saniyedeki hatırlatıcıların birkaç saniye gecikmesine sebep olabilirler. 
  - Çözüm: Bu hatırlatıcıların e-postalarını, hatırlatıcıları kontrol eden iş parçacığında (thread) göndermek yerine e-posta gönderme
  işlemi hatırlatıcıları kontrol eden iş parçacığı (thread) tarafından başka bir iş parçacığına (thread) atanabilir. Böylece küçük
  gecikmelerin önüne geçilebilir.






