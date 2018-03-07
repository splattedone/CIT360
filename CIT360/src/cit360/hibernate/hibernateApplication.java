package cit360.hibernate;

/*
------------------------------
Create Application Class
------------------------------
*/

  //Necessary imports
        import java.util.List; 
        import java.util.Date;
        import java.util.Iterator; 

        import org.hibernate.HibernateException; 
        import org.hibernate.Session; 
        import org.hibernate.Transaction;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;
        
  //Create ManageEmployee class with SessionFactory included

        public class ManageEmployee {
           private static SessionFactory factory; 
           public static void main(String[] args) {

  //Note the try/catch that will let you know if the session isn't successfully created.
              try {
                 factory = new Configuration().configure().buildSessionFactory();
              } catch (Throwable ex) { 
                 System.err.println("Failed to create sessionFactory object." + ex);
                 throw new ExceptionInInitializerError(ex); 
              }

  //Our new ManageEmployee object with the nickname "ME" used going forward.
              ManageEmployee ME = new ManageEmployee();

  //Detailed notes about each of these actions can be found where each method is created.
              /* Add few employee records in database */
              Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
              Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
              Integer empID3 = ME.addEmployee("John", "Paul", 10000);

              /* List down all the employees */
              ME.listEmployees();

              /* Update employee's records */
              ME.updateEmployee(empID1, 5000);

              /* Delete an employee from the database */
              ME.deleteEmployee(empID2);

              /* List down new list of the employees */
              ME.listEmployees();
           }
           
    /*
      Create new employees. For each line, the method will create a new session, add a row to the Employee table, populate the 
      relevant data, generate an id for that row, then commit the transaction. This also rolls back any changes to the DB if the 
      transaction isn't completed successfully (this is important for the DB), then closes the session. 
    */
    
           /* Method to CREATE an employee in the database */
           public Integer addEmployee(String fname, String lname, int salary){
              Session session = factory.openSession();
              Transaction tx = null;
              Integer employeeID = null;

              try {
                 tx = session.beginTransaction();
                 Employee employee = new Employee(fname, lname, salary);
                 employeeID = (Integer) session.save(employee); 
                 tx.commit();
              } catch (HibernateException e) {
                 if (tx!=null) tx.rollback();
                 e.printStackTrace(); 
              } finally {
                 session.close(); 
              }
              return employeeID;
           }

    /*
      Print out the employee table in java. Each time it's called this method will invoke the session factory to open a new session,
      begin a new transaction, and using the List and Iterator methods each employees first and last name, along with salary. Note
      the table id, or employee id, is ignored in this example, and this basic function (nor the DB table) doesn't allow for filtering 
      of employees based on specified criteria, such as if they're active or not.
      
      Once iterator.hasNext() returns false, the session is committed.
      Again, this all rolls back if there's an error.
      Finally, the session is closed.
    */

           /* Method to  READ all the employees */
           public void listEmployees( ){
              Session session = factory.openSession();
              Transaction tx = null;

              try {
                 tx = session.beginTransaction();
                 List employees = session.createQuery("FROM Employee").list(); 
                 for (Iterator iterator = employees.iterator(); iterator.hasNext();){
                    Employee employee = (Employee) iterator.next(); 
                    System.out.print("First Name: " + employee.getFirstName()); 
                    System.out.print("  Last Name: " + employee.getLastName()); 
                    System.out.println("  Salary: " + employee.getSalary()); 
                 }
                 tx.commit();
              } catch (HibernateException e) {
                 if (tx!=null) tx.rollback();
                 e.printStackTrace(); 
              } finally {
                 session.close(); 
              }
           }

  /*
    For each invocation, a new session is created. Note this method requires it be given the employee id (which, again, isn't visible
    via the tools created in this java program), and the new salary.
    
    Begin transaction. Find the employee based on the given EmployeeID, then set the salary to the given integer. Update employee record.
    Comit, rollback if there are errors, otherwise close the session.
  */

           /* Method to UPDATE salary for an employee */
           public void updateEmployee(Integer EmployeeID, int salary ){
              Session session = factory.openSession();
              Transaction tx = null;

              try {
                 tx = session.beginTransaction();
                 Employee employee = (Employee)session.get(Employee.class, EmployeeID); 
                 employee.setSalary( salary );
             session.update(employee); 
                 tx.commit();
              } catch (HibernateException e) {
                 if (tx!=null) tx.rollback();
                 e.printStackTrace(); 
              } finally {
                 session.close(); 
              }
           }

  /*
    For each invocation, pass the employee id to be deleted. Open a session, begin the transaction, find the employeeID, delete that 
    row. Commit, rollback if there are errors, then close the session.
  */

           /* Method to DELETE an employee from the records */
           public void deleteEmployee(Integer EmployeeID){
              Session session = factory.openSession();
              Transaction tx = null;

              try {
                 tx = session.beginTransaction();
                 Employee employee = (Employee)session.get(Employee.class, EmployeeID); 
                 session.delete(employee); 
                 tx.commit();
              } catch (HibernateException e) {
                 if (tx!=null) tx.rollback();
                 e.printStackTrace(); 
              } finally {
                 session.close(); 
              }
           }
        }