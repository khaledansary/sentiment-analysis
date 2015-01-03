/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Preprocess;

import DAO.DB;
import DAO.DB_PreporcessDoc;
import DependencyParser.RunStanfordParser;
import QAnswer.QA;
import QAnswer.QASplit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khaledd
 */
public class Preprocessing {
    
    DB db = new DB();
    DB_PreporcessDoc preprocess= new DB_PreporcessDoc();
    
    public void preprocessingText(int docid,String location)
    {
      /*  
        QASplit qasplit = new QASplit();
        SentenceSpliting spliting = new SentenceSpliting ();
        List<QA> anspara = new ArrayList<QA>();
        List<QA> quespara = new ArrayList<QA>();
        String filetext = db.getFileText(docid);//start from 35
        
        anspara=qasplit.getAnswer(filetext.trim());
        quespara=qasplit.getQuestion(filetext.trim());
        String doc_allans="",doc_allques="";
        DB_PreporcessDoc preprocess= new DB_PreporcessDoc();
        FileOperation fb=new FileOperation();
        
        
        for(int i=0;i<anspara.size();i++)
        {
            String apara=spliting.getSentences(anspara.get(i).getAnswer().trim(),docid);
            doc_allans+=apara+"\n";
            preprocess.commit_ans_para(docid, apara);
        }
        preprocess.commit_doc(docid, doc_allans);
        fb.WriteIntoFile(location+docid, doc_allans);
        
       /* try {
            RunStanfordParser r = new RunStanfordParser(location+docid);
        } catch (IOException ex) {
            Logger.getLogger(Preprocessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i=0;i<quespara.size();i++)
        {
            String qpara=spliting.getSentences(quespara.get(i).getQuestion(),docid);
            doc_allques+=qpara+"\n";
            preprocess.commit_ques_para(docid, qpara);
        }
        preprocess.commit_ques(docid, doc_allques);*/
        
    }
    public void QAblock(int docid,String location)
    {
       
        SentenceSpliting spliting = new SentenceSpliting ();
        QASplit qasplit = new QASplit();
        List<QA> anspara = new ArrayList<QA>();
        List<QA> quespara = new ArrayList<QA>();
        String filetext = db.getFileText(docid);//start from 35
        anspara=qasplit.getAnswer(filetext.trim());
        quespara=qasplit.getQuestion(filetext.trim());
        for(int i=0;i<anspara.size();i++)
        {
            try{
                String qpara=spliting.getSentences(quespara.get(i).getQuestion().trim(),docid,i,0);
                String apara=spliting.getSentences(anspara.get(i).getAnswer().trim(),docid,i,1);
                System.out.println("Question: "+i +": "+qpara);
                System.out.println("Answer: "+i+": "+apara);
                System.out.println("...................... ");
                System.out.println("...................... ");

                preprocess.commit_qablock(docid,qpara+"\n"+ apara, i, 1);
            }catch(Exception e)
            {
                System.out.println("out of bound "+e);
            }
            
         
            //preprocess.commit_ans_para(docid, apara);
        }
        
    }
}
