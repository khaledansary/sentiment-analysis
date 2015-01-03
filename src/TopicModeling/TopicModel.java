/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TopicModeling;

/**
 *
 * @author khaledd
 */
import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.*;
import cc.mallet.topics.*;
import static cc.mallet.topics.ParallelTopicModel.logger;
import cc.mallet.types.*;
import cc.mallet.util.*;
import java.io.*;

import java.util.*;
import java.util.regex.*;

public class TopicModel {
    
    public void getModelReady(String inputFile) throws IOException {
        if(inputFile != null && (! inputFile.isEmpty())) {
            List<Pipe> pipeList = new ArrayList<Pipe>();
            pipeList.add(new Target2Label());
            pipeList.add(new Input2CharSequence("UTF-8"));
            pipeList.add(new CharSequence2TokenSequence());
            pipeList.add(new TokenSequenceLowercase());
            pipeList.add(new TokenSequenceRemoveStopwords(new File("H:\\Data\\stoplists\\en.txt"), "UTF-8", false, false, false));
            pipeList.add(new TokenSequence2FeatureSequence());      
            StringReader fileReader = new StringReader(inputFile);
            //Reader fileReader = new InputStreamReader(new FileInputStream(new File(inputFile)), "UTF-8");
            CsvIterator ci = new CsvIterator (fileReader, Pattern.compile("^(\\S*)[\\s,]*(\\S*)[\\s,]*(.*)$"),
                    3, 2, 1); // data, label, name fields

            InstanceList instances = new InstanceList(new SerialPipes(pipeList));
            instances.addThruPipe(ci);

            ObjectOutputStream oos;
            oos = new ObjectOutputStream(new FileOutputStream("H:\\Data\\stoplists\\Model.vectors"));
            oos.writeObject(instances);
            oos.close();
        }
    }
    public void applyLDA() throws IOException {     

        int numTopics = 5;
        ParallelTopicModel model = new ParallelTopicModel(numTopics, 1.0, 0.01);
        InstanceList training = InstanceList.load (new File("H:\\Data\\stoplists\\Model.vectors"));
        //logger.debug("InstanceList Data loaded.");

        if (training.size() > 0 &&
                training.get(0) != null) {
            Object data = training.get(0).getData();
            if (! (data instanceof FeatureSequence)) {
               // logger.error("Topic modeling currently only supports feature sequences.");
                System.exit(1);
            }
        }

        // IT HAS TO BE SET HERE, BEFORE CALLING ADDINSTANCE METHOD.
        model.setRandomSeed(5);
        model.addInstances(training);

        model.estimate();       
        model.printTopWords(new File("H:\\Data\\stoplists\\topic_keys_java.txt"), 25,
                false);
        model.printDocumentTopics(new File ("H:\\Data\\stoplists\\document_topicssplit_java.txt"));
        
         Alphabet dataAlphabet = training.getDataAlphabet();
        
        FeatureSequence tokens = (FeatureSequence) model.getData().get(0).instance.getData();
        LabelSequence topics = model.getData().get(0).topicSequence;
        
        Formatter out = new Formatter(new StringBuilder(), Locale.US);
        for (int position = 0; position < tokens.getLength(); position++) {
            out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)), topics.getIndexAtPosition(position));
        }
        System.out.println(out);
        
        // Estimate the topic distribution of the first instance, 
        //  given the current Gibbs state.
        double[] topicDistribution = model.getTopicProbabilities(0);

        // Get an array of sorted sets of word ID/count pairs
        ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();
        
        // Show top 5 words in topics with proportions for the first document
//        int numTopics = 10;
        for (int topic = 0; topic < numTopics; topic++) {
            Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();
            
            out = new Formatter(new StringBuilder(), Locale.US);
            out.format("%d\t%.3f\t", topic, topicDistribution[topic]);
            int rank = 0;
            while (iterator.hasNext() && rank < 5) {
                IDSorter idCountPair = iterator.next();
                //if(idCountPair.getWeight()>=5)
                //{
                    out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
                //}
                rank++;
            }
            System.out.println(out);
        }
        
        // Create a new instance with high probability of topic 0
        StringBuilder topicZeroText = new StringBuilder();
        Iterator<IDSorter> iterator = topicSortedWords.get(0).iterator();

        int rank = 0;
        while (iterator.hasNext() && rank < 5) {
            IDSorter idCountPair = iterator.next();
            topicZeroText.append(dataAlphabet.lookupObject(idCountPair.getID()) + " ");
            rank++;
        }

        // Create a new instance named "test instance" with empty target and source fields.
        InstanceList testing = new InstanceList(training.getPipe());
        testing.addThruPipe(new Instance(topicZeroText.toString(), null, "test instance", null));

        TopicInferencer inferencer = model.getInferencer();
        double[] testProbabilities = inferencer.getSampledDistribution(testing.get(0), 10, 1, 5);
        System.out.println("0\t" + testProbabilities[0]);
        
        
    }
    public void getTopic(String filePath) throws Exception {

      //  String filePath = "H:\\Data\\topicmodel\\ap.txt";
        // Begin by importing documents from text to feature sequences
        ArrayList<Pipe> pipeList = new ArrayList<Pipe>();

        // Pipes: lowercase, tokenize, remove stopwords, map to features
        pipeList.add( new CharSequenceLowercase() );
        pipeList.add( new CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")) );
        pipeList.add( new TokenSequenceRemoveStopwords(new File("H:\\Data\\stoplists\\en.txt"), "UTF-8", false, false, false) );
        pipeList.add( new TokenSequence2FeatureSequence() );

        InstanceList instances = new InstanceList (new SerialPipes(pipeList));

        Reader fileReader = new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8");
        instances.addThruPipe(new CsvIterator (fileReader, Pattern.compile("^(\\S*)[\\s,]*(\\S*)[\\s,]*(.*)$"),
                                               3, 2, 1)); // data, label, name fields

        // Create a model with 100 topics, alpha_t = 0.01, beta_w = 0.01
        //  Note that the first parameter is passed as the sum over topics, while
        //  the second is the parameter for a single dimension of the Dirichlet prior.
        int numTopics = 10;
        ParallelTopicModel model = new ParallelTopicModel(numTopics, 1.0, 0.01);

        model.addInstances(instances);

        // Use two parallel samplers, which each look at one half the corpus and combine
        //  statistics after every iteration.
        model.setNumThreads(2);

        // Run the model for 50 iterations and stop (this is for testing only, 
        //  for real applications, use 1000 to 2000 iterations)
        model.setNumIterations(50);
        model.estimate();

        // Show the words and topics in the first instance

        // The data alphabet maps word IDs to strings
        Alphabet dataAlphabet = instances.getDataAlphabet();
        
        FeatureSequence tokens = (FeatureSequence) model.getData().get(0).instance.getData();
        LabelSequence topics = model.getData().get(0).topicSequence;
        
        Formatter out = new Formatter(new StringBuilder(), Locale.US);
        for (int position = 0; position < tokens.getLength(); position++) {
            out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)), topics.getIndexAtPosition(position));
        }
        System.out.println(out);
        
        // Estimate the topic distribution of the first instance, 
        //  given the current Gibbs state.
        double[] topicDistribution = model.getTopicProbabilities(0);

        // Get an array of sorted sets of word ID/count pairs
        ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();
        
        // Show top 5 words in topics with proportions for the first document
        for (int topic = 0; topic < numTopics; topic++) {
            Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();
            
            out = new Formatter(new StringBuilder(), Locale.US);
            out.format("%d\t%.3f\t", topic, topicDistribution[topic]);
            int rank = 0;
            while (iterator.hasNext() && rank < 10) {
                IDSorter idCountPair = iterator.next();
                //if(idCountPair.getWeight()>=5)
                //{
                    out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
                //}
                rank++;
            }
            System.out.println(out);
        }
        
        // Create a new instance with high probability of topic 0
        StringBuilder topicZeroText = new StringBuilder();
        Iterator<IDSorter> iterator = topicSortedWords.get(0).iterator();

        int rank = 0;
        while (iterator.hasNext() && rank < 5) {
            IDSorter idCountPair = iterator.next();
            topicZeroText.append(dataAlphabet.lookupObject(idCountPair.getID()) + " ");
            rank++;
        }

        // Create a new instance named "test instance" with empty target and source fields.
        InstanceList testing = new InstanceList(instances.getPipe());
        testing.addThruPipe(new Instance(topicZeroText.toString(), null, "test instance", null));

        TopicInferencer inferencer = model.getInferencer();
        double[] testProbabilities = inferencer.getSampledDistribution(testing.get(0), 10, 1, 5);
        System.out.println("0\t" + testProbabilities[0]);
    }

}