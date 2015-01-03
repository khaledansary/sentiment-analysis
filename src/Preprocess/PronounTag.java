/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Preprocess;

/**
 *
 * @author khaledd
 */
public class PronounTag {
    String noun;
    String pronountag;
    int position;

    public PronounTag(String noun, String pronountag, int position) {
        this.noun = noun;
        this.pronountag = pronountag;
        this.position = position;
    }

    public String getNoun() {
        return noun;
    }

    public void setNoun(String noun) {
        this.noun = noun;
    }

    public String getPronountag() {
        return pronountag;
    }

    public void setPronountag(String pronountag) {
        this.pronountag = pronountag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    
    
    
}
