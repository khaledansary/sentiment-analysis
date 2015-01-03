/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sentiment_opinionmining;

import DAO.DB;
import DAO.DB_Aspect_opinionWord;
import DAO.DB_PreporcessDoc;
import DAO.DB_QAblockExtract;
import NER.NERAnnotation;
import Preprocess.Preprocessing;
import Preprocess.ProcessingPronoun;
import Preprocess.PronounTag;
import QAnswer.QABlock;
import QAnswer.QASplit;
import SentiStrength.SentiStrengthApp;
import StemmingLemma.StemmingLemmaText;
import TopicAspectGrouping.TAGrouping;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author khaledd
 */
public class Sentiment_OpinionMining {
    private static StanfordCoreNLP pipeline;
    /**
     * @param args the command line arguments
     */
    static PronounTag lastNPIt = new PronounTag("it","it",0);
    static PronounTag lastNPThey = new PronounTag("they","they",0);
        
    public static void main(String[] args) {
        
        //DB_Aspect_opinionWord db = new DB_Aspect_opinionWord();
        
        //StemmingLemmaText stem = new StemmingLemmaText();
        
        //System.out.println(stem.getStem_lemmaText("Unions"));
        //TAGrouping tagroup= new TAGrouping();
        //tagroup.topicdocument();
        
       /* for(int i=35;i<75;i++)
        {
            db.createTopicAspectFile(i);
        }*/
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        Preprocessing pre_process= new Preprocessing();
        DB_QAblockExtract qablock;
        DB_PreporcessDoc aspect;
        //NERAnnotation ner = new NERAnnotation();
        //List<PronounTag> nounIttag;
        //List<PronounTag> nounTheytag;
        //ProcessingPronoun pronoun = new ProcessingPronoun();
        //PronounTag lastNPIt,lastNPThey;
        
       /* for(int i=35;i<48;i++)
        {
            pre_process.preprocessingText(i,"H:\\QualitativeData\\Preprocessfiles\\");
            pre_process.QAblock(i, "H:\\QualitativeData\\Preprocessfiles\\");
        }*/
       /// pre_process.preprocessingText(34,"H:\\QualitativeData\\Preprocessfiles\\");
       /* for(int i=35;i<=115;i++)
        {
            System.out.println("doc: "+i);
            lastNPThey = new PronounTag("they","they",0);
            qablock = new DB_QAblockExtract();
            aspect =new DB_PreporcessDoc ();
            //aspect.aspect_Extraction(35);
            List<Integer> blocks = qablock.getQAblockID(i);
            for(int blockid: blocks)
            {
                System.out.println(blockid);
                List<QABlock> postexts= qablock.getQAblock(i, blockid);
                 System.out.println("sentence: "+postexts);
                for(QABlock text : postexts)
                {
                    System.out.println("sentence: "+text.getSenttext());
                    System.out.println(text.getSentid()+": "+text.getBlocktype()+": "+text.getPossentece());
                  /* nounIttag = new ArrayList<PronounTag>();
                    nounTheytag = new ArrayList<PronounTag>();
                    
                    nounIttag=pronoun.pronounItResolver(text.getPossentece());
                    nounTheytag=pronoun.pronounTheyResolver(text.getPossentece());
                    
                    System.out.println("last NPs..........");
                    System.out.println(lastNPIt.getNoun() +" "+lastNPIt.getPronountag());
                    System.out.println(lastNPThey.getNoun() +" "+lastNPThey.getPronountag());
                    System.out.println(".................");
                    String newpos=pronoun.swapNPtoPRP(text.getPossentece(),lastNPIt, lastNPThey);
                    System.out.println("==================");
                    System.out.println("new pos string "+newpos);
                    System.out.println("==================");
                    //System.out.println("after pos tag removal "+newpos.replaceAll("_(\\w{2,5})", ""));
                    String newsentence=newpos.replaceAll("_(\\w{2,5})", "");*/
                    ///aspect.aspect_Extraction(text.getSentid(), text.getBlocktype(),blockid,newpos,newsentence);
                    //aspect.aspect_Extraction(text.getSentid(), text.getBlocktype(),blockid,i,text.getPossentece(),text.getSenttext());
                    /*if(nounIttag!= null && !nounIttag.isEmpty())
                    {
                        
                        lastNPIt.setNoun(nounIttag.get(nounIttag.size()-1).getNoun());
                        lastNPIt.setPronountag(nounIttag.get(nounIttag.size()-1).getPronountag());
                        lastNPIt.setPosition(nounIttag.get(nounIttag.size()-1).getPosition());
                    }
                    if(nounTheytag!= null && !nounTheytag.isEmpty())
                    {
                        lastNPThey.setNoun(nounTheytag.get(nounTheytag.size()-1).getNoun());
                        lastNPThey.setPronountag(nounTheytag.get(nounTheytag.size()-1).getPronountag());
                        lastNPThey.setPosition(nounTheytag.get(nounTheytag.size()-1).getPosition());
                    }
                    
                    //lastNPIt=nountag.lastIndexOf(ner)
                    //aspect.aspect_Extraction(text.getSentid(), text.getBlocktype(),text.getPossentece(),text.getSenttext());
                    
                }
            }
        }*/
        
    }
    
}
