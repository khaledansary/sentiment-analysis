/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Preprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author khaledd
 */
public class ProcessingPronoun {
    

    List<PronounTag> nounIttag;
    List<PronounTag> nounTheytag;
    
    public List<PronounTag>  pronounItResolver(String postag){
        String regit="(\\w*\\_(NN|NNP)) | (\\w*_(NN|NNP) \\w*_(NN|NNP)) | (\\w*_(JJ|JJR|JJS) \\w*_(NN|NNP)) | (\\w*_(RB(R|S)?) \\w*_(JJ|JJR|JJS) \\w*_(NN|NNP)) | (\\w*_(JJ|JJR|JJS) \\w*_(NN|NNP) \\w*_(NN|NNP) \\w*_(NN|NNP)) | (\\w*_(JJ|JJR|JJS) \\w*_(NN|NNP) \\w*_(NN|NNP))";
       
        Pattern patt1  = Pattern.compile(regit);
        Matcher matchit = patt1.matcher(postag);
        List<PronounTag> plist = new ArrayList<PronounTag>();
        
        
        while(matchit.find()) {
             System.out.println("<"+matchit.group()+","+"it, "+postag.indexOf(matchit.group())+">");
             plist.add(new PronounTag(matchit.group(),"it", postag.indexOf(matchit.group())));
            
        }
                
        
        return plist;
    }
    public List<PronounTag>  pronounTheyResolver(String postag){
         String regthey="(\\w*\\_(NNS|NNPS)) | (\\w*_(NNS|NNPS) \\w*_(NNS|NNPS)) | (\\w*_(JJ|JJR|JJS) \\w*_(NNS|NNPS)) | (\\w*_(RB(R|S)?) \\w*_(JJ|JJR|JJS) \\w*_(NNS|NNPS)) | (\\w*_(JJ|JJR|JJS) \\w*_(NNS|NNPS) \\w*_(NNS|NNPS) \\w*_(NNS|NNPS)) | (\\w*_(JJ|JJR|JJS) \\w*_(NNS|NNPS) \\w*_(NNS|NNPS))";
         Pattern pattthey  = Pattern.compile(regthey);
         List<PronounTag> plist = new ArrayList<PronounTag>();
        Matcher matchthey = pattthey.matcher(postag);
         while(matchthey.find()) {
             System.out.println("<"+matchthey.group()+","+"they>");
              plist.add(new PronounTag(matchthey.group(),"they", postag.indexOf(matchthey.group())));
           
        }
        return plist; 
    }
    public String swapNPtoPRP(String postag, PronounTag LastIt, PronounTag LastThey){
        
        String regit="(\\w*\\_(PRP))";
        Pattern patt1  = Pattern.compile(regit);
        Matcher matchit = patt1.matcher(postag);
        ProcessingPronoun pronoun = new ProcessingPronoun();
        
        
         while(matchit.find()) {
             if(matchit.group().equals("it_PRP") && postag.indexOf(matchit.group())==0 ){
                 postag=postag.replace(matchit.group(), LastIt.getNoun());
                 //System.out.println("it in frist position");
                 
             }
             else if((matchit.group().equals("they_PRP") || matchit.group().equals("them_PRP")) && postag.indexOf(matchit.group())==0)
             {
                 postag=postag.replace(matchit.group(), LastThey.getNoun());
                 
                 //System.out.println("they or them in frist position");
             }
             else{
                 if(matchit.group().equals("it_PRP") && postag.indexOf(matchit.group())!=0 )
                 {
                    nounIttag = new ArrayList<PronounTag>();
                    
                    //System.out.println("it in middle position");
                    //postag=postag.replace(matchit.group(), LastIt.getNoun());
                    nounIttag=pronoun.pronounItResolver(postag);
                    if(nounIttag!=null && !nounIttag.isEmpty())
                    {
                        int maxpostion;
                        String lastnoun="";
                        for(PronounTag noun:nounIttag)
                        {
                            
                            if(noun.position<postag.indexOf(matchit.group()))
                            {
                                maxpostion=noun.position;
                                lastnoun=noun.getNoun();
                                
                               // System.out.println("postion: "+maxpostion+" lastnoun: "+lastnoun+" it position: "+postag.indexOf(matchit.group()));
                            }
                        }
                        if(!lastnoun.isEmpty())
                        {
                             postag=postag.replace(matchit.group(),lastnoun);
                             //System.out.println("set noun <");
                        }
                        else
                        {
                             postag=postag.replace(matchit.group(), LastIt.getNoun());
                             //System.out.println("set noun < failed and set last one");
                        }
                    }
                    else
                    {
                         postag=postag.replace(matchit.group(), LastIt.getNoun());
                         //System.out.println("if does not have any noun");
                    }
                    
                 
                        
                }
                else if((matchit.group().equals("they_PRP") || matchit.group().equals("them_PRP")) && postag.indexOf(matchit.group())!=0)
                {
                   // postag=postag.replace(matchit.group(), LastThey.getNoun());
                    nounTheytag = new ArrayList<PronounTag>();
                    nounTheytag=pronoun.pronounTheyResolver(postag);
                    if(nounTheytag!=null && !nounTheytag.isEmpty())
                    {
                        int maxpostion;
                        String lastnoun="";
                        for(PronounTag noun:nounTheytag)
                        {
                            
                            if(noun.position<postag.indexOf(matchit.group()))
                            {
                                maxpostion=noun.position;
                                lastnoun=noun.getNoun();
                            }
                        }
                        if(!lastnoun.isEmpty())
                        {
                             postag=postag.replace(matchit.group(),  lastnoun);
                        }
                        else
                        {
                             postag=postag.replace(matchit.group(), LastThey.getNoun());
                        }
                    }
                    else
                    {
                        postag=postag.replace(matchit.group(), LastThey.getNoun());
                    }
                }
                
                 //postag=s;
             }
             //System.out.println("<"+matchit.group()+","+"it, "+postag.indexOf(matchit.group())+">");
             
             
        }
        
        return postag;
    }
  
}
