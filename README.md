# Team-YOLO

SERVER

Kravspec. – Serveren:

S1 : Server skal kunne validere login baseret på et hashet password.
•	Genkendelse af bruger (log)
o	Godkendelse af identitet ikke nødvendigt (nice to have)
•	Sikkerhed for password


S2 : Serveren skal kunne oprette, slette og opdatere en bruger. 
•	I tilfælde af glemt password (sikkerhed)


S3 : Serveren skal udstille et API, som gør det nemt at udarbejde klienter der kan trække på serverens funktionalitet. API’et skal dække følgende funktionaliteter:
•	En elev skal kunne oprette sig som bruger 
o	Fulde navn som fremgår af studiekort, (studieID) og personlig password – kan scannes hvis nødvendigt
•	En elev/kantinepersonale skal kunne logge ind/ud
•	En elev skal kunne ændre brugeroplysninger (password)
•	En elev skal kunne vælge CBS-afdeling for afhentning
•	En elev skal kunne tilgå liste med priser og vareinformation baseret på afdeling
o	En bruger skal kunne sammensætte egen brugerdefineret indkøbskurv (betaling ved kasse)
•	Kantinepersonalet skal kunne tilgå liste for bestillinger
•	Kantinepersonalet skal kunne markere/(”alerte” eleven) når en ordre er klar til afhentning
o	Eleven skal selv refreshe ordresiden og holde sig opdateret på når ordren er færdig (easy method).
o	Eleven fremviser studiekort ved afhentning 

KLIENT

Kravspec. – Klienten:
K1 : Klienten skal udstille et login-vindue for brugeren, samt give brugeren mulighed for at logge ud igen. 

K2 : Klienten skal kunne oprette en elev

K3 : Klienten skal kunne ændre brugeroplysninger for den elev, der er logget ind.

K4: Klienten skal kunne vise købsmuligheder ved forskellige afdelinger (elev)

K4.1 : Klienten skal kunne tilgå vareinformation og priser (elev)

K5: Klienten skal kunne sammensætte brugerdefineret indkøbskurv (elev)

K6 : Klienten skal kunne tilgå liste over bestillinger (kantinepersonalet).

K7 : Klienten skal kunne markere færdige ordre (kantinepersonalet).

Branch test


