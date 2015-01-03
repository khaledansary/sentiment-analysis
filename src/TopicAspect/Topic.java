/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TopicAspect;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author khaledd
 */
public class Topic {
    
    String topic;
    List<TopicAspect> aspectlist =new ArrayList<TopicAspect>();

    public List<TopicAspect> getAspectlist() {
        return aspectlist;
    }

    public void setAspectlist(List<TopicAspect> aspectlist) {
        this.aspectlist = aspectlist;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    
}
