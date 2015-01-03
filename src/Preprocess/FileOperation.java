package Preprocess;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Khaled
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

public class FileOperation {
    
    private static String fullPath;
    private static char pathSeparator, extensionSeparator;
    
    public void ReadMultipleFile()
    {
        File folder = new File("H:\\Data\\dataforresearch");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }
    }
    public void SetFileName(String str, char sep, char ext)
    {
        fullPath = str;
        pathSeparator = sep;
        extensionSeparator = ext;
    }
    public String FileName()
    {
         //int dot = fullPath.lastIndexOf(extensionSeparator);
         int sep = fullPath.lastIndexOf(pathSeparator);
         return fullPath.substring(sep + 1);
    }
    public void CreateFolder(String directoryName)
    {
        File theDir = new File(directoryName);
                   // if the directory does not exist, create it
          if (!theDir.exists())
          {
           // System.out.println("creating directory: " + directoryName);
            boolean result = theDir.mkdir();  
            if(result){    
               System.out.println("DIR created");  
             }

          }
          else 
          {
           
            try {
                FileUtils.deleteDirectory(theDir);
                System.out.println(theDir+" DIR deleted");  
            } catch (IOException ex) {
                Logger.getLogger(FileOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
             
             
             boolean result = theDir.mkdir();  
            if(result){    
               System.out.println(theDir+" DIR created");  
             }
          }
          
    }
     public  List<String>  readfile(String datapath)
    {
        List<String> listoffilename = new ArrayList<String>();
        File folder = new File(datapath);
        System.out.println("data path: "+folder.getName());
        File[] listOfFolder = folder.listFiles();
        for (File folders : listOfFolder) {
            System.out.println("folder read: "+datapath+folders.getName());
            File folderfiles = new File(datapath+folders.getName());
            File[] listOfFiles = folderfiles.listFiles();
            for (File foldersfile : listOfFiles) {
                
              //  System.out.println("file name: "+foldersfile.getName());
                listoffilename.add(datapath+folders.getName()+"\\"+foldersfile.getName());
               // ReadDocFiles(datapath+folders.getName()+"\\"+foldersfile.getName());
                
            }
            
        }
        return listoffilename;
    }
    public String ReadDocFiles(String filename)
    {
        File file = new File(filename);
        FileInputStream fis;
        String filetext = null;
        //String filetext;
        try {
            fis = new FileInputStream(file);
            byte[] data = new byte[(int)file.length()];
             fis.read(data);
             fis.close();
             filetext = new String(data, "UTF-8");
           //  System.out.println(filetext);
             
             
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileOperation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filetext;
         
    }
    public void WriteIntoFile(String filename,String sentence)
   {
       FileWriter fstream;
        try {
            fstream = new FileWriter(filename,true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(sentence);
            out.close();
            fstream.close();
        } catch (IOException ex) {
            Logger.getLogger(FileOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
   }
    public void filePreprocess(String str)
    {
        Scanner scanner = new Scanner(str);
        while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          // process the line
        }
        scanner.close();
        
    }
    
}
