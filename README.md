# MonsterCardTradingGame aka Monster Trading Card Game

## Notizen 13.11.2023

socket reader selber implementieren zb
klasse die sich darum kümmert den request aus dem socket zu bekommen - nimmt das http aus dem stream und gibt einen string zurück
client-server overview mdn web docs
das dann in eine objekt orientierte java klasse umzuwandeln ist aufgabe des mappers - das geht dann wieder an den handler zurück
dieses request objekt wird dann an die app weitergegeben

implementierung des mappers heute?


## Notzien 16.11.2023

user
chard
stack
deck
coin
package
battle

tabellen und verbinden in der db, wie interagiere ich mit diesen daten

indem wir routen aufrufen - diese verändern dann daten in der datenbank oder starten andere aktionen

parallelitäten im battle - mehrere aufrufe gleichzeitig


# Empfählung

neus package erstellen
mtcg unter apps mit MtcgApp class public Response handle(Request request) {}
mit pachage controller (presentation layer) - controller arbeiten einzelne Routen ab zb UserController
mit package entity (eher data layer) - alles was wir auf die db mappen können zb class Card
mit pack service (logic layer) - zb transactionService - für aufgaben tun zuständig
mit pack repository (data layer) - kommuniziert mit der db für abfragen zb PachageRepository


