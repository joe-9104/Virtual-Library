# Virtual Library Management System

This project is a desktop application developed in *Java* to modernize and streamline the management of book loans and returns within a library. The application allows users such as students, teachers, and librarians to interact with the system in various ways, including viewing, borrowing, returning, and reserving books.

## Features

- **User Authentication**: The system supports different types of users, including students, teachers, and librarians, who can authenticate to access the application.
  
- **Book Management**: Users can search for books, view their details (title, author, genre, availability), and consult the catalog.

- **Borrow and Return Books**: Students and teachers can borrow available books and return them when finished.

- **Reservation System**: If a book is unavailable, users can place a reservation and be notified when it becomes available.

- **Loan History**: Each user can view their borrowing history, including the list of books borrowed, corresponding dates, and current loan status (ongoing, completed).

- **Email Notifications**: The system automatically sends email reminders to users regarding book return deadlines.

- **Statistical Reports**: Librarians can generate statistical reports on the most borrowed books and the most active users.

## Database Structure

The application interacts with a database that consists of the following main tables:

- **User**: Stores user information including ID, name, login credentials, and role (student, teacher, librarian).
  
- **Book**: Stores book information including ID, title, author, genre, and availability.

- **Loan**: Tracks the book loans with fields for loan ID, user ID, book ID, loan date, return date, and status.

- **Reservation**: Manages reservations with fields for reservation ID, user ID, book ID, reservation date, and status.

## Key Use Cases

1. **User Authentication**: All users must authenticate to use the system.
2. **Catalog Consultation**: Students and teachers can search for and view available books.
3. **Borrow/Return Management**: Users can borrow available books and return them through the system.
4. **Book Reservation**: Users can reserve books that are currently unavailable.
5. **Loan History**: Users can review their borrowing history.
6. **Email Notifications**: Librarians can send email reminders to users about return deadlines.
7. **Report Generation**: Librarians can generate reports on book loans and user activity.

## How to Use

1. **Clone the repository**: 
   ```bash
   git clone https://github.com/joe-9104/Virtual-Library.git
   ```
2. **Set up the database**: Create the required tables as described in the database structure section
3. **Run the application**: To run the project from the command line, go to the dist folder and type the following:
  ```bash
  java -jar "Gestion_biblio.jar"
  ```
