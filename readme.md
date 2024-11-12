# Projektstart-Anleitung

Um dieses Projekt erfolgreich zu starten, folge bitte den untenstehenden Schritten:

## Backend einrichten

1. **Erstelle die Datei `application-secrets.properties`**  
   Lege die Datei im Verzeichnis `backend/src/main/resources` an und fülle sie mit den folgenden Umgebungsvariablen:

   ```properties
   DATABASE_URL=your-database-url
   DATABASE_USERNAME=your-database-username
   DATABASE_PASSWORD=your-database-password
   ```

2. **Projekt bauen**  
   Führe im Hauptverzeichnis des Projekts den folgenden Befehl aus:

   ```bash
   mvn clean install
   ```

3. **Backend starten**  
   Nachdem der Build-Prozess abgeschlossen ist, kann das Backend gestartet werden.

## Frontend einrichten

1. **Abhängigkeiten installieren**  
   Gehe in das Frontend-Verzeichnis und führe den folgenden Befehl aus:

   ```bash
   npm ci
   ```

2. **Frontend starten**  
   Nach der Installation der Abhängigkeiten kann das Frontend gestartet werden.

