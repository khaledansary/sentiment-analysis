/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DependencyParser.ParseDependency;
import POSTagging.PosTagger;
import SentiStrength.SentiStrengthApp;
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
 * @autho-r khaledd
 */
public class DB_PreporcessDoc {
    
    private static Connection con = null;
    private static PreparedStatement ps,ps1,ps2,ps3 = null;
    private static ResultSet rs,rs1,r2 = null;
    DB_Aspect_opinionWord aspect = new DB_Aspect_opinionWord();
    DB db = new DB();
    ParseDependency dp = new ParseDependency();
    SentiStrengthApp sentiscore = new SentiStrengthApp();
    
    public void commit_doc(int docid, String txt)
    {
        String sql = "insert into preprocess_documents(docid, ans) values (?, ?)";
        
        
        con = db.dbConnect();
        try {
             ps = con.prepareStatement(sql);
             ps.setInt(1, docid);
             ps.setString(2, txt);
             
            int count = ps.executeUpdate ();
            
            if(count>0)
            {
               // System.out.println("committed all");
            }
            else
            {
                System.out.println("not committed all ans");
            }
            

            
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
    public void commit_ans_para (int docid, String txt)
    {
        String sql = "insert into documents_ansparagraph(docid, ansparagraph) values (?, ?)";
        
        
        con = db.dbConnect();
        try {
             ps = con.prepareStatement(sql);
             ps.setInt(1, docid);
             ps.setString(2, txt);
             
            int count = ps.executeUpdate ();
            
            if(count>0)
            {
               // System.out.println("committed ans para");
            }
            else
            {
                System.out.println("not all ans para");
            }
            


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
    public void commit_ques_para (int docid, String txt)
    {
        String sql = "insert into documents_quesparagraph(docid,quesparagraph) values (?, ?)";
        
        
        con = db.dbConnect();
        try {
             ps = con.prepareStatement(sql);
             ps.setInt(1, docid);
             ps.setString(2, txt);
             
            int count = ps.executeUpdate ();
            
            if(count>0)
            {
                //System.out.println("committed all ques para");
            }
            else
            {
                System.out.println("not all ques para");
            }
            

            
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
    public void commit_ques(int docid, String txt)
    {
        String sql = "insert into preprocess_documents_question(docid,ques) values (?, ?)";
        
        
        con = db.dbConnect();
        try {
             ps = con.prepareStatement(sql);
             ps.setInt(1, docid);
             ps.setString(2, txt);
             
            int count = ps.executeUpdate ();
            
            if(count>0)
            {
                //System.out.println("committed all ques ");
            }
            else
            {
                System.out.println("not all ques ");
            }
            

            
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
    public void commit_sentences(int docid, String txt,String postag,int blockid,int blocktype)
    {
        String sql = "insert into doc_sentence(docid, senttext, postext,blockid,blocktype) values (?, ?, ?,?,?)";
        
        
        con = db.dbConnect();
        try {
             ps = con.prepareStatement(sql);
             ps.setInt(1, docid);
             ps.setString(2, txt);
             ps.setString(3, postag);
             ps.setInt(4, blockid);
             ps.setInt(5, blocktype);
             
            int count = ps.executeUpdate ();
            
            if(count>0)
            {
              //  System.out.println("committed all sentences");
            }
            else
            {
                System.out.println("not committed all sentences");
            }
            

            
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
    public void commit_qablock(int docid, String txt,int blockid,int blocktype)
    {
        String sql = "insert into qablock(docid, para,blockid) values (?, ?,?)";
        
        
        con = db.dbConnect();
        try {
             ps = con.prepareStatement(sql);
             ps.setInt(1, docid);
             ps.setString(2, txt);
             
             ps.setInt(3, blockid);
             
             
            int count = ps.executeUpdate ();
            
            if(count>0)
            {
              //  System.out.println("committed all sentences");
            }
            else
            {
                System.out.println("not committed all sentences");
            }
            

            
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
    public void commit_aspects(int senteceid, String txt)
    {
        String sql = "insert into sentence_aspect(sentid,aspecttext) values (?, ?)";
        
        
        con = db.dbConnect();
        try {
             ps = con.prepareStatement(sql);
             ps.setInt(1, senteceid);
             ps.setString(2, txt);
             
             
            int count = ps.executeUpdate ();
            
            if(count>0)
            {
                //System.out.println("committed all sentences");
            }
            else
            {
                System.out.println("not committed all sentences");
            }
            

            
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
    public void aspect_Extraction(int sentid,int blocktype,int blockid,int docid, String postext,String text){
        
        
        
        String sql2 = "insert into sentence_aspect(sentid,aspecttext) values (?, ?)";
        
        String sql3 = "insert into sentence_aspect_dep(sentid,aspecttext,dependency,dependecy_word) values (?, ?, ?, ?)";
        String sql4 = "insert into sentence_opinion_word(sentid,opinionword) values (?, ?)";
        
        con = db.dbConnect();
       
        List<String> ls = new ArrayList<String>();
        List<String> as = new ArrayList<String>();
        List<String> op_words = new ArrayList<String>();
        try{
            
        
            if(blocktype==0)
            {
                ls=aspect.getAspects(postext);

                for(String topic:ls)
                {

                    try {
                            ps1 = con.prepareStatement("insert into autometic_extracted_topic(sentid,docid,blockid,topictext) values (?,?,?,?)");
                            ps1.setInt(1, sentid);
                            ps1.setInt(2, docid);
                            ps1.setInt(3, blockid);
                            ps1.setString(4, topic);


                           int count = ps1.executeUpdate ();

                           if(count>0)
                           {
                               //System.out.println("committed all aspect_NP " +aspect);
                           }
                           else
                           {
                               System.out.println("not committed all sentences");
                           }



                           ps1.close();

                       } catch (SQLException ex) {
                           Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                       }


                }
            }
            else 
            {

                ls=aspect.getAspects(postext);

                for(String asp:ls)
                {

                    try {
                            ps1 = con.prepareStatement("insert into autometic_extracted_aspect(sentid,docid,blockid,asptecttext) values (?,?, ?,?)");
                            ps1.setInt(1, sentid);
                            ps1.setInt(2, docid);
                            ps1.setInt(3, blockid);
                            ps1.setString(4, asp);


                           int count = ps1.executeUpdate ();

                           if(count>0)
                           {
                               //System.out.println("committed all aspect_NP " +aspect);
                           }
                           else
                           {
                               System.out.println("not committed all sentences");
                           }



                           ps1.close();

                       } catch (SQLException ex) {
                           Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                       }

                }
                op_words = aspect.getOpinionWord(postext);
                for(String opinion_word:op_words)
                {

                    try {
                            ps1 = con.prepareStatement(sql4);
                            ps1.setInt(1, sentid);
                            ps1.setString(2, opinion_word);


                           int count = ps1.executeUpdate ();

                           if(count>0)
                           {
                               //System.out.println("committed all aspect_NP " +aspect);
                           }
                           else
                           {
                               System.out.println("not committed all sentences");
                           }



                           ps1.close();

                       } catch (SQLException ex) {
                           Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                       }

                }

                as=dp.getAspect_OpinionPair(text);
                {
                    for(String aspect_dep:as)
                    {
                         String aspect_opinion[]=aspect_dep.split(",");
                    try {
                            ps2 = con.prepareStatement(sql3);
                            ps2.setInt(1, sentid);
                            ps2.setString(2, aspect_opinion[0]);
                            ps2.setString(3, aspect_dep);
                            ps2.setString(4, aspect_opinion[1]);

                           int count = ps2.executeUpdate ();

                           if(count>0)
                           {
                              // System.out.println("committed aspect"+aspect_dep);
                           }
                           else
                           {
                               System.out.println("not committed all sentences");
                           }



                           ps2.close();


                       } catch (SQLException ex) {
                           Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                       }

                    }

                }
                //try{
                    commit_adjective(sentid, postext);
                    commit_adverb(sentid, postext);
                    commit_verb(sentid, postext);
                    commit_pronoun(sentid, postext);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }   
        finally{
           try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        
    }
    public void commit_adjective(int sentid,String postxt)
    {
        String sql4 = "insert into sentence_adjective_word(sentid,adjective) values (?, ?)";
        List<String> ad_words = new ArrayList<String>();
        ad_words = aspect.getAdjective(postxt);
        if(ad_words!=null)
        {
            for(String adjective_word:ad_words)
            {

                try {
                    ps1 = con.prepareStatement(sql4);
                    ps1.setInt(1, sentid);
                    ps1.setString(2, adjective_word);


                   int count = ps1.executeUpdate ();

                   if(count>0)
                   {
                       //System.out.println("committed all aspect_NP " +aspect);
                   }
                   else
                   {
                       System.out.println("not committed all sentences adjective");
                   }
                  

                   ps1.close();
                   
                   

                } catch (SQLException ex) {
                    Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                }   

            }
        }

        
    }
    public void commit_adverb(int sentid,String postxt)
    {
        String sql4 = "insert into sentence_adverb_word(sentid,adverb) values (?, ?)";
        List<String> ad_words = new ArrayList<String>();
        ad_words = aspect.getAdverb(postxt);
        for(String adverb_word:ad_words)
        {

            try {
                ps1 = con.prepareStatement(sql4);
                ps1.setInt(1, sentid);
                ps1.setString(2, adverb_word);


               int count = ps1.executeUpdate ();

               if(count>0)
               {
                   //System.out.println("committed all aspect_NP " +aspect);
               }
               else
               {
                   System.out.println("not committed all sentences adverb");
               }


//               int score=sentiscore.SentiWordScore(adverb_word);
  //              String label=sentiscore.sentimentlebel(score);
    //            commit_SentiScore(sentid, adverb_word, "Adv", "uni", label, score);

               ps1.close();

            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        
    }
     public void commit_verb(int sentid,String postxt)
    {
        String sql4 = "insert into sentence_verb_word(sentid,verb) values (?, ?)";
        List<String> verb_words = new ArrayList<String>();
        verb_words = aspect.getVerb(postxt);
        for(String verb_word:verb_words)
        {

            try {
                ps1 = con.prepareStatement(sql4);
                ps1.setInt(1, sentid);
                ps1.setString(2, verb_word);


               int count = ps1.executeUpdate ();

               if(count>0)
               {
                   //System.out.println("committed all aspect_NP " +aspect);
               }
               else
               {
                   System.out.println("not committed all sentences verb");
               }


               ps1.close();

            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        
    }
    public void commit_pronoun(int sentid,String postxt)
    {
        String sql4 = "insert into sentence_pronoun_word(sentid,pronoun) values (?, ?)";
        List<String> pro_words = new ArrayList<String>();
        pro_words = aspect.getPronoun(postxt);
        for(String pronoun_word:pro_words)
        {

            try {
                ps1 = con.prepareStatement(sql4);
                ps1.setInt(1, sentid);
                ps1.setString(2, pronoun_word);


               int count = ps1.executeUpdate ();

               if(count>0)
               {
                   //System.out.println("committed all aspect_NP " +aspect);
               }
               else
               {
                   System.out.println("not committed all sentences adverb");
               }


               
               ps1.close();

            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        
    }
    public void commit_SentiScore(int sentid,String word,String type,String ngram, String sentiment,int score)
    {
        String sql4 = "insert into sentiment_lexicon(sentid,opinionword,postype,ngram,sentiment,score) values (?, ?, ?, ?, ?, ?)";
        
        try {
            ps3 = con.prepareStatement(sql4);
            ps3.setInt(1, sentid);
            ps3.setString(2, word);
            ps3.setString(3, type);
            ps3.setString(4, ngram);
            ps3.setString(5, sentiment);
            ps3.setInt(6, score);


           int count = ps3.executeUpdate ();

           if(count>0)
           {
               //System.out.println("committed all aspect_NP " +aspect);
           }
           else
           {
               System.out.println("not committed all sentences verb");
           }



           ps3.close();

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }

        

        
    }
    
}
