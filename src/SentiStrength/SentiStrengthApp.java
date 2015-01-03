/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SentiStrength;

/**
 *
 * @author khaledd
 */

import uk.ac.wlv.sentistrength.*;

public class SentiStrengthApp {
    SentiStrength sentiStrength;
    String ssthInitialisation[] = {"sentidata", "c:/SentStrength_Data/", "explain"};
    public int classificateSentiment(String received) {
		String[] values = received.split(" ");
		int v1 = Integer.valueOf(values[0]);
		int v2 = Integer.valueOf(values[1]);
		int result = v1+v2;
		return result;
    }

    public String sentimentlebel(int score)
    {
        String label="";
        
        if(score<0)
        {
            label="negative";
        }
        else if(score>0)
        {
            label="positive";
        }
        else if(score==0)
        {
            label="neutral";
        }
                    
        
        return label;
        
    }
    void SentiStrengthApp()
    {
        sentiStrength = new SentiStrength(); 
        sentiStrength.initialise(ssthInitialisation);
    }
    public int SentiWordScore(String str) {
   
        int score;
        score=classificateSentiment(sentiStrength.computeSentimentScores(str));
        System.out.println("senti score:"+str+": "+score); 
      
        return score;
    }
}
