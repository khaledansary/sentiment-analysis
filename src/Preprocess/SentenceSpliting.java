/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Preprocess;

import DAO.DB_PreporcessDoc;
import DependencyParser.ParseDependency;
import POSTagging.PosTagger;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author khaledd
 */
public class SentenceSpliting {
    
    PosTagger postag= new PosTagger();
    DB_PreporcessDoc dbprocess = new DB_PreporcessDoc();
    //ParseDependency dependency = new ParseDependency();
    
    public String getSentences(String filetext,int docid,int blockid, int blocktype)
            
    {
        List<String> sentences = new ArrayList<String>();
        
        Reader reader = new StringReader(filetext.trim());
        DocumentPreprocessor dp = new DocumentPreprocessor(reader);

        List<String> sentenceList = new LinkedList<String>();
        Iterator<List<HasWord>> it = dp.iterator();
        while (it.hasNext()) {
           StringBuilder sentenceSb = new StringBuilder();
           List<HasWord> sentence = it.next();
           for (HasWord token : sentence) {
              if(sentenceSb.length()>1) {
                 sentenceSb.append(" ");
              }
              sentenceSb.append(token);
           }
           sentenceList.add(sentenceSb.toString());
        }
        String splitsentences="";
        for(String sentence:sentenceList) {
         //  System.out.println(sentence);
           //splitsentences+=sentence+"\n";
           //dbprocess.commit_sentences(docid,sentence);
           postag.TagingText(sentence,docid,blockid,blocktype);
           //dependency.getAspect_OpinionPair(sentence);
           splitsentences+=getSpitSentences(sentence+"\n");
           
        }
        
        return splitsentences;
        
    }
    public String getSpitSentences(String filetext)
    {
        String[] sentences = filetext.split("[\\.\\!\\?]");
        String splitdot="";
        for (int i=0;i<sentences.length;i++){  
            
            splitdot +=sentences[i];
        }  
        return splitdot;
    }

}
