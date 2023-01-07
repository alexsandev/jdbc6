package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {
    public static void main(String[] args){
       Connection conn = null;
       Statement st = null;
       try{
          conn = DB.getConnection();
          conn.setAutoCommit(false);
          st = conn.createStatement();

          int rowsAffected1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090.00 WHERE DepartmentId = 1");

          //FAKE ERROR:
          //int x = 2;
          //if(x != 1){
          //     throw new SQLException("Fake error!");
          //}

          int rowsAffected2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090.00 WHERE DepartmentId = 2");
          
          conn.commit();

          System.out.println("Done! Rows01: " + rowsAffected1);
          System.out.println("Done! Rows02: " + rowsAffected2);
       }
       catch(SQLException e){
          try {
               conn.rollback();
               throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
          } 
          catch (SQLException e1) {
               throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
          }
       }
       finally{
            DB.closeStatement(st);
            DB.closeConnection();
       }
    }
}
