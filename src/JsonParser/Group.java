package JsonParser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author khaledd
 */


import java.util.ArrayList;
import java.util.List;


public class Group {


private String context;

private List<Instance> instances = new ArrayList<Instance>();

private List<Positive> positives = new ArrayList<Positive>();

private List<Negative> negatives = new ArrayList<Negative>();

private List<Aspect> aspects = new ArrayList<Aspect>();

/**
* 
* @return
* The context
*/
public String getContext() {
return context;
}

/**
* 
* @param context
* The context
*/
public void setContext(String context) {
this.context = context;
}

/**
* 
* @return
* The instances
*/
public List<Instance> getInstances() {
return instances;
}

/**
* 
* @param instances
* The instances
*/
public void setInstances(List<Instance> instances) {
this.instances = instances;
}

/**
* 
* @return
* The positives
*/
public List<Positive> getPositives() {
return positives;
}

/**
* 
* @param positives
* The positives
*/
public void setPositives(List<Positive> positives) {
this.positives = positives;
}

/**
* 
* @return
* The negatives
*/
public List<Negative> getNegatives() {
return negatives;
}

/**
* 
* @param negatives
* The negatives
*/
public void setNegatives(List<Negative> negatives) {
this.negatives = negatives;
}

/**
* 
* @return
* The aspects
*/
public List<Aspect> getAspects() {
return aspects;
}

/**
* 
* @param aspects
* The aspects
*/
public void setAspects(List<Aspect> aspects) {
this.aspects = aspects;
}

}