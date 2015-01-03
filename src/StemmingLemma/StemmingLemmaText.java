/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package StemmingLemma;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.text.Annotation;
import java.util.Properties;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

/**
 *
 * @author khaledd
 */
public class StemmingLemmaText {
    private StanfordCoreNLP pipeline;
    private edu.stanford.nlp.pipeline.Annotation document;
    public StemmingLemmaText()
    {
        Properties props = new Properties();

        props.put("annotators", "tokenize, ssplit, pos, lemma");
        pipeline = new StanfordCoreNLP(props);
        
  //      Annotation document = new Annotation(text);
//        pipeline.annotate((Iterable<edu.stanford.nlp.pipeline.Annotation>) document);
         

        
    }
    public String getStem_lemmaText(String text)
    {
        String str="";
        document = pipeline.process(text);
        for(CoreMap sentence: document.get(SentencesAnnotation.class)) {
            for(CoreLabel token: sentence.get(TokensAnnotation.class)) {
                String word = token.get(TextAnnotation.class);
                String lemma = token.get(LemmaAnnotation.class);
                str +=lemma+" ";
                
            }
        }
        return str;
    }
    
}
