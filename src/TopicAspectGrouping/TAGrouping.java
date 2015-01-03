/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TopicAspectGrouping;

import DAO.DB;
import StemmingLemma.StemmingLemmaText;
import TopicModeling.TopicModel;
import static UnsupervisedTopicAspectExtraction.TopicAspectParsing.preprocess;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khaledd
 */
public class TAGrouping {
    
    private static Connection con = null;
    private static PreparedStatement ps,ps1,ps2,ps3 = null;
    private static ResultSet rs,rs1,rs2 = null;
    //StemmingLemmaText stem = new StemmingLemmaText();
    static DB db = new DB();
    static int i=0;
    public static void topicfreq(int docid)
    {
        con = db.dbConnect();
        
        try {
            
            
            String sql = "SELECT distinct topictext FROM autometic_extracted_topic";
          
            ps= con.prepareStatement(sql);
            rs= ps.executeQuery(); 
            
            while (rs.next())
            {
                 List<String> list = new ArrayList<String>();
                String topic=rs.getString("topictext");
                ps1= con.prepareStatement("SELECT topictext FROM autometic_extracted_topic where topictext=?");
                //ps1.setInt(1,docid);
                ps1.setString(1,topic);
                rs1=ps1.executeQuery();
                
                while(rs1.next())
                {
                    list.add(rs1.getString("topictext").toLowerCase());
                    ///System.out.println("<"+rs1.getString("docid")+","+rs1.getString("aspectext")+">");
                }
                Map<String, Integer> map = new HashMap<String, Integer>();
                for (String temp : list) {
                    Integer count = map.get(temp);
                    map.put(temp, (count == null) ? 1 : count + 1);
                }
                Map<String, Integer> sortedMap = sortByComparator(map);
                printMap(sortedMap);
               // i++;
               // System.out.println("... ...");
                rs1.close();
                ps1.close();
            }
           // System.out.println("Total Topic: "+i);
            rs.close();
            ps.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TAGrouping.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void topicMerging(int docid)
    {
        con = db.dbConnect();
        
        try {
            String sql = "SELECT distinct topictext FROM autometic_extracted_topic where docid="+docid;
          
             ps= con.prepareStatement(sql);
            rs= ps.executeQuery(); 
            
            while (rs.next())
            {
                 List<String> list = new ArrayList<String>();
                String topic=rs.getString("topictext");
                ps1= con.prepareStatement("SELECT at.docid,topictext,asptecttext FROM autometic_extracted_topic at,autometic_extracted_aspect aa where at.blockid=aa.blockid and at.docid=? and at.topictext=?");
                ps1.setInt(1,docid);
                ps1.setString(2,topic);
                rs1=ps1.executeQuery();
                
                while(rs1.next())
                {
                    list.add(rs1.getString("asptecttext").toLowerCase());
                    ///System.out.println("<"+rs1.getString("docid")+","+rs1.getString("aspectext")+">");
                }
                System.out.println("Topic: ............"+topic+"{");
                Map<String, Integer> map = new HashMap<String, Integer>();
                for (String temp : list) {
                    Integer count = map.get(temp);
                    map.put(temp, (count == null) ? 1 : count + 1);
                }
                printMap(map);
                System.out.println("}");
               // i++;
                System.out.println("... ...");
                rs1.close();
                ps1.close();
            }
           // System.out.println("Total Topic: "+i);
            rs.close();
            ps.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TAGrouping.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void topicdocument(int docid)
    {
        String sql = "SELECT distinct topictext FROM annotate_topic where docid="+docid;
        String topicaspect="";
        
        con = db.dbConnect();
        try {
            ps= con.prepareStatement(sql);
            rs= ps.executeQuery(); 
            while (rs.next())
            {
                List<String> list = new ArrayList<String>();
                String topic=rs.getString("topictext");
                ps1= con.prepareStatement("SELECT docid,topictext,aspectext FROM annotate_topic at,annotate_aspect aa where at.topicid=aa.topicid and at.docid=? and at.topictext=?");
                ps1.setInt(1,docid);
                ps1.setString(2,topic);
                rs1=ps1.executeQuery();
                System.out.println("Topic: ............"+topic);
                while(rs1.next())
                {
                    list.add(rs1.getString("aspectext"));
                    ///System.out.println("<"+rs1.getString("docid")+","+rs1.getString("aspectext")+">");
                }
                Map<String, Integer> map = new HashMap<String, Integer>();
                for (String temp : list) {
                    Integer count = map.get(temp);
                    map.put(temp, (count == null) ? 1 : count + 1);
                }
                printMap(map);
               // System.out.println("... ...");
                rs1.close();
                ps1.close();
                
                
                
            }
            rs.close();
            ps.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
     public static void printMap(Map<String, Integer> map){
        
	for (Map.Entry<String, Integer> entry : map.entrySet()) {
            
          // if(entry.getValue()>10)
           //{
		System.out.println(entry.getKey() + "("+entry.getValue()+"),");
           //}
                
	}
      //  i++;
        
 
    }
      public void WriteTofile(String text,String filename)
    {
       
        FileWriter file=null;
        try {
            file = new FileWriter("H:\\Thesis Development\\Thesis\\TopicAspectVisualization\\web\\"+filename);
            file.write(text);
            System.out.println("Successfully create File...");
            
 
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException ex) {
                
            }
            
        }
    }
     public static void main(String[] args) {
         for(int i=35;i<75;i++)
         {
            //topicfreq(i);
            System.out.println("Document Start...................."+i); 
             //topicfreq(i);
            topicdocument(i);
            System.out.println("Document End...................."+i);
         }
         /*con = db.dbConnect();
         TopicModel topic1 = new TopicModel();
        try {
            ps= con.prepareStatement("SELECT senttext from doc_sentence where blocktype=1 and docid=35");
            rs= ps.executeQuery(); 
            String topic="";
            while (rs.next())
            {
               
                topic +=rs.getString("senttext")+"\n";
                
                
            }
            try {
                 
                    topic1.getModelReady(topic);
                    topic1.applyLDA();
                } catch (Exception ex) {
                    System.out.println("Toopic model error"+ex);
                }
            rs.close();
            ps.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }*/
         
         
        //topicdocument();
        
    }
    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {
 
		// Convert Map to List
		List<Map.Entry<String, Integer>> list = 
			new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
 
		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
                                           Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
 
		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}

