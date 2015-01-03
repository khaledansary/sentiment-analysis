/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TestDataSet;

import DAO.DB;
import DependencyParser.ParseDependency;
import POSTagging.PosTagger;
import QAnswer.QA;
import QAnswer.QASplit;
import StemmingLemma.StemmingLemmaText;
import StopWords.StopWords;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author khaledd
 */
public class TestData {
    
    static PosTagger pos= new PosTagger();
    static StemmingLemmaText stemlemma = new  StemmingLemmaText();
    static DB db= new DB();
    static ParseDependency as_opinion = new ParseDependency();
    static StopWords removestopword = new StopWords();
     private static Connection con = null;
    private static PreparedStatement ps,ps1 = null;
    
    public static void QABlock()
    {
        String doctext=db.getFileText(36);
        //System.out.println(doctext);
        QASplit qa = new QASplit();
        List<QA> qablock = qa.getQABlock(doctext);
        
        int i=1;
        System.out.println("Topic list");
        for(QA qtext:qablock)
        {
            List<String> sentences=getSentences(qtext.getAnswer());
            
           /* for(String sent:sentences)
            {
                as_opinion.getAspect_OpinionPair(sent);
            
            }*/    
            QAAspects(qtext.getAnswer());
         
            
           
        }
        
    }
    public static void test()
    {
        
        String Qstr="Right. One of the things that we're looking at is basically inter-organisational relationships and especially as it "
                + "applies to the supply chain and that sort of thing, things like that and we were wondering how sales and marketing strategy "
                + "is at the moment and how it's changed perhaps over the history of Hotel China and when it came out of [the original company].";
        String Astr="I think a couple of things. One is that I certainly remember as part of [that company], "
                + "of course that was owned by [another company] who were a very small fish in a very very large "
                + "pond and in '83 we became one of 767 employees on that first day and it was sink or swim and very"
                + " much a greater focus on the business. And certainly the Chairman that came in and the mission statement you see"
                + " above my head here which reflects the attitude of this business is that we're here to make profit, "
                + "and when we've made it and we re-invest it and the management team at that time, and ever since that, "
                + "has been basically, cut us in half and that's what we stand for, and we've always delivered, irrespective"
                + " of market condition. We are, it's interesting, and you look at the background to your particular project "
                + "and you do see companies that perhaps are more adventurous in terms of how they're approaching models at "
                + "this present time and we really aren't. We're a very traditional manufacturing company and it's interesting"
                + " you can see on our mission statement it talks about manufacturing first class products and we really do go "
                + "against the grain I think in many respects. At a time when many companies in this area who've gone through some "
                + "fairly turbulent times, if you read the performance of [another local ceramics company] in today's press it's been "
                + "a very difficult time for a number of companies. A number of those organisations have sought to resolve those issues"
                + " by purchasing products from overseas where labour costs are obviously lower and they can buy the blanks, or decorated "
                + "products at significantly lower unit costs. We haven't taken that attitude. Our attitude is that we will continue to "
                + "re-invest locally, we'll plough the profit that we make back into the business. We have no borrowings. Everything that"
                + " we fund is from our own reserves and we continue along the route of, I suppose, a traditional English manufacturer."
                + " But we're successful at it. We have in terms of a sales and marketing strategy, as a business every five years we bring "
                + "in outside consultancy and that's ranged from, last time we used a company called [name] who are part of [an accountancy "
                + "firm]. Prior to that we had the *?* Group, so every five years we have outside influence and I think that's largely driven"
                + " in the first instance by non-Executive Directors that we have. The do, and have, and did, for example the last part of "
                + "research a big part of the project focused upon a manufacturing product in Mexico because our biggest growth market has "
                + "been the United States. Reality is that that isn't going to happen. The shareholder is very happy that he's got control "
                + "of the business here on one site. Manufacturing's all on one site, distribution's now on, albeit over the canal, the same "
                + "site and it's the one, I think the one thing that we do have, we can control our quality, we can control our manufacturing. "
                + "If I have an issue, he and I meet daily. He'll go out there, he can make things happen. We sit together every lunchtime as "
                + "a board, have lunch together so we can discuss issues and that's impossible when you're even ten miles away, never mind "
                + "5,000 or 10,000 miles away. So we have control. We control our quality, we control our delivery, we control our efficiencies "
                + "and we can work together as a team. That does go "
                + "against the grain of what not only a number of British companies are doing but indeed in Stoke-on-Trent. But our success "
                + "is there for all to be seen.";
        
        String Q="Was that a personal philosophy of perhaps the Chairman, that it would be all done in-house and on, as far as far as possible, one site?";
        String A="Yes. From day one with the business, we acquired this manufacturing site, albeit it was significantly smaller then than it is today "
                + "and we had a manufacturing site over at Hanley, which is three and half miles away, and that was ultimately closed and it was always "
                + "an intent from day one to put manufacturing on one site. And we have, since we achieved that, stitched on extensions and it was always "
                + "an intent to put distribution again on the same site and we have historically bought whatever we could that's been on our target list of "
                + "properties. We have acquired dozens and dozens and dozens of, from working men's clubs to pubs to sandwich shops to bookmakers to, you name "
                + "it we've bought it, to give us the opportunity of extending our manufacturing facility. And we have planning permission that's proceeding at "
                + "the moment to again further extend manufacturing. And the challenge that we have as a business is, quite frankly, if you look at the market "
                + "place at the moment we're under severe pressure, as indeed all manufacturing is, from customers who are getting ever larger from distributors "
                + "who are equally increasing in size who have a commercial muscle that they apply to you that says, well, okay, we don't want to talk about price "
                + "increase, we want to talk about price decrease. Our discounts rise every year, it's something that we have to fight in sales but it's a fact, and "
                + "there is slippage and we have to basically ensure the manufacturing is able to reduce its costs by at least that level and hopefully will. The fact "
                + "that "
                + "we do have the control we have gives us the ability to withstand a fighting chance and we've achieved it, certainly for the last five years.";
        
        
        String Q1="And do you think this is to do with the change of management or";
        
        String A1="Like I say, I think both sides. I was going to say blame, it's not a blame thing at all really, but both sides have changed but they've not changed in a good way that it's made things better. And as I say I think it's both sides, it's not one or the other.";
        
        System.out.println("Topic....");
        System.out.println();
        QAAspects(Q1);
        System.out.println();
        System.out.println("Aspect....");
        System.out.println();
        QAAspects(A1);
        
    }
     public static List<String> getAdjective(String tag_sentence)
    {
        List<String> list =new ArrayList<String>();
        
       String reg="\\w*\\_(JJ|JJR|JJS)| \\w*_(RB|RBR|RBS) \\w*\\_(JJ|JJR|JJS)";
       String removreg="_(NN(S|P)?|JJ(R|S)?|RB(R|S)? |(VBG|VGB|VBP)|(IN)|(DT))";
       Pattern patt  = Pattern.compile(reg);
       
        Matcher match = patt.matcher(tag_sentence.trim());
        while(match.find()) {
             //System.out.println("NP: "+match.group());
           //  System.out.println("Adjective: "+match.group().replaceAll(removreg,""));
             list.add(match.group().replaceAll(removreg,""));
            
        }
        
        
        return list;
    }
    public static void QAAspects(String str)
    {
        MaxentTagger postagger = pos.getPosTagger();
        List<String> sentences=getSentences(str);
        List<String> aspects;
        for(String sent:sentences)
        {
            String posstring = postagger.tagString(stemlemma.getStem_lemmaText(sent.replaceAll("-", "_")));
           // System.out.println(sent);
            System.out.println(posstring);
            
            aspects = getUnigram(posstring);
                    
            
           
            for(String aspect:aspects)
            {
                if(!aspect.isEmpty())
                System.out.println(aspect.toLowerCase().trim());
            }
           
            aspects = getBigram(posstring);
                    
            
           
            for(String aspect:aspects)
            {
                if(!aspect.isEmpty())
                System.out.println(aspect.toLowerCase().trim());
            }
            aspects = getTrigram(posstring);
                    
            
           
            for(String aspect:aspects)
            {
                if(!aspect.isEmpty())
                System.out.println(aspect.toLowerCase().trim());
            }
            aspects = getSkipNgram(posstring);
                    
            
           
            for(String aspect:aspects)
            {
                if(!aspect.isEmpty())
                System.out.println(aspect.toLowerCase().trim());
            }
            
            aspects = getAdjective(posstring);
                    
            
            
            for(String aspect:aspects)
            {
                if(!aspect.isEmpty())
                System.out.println(".....: "+aspect.toLowerCase().trim());
            }
            
            
        }
    }
    
    public static List<String> getUnigram(String tag_sentence)
    {
        List<String> aspects =new ArrayList<String>();
    
       String reg=" \\w*_(NN|NNS|NNP)";
       String removreg="_(NN(S|P)?|JJ(R|S)?|RB(R|S)? |(VBG|VGB|VBP)|(IN)|(DT))";
       Pattern patt  = Pattern.compile(reg);
       
        Matcher match = patt.matcher(tag_sentence);
        int i=0;
        while(match.find()) {
             //System.out.println("NP: "+match.group());
            // System.out.println(i+" Aspects: "+match.group().replaceAll(removreg,""));
             aspects.add(match.group().replaceAll(removreg,""));
             i++;
            
        }
        
        return aspects;
    }
    public static List<String> getBigram(String tag_sentence)
    {
        List<String> aspects =new ArrayList<String>();
    
        String reg="\\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP) |"
               + "\\w*\\_(NN|NNS|NNP) \\w*\\_(NN|NNS|NNP)|";
        
       String removreg="_(NN(S|P)?|JJ(R|S)?|RB(R|S)? |(VBG|VGB|VBP)|(IN)|(DT))";
       Pattern patt  = Pattern.compile(reg);
       
        Matcher match = patt.matcher(tag_sentence);
        int i=0;
        while(match.find()) {
             //System.out.println("NP: "+match.group());
            // System.out.println(i+" Aspects: "+match.group().replaceAll(removreg,""));
             aspects.add(match.group().replaceAll(removreg,""));
             i++;
            
        }
        
        return aspects;
    }
    public static List<String> getTrigram(String tag_sentence)
    {
        List<String> aspects =new ArrayList<String>();
    
        String reg=" \\w*_(RB|RBR|RBS) \\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP)|"
               + "\\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP) \\w*\\_(NN|NNS|NNP)|"
               + "\\w*_(RB|RBR|RBS) \\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP)  |"
               + "\\w*\\_(NN|NNS|NNP) \\w*\\_(IN) \\w*\\_(NN|NNS|NNP)|"
               + "\\w*\\_(NN|NNS|NNP) \\w*\\_(VBG|VGB) \\w*\\_(NN|NNS|NNP)|"
               + "\\w*\\_(NN|NNS|NNP) \\w*\\_(NN|NNS|NNP) \\w*\\_(NN|NNS|NNP)|"
               + "\\w*\\_(NN|NNS|NNP) \\w*\\_(VBG|VGB|VBP) \\w*\\_(NN|NNS|NNP)";
        
       String removreg="_(NN(S|P)?|JJ(R|S)?|RB(R|S)? |(VBG|VGB|VBP)|(DT))|\\w*\\_(IN)|\\w*\\_(DT)";
       Pattern patt  = Pattern.compile(reg);
       
        Matcher match = patt.matcher(tag_sentence);
        int i=0;
        while(match.find()) {
             //System.out.println("NP: "+match.group());
            // System.out.println(i+" Aspects: "+match.group().replaceAll(removreg,""));
             aspects.add(match.group().replaceAll(removreg,""));
             i++;
            
        }
        
        return aspects;
    }
        public static List<String> getSkipNgram(String tag_sentence)
    {
        List<String> aspects =new ArrayList<String>();
    
        String reg="\\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP) \\w*\\_(IN) \\w*_(RB|RBR|RBS) \\w*\\_(DT) \\w*\\_(NN|NNS|NNP) |"
               + "\\w*\\_(NN|NNS|NNP) \\w*\\_(IN) \\w*\\_(NN|NNS|NNP)|"
               + "\\w*_(NN|NNS|NNP) \\w*\\_(IN) \\w*\\_(VBG|VGB|VBP) \\w*\\_(DT) \\w*\\_(NN|NNS|NNP) |"
               + "\\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP) \\w*\\_(IN) \\w*\\_(DT) \\w*\\_(NN|NNS|NNP) |"
               ;
        String removreg="_(NN(S|P)?|JJ(R|S)?|RB(R|S)? |(VBG|VGB|VBP)|(DT))|\\w*\\_(IN)|\\w*\\_(DT)";
        Pattern patt  = Pattern.compile(reg);
       
        Matcher match = patt.matcher(tag_sentence);
        int i=0;
        while(match.find()) {
             aspects.add(match.group().replaceAll(removreg,""));
             i++;
            
        }
        
        return aspects;
    }
    public static List<String> getAspects(String tag_sentence)
   {
       List<String> aspects =new ArrayList<String>();
       //System.out.println("Aspects=====");
       //System.out.println();
// personal_JJ philosophy_NN of_IN perhaps_RB the_DT Chairman_NNP
       //way_NN of_IN do_VBP the_DT work_NN
       //much_JJ self-discipline_NN
       //both_DT side_NN have_VBP change_NN
       String reg=" \\w*_(RB|RBR|RBS) \\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP)|\\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP) \\w*\\_(IN) \\w*_(RB|RBR|RBS) \\w*\\_(DT) \\w*\\_(NN|NNS|NNP) |\\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP) |"
               + "\\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP) \\w*\\_(NN|NNS|NNP)|"
               + "\\w*_(RB|RBR|RBS) \\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP)  |"
               + "\\w*\\_(NN|NNS|NNP) \\w*\\_(IN) \\w*\\_(NN|NNS|NNP)|\\w*_(NN|NNS|NNP) \\w*\\_(IN) \\w*\\_(VBG|VGB|VBP) \\w*\\_(DT) \\w*\\_(NN|NNS|NNP) |"
               + "\\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP) \\w*\\_(IN) \\w*\\_(DT) \\w*\\_(NN|NNS|NNP) | \\w*\\_(NN|NNS|NNP) \\w*\\_(VBG|VGB) \\w*\\_(NN|NNS|NNP)|"
               + "\\w*\\_(NN|NNS|NNP) \\w*\\_(NN|NNS|NNP)|"
               + "\\w*\\_(NN|NNS|NNP) \\w*\\_(NN|NNS|NNP) \\w*\\_(NN|NNS|NNP)|\\w*_(JJ|JJR|JJS) \\w*_(NN|NNS|NNP)"
               + "|\\w*\\_(DT) \\w*\\_(NN|NNS|NNP) \\w*\\_(VBG|VGB|VBP) \\w*\\_(NN|NNS|NNP)";
       String removreg="_(NN(S|P)?|JJ(R|S)?|RB(R|S)? |(VBG|VGB|VBP)|(IN)|(DT))";
       Pattern patt  = Pattern.compile(reg);
       
        Matcher match = patt.matcher(tag_sentence);
        int i=0;
        while(match.find()) {
             //System.out.println("NP: "+match.group());
            // System.out.println(i+" Aspects: "+match.group().replaceAll(removreg,""));
             aspects.add(match.group().replaceAll(removreg,""));
             i++;
            
        }
        
        return aspects;
   }
    
    public static List<String> getSentences(String filetext)
            
    {
        List<String> sentences = new ArrayList<String>();
        
        Reader reader = new StringReader(filetext.trim());
        DocumentPreprocessor dp = new DocumentPreprocessor(reader);

        List<String> sentenceList = new LinkedList<String>();
        Iterator<List<HasWord>> it = dp.iterator();
        while (it.hasNext()) {
           StringBuilder sentenceSb = new StringBuilder();
           List<HasWord> sentence = it.next();
           for (HasWord token : sentence) {
              if(sentenceSb.length()>1) {
                 sentenceSb.append(" ");
              }
              sentenceSb.append(token);
           }
           sentenceList.add(sentenceSb.toString());
        }
        
        for(String sentence:sentenceList) {
           //System.out.println(sentence);    
           //sentences.add(removestopword.removeStopwords(getSpitSentences(sentence)));
            sentences.add(getSpitSentences(sentence));
           
        }
        
        return sentences;
        
    }
    public static String getSpitSentences(String filetext)
    {
        String[] sentences = filetext.split("[\\.\\!\\?]");
        String splitdot="";
        for (int i=0;i<sentences.length;i++){  
            
            splitdot +=sentences[i];
        }  
        return splitdot;
    }
     public void commit_aspects(int senteceid, String txt)
    {
        String sql = "insert into sentence_aspect(sentid,aspecttext) values (?, ?)";
        
        
        con = db.dbConnect();
        try {
             ps = con.prepareStatement(sql);
             ps.setInt(1, senteceid);
             ps.setString(2, txt);
             
             
            int count = ps.executeUpdate ();
            
            if(count>0)
            {
                //System.out.println("committed all sentences");
            }
            else
            {
                System.out.println("not committed all sentences");
            }
            

            
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
           try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        
    }
    public static void main(String arg[])
    {
        QABlock();
    }

}   
