/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NGram;

/**
 *
 * @author khaledd
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;

import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.shingle.ShingleAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Version;



public class NGramExtractor {

    public List<String> extract(String filename,int min,int max)
    {
        List<String> ngramwords= new ArrayList<String>();
        try
            {			
                    Reader reader = new StringReader(filename.trim());

                    // Parse the file into n-gram tokens
                    SimpleAnalyzer simpleAnalyzer = new SimpleAnalyzer(Version.LUCENE_35);			
                    ShingleAnalyzerWrapper shingleAnalyzer = new ShingleAnalyzerWrapper(simpleAnalyzer, min, max);

                    TokenStream stream = shingleAnalyzer.tokenStream("contents", reader);
                    CharTermAttribute charTermAttribute = stream.getAttribute(CharTermAttribute.class);
                    stream.reset();
                    // Do something with the tokens
                    ArrayList<String> gram = new ArrayList<String>();
                    while (stream.incrementToken())
                    {	
                        ngramwords.add(charTermAttribute.toString());
                            					
                    }

                    stream.end();
                    stream.close();
                    //LOG.log(Level.INFO, datasentences+sentencesfilefolder.getName() + " completed.");
            }

            catch (FileNotFoundException e)
            {
                System.out.println("1: "+e);
                    //LOG.log(Level.SEVERE, "Parse Failed.  Reason: " + e.getMessage(), e);
            }

            catch (IOException e)
            {
                System.out.println("2: "+e);
                    //LOG.log(Level.SEVERE, "Parse Failed.  Reason: " + e.getMessage(), e);
            }
        return ngramwords;
    }
}
