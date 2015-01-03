/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UnsupervisedTopicAspectExtraction;

import DAO.DB;
import QAnswer.QA;
import QAnswer.QASplit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author khaledd
 */
public class TopicAspectParsing {
    static DB db = new DB();
    
    public static void preprocess(int docid)
    {
        String doctext=db.getFileText(docid);
        List<QA> anspara = new ArrayList<QA>();
        List<QA> quespara = new ArrayList<QA>();
        QASplit qasplit = new QASplit();
        System.out.println(doctext);
    }
    
    public static void main(String[] args) {
        preprocess(35);
        
    }
    
    
}
