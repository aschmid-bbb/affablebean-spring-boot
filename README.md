AffableBean Spring Boot Klon
============================

Dies ist mittelmässige Kopie des AffableBean Projekts mit Spring Boot und Thymeleaf. Das Ziel des Projekts war, eine
Applikation mit den gleichen Seiten / Bedienelementen und der gleichen Datenbank wie das ursprüngliche AffableBean
Projekt zu erstellen.

Setup
-----

- Java 17 installieren
- Datenbank einrichten (Siehe `mysql/README.md`)
- In den Ordner des Projekts wechseln
- `./gradlew clean build` ausführen zum builden
- `./gradlew bootRun` ausführen
- Applikation sollte unter http://localhost:8080 verfügbar sein

Aufbau Frontend
---------------
- `src/main/resources/application.properties`: Die Konfigurationsdatei für die Datenbankverbindung und das Admin Login
- `src/main/resources/messages.properties`: Die Fallback übersetzungen
- `src/main/resources/messages_en.properties`: Die Englischen übersetzungen
- `src/main/resources/messages_de.properties`: Die Deutschen übersetzungen
- `src/main/resources/static`: Statische Ressourcen für die Webapplikation (Bilder, JS, CSS)
- `src/main/resources/templates`: Thymeleaf Templates
- `src/main/resources/templates/admin/`: Templates für die Admin-Oberfläche
- `src/main/resources/templates/admin/index.html`: Das Haupt-Template für die Admin-Oberfläche
- `src/main/resources/templates/admin/login.html`: Die Loginseite
- `src/main/resources/templates/admin/fragments/`: Template "Schnipsel" die bei anderen Admin-Seiten eingebunden werden
- `src/main/resources/templates/admin/views/`: Die Views der Admin-Oberfläche
- `src/main/resources/templates/shop/`: Templates für die Webshop-Oberfläche
- `src/main/resources/templates/shop/index.html`: Das Haupt-Template für den Webshop
- `src/main/resources/templates/shop/fragments/`: Template "Schnipsel" die bei anderen Webshop-Seiten eingebunden werden
- `src/main/resources/templates/shop/views/`: Die Views für den Webshop

Aufbau Backend
--------------
- `ch.bbbaden.webshop.WebshopApplication`: Die Spring Boot Application (Das wird gestartet)
- `ch.bbbaden.webshop.WebshopConfiguration`: Die Spring Boot Konfiguration
- `ch.bbbaden.webshop.cart`: Package in welchem die Warenkorb-Logik implementiert ist.
- `ch.bbbaden.webshop.controller.records`: Package für Records / Beans um Daten zwischen Backend und Frontend zu übermitteln
- `ch.bbbaden.webshop.controller.AdminController`: Controller für die Admin-Oberfläche
- `ch.bbbaden.webshop.controller.WebshopController`: Controller für den Webshop
- `ch.bbbaden.webshop.model.entity`: Die JPA Entities für das ORM Mapping
- `ch.bbbaden.webshop.model.repositories`: Die JPA Repositories
- `ch.bbbaden.webshop.model.OrderService`: Der Service der verwendet wird, um Aufträge zu speichern

