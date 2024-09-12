package com.pointershow.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.pointershow.manager.PasswordManager;
import com.pointershow.manager.PasswordManager.PasswordEntry;



public class PasswordBackupDao {
    private static final String DB_NAME="backup";
    private static final String JDBC_URL="jdbc:mysql://localhost:3306/"+DB_NAME;
    private static final String DB_USER="root";
    private static final String DB_PASSWORD="root";
    private static final String INSERT_QUERY="insert into passwords(username,password) values(?,?)";
    private static final String GET_ALL_QUERY="select * from passwords";
    private static final String UPDATE_QUERY="update passwords set password=? where username=?";

    private static void insertNewEntry(PasswordEntry entry) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            pstmt = connection.prepareStatement(INSERT_QUERY);
            pstmt.setString(1, entry.userid);
            pstmt.setString(2, entry.password);
            int rows = pstmt.executeUpdate();
            System.out.println(rows + " affected");
        } catch (SQLException e) {
            if(!e.getMessage().startsWith("Duplicate")){
                // this is to make sure error msg not printed if duplicate entry because it is not a problem 
                System.err.println("SQL Error : "+e.getMessage());
            }
            else{
                // this will update password for the entry in the database
                System.out.println("Updating "+entry.userid+" Password...");
                PasswordBackupDao.updateEntry(entry);
            }
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    private static List<PasswordEntry> getAllEntries(){
        List<PasswordEntry> entries=new LinkedList<PasswordEntry>();
        Connection con=null;
        Statement stmt=null;
        ResultSet rs=null;
        try {
           con=DriverManager.getConnection(JDBC_URL, DB_USER,DB_PASSWORD);
           stmt=con.createStatement();
           rs=stmt.executeQuery(GET_ALL_QUERY);
           while(rs.next()){
            entries.add(new PasswordEntry(rs.getString(1),rs.getString(2)));
           }
        } catch (SQLException e) {
                System.err.println("SQL Error : "+e.getMessage());
            
        }
        finally{
            try{
                if(con!=null) con.close();
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
            }
            catch(SQLException e){
                System.err.println("SQL Error while cleanup : "+e.getMessage());
            }
        }
        return entries;
    }

    private static void updateEntry(PasswordEntry entry){
        Connection con=null;
        PreparedStatement pstmt=null;
        try {
            con=DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASSWORD);
            pstmt=con.prepareStatement(UPDATE_QUERY);
            pstmt.setString(1, entry.password);
            pstmt.setString(2, entry.userid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error : "+e.getMessage());
        }
        finally{
            try {
                if(con!=null) con.close();
                if(pstmt!=null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("SQL Error while cleanup : "+e.getMessage());
            }
        }
    }

    public void syncWithDb(){
        for(PasswordEntry entry:PasswordManager.getAllEntries()){
            PasswordBackupDao.insertNewEntry(entry);
        }
        for(PasswordEntry entry:PasswordBackupDao.getAllEntries()){
            PasswordManager.addNewEntry(entry);
        }
        
        System.out.println("Backup Success !");
    }
}
