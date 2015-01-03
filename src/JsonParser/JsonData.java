/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonParser;

/**
 *
 * @author khaledd
 */
public class JsonData {
    private String topic;
    private String aspect;
    private String positiveword;
    private String negativeword;

   
    public void posAspects(String topic, String aspect, String positiveword) {
        this.topic = topic;
        this.aspect = aspect;
        this.positiveword = positiveword;
    }

    public void negAspects(String topic, String aspect, String negativeword) {
        this.topic = topic;
        this.aspect = aspect;
        this.negativeword = negativeword;
    }
    
      public JsonData(String topic, String aspect, String positiveword, String negativeword) {
        this.topic = topic;
        this.aspect = aspect;
        this.positiveword = positiveword;
        this.negativeword = negativeword;
    }

   
   

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public String getPositiveword() {
        return positiveword;
    }

    public void setPositiveword(String positiveword) {
        this.positiveword = positiveword;
    }

    public String getNegativeword() {
        return negativeword;
    }

    public void setNegativeword(String negativeword) {
        this.negativeword = negativeword;
    }
    
    
    
}
