#README - Aufgabe8

##Projekt starten

- Datenbankeigenschaften: Name = **test4**; usernamen = **admin**; password = **test**
- benötigte dependencies:

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
            <version>2.2.2.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot</artifactId>
            <version>2.6.7</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.19</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
            <version>2.4.3</version>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
            <version>1.4.200</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-core</artifactId>
            <version>5.7.1</version>
        </dependency>

###Testbenutzer
- **Student**: username = *huberma*; password = *1234*
- **Assistent**: username =  *meierth*; password = *1234*
- **Admin**: username = *wadlerel*; password = *1234*
##Tabellen
###User
Alle Userarten werden zusammen in einer Tabelle gespeichert.

###Project
Alle Projektarten werden zusammen in eine Tabelle gespeichert. Zur Unterscheidung existiert das Attribut "type".
- 1 - Projekt
- 2 - Bachelorarbeit
- 3 - Masterarbeit

###Beziehungen
Zwischen Admin/Assistent und Projekt besteht eine One-to-Many Beziehung. 
Zwischen Student und Projekt eine One-to-One Beziehung, da ein Student nur ein Projekt gleichzeitig haben darf.

##Student
Wenn man sich als Student einloggt kommt man auf die "WelcomeStudent"-page.
Dort sieht man das aktuelle Projekt (falls vorhanden). Klick man auf Find new Projekt, kann man eine Projekt hinzufügen falls keines vorhanden ist. 

sonst wird der Button, zum Hinzufügen, entfernt.
Es werden immer nur die Projekte angezeigt für die der Student die passenden Berechtigungen hat.
Wird ein Projekt abgeschlossen erhöht sich das Level des Studenten. 

##Assistent
Assistenten haben lediglich die Berechtigung ihre eigenen Projekte zu erstellen, zu bearbeiten und zu löschen.
Sie können Grenzen für die maximale Anzahl an Arbeiten definieren. Diese darf nicht überschritten werden.

##Admin
Admins können neue Studenten und Projekte erstellen. Alle existierenden Projekte bearbeiten und löschen.
Sie können Grenzen für die maximale Anzahl an Arbeiten definieren. Diese darf nicht überschritten werden.
