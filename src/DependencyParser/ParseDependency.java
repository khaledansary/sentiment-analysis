/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DependencyParser;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.TreeGraphNode;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import java.io.StringReader;
import java.text.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author khaledd
 */
public class ParseDependency {
    StringReader sr;
    PTBTokenizer tkzr;
    LexicalizedParser lp;
    public ParseDependency()
    {
         lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
    }
    public List<String> getPair(Object[] list,String relation1,String relation2,int findequal)
    {
        List<String> result = new ArrayList<String>();
        
        for (Object object : list) {
            TypedDependency typedDependency =(TypedDependency) object;
            TreeGraphNode tgv = typedDependency.gov();
            TreeGraphNode tdp = typedDependency.dep();
            if (typedDependency.reln().getShortName().equals(relation1)) {
                if(relation1.equals("amod")&& relation2.equals("") && findequal==6)
                {
                    result.add(typedDependency.gov().nodeString()+","+typedDependency.dep().nodeString());
                }
                else
                {
                    for (Object object2 : list) {
                        TypedDependency typedDependency2 = (TypedDependency) object2;
                        TreeGraphNode tgv2 = typedDependency2.gov();
                        TreeGraphNode tdp2 = typedDependency2.gov();
                        if (typedDependency2.reln().getShortName().equals(relation2)) {

                            if(findequal==1)
                            {
                                if((typedDependency.dep().nodeString().equals(typedDependency2.gov().nodeString())) && tdp==tgv2)
                                {
                                    //System.out.println("find dependency");
                                    //System.out.println(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString());
                                    result.add(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+","+typedDependency.gov().nodeString());
                                }
                            }
                            else if(findequal==2)
                            {
                                if((typedDependency.gov().nodeString().equals(typedDependency2.gov().nodeString())) && tgv==tgv2)
                                {
                                    result.add(typedDependency2.gov().nodeString());
                                    //System.out.println("find dependency2");
                                    //System.out.println(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString());
                                    //result=typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString();
                                }
                            }
                            else if(findequal==22)
                            {
                                if((typedDependency.dep().nodeString().equals(typedDependency2.gov().nodeString())) && tdp==tdp2)
                                {
                                    result.add(typedDependency2.dep().nodeString()+" "+typedDependency.dep().nodeString());
                                    //System.out.println("find dependency2"); 
                                    //System.out.println(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString());
                                    //result=typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString();
                                }
                            }
                            else if(findequal==3)
                            {
                                if(typedDependency.gov().nodeString().equals(typedDependency2.gov().nodeString()) && tgv==tgv2)
                                {
                                    //System.out.println("find dependency");
                                    //System.out.println(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString());
                                    result.add(typedDependency2.dep().nodeString()+","+typedDependency.gov().nodeString());
                                }
                            }
                            else if(findequal==4)
                            {
                               
                                if((typedDependency.gov().nodeString().equals(typedDependency2.gov().nodeString())) && tgv==tgv2)
                                {
                                    //System.out.println("find dependency");
                                    //System.out.println(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString());
                                    result.add(typedDependency.dep().nodeString()+","+typedDependency2.dep().nodeString());
                                }
                            }
                            else if(findequal==5)
                            {
                                if((typedDependency.gov().nodeString().equals(typedDependency2.gov().nodeString()))&& tgv==tgv2)
                                {
                                    result.add(typedDependency2.dep().nodeString());
                                    //System.out.println("find dependency2");
                                    //System.out.println(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString());
                                    //result=typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString();
                                }
                            }
                            else if(findequal==55)
                            {
                                if((typedDependency.gov().nodeString().equals(typedDependency2.gov().nodeString())) && tgv==tgv2)
                                {
                                    //System.out.println("find dependency");
                                    //System.out.println(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString());
                                    result.add(typedDependency2.dep().nodeString()+" "+typedDependency.gov().nodeString());
                                }
                            }
                            else if(findequal==7)
                            {
                                if((typedDependency.dep().nodeString().equals(typedDependency2.gov().nodeString())) && tdp==tgv2)
                                {
                                    result.add(typedDependency.gov().nodeString()+","+typedDependency2.dep().nodeString());
                                    //System.out.println("find dependency2");
                                    //System.out.println(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString());
                                    //result=typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString();
                                }
                            }
                            else if(findequal==8)
                            {
                                if((typedDependency.gov().nodeString().equals(typedDependency2.gov().nodeString())) && tgv==tgv2)
                                {
                                    //System.out.println("find dependency");
                                    //System.out.println(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString());
                                    result.add(typedDependency2.dep().nodeString()+","+typedDependency.dep().nodeString());
                                }
                            }
                            else if(findequal==9)
                            {
                                if((typedDependency.dep().nodeString().equals(typedDependency2.dep().nodeString())) && tdp==tdp)
                                {
                                    //System.out.println("find dependency");
                                    //System.out.println(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString());
                                    result.add(typedDependency.gov().nodeString()+","+typedDependency2.gov().nodeString());
                                }
                            }
                            else if(findequal==11)
                            {
                                if((typedDependency.gov().nodeString().equals(typedDependency2.gov().nodeString())) && tgv==tgv)
                                {
                                    //System.out.println("find dependency");
                                    //System.out.println(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString());
                                    result.add(typedDependency.gov().nodeString()+","+typedDependency2.gov().nodeString());
                                }
                            }
                            else if(findequal==12)
                            {
                                if((typedDependency.dep().nodeString().equals(typedDependency2.dep().nodeString())) && tdp==tdp)
                                {
                                    //System.out.println("find dependency");
                                    //System.out.println(typedDependency2.dep().nodeString()+" "+typedDependency2.gov().nodeString()+", "+typedDependency.gov().nodeString());
                                    result.add(typedDependency2.dep().nodeString() +" "+typedDependency2.gov().nodeString()+","+typedDependency.gov().nodeString());
                                }
                            }
                        }
                    }
                }
            }
            
            

        }
        
        return result;
    }
    
    public List<String> getAspect_OpinionPair(String str)
    {
        sr = new StringReader(str);
        tkzr = PTBTokenizer.newPTBTokenizer(sr);
        List toks = tkzr.tokenize();
        Tree parse = (Tree) lp.apply(toks);
        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
        Collection<TypedDependency> td = gs.typedDependenciesCollapsed();
        System.out.println(td);
        List<String> ls = new ArrayList<String>();
        Object[] list = td.toArray();
        List<String> pair_1=getPair(list,"nsubj","nn",1);
        for(String pair1: pair_1)
        {
            if(!pair1.isEmpty())
            {
                System.out.println("find pair1: "+pair1);
                ls.add(pair1);
            }
        }
        
        List<String> pair_2=getPair(list,"nsubj","xcomp",2);
        for(String pair2: pair_2)
        {
            if(!pair2.isEmpty())
            {
                List<String> pair_22=getPair(list,"dobj","nn",22);
                for(String pair22: pair_22)
                {
                    if(!pair22.isEmpty())
                    {
                        System.out.println("find pair2: ("+pair22+","+pair2+")");
                        ls.add(pair22+", "+pair2);
                    }
                }
            }
        }
        List<String> pair_3=getPair(list,"nsubj","dobj",3);
        for(String pair3: pair_3)
        {
            if(!pair3.isEmpty())
            {
                System.out.println("find pair3: "+pair3);
                ls.add(pair3);
            }
        }    
        List<String> pair_4=getPair(list,"nsubj","acomp",4);
        for(String pair4: pair_4)
        {
            if(!pair4.isEmpty())
            {
                System.out.println("find pair4: "+pair4);
                ls.add(pair4);
            }
        }    
        List<String> pair_5=getPair(list,"nsubj","acomp",5);
        for(String pair5: pair_5)
        {
            if(!pair5.isEmpty())
            {
                List<String> pair_55=getPair(list,"rcmod","nn",55);
                for(String pair55: pair_55)
                {
                    if(!pair55.isEmpty())
                    {
                        System.out.println("find pair5: "+pair55 +","+pair5);
                        ls.add(pair55 +","+pair5);
                    }
                }
            }
        }
        
        List<String> pair_6=getPair(list,"amod","",6);
        for(String pair6:pair_6)
        {
            if(!pair6.isEmpty())
            {

                System.out.println("find pair6: "+pair6);
                ls.add(pair6);
            }
        }
        List<String> pair_7=getPair(list,"amod","amod",7);
        for(String pair7:pair_7)
        {
            if(!pair7.isEmpty())
            {

                System.out.println("find pair7: "+pair7);
                ls.add(pair7);
            }
        }
        List<String> pair_7a=getPair(list,"amod","conj_and",7);
        for(String pair7a:pair_7)
        {
            if(!pair7a.isEmpty())
            {

                System.out.println("find pair7a: "+pair7a);
                ls.add(pair7a);
            }
        }
        List<String> pair_8=getPair(list,"amod","conj_and",8);
        for(String pair8:pair_8)
        {
            if(!pair8.isEmpty())
            {

                System.out.println("find pair9: "+pair8);
                ls.add(pair8);
            }
        }
        List<String> pair_10=getPair(list,"nsubj","nn",10);
        for(String pair10:pair_10)
        {
            if(!pair10.isEmpty())
            {

                System.out.println("find pair10: "+pair10);
                ls.add(pair10);
            }
        }
        List<String> pair_11=getPair(list,"nsubj","prep_with",11);
        for(String pair11a:pair_11)
        {
           if(!pair11a.isEmpty())
            {
                List<String> pair_12=getPair(list,"prep_with","nn",12);
                for(String pair12: pair_12)
                {
                    if(!pair12.isEmpty())
                    {
                        System.out.println("find paart12: "+pair12);
                        ls.add(pair12);
                    }
                }
            }
        }
        //System.out.println(list.length);
        //TypedDependency typedDependency,typedDependency2,typedDependency3;
        //for (Object object : list) {
            
           // getPair(list,TypedDependency typedDependency,TypedDependency typedDependency2,String relation);
            //typedDependency = (TypedDependency) object;
            //System.out.println("Depdency Name: "+typedDependency.dep().nodeString()+ " :: "+ "Node: "+typedDependency.reln()+":: Gov: "+typedDependency.gov().nodeString());
          //  if (typedDependency.reln().getShortName().equals("nsubj")) {
              
              //if(!pair1.isEmpty())
              //{
                //  System.out.println("find dependency: "+pair1);
              //}
             
            //}
                
            
        //}
        return ls;
    }
    
}
