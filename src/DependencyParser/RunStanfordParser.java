/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DependencyParser;


import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.io.NumberRangeFileFilter;
import edu.stanford.nlp.io.NumberRangesFileFilter;
import edu.stanford.nlp.ling.*;

import edu.stanford.nlp.parser.KBestViterbiParser;
import edu.stanford.nlp.parser.ViterbiParser;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WhitespaceTokenizer;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.trees.international.arabic.ArabicTreebankLanguagePack;
import edu.stanford.nlp.util.Function;
import edu.stanford.nlp.util.Generics;

import edu.stanford.nlp.util.Pair;
import edu.stanford.nlp.util.ScoredObject;
import edu.stanford.nlp.util.Timing;
 
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
/**
 *
 * @author khaledd
 */
public final class RunStanfordParser {
    
    public RunStanfordParser(String filename) throws FileNotFoundException, IOException
    {
        // input format: data directory, and output directory
		
        String fileToParse=filename;

        LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
        //lp.setOptionFlags(new String[]{"-maxLength", "80", "-retainTmpSubcategories"}); // set max sentence length if you want

        // Call parser on files, and tokenize the contents
        FileInputStream fstream = new FileInputStream(fileToParse);
        DataInputStream in = new DataInputStream(fstream); // Get the object of DataInputStream
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringReader sr; // we need to re-read each line into its own reader because the tokenizer is over-complicated garbage
        PTBTokenizer tkzr; // tokenizer object
        WordStemmer ls = new WordStemmer(); // stemmer/lemmatizer object

        // Read File Line By Line
        String strLine;
        while ((strLine = br.readLine()) != null)   {
            System.out.println ("Tokenizing and Parsing: "+strLine); // print current line to console

            // do all the standard java over-complication to use the stanford parser tokenizer
            sr = new StringReader(strLine);
            tkzr = PTBTokenizer.newPTBTokenizer(sr);
            List toks = tkzr.tokenize();
            System.out.println ("tokens: "+toks);

                Tree parse = (Tree) lp.apply(toks); // finally, we actually get to parse something

             // Output Option 1: Printing out various data by accessing it programmatically

                // Get words, stemmed words and POS tags
                    ArrayList<String> words = new ArrayList();
                    ArrayList<String> stems = new ArrayList();
                    ArrayList<String> tags = new ArrayList();

                    // Get words and Tags
                    ls.visitTree(parse); // apply the stemmer to the tree
                    for (TaggedWord tw : parse.taggedYield()){
                        if (tw.tag().startsWith("N") || tw.tag().startsWith("J")) {
                            words.add(tw.word());
                            tags.add(tw.tag());
                        }
                    }
                    System.out.println("Noun and Ajective words: "+words); 
                    System.out.println("POStags: "+tags); 

                   // Get stems
                    ls.visitTree(parse); // apply the stemmer to the tree
                    for (TaggedWord tw : parse.taggedYield()){
                            stems.add(tw.word());
                    }

                    // Get dependency tree
                TreebankLanguagePack tlp = new PennTreebankLanguagePack();
                GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
                GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
                Collection tdl = gs.typedDependenciesCollapsed();

                    // And print!
                System.out.println("words: "+words); 
                System.out.println("POStags: "+tags); 
                System.out.println("stemmedWordsAndTags: "+stems); 
                System.out.println("typedDependencies: "+tdl); 
              //  getAspect_OpinionWord(tdl.toString(),words,tags);


                TreePrint tp = new TreePrint("words,penn");
               //TreePrint tn = new TreePrint("words,typedDependenciesCollapsed");
               //TreePrint to = new TreePrint("rootLabelOnlyFormat,penn");
               
               
              //System.out.println("Tree print"+tp.); 
               tp.printTree(parse);
               //tn.printTree(parse);
               System.out.println("Noun Phrases are: -------"); //(NP (DT a) (JJ temporary) (NN searcher))
               String reg="(\\(DT \\w*\\) \\((NN||NNS||NNP) \\w*\\)) | (\\(DT \\w*\\) \\((JJ||JJR||JJS) \\w*\\) \\((NN||NNS||NNP) \\w*\\)) | (\\((NN||NNS||NNP) \\w*\\) \\((NN||NNS||NNP) \\w*\\)) | (\\(DT \\w*\\) \\((NN||NNS||NNP) \\w*\\) \\((NN||NNS||NNP) \\w*\\))";
               Pattern patt  = Pattern.compile(reg);
               System.out.println(" Noun Phrase List:..");
               dfs(parse,parse,patt);
               
                //for (Tree subtree: parse)
                //{

                 /* if(subtree.label().value().equals("NP"))
                  {
                      
                    String a=subtree.toString();  
                   //System.out.println(a);
                    Matcher match = patt.matcher(a.trim());
                    while(match.find()) {
                         System.out.println("NP: "+match.group());
                    }
                  }*/
                    /*for(Tree np:subtree)
                    {
                        if(np.label().value().equals("NP"))
                        {
                            for(Tree n:np)
                            {
                                if(np.label().value().equals("\\(DT \\w*\\) \\((NN||NNS||NNP) \\w*\\)"))
                                {
                                     System.out.println("NP tag Tags: "+np);
                                     System.out.println(Sentence.listToString(np.yield()));
                                }
                                else if(np.label().value().equals("\\((NN||NNS||NNP) \\w*\\) \\((NN||NNS||NNP) \\w*\\)"))
                                {
                                    System.out.println("NP tag Tags: "+np);
                                     System.out.println(Sentence.listToString(np.yield()));
                                }
                                else if(np.label().value().equals("\\(DT \\w*\\) \\((NN||NNS||NNP) \\w*\\) \\((NN||NNS||NNP) \\w*\\)"))
                                {
                                    System.out.println("NP tag Tags: "+np);
                                    System.out.println(Sentence.listToString(np.yield()));
                                }
                                else{
                                    if(n.label().value().equals("NP"))
                                    {
                          
                                        System.out.println("N tag Tags: "+n);
                                        System.out.println(Sentence.listToString(n.yield()));
                                    }
                                }
                                    
                                
                            }
                           
                        }
                    }*/
                     

                   //}
                //}
                System.out.println(); // separate output lines*/
        }

    }
        public static void dfs(Tree node, Tree parent,Pattern patt) {
          if (node == null || node.isLeaf()) {
             return;
          }
          //if node is a NP - Get the terminal nodes to get the words in the NP      
          if(node.value().equals("NP") ) {

          //   System.out.println(" Noun Phrase is ");
             /*List<Tree> leaves = node.getLeaves();

             for(Tree leaf : leaves) {
                System.out.println(leaf.toString());

             }*/
              //Matcher match = patt.matcher(a.trim());
               Matcher match = patt.matcher(node.toString().trim());
                    while(match.find()) {
                         System.out.println("NP: "+match.group());
                    }
           

           
             

        }

        for(Tree child : node.children()) {
             dfs(child, node,patt);
        }

     }
    public void getAspect_OpinionWord(String nodes,ArrayList<String> w,ArrayList<String> t){
        
        //[det(voice-2, the-1), nsubj(clear-9, voice-2), poss(phone-5, my-4), prep_on(voice-2, phone-5), cop(clear-9, be-6), neg(clear-9, not-7), advmod(clear-9, so-8), root(ROOT-0, clear-9), acomp(clear-9, worse-11), poss(phone-15, my-13), amod(phone-15, previous-14), prep_than(worse-11, phone-15)]
       // String subreg="(nsubj)||(nsubjpass)||(nn)||(dep)";
        String[] splittednode = nodes.split("\\),");
        List<String> subjects = new ArrayList<String>();
        
        for(String node:splittednode)
        {
            System.out.println(node);
            String relation = node.substring(0,node.indexOf("(")).trim();
            System.out.println(relation);
            String govrnor = node.substring(node.indexOf("(")+1, node.indexOf(",")).trim();
            
            String gov = govrnor.replace(govrnor.substring(govrnor.indexOf("-")),"").trim();
            System.out.println(gov);
            
            
            String dep =node.substring(node.indexOf(",")+1).trim();
            
            String dependency = dep.replace(dep.substring(dep.indexOf("-")),"").trim();
            
            System.out.println(dependency);
            
            if(relation.equals("nsubj")||relation.equals("nsubjpass")||relation.equals("nn")||relation.equals("dep"))
            {
                
                
                String postag = t.get(w.indexOf(gov));
                
                System.out.println("POS Tag "+t.get(w.indexOf(gov)));
                
                if(postag.equals("VB")||postag.equals("VBD")||postag.equals("VBG")||postag.equals("VBN")||postag.equals("VBP")||postag.equals("VBZ"))
                {
                    System.out.println("POS Tag "+t.get(w.indexOf(gov)));
                    System.out.println("Verb "+govrnor);
                }
            }
        }

        //System.out.println("typedDependencies: string"+nodes); 
        
        
    }

	
}

