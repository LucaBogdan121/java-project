This is a Java application that simulates a car rental system. I have included a clean layer architecture, respecting all OOP principles.
1. FEATURES:
   As in a usual car rental system, we can manage cars and reservations for them. Data can be retrived and stored in multiple ways (e.g. in-memory , JSON/CSV/BINARY files and in a database).
   These settings can be changed from the "settingns.properties" file.
   I have structured the project in:
     -domain: contains core entities
     -repository: contains repository interfaces
     -service: contains the business logic
     -gui: contains the files for a Graphical User Interface made using JavaFX and SceneBuilder
3. RUNNING THE PROJECT:
  I recommend running this project via an IDE (I use IntelliJ). Please make sure your project SDK is compatible with temurin-21
Note: For trying the databese feature for storing information please make sure that the SQLite JDBC is in your classpath.
