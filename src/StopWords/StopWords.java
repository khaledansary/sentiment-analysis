/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package StopWords;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.ClassicTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

/**
 *
 * @author khaledd
 */
public class StopWords {
    
    public String removeStopwords(String input)
    {
        TokenStream tokenStream = new ClassicTokenizer(Version.LUCENE_35, new StringReader(input));
// remove stop words
        tokenStream = new StopFilter(Version.LUCENE_35, tokenStream, EnglishAnalyzer.getDefaultStopSet());

        // retrieve the remaining tokens
        Set<String> tokens = new HashSet<String>();
        CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
        String str="";
        try {
            tokenStream.reset();
        } catch (IOException ex) {
            Logger.getLogger(StopWords.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (tokenStream.incrementToken()) {
                tokens.add(token.toString());
                str +=token.toString()+" ";
                //System.out.println(token.toString());
            }
        } catch (IOException e) {
            // log
        }
        return str;
    }
}
