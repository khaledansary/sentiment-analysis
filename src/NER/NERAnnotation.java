/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NER;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Triple;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author khaledd
 */
public class NERAnnotation {
   
    private static final Properties props = new Properties();
    private StanfordCoreNLP pipeline;
    
    public StanfordCoreNLP initNER()
    {
       //  props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
        //props.put("ssplit.isOneSentence", "true");
        props.put("pos.model", "H:\\nlp jar files\\stanford-postagger-2014-08-27\\stanford-postagger-2014-08-27\\models\\english-left3words-distsim.tagger");
        props.put("pos.maxlen", "30");
        
      //  props.put("parse.model", "H:\\NER\\germanPCFG.ser.gz");
       // props.put("parse.flags", "");
        
        //props.put("encoding", "utf-8");
        
       
        props.put("ner.model", "H:\\NER\\english.muc.7class.nodistsim.crf.ser.gz");
         props.put("ner.useSUTime", "false");
       // props.put("ner.applyNumericClassifiers", "false");
        pipeline = new StanfordCoreNLP(props);
        
        return pipeline;
    }
     public List<Entity> extractEntities(
            CoreMap sentence) {
        List<Entity> entityList = new ArrayList<Entity>();

        CoreLabel prevEntity = null;
        String tag="";

        for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
            String entityTag = token.get(NamedEntityTagAnnotation.class);
           
            //System.out.println(entityTag);
            if (entityTag.compareToIgnoreCase("LOCATION") == 0
                    || entityTag.compareToIgnoreCase("DATE") == 0
                    || entityTag.compareToIgnoreCase("PERSON") == 0
                    || entityTag.compareToIgnoreCase("ORGANIZATION") == 0
                    || entityTag.compareToIgnoreCase("MISC") == 0) 
            {
                 
                if (prevEntity != null) {
                    if (prevEntity.get(NamedEntityTagAnnotation.class)
                            .compareToIgnoreCase(entityTag) == 0
                            && prevEntity.endPosition() == token
                                                            .beginPosition() - 1) 
                    {
                        prevEntity.setEndPosition(token.endPosition());
                        prevEntity.set(TextAnnotation.class,prevEntity.get(TextAnnotation.class) + " " + token.get(TextAnnotation.class));
                       // tag=entityTag;
                        
                       // System.out.println(entityTag);
                    } 
                    else {
                        Triple<String, Integer, Integer> triple=new Triple<String, Integer, Integer>(
                        prevEntity.get(TextAnnotation.class),prevEntity.beginPosition(), prevEntity.endPosition());
                        entityList.add(new Entity(triple,tag));
                        prevEntity = token;
                        tag=entityTag;
                        
                        
                    }
                } 
                else 
                {
                    prevEntity = token;
                    tag=entityTag;
                        //System.out.println(entityTag);
                }
            }
        }

        if (prevEntity != null) {
            
            Triple<String, Integer, Integer> triple=new Triple<String, Integer, Integer>(
            prevEntity.get(TextAnnotation.class),prevEntity.beginPosition(), prevEntity.endPosition());
                        entityList.add(new Entity(triple,tag));
                        tag="";
                       // System.out.println(tag);
        }

        return entityList;
    }

     
     
     
     public void GermanNER(String text, StanfordCoreNLP pipeline)
     {
        
               
      
        Annotation document = new Annotation(text);
        
        pipeline.annotate(document);
       
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
     
        for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
          
          String word = token.get(TextAnnotation.class);
          String ne = token.get(NamedEntityTagAnnotation.class);    
          if(!ne.equals("O"))
          {
            System.out.println(word+" "+ ne);
          }
        }
      
        
       
      }
    
     }
     public String MultiNER(String text, StanfordCoreNLP pipeline)
     {
        
        String NERText="";
         
        Annotation document = new Annotation(text);      
        List<String> matches = new ArrayList<String>();
       
        System.out.println("sent NER:"+text);
        pipeline.annotate(document);
       
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        
        for(CoreMap sentence: sentences) {
     
           List<Entity> triple = new ArrayList<Entity>();
           triple = extractEntities(sentence);
       
            for(int i=0;i<triple.size();i++)
            {
                System.out.println("entities: "+triple.get(i).nerentities.first()+" type "+triple.get(i).getNertype());
                
                NERText +=triple.get(i).nerentities.first()+"-"+triple.get(i).getNertype();
                NERText +=",";
              //  System.out.println("entities: "+triple.get(i).getNertype());
                //System.out.println(triple.get(i).second());
                //System.out.println(triple.get(i).third());
            }
        }
        return NERText;
    
    
     
    }
}

/*
String word = token.get(CoreAnnotations.TextAnnotation.class);

            int previousWordIndex;
            if (entity.equals(token.get(CoreAnnotations.NamedEntityTagAnnotation.class))) {
                count++;
                if (previousCount != 0 && (previousCount + 1) == count) {
                    previousWordIndex = matches.size() - 1;
                    String previousWord = matches.get(previousWordIndex);
                    matches.remove(previousWordIndex);
                    previousWord = previousWord.concat(" " + word);
                    matches.add(previousWordIndex, previousWord);

                } else {
                    matches.add(word);
                }
                previousCount = count;
            }
            else
            {
                count=0;
                previousCount=0;
            }
*/