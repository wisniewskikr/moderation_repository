1. Stworzenie 4 baz danych:
- 2 dla developerów
- 1 dla Hudson
- 1 dla Producji
2. Wskazanie na bd w properties:
- jdbc.properties: bd dla developerów i produkcyjna;
- jdbc-test.properties: bd testowa dla developerów i dla Hudson.
3. Uruchomienie skryptów inicjujących bazy danych;
4. Przegranie drivera do servera (potrzebny do autentykacji);
5. Wskazanie na bd na serverze (autentykacja);
6. Zbudowanie projektu z profilem "development";
7. Zdeployowanie projektu na serwerze.


Developer test db
-clear
-update

Developer main db
-clear
-update
-init

Hudson test db
-clear
-update

Production main db
-update