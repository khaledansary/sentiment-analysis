/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AspectMining;

import POSTagging.PosTagger;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khaledd
 */
public class Aspects {
    
    PosTagger pos;
    void Aspects()
    {
        pos = new PosTagger();
    }
    
    public void docAspectMinining(int docid,String location)
    {
        Map<Integer, Map> docaspect;
        docaspect = getDocAspects(docid,location);
        
        Set<Integer> keys = docaspect.keySet();
        for (Integer key : keys) {

           // System.out.println("docid = " + key);

           
            Set<String> sentence = docaspect.get(key).keySet();
            for (String aspectkey : sentence) {
                System.out.println("docid = " + key);
                System.out.println("Sentence = " + aspectkey);
                System.out.println("Aspects = " + docaspect.get(key).get(aspectkey));
                
                
               

                

            }

        }
       
    }
    
    public Map getDocAspects(int docid,String location)
    {
        BufferedReader br;
        Map<Integer, Map> aspects = new HashMap<Integer, Map>();
        try {
            br = new BufferedReader(new FileReader(location));
        
            
            String line = br.readLine();

            while (line != null) {
               
                line = br.readLine();
                //aspects.put(docid,pos.TagingText(line,docid));
                
                
            }
            
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Aspects.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }
        return aspects;
    }
}
