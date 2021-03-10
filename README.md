# Time-off Manager (ToM)

## Rășinar Ioan-Traian

## Căliman Andrei-Bogdan

### Descriere generală

ToM este o aplicație WEB de gestiune a zilelor de concediu ale angajaților tuturor departamentelor unei firme, fiind folosită strict pentru uz intern.
Ea este administrată de departamentul de IT al firmei, care asigură mentenața și înregistrarea de conturi de autentificare ale angajaților, solicitate de departamentul de HR.

### Roluri la autentificare:

-   Angajat (pentru care se cunoaște liderul de echipă și departamenul în care lucrează)
-   Reprezentant HR
-   Reprezentant IT

### Reprezentant HR:

După procesarea datelor unui angajat, prin intermediul aplicației, echipa de HR îi va solicita echipei de IT să creeze un cont pentru angajatul respectiv.
Toate solicitările de concediu ale angajaților, înaintate de liderii de echipă ai firmei, vor fi procesate de către departamentul de HR. Anagajatul va primi ulterior datele necesare pentru concediu, tot prin intermediul aplicației.
De precizat este faptul că și reprezentanții HR dispun de funcționalitățile aplicației la fel ca un angajat.

### Reprezentant IT:

Scopul acestui rol este de a asigura mentenanta platformei.
Crearea conturilor pentru angajați se face automat pe baza datelor personale primite de la HR, iar credentialele vor fi trimise automat prin mail către viitorul angajat. Parola și username-ul se vor genera pe loc, vor fi transmise angajatului, care are obligația de a-și alege o noua parolă la prima autentificare.
De precizat este faptul că și reprezentanții IT dispun de funcționalitățile aplicației la fel ca un angajat (Deși au rolul de mentenanță, nu au drept de decizie pentru validarea concediilor).

### Angajat:

Când un angajat primește credențialele contului, aceste va fi obligat să își seteze o nouă parolă
Fiecare angajat poate solicita prin intermediul aplicației tipul de concediu (medical,situații familiale speciale, home-office, odihnă) dorite pe o anumită perioadă. Cererea este înaintată liderului de echipă, care o poate accepta sau nu (în ambele cazuri, solicitantul este notificat), printr-o interfață grafică dedicată. Dacă solicitantul este un lider de echipă, trebuie delegat un nou lider, deci solicitarea trebuie aprobată de către liderul său de echipă („liderul liderului”) și acceptată de persoana delegată. Dacă o solicitare este acceptată, aceasta va fi trimisă departamentului de HR.

### Functionalitati:

-   autentificare
-   schimbare parola
-   trimiterea credentialelor pe mail
-   creare cerere de concediu
-   acceptare/respingere cerere de către team leader
-   posibilitatea de feedback pentru cereri
-   vizualizarea starii cererii(acceptata/respinsa)

![](/images/Architecture.png)
![](/images/LogIn.png)
![](/images/Request.png)
![](/images/HolidayRequest.png)
![](/images/Calendar.png)
