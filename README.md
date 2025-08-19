# 📅 Agenda Java

A simple **console-based scheduling system** built in Java, following
OOP principles.

## 🚀 Features

-   Add, list, update, delete users (appointments)
-   Search by name or email
-   Save and load appointments from file
-   Input validation (email format, phone number, date)
-   Exception handling with custom exceptions

## 🛠️ Technologies

-   Java 17+
-   OOP (Encapsulation, Separation of Concerns)
-   File I/O with `BufferedReader` and `FileWriter`
-   Collections API (`List`, `Map` in extended versions)

## 📂 Project Structure

    agenda/
    ├── application/
    │   └── App.java   # Main entry point
    ├── model/
    │   ├── entities/
    │   │   └── Schedule.java          # Entity class
    │   └── service/
    │       └── ScheduleService.java   # Business logic
    └── model/exceptions/
        └── DataException.java         # Custom exception

## ⚡ How to Run

1.  Clone the repository:

    ``` bash
    git clone https://github.com/EmersonSN/agenda.git
    cd agenda
    ```

2.  Compile the project:

    ``` bash
    javac -d bin src/agenda/**/*.java
    ```

3.  Run the application:

    ``` bash
    java -cp bin agenda.application.App
    ```

## 📌 Next Steps

-   Add JUnit tests
-   Add logging instead of `System.out.println`
-   Start to use SpringBoot for API CRUD

------------------------------------------------------------------------

💻 Developed by Emerson Santos
