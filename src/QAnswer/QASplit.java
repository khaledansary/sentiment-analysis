/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QAnswer;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author khaledd
 */
public class QASplit {
      
     public List<QA> getQABlock(String filetext){
        
        String reg1="^(Q.).*";
        String reg2="^(A.).*";
        
        
        List<QA> qa = new ArrayList<>();
        
        Scanner scanner = new Scanner(filetext.trim());
            int flag=0;
            String question="",answer="";
            while (scanner.hasNextLine()) {
                QA qablock =new QA();
                String line = scanner.nextLine().trim();
                System.out.println(line);
                
                if(line.matches(reg1)){
                  
                    
                    question+=line+"\n";
                    
                    flag=1;
                    
                   
                }
                else if(line.matches(reg2)){
                  //  System.out.println(line.replace("A.", ""));
                   
                   answer+=line+"\n"; 
                   flag=0;
                   
                }
                if(flag==0)
                {
                  if(!question.isEmpty()||!answer.isEmpty())
                  {
                    qablock.setQuestion(question.trim().replace("Q.", ""));
                    qablock.setAnswer(answer.trim().replace("A.", ""));
                    qa.add(qablock);
                    question="";
                    answer="";
                  }
                }
            }
        
        scanner.close();
        return qa;
        
    }
    public List<QA> getAnswer(String filetext){
        
        String reg="^(A.).*";
        
        List<QA> qa = new ArrayList<>();
        
        Scanner scanner = new Scanner(filetext);
        
            while (scanner.hasNextLine()) {
                QA qablock =new QA();
                String line = scanner.nextLine().trim();
                //System.out.println(line);
                if(line.matches(reg)){
                  //  System.out.println(line.replace("A.", ""));
                    qablock.setQuestion(line.replace("Q.", ""));
                    qablock.setAnswer(line.replace("A.", ""));
                    qa.add(qablock);
                }
   
            }
        
        scanner.close();
        return qa;
        
    }
    public List<QA> getQuestion(String filetext){
        
        String reg="^(Q.).*";
        
        List<QA> qa = new ArrayList<>();
        
        Scanner scanner = new Scanner(filetext);
        
            while (scanner.hasNextLine()) {
                QA ques =new QA();
                String line = scanner.nextLine().trim();
                //System.out.println(line);
                if(line.matches(reg)){
                  //  System.out.println(line.replace("Q.", ""));
                    ques.setQuestion(line.replace("Q.", ""));
                    qa.add(ques);
                }
   
            }
        
        scanner.close();
        return qa;
        
    }
    
}
