/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khaledd
 */
public class DB {
    
    private static Connection con = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
     public Connection dbConnect()
    {
        
        String dbUserName = "root"; 
        String dbPassword = ""; 
        String dbUrl = "jdbc:mysql://localhost/sentiment_analysis";
            try
            {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    Connection conn = DriverManager.getConnection(
                      dbUrl, dbUserName, dbPassword);
                    return conn;

            }
            catch (Exception e)
            {
                    e.printStackTrace();
                    return null;
            }
    }
    public String getFileText(int id){
        
        
        String sql = "select text from editor_document where docid="+id;
        String doctext="";
        
        con = dbConnect();
        try {
            ps= con.prepareStatement(sql);
            rs= ps.executeQuery(); 
            while (rs.next())
            {
                doctext =rs.getString("text");
                //System.out.println("document text: "+doctext);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
            if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                    Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
     
        return doctext.trim();
    }
}
