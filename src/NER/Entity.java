/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NER;

import edu.stanford.nlp.util.Triple;

/**
 *
 * @author khaledd
 */
public class Entity {

    
    Triple<String, Integer, Integer> nerentities;
    String nertype;

    public Triple<String, Integer, Integer> getNerentities() {
        return nerentities;
    }

    public void setNerentities(Triple<String, Integer, Integer> nerentities) {
        this.nerentities = nerentities;
    }

    public String getNertype() {
        return nertype;
    }

    public void setNertype(String nertype) {
        this.nertype = nertype;
    }

    public Entity(Triple<String, Integer, Integer> nerentities, String nertype) {
        this.nerentities = nerentities;
        this.nertype = nertype;
    }
    public Entity(Triple<String, Integer, Integer> nerentities) {
        this.nerentities = nerentities;
    }

    public Entity(String nertype) {
        this.nertype = nertype;
    }
    
    
}
