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

import TopicAspect.Topic;
import TopicAspect.TopicAspect;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParseJson {

        public List<JsonData> parseJson(String data)
        {
            Gson gson = new Gson();
            AnnotateText anotate = gson.fromJson(data, AnnotateText.class);
            List<Group> group = anotate.getGroups();
            List<JsonData> ls=new ArrayList<JsonData>();
            for(Group gp:group)
            {
                List<Instance> instance= gp.getInstances();
               // System.out.println(".............Topic");
                for(Instance n: instance){
                    //System.out.println(n.getText());
                   // System.out.println("...........Aspect");
                    List<Aspect> aspects =gp.getAspects();
                    List<Positive> positive=gp.getPositives();
                    List<Negative> negatives=gp.getNegatives();
                    int i=0;
                    for(Aspect aspect: aspects){
                        //JsonData jsondata = new JsonData();
                       // System.out.println("Aspect words"+aspect.getText());
                        
                       
                        ls.add(new JsonData(n.getText(),aspect.getText(),positive.get(i).getText(),negatives.get(i).getText()));
                        i++;
                    }
                }
                /*System.out.println("...........Aspect");
                List<Aspect> aspects =gp.getAspects();
                for(Aspect aspect: aspects){
                    System.out.println(aspect.getText());
                }
                System.out.println("...........Positive words");
                
                for(Positive pos: positive){
                    
                }
                System.out.println("............Negative words");
                
                for(Negative neg: negatives){
                   
                }*/
                
                
            }
           return ls;
        }
        public List<Topic> parseJsonData(String data)
        {
            Gson gson = new Gson();
            AnnotateText anotate = gson.fromJson(data, AnnotateText.class);
            List<Group> group = anotate.getGroups();
            List<Topic> ls=new ArrayList<Topic>();
            for(Group gp:group)
            {
                List<Instance> instance= gp.getInstances();
               // System.out.println(".............Topic");
                for(Instance n: instance){
                    //System.out.println(n.getText());
                   // System.out.println("...........Aspect");
                    List<Aspect> aspects =gp.getAspects();
                    List<Positive> positive=gp.getPositives();
                    List<Negative> negatives=gp.getNegatives();
                    int i=0;
                    Topic topic = new Topic();
                    
                    topic.setTopic(n.getText());
                    List<TopicAspect> ta = new ArrayList<TopicAspect>();
                    
                    for(Aspect aspect: aspects){
                        
                        TopicAspect taspects = new TopicAspect();
                        
                        taspects.setAspecttext(aspect.getText());
                        try{
                             if(positive.get(i).getText()!=null)
                                {
                                    taspects.setPositiveword(positive.get(i).getText());
                                }
                                if(negatives.get(i).getText()!=null)
                                {
                                   taspects.setNegativeword(negatives.get(i).getText());
                                }
                        }catch(Exception e)
                        {
                            System.out.println("pos out of bount"+e);
                        }
                        
                         
                        
                       
                        
                        ta.add(taspects);
                       
                        
                        //JsonData jsondata = new JsonData();
                       // System.out.println("Aspect words"+aspect.getText());
                        
                        //Aspect as = new Aspect();
                        //as.setText(aspect.getText());
                        
                        //topic.;
                       
                        //ls.add(new JsonData(n.getText(),aspect.getText(),positive.get(i).getText(),negatives.get(i).getText()));
                        i++;
                    }
                    topic.setAspectlist(ta);
                    ls.add(topic);
                }
                /*System.out.println("...........Aspect");
                List<Aspect> aspects =gp.getAspects();
                for(Aspect aspect: aspects){
                    System.out.println(aspect.getText());
                }
                System.out.println("...........Positive words");
                
                for(Positive pos: positive){
                    
                }
                System.out.println("............Negative words");
                
                for(Negative neg: negatives){
                   
                }*/
                
                
            }
           return ls;
        }
	
	public static void dumpJSONElement(JsonElement element,String type) {
            if (element.isJsonObject()) {
                //System.out.println("Is an object");
                JsonObject obj = element.getAsJsonObject();
                java.util.Set<java.util.Map.Entry<String,JsonElement>> entries = obj.entrySet();
                java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entries.iterator();
                while (iter.hasNext()) {
                    java.util.Map.Entry<String,JsonElement> entry = iter.next();
                  //  System.out.println("Key: " + entry.getKey());
                    if(entry.getKey().toString().equals("instances"))
                    {
                          System.out.println("............Topic: ");
                           dumpJSONElement(entry.getValue(),"topic");
                           
                           
                    }
                    if(entry.getKey().toString().equals("aspects"))
                    {
                          System.out.println("............aspects: ");
                           dumpJSONElement(entry.getValue(),"aspect");
                           
                    }
                    else if(entry.getKey().toString().equals("positives"))
                    {
                        System.out.println(".............Positive Words ");
                        dumpJSONElement(entry.getValue(),"positive");
                    }
                    else if(entry.getKey().toString().equals("negatives"))
                    {
                        System.out.println(".............negatives Words ");
                        dumpJSONElement(entry.getValue(),"negative");
                    }
                    else
                    {
                        dumpJSONElement(entry.getValue(),"");
                    }
                    
                    
                    
                }

            } else if (element.isJsonArray()) {
                JsonArray array = element.getAsJsonArray();
                //System.out.println("Is an array. Number of values: " + array.size());
                java.util.Iterator<JsonElement> iter = array.iterator();
                while (iter.hasNext()) {
                    JsonElement entry = iter.next();
                    
                    dumpJSONElement(entry,"");
                }
            } else if (element.isJsonPrimitive()) {
                //System.out.println("Is a primitive");
                JsonPrimitive value = element.getAsJsonPrimitive();
                if (value.isBoolean()) {
                    //System.out.println("Is boolean: " + value.getAsBoolean());
                } else if (value.isNumber()) {
                  //  System.out.println("Is number: " + value.getAsNumber());
                } else if (value.isString()) {
                    //if(!value.getAsString().equals("empty"))
                    //{
                    //if(type.equals("topic"))
                    //{
                        System.out.println(type+" :" + value.getAsString());
                    //}
                }
            } else if (element.isJsonNull()) {
                System.out.println("Is NULL");
            } else {
                System.out.println("Error. Unknown type of element");
            }
        }
	public void jsonParse(String filePath) {

            FileReader fr = null;
            try {
                JsonParser parser = new JsonParser();
                fr = new FileReader(filePath);
                
                JsonElement data = parser.parse(fr);
                //parseJson(data);
                dumpJSONElement(data,"");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ParseJson.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(ParseJson.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	}

}