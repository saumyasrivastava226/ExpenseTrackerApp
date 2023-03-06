# ExpenseTrackerApp



A web-application build with using Thymeleaf and SpringBoot that helps you keep track of expenses.Things used/learned in the project :
Spring Core, Spring Boot, Spring Security, JPA, Hibernate (Many to one relational mapping for between user and expenses ),Thymelaef templates, CSS, Lombok library annotations.




Design Pattern :  I incorporated the Model-View-Controller (MVC) architectural pattern in a recent project. This design pattern allowed me to cleanly separate the application logic, user interface, and data management components of the project. All the entities are kept inside enity package, inside controller package all the hanlder methods are present, all UI- layers have been kept inside the resources folder. Using the MVC pattern allowed me to iterate quickly on the project, as changes to one layer did not require modifications to the other layers. Additionally, it helped me write clean, modular code that was easier to debug and maintain.


The app includes the following features
- Read expenses from database
- Save expenses to database
- Delete the expense
- Update the expenses
- Filter the expenses
- Calculate total expenses
- Server side validations
- Client side validations
- Login/Register(with Spring Security feature added)
- Logout 
- Handle Exceptions
