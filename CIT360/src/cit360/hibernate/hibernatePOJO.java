package cit360.hibernate;

/*
  The following examples were pulled from (https://www.tutorialspoint.com/hibernate/hibernate_examples.htm) and modified slightly. 
  The commentary is all my work.
*/

/*
------------------------------
POJO Class Setup
------------------------------
*/

      //Create the employee object and basic getter/setter functions.
      public class Employee {
         private int id;
         private String firstName; 
         private String lastName;   
         private int salary;  

         public Employee() {}
         public Employee(String fname, String lname, int salary) {
            this.firstName = fname;
            this.lastName = lname;
            this.salary = salary;
         }

         public int getId() {
            return id;
         }

    */
      Setting a new id for an entry in a DB can be dangerous if not used properly. Uneducated users could cause serious issues with 
      the DB if they can freely edit this field. I would suggest leaving this functionality out unless you have a specific use and
      your users will be familiar with databases. 
    */
         public void setId( int id ) {
            this.id = id;
         }

         public String getFirstName() {
            return firstName;
         }

         public void setFirstName( String first_name ) {
            this.firstName = first_name;
         }

         public String getLastName() {
            return lastName;
         }

         public void setLastName( String last_name ) {
            this.lastName = last_name;
         }

         public int getSalary() {
            return salary;
         }

         public void setSalary( int salary ) {
            this.salary = salary;
         }
      }
      
/*