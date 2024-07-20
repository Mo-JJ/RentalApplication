# RentalApplication (Java)

This Java application helps manage a rental system, allowing users to create citizens (landlords and tenants), residences (studios and villas), and manage rental leases. It uses Gson library for JSON serialization and deserialization to save and load data from files.

## Four Pillars of OOP Demonstration
This project serves as a demonstration of the four pillars of OOP paradigm:

-  Inheritance: e.g. **Tenant** and **Landlord** classes inherit from the **Citizen** class, **Studio** and **Villa** classes inherit from the abstract **Residence** class.
-  Abstraction: Abstract classes like **MoneyTransferPlatform**, **Residence**, **Citizen**, as well as **Rentable** interface provide a common interface for their subclasses while hiding implementation details.
-  Polymorphism: Methods like **_calculateRentingPrice_** in the **Rentable** interface exhibit polymorphism by allowing different behaviors for different types of residences (Studio/Villa). Generic programming was also utilized by using abstract references to hold sub-type instances.
-  Encapsulation: Private fields and public methods ensured the stability of attributes in objects in all classes.

## Key Features for Users

-  Create and manage citizens (tenants and landlords).
-  Create and manage residences (studios and villas).
-  Manage rental leases between citizens and residences.
-  Process and manage transactions through online payment platforms like PayPal & Visa.
-  Save and load data to/from JSON files.

## Helping users getting started
### Adding Sample Data
To help users get started, the **createSampleObjects** method in the **Main class** adds sample citizens, residences, and rental leases.
### Adding JSON Files
JSON files were created to allow users keeping track of their work (save and load data), with a possibility to add data directly to the .json files instead of working through the console (but be cautious here and pay attention to class schemes).
The **saveWorkToFiles** and **loadAllFiles** methods manage this functionality.

_**Feel free to comment out the createSampleObjects and loadAllFiles calls if you want a fresh start.**_

## Prerequisites
-  Java 8 or higher
-  Gson library

## Installation
-  Clone the repository.
-  Ensure you have the necessary dependencies (Gson).

## Simple Diagram.
Simple Diagram Showcasing the General Relationships Between the App Components.
<br/><br/>
![Group 134](https://github.com/user-attachments/assets/3d3ef77d-4819-402f-bf7b-0e33c1a8cfd4)    
