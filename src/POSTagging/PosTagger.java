/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package POSTagging;

//import Sentiwordnet.SentiWordNetFunction;
import DAO.DB_PreporcessDoc;
import NGram.NGramExtractor;
import StemmingLemma.StemmingLemmaText;
import StopWords.StopWords;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author khaledd
 *another/DT sharp/JJ dive/NN
trade/NN figures/NNS
any/DT new/JJ policy/NN measures/NNS
earlier/JJR stages/NNS
Panamanian/JJ dictator/NN Manuel/NNP Noriega/NNP 
 */
public class PosTagger {
    
   private MaxentTagger tagger;
  // LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
   String pathToSWN="H:\\Thesis Development\\Thesis\\NLP\\src\\Sentiwordnet\\SentiWordNet_3.0.0_20130122.txt";
   NGramExtractor ngram = new NGramExtractor();
   List<String> ngramwords = new ArrayList<String>();
   // String reg="(\\(DT \\w*\\) \\((NN||NNS||NNP) \\w*\\)) | (\\(DT \\w*\\) \\((JJ||JJR||JJS) \\w*\\) \\((NN||NNS||NNP) \\w*\\)) | (\\((NN||NNS||NNP) \\w*\\) \\((NN||NNS||NNP) \\w*\\)) | (\\(DT \\w*\\) \\((NN||NNS||NNP) \\w*\\) \\((NN||NNS||NNP) \\w*\\))";
   //String reg = "(((\\w*_DT)(\\w*\\_(NN|NNS|NNP))) | ((\\w*_(NN|NNS|NNP)) (\\w*_(NN|NNS|NNP))) | ((\\w*_DT)(\\w*_(JJ||JJR||JJS))(\\w*_(NN||NNS||NNP))))";
   String reg="(\\w*\\_(NN|NNS|NNP)) | (\\w*_(NN|NNS|NNP) \\w*_(NN|NNS|NNP)) | (\\w*_(JJ|JJR|JJS) \\w*_(NN||NNS||NNP)) | (\\w*_DT \\w*_(JJ|JJR|JJS) \\w*_(NN||NNS||NNP)) | (\\w*_(JJ||JJR||JJS) \\w*_(NN||NNS||NNP) \\w*_(NN||NNS||NNP) \\w*_(NN||NNS||NNP)) | (\\w*_(JJ|JJR|JJS) \\w*_(NN||NNS||NNP) \\w*_(NN||NNS||NNP))";
   String removreg="_(NN(S|P)?|JJ?|DT)";
  // SentiWordNetFunction sentiwordnet = new SentiWordNetFunction(pathToSWN);
   //public  String REGEX = "([a-zA-Z]*_NN\\w?\\w?\\b)||([a-zA-Z]*_JJ\\w?\\w?\\b)||([a-zA-Z]*_DT\\w?\\w?\\b)||([a-zA-Z]*_NNS\\w?\\w?\\b)||([a-zA-Z]*_VBG\\w?\\w?\\b)";
   Pattern patt  = Pattern.compile(reg);
   StemmingLemmaText stemlemma;
   StopWords stopwors;
   DB_PreporcessDoc dbprocess = new DB_PreporcessDoc();
   
   public PosTagger()
   {
       // Initialize the tagger
       tagger = new MaxentTagger("H:\\nlp jar files\\stanford-postagger-2014-08-27\\stanford-postagger-2014-08-27\\models\\english-left3words-distsim.tagger");
       stemlemma = new  StemmingLemmaText();
   }
   
   public MaxentTagger getPosTagger()
   {
       //tagger = new MaxentTagger("H:\\nlp jar files\\stanford-postagger-2014-08-27\\stanford-postagger-2014-08-27\\models\\english-left3words-distsim.tagger");
       return tagger;
   }
   public Map TagingText(String str,int docid,int blockid, int blocktype)
   {
       // String rmstText=stopwors.removeStopwords(str);
        //List<List<String>> pos_aspect= new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> aspectslist = new ArrayList<String>();
        String tagged = tagger.tagString(stemlemma.getStem_lemmaText(str));
        System.out.println();
        System.out.println(tagged);
        //aspectslist=getAspects(tagged);
        
        dbprocess.commit_sentences(docid, str, tagged,blockid,blocktype);
        
        map.put(str,aspectslist);
        return map;
   }
   
   public List<String> getAspects(String tag_sentence)
   {
       List<String> aspects =new ArrayList<String>();
       System.out.println("Aspects=====");
       System.out.println();
       
        Matcher match = patt.matcher(tag_sentence);
        while(match.find()) {
             System.out.println("NP: "+match.group());
             System.out.println("Aspects: "+match.group().replaceAll(removreg,""));
             aspects.add(match.group().replaceAll(removreg,""));
            
        }
        
        return aspects;
   }
   
   public String Taggedwords(String str)
   {
        String words = null;
        return words;
   }
   
   public List<String> ExtractWordsByRegex(String sentenceWithTags,String REGEX) {
    List<String> nouns = new ArrayList<String>();
    String[] words = sentenceWithTags.split("\\s+");
    for (int i = 0; i < words.length; i++) {
        if(words[i].matches(REGEX)) {
                //System.out.println(" Matched ");
                 //remove the suffix _NN* and retain  [a-zA-Z]*
                //nouns.add(words[i].replaceAll("_NN\\w?\\w?\\b", ""));
                nouns.add(words[i]);
            }
        }
        return nouns;
    }
   
   public List<String> ExtractAdjWordsByRegex(String sentenceWithTags,String REGEX) {
    List<String> adjectives = new ArrayList<String>();
    String[] words = sentenceWithTags.split("\\s+");
    for (int i = 0; i < words.length; i++) {
        if(words[i].matches(REGEX)) {
                //System.out.println(" Matched ");
                 //remove the suffix _NN* and retain  [a-zA-Z]*
                //nouns.add(words[i].replaceAll("_NN\\w?\\w?\\b", ""));
                
                try{
                   // Double score = sentiwordnet.extract(words[i].replaceAll("(_JJ\\w?\\w?\\b)||(_JJS\\w?\\w?\\b)", ""),"a");
             //       adjectives.add(words[i].replaceAll("(_JJ\\w?\\w?\\b)||(_JJS\\w?\\w?\\b)", "")+"\t"+score+"\n");
                   
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        }
        return adjectives;
    }

            
   
    
}
