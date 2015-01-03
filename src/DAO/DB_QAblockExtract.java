/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import QAnswer.QABlock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khaledd
 */
public class DB_QAblockExtract {
    private static Connection con = null;
    private static PreparedStatement ps,ps1 = null;
    private static ResultSet rs,rs1 = null;
    private DB db = new DB();
    
    public List<Integer> getQAblockID(int id){
        
        List<Integer> qablock = new ArrayList<Integer>();
        String sql = "select blockid from qablock where docid="+id;
        String doctext="";
        
        con = db.dbConnect();
        try {
            ps= con.prepareStatement(sql);
            rs= ps.executeQuery(); 
            while (rs.next())
            {
                qablock.add(rs.getInt("blockid"));
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
        
     
        return qablock;
    }
    public List<QABlock> getQAblock(int docid,int blockid){
        
        List<QABlock> qablock = new ArrayList<QABlock>();
        String sql = "select sentid,blocktype, postext,senttext from doc_sentence where docid="+docid+" and blockid="+blockid;
        
        
        con = db.dbConnect();
        try {
            ps1= con.prepareStatement(sql);
            rs1= ps1.executeQuery(); 
            while (rs1.next())
            {
                QABlock block = new QABlock();
                
                block.setSentid(rs1.getInt("sentid"));
                block.setBlocktype(rs1.getInt("blocktype"));
                block.setPossentece(rs1.getString("postext"));
                block.setSenttext(rs1.getString("senttext"));
                
                qablock.add(block);
               // qablock.add(rs1.getInt("blockid"));
                //System.out.println("document text: "+doctext);
            }

            rs1.close();
            ps1.close();

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
            if (rs1 != null) {
                    try {
                        rs1.close();
                    } catch (SQLException ex) {
                    Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps1 != null) {
                try {
                    ps1.close();
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
        
     
        return qablock;
    }
}
