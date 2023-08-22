# library-app
Web-service that transfers the library to electronic accounting of books.

Librarians have the ability to register new readers, lend them books, and release books (after the reader returns the book back to the library). Also, librarians can add new books to the system, edit existing ones or delete them.

Readers can access all available books sorted by release year. They can also search for the desired book by the initial letters of the book name.
It also takes into account the delay of the book by the reader. If more than 10 days have passed since the book was accepted by the reader, it will automatically be highlighted in red on the user's page.
## Getting Started 
The following programs must be installed on your computer:
 Java Development Kit (JDK);
 development environment (IDE) such as Eclipse, IntelliJ IDEA or another;
 Apache Maven;
 PostgreSQL database; Tomcat.

Clone this repository to your local computer:

<code>git clone https://github.com/polyrythms/library-app</code>

Open the project in the development environment.
Download all the necessary sources and documentation from Maven.

Create a new PostgreSQL database and implement a connection to the service. All connection settings you can find in ***/resources/database.properties***.
To create the necessary tables in the database and to get test data execute the SQL queries from file ***/resources/db.sql***.

Add Tomcat run configuration with the artefact to deploy **"library:war exploded"**. Delete any information from the field **"Application context"**.

Now the application is ready to running.


## Running the application
Run the application using Tomcat configuration.
Open any browser you have and enter one of the following addresses:

***http://localhost:8080/books/*** - access to book list

***http://localhost:8080/people/*** - access to person list

## Technology Stack
Spring Framework / Spring MVC / Spring Data JPA / Hibernate / Maven / PostgreSQL / Thymeleaf
