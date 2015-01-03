/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import JsonParser.JsonData;
import JsonParser.ParseJson;
import StemmingLemma.StemmingLemmaText;
import TopicAspect.Topic;
import TopicAspect.TopicAspect;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author khaledd
 */
public class DB_Aspect_opinionWord {
    
    private static Connection con = null;
    private static PreparedStatement ps,ps1,ps2 = null;
    private static ResultSet rs,rs1,rs2 = null;
    DB db = new DB();
    StringReader sr;
    StemmingLemmaText stem = new StemmingLemmaText();
        
        //System.out.println(stem.getStem_lemmaText("Unions"));
    public void createTopicAspectFile(int id)
    {
        
        
        String sql = "select annotation from editor_document where docid="+id;
        
        
        con = db.dbConnect();
        try {
            ps= con.prepareStatement(sql);
            rs= ps.executeQuery(); 
            while (rs.next())
            {
                
                ParseJson json = new ParseJson();
                    List<Topic> ls = new ArrayList<Topic>();
                    ls=json.parseJsonData(rs.getString("annotation"));
                   

                    System.out.println("List......");
                    String temptopic=null;
                    for(Topic a:ls)
                    {
                       ps1= con.prepareStatement("insert into annotate_topic(docid,topictext) values(?,?)");
                       ps1.setInt(1, id);
                       ps1.setString(2, stem.getStem_lemmaText(a.getTopic()).toLowerCase());
                       int count = ps1.executeUpdate ();
                       
                       if(count>1)
                       {
                           System.out.println("topic inserted"+a.getTopic());
                       }
                       ps1.close();
                       
                       ps1= con.prepareStatement("select MAX(topicid) as id from annotate_topic");
                       rs1= ps1.executeQuery();
                       int lastid=0;
                       while (rs1.next())
                       {
                          lastid=rs1.getInt("id");
                       }
                       
                       rs1.close();
                       ps1.close();
                       
                       //System.out.println("Topic: "+a.getTopic());
                       System.out.println("---------------");
                       ps1= con.prepareStatement("insert into annotate_aspect(topicid,aspectext,opinionword ,opiniontype ) values(?,?,?,?)");
                       for(TopicAspect ta:a.getAspectlist())
                       {
                            //System.out.println(ta.getAspecttext());
                           try{
                                if(!ta.getPositiveword().trim().isEmpty() && !ta.getPositiveword().trim().equals("empty"))
                            {
                                System.out.println("<"+ta.getAspecttext()+","+ta.getPositiveword()+">");
                                ps1.setInt(1, lastid);
                                ps1.setString(2,stem.getStem_lemmaText(ta.getAspecttext()).toLowerCase());
                                ps1.setString(3,stem.getStem_lemmaText(ta.getPositiveword()).toLowerCase());
                                ps1.setString(4,"positive");
                                int count1 = ps1.executeUpdate ();

                                if(count1>1)
                                {
                                    System.out.println("pos aspect inserted"+ta.getAspecttext());
                                }
                                
                            }
                            if(!ta.getNegativeword().trim().isEmpty()&&!ta.getNegativeword().trim().equals("empty"))
                            {
                                System.out.println("<"+ta.getAspecttext()+","+ta.getNegativeword()+">");
                                ps1.setInt(1, lastid);
                                ps1.setString(2,stem.getStem_lemmaText(ta.getAspecttext()).toLowerCase());
                                ps1.setString(3,stem.getStem_lemmaText(ta.getPositiveword()).toLowerCase());
                                ps1.setString(4,"negative");
                                int count1 = ps1.executeUpdate ();

                                if(count1>1)
                                {
                                    System.out.println("neg aspect inserted"+ta.getAspecttext());
                                }
                            }
                               
                           }catch(Exception e)
                           {
                               System.out.println(e);
                           }
                           
                        }
                       ps1.close();
                       System.out.println("---------------");        
                       
                               
                        
                        /*if(a.getNegativeword().equals("empty"))
                        {

                            System.out.println(a.getTopic()+" :"+a.getAspect()+" :"+a.getPositiveword());
                        }
                        else{

                            System.out.println(a.getTopic()+" :"+a.getAspect()+" :"+a.getNegativeword());
                        }*/


                    }
            }

            rs.close();
            ps.close();

            
          

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
           try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }
    public void get_Aspect_dependency(int docid)
    {
        String anstext="";
        
        String sql = "select ans from preprocess_documents where docid="+docid;
        
        
        con = db.dbConnect();
        try {
             ps= con.prepareStatement(sql);
            rs= ps.executeQuery(); 
            while (rs.next())
            {
                anstext=rs.getString("ans");
            }

            rs.close();
            ps.close();

            
          //  sr = new StringReader(anstext);
            Scanner scanner = new Scanner(anstext);
            while (scanner.hasNextLine()) {
              String line = scanner.nextLine();
              // process the line
            }
            scanner.close();

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
           try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        
    }
   
    public List<String> getAspects(String tag_sentence)
   {
       List<String> aspects =new ArrayList<String>();
       System.out.println("Aspects=====");
       System.out.println();
       String reg="(\\w*\\_(NN|NNS|NNP)) | (\\w*_(NN|NNS|NNP) \\w*_(NN|NNS|NNP)) | (\\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP)) | (\\w*_(RB(R|S)?) \\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP)) | (\\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP) \\w*_(NN|NNS|NNP) \\w*_(NN|NNS|NNP)) | (\\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP) \\w*_(NN|NNS|NNP))";
       String removreg="_(NN(S|P)?|JJ(R|S)?|RB(R|S)?)";
       Pattern patt  = Pattern.compile(reg);
       
        Matcher match = patt.matcher(tag_sentence);
        int i=0;
        while(match.find()) {
             //System.out.println("NP: "+match.group());
            // System.out.println(i+" Aspects: "+match.group().replaceAll(removreg,""));
             aspects.add(match.group().replaceAll(removreg,""));
             i++;
            
        }
        
        return aspects;
   }
    public List<String> getOpinionWord(String tag_sentence)
   {
       List<String> aspects =new ArrayList<String>();
       System.out.println("Aspects=====");
       System.out.println();
       String reg="(\\w*_(RB|RBR|RBS) \\w*_(JJ|JJR|JJS))|(\\w*_(RB|RBR|RBS) \\w*_(VB|VBD|VBN|VBG))";
       String removreg="_(JJ(R|S)?|RB(R|S)?|VB(D|N|G)?)";
       Pattern patt  = Pattern.compile(reg);
       
        Matcher match = patt.matcher(tag_sentence.trim());
        while(match.find()) {
             //System.out.println("NP: "+match.group());
             //System.out.println("Adjective_Adverb: "+match.group().replaceAll(removreg,""));
             aspects.add(match.group().replaceAll(removreg,""));
            
        }
        
        return aspects;
   }
    public List<String> getAdjective(String tag_sentence)
    {
        List<String> list =new ArrayList<String>();
        
         String reg="\\w*\\_(JJ|JJR|JJS)";
       String removreg="_JJ(R|S)?";
       Pattern patt  = Pattern.compile(reg);
       
        Matcher match = patt.matcher(tag_sentence.trim());
        while(match.find()) {
             //System.out.println("NP: "+match.group());
           //  System.out.println("Adjective: "+match.group().replaceAll(removreg,""));
             list.add(match.group().replaceAll(removreg,""));
            
        }
        
        
        return list;
    }
    public List<String> getAdverb(String tag_sentence)
    {
        List<String> list =new ArrayList<String>();
        
         String reg="\\w*_(RB|RBR|RBS)";
       String removreg="_RB(R|S)?";
       Pattern patt  = Pattern.compile(reg);
       
        Matcher match = patt.matcher(tag_sentence.trim());
        while(match.find()) {
             //System.out.println("NP: "+match.group());
           //  System.out.println("Adverb: "+match.group().replaceAll(removreg,""));
             list.add(match.group().replaceAll(removreg,""));
            
        }
              
        return list;
    }
     public List<String> getPronoun(String tag_sentence)
    {
        List<String> list =new ArrayList<String>();
        
         String reg="\\w*_(PRP)";
       String removreg="_(PRP)";
       Pattern patt  = Pattern.compile(reg);
       
        Matcher match = patt.matcher(tag_sentence.trim());
        while(match.find()) {
             //System.out.println("NP: "+match.group());
             //System.out.println("pronoun: "+match.group().replaceAll(removreg,""));
             list.add(match.group().replaceAll(removreg,""));
            
        }
              
        return list;
    }
    public List<String> getVerb(String tag_sentence)
    {
        List<String> list =new ArrayList<String>();
        
         String reg="\\w*_(VB|VBD|VBN|VBG)";
       String removreg="_VB(D|N|G)?";
       Pattern patt  = Pattern.compile(reg);
       
        Matcher match = patt.matcher(tag_sentence.trim());
        while(match.find()) {
             //System.out.println("NP: "+match.group());
            // System.out.println("Verb: "+match.group().replaceAll(removreg,""));
             list.add(match.group().replaceAll(removreg,""));
            
        }
              
        return list;
    }
    
    
}
