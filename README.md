# tema1-poo - Energex-Lite

Energex-Lite proceaza data despre piata  de energie electrica din Romania dintr-un fisier JSON. Aplicatia valideaza punctele primite si raspunde la interogari despre preturi, prognoze  si consum.

Arhitectura aplicatiei 
    Am folosit 3 clase principale cu responsabilitati bine delimitate.
    In plan secundar, am definit clase pentru fiecare tip de date posibil : DAM_PRICES, DAM_PRICES_PREDICTION, etc. , precum si clase pentru punctele acestor tipuri de date. 

Clase principale utilizate:
- DataValidator - grupeaza regulile de validare pentru fiecare tip de date. In cazul in care datele primite sunt invalide, returneaza un mesaj de eroare corespunzator, iar altfel mesajul este null.
- MarketDataRepository - stocheaza datele primite si raspunde la interogari. Creaza cate un hashmap pentru fiecare tip de date. Cheia este un LocalDate, iar valoarea este documentul zilei cu cele 96 de intrari specifice fiecarei zilei. Folosesc aceste map-uri atat la salvare, cat si la interogare.
- CommandProcessor - executa comenzile in ordinea din fisier, inclusiv cand au acelasi timestamp. Primeste prin constructor un validator si un repository, pe care le foloseste mai departe.
Toate comenzile trec prin process(), care apeleaza metoda potrivita in functie de tipul comenzii. Comenzile startImport si stopImport modifica campul importActive, care pastreaza starea importului intre doua apeluri. Pentru get si pentru import/patch am doua metode separate, handleGet si handleImportAndPatch, pe care le apelez doar din process(). Import si patch au o metoda comuna pentru ca mi-am dat seama ca cele doua comenzi seamana foarte mult.

  
Utilizarea AI-ului
    Am folosit ChatGPT pentru a clarifica enuntul, a discuta variante de design si exemplele din testele publice, atunci cand acestea nu treceau. I-am aratat ierarhia mea de clase si i-am cerut feedback si sugestii de imbunatatire. Am discutat despre interogarile de tip LATEST din comanda get si i-am cerut o varianta de implementare, de la care am pornit ulterior. 
    Am folosit AI-ul si pentru construirea raspunsurilor JSON. 




    
