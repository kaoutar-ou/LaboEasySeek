package org.irisi.laboeasyseek.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topic.utils.FileUtil;
import com.topic.model.GibbsSamplingLDA;

public class RawDataProcess {


    public static void main(String[] args) throws IOException {
//        //read data
        ArrayList<String> docLines = new ArrayList<String>();
        FileUtil.readLines("C:\\Users\\kaout\\irisi5\\projects\\LaboEasySeek\\LaboEasySeek - Copy\\src\\main\\java\\org\\irisi\\laboeasyseek\\temp\\en.txt", docLines, "utf-8");
        ArrayList<String> doclinesAfter = new ArrayList<String>();
        for(String line : docLines){
            //get all word for a document
            ArrayList<String> words = new ArrayList<String>();
            //lemmatization using StanfordCoreNLP
            FileUtil.getlema(line, words);
            //remove noise words
            String text = FileUtil.RemoveNoiseWord(words);
            doclinesAfter.add(text);
        }
//        // write data
        FileUtil.writeLines("C:\\Users\\kaout\\irisi5\\projects\\LaboEasySeek\\LaboEasySeek - Copy\\src\\main\\java\\org\\irisi\\laboeasyseek\\temp\\rawdata_process_2.txt", doclinesAfter, "utf-8");


        GibbsSamplingLDA lda = new GibbsSamplingLDA("C:\\Users\\kaout\\irisi5\\projects\\LaboEasySeek\\LaboEasySeek - Copy\\src\\main\\java\\org\\irisi\\laboeasyseek\\temp\\rawdata_process_2.txt", "utf-8", 1, 0.1,
                0.01, 500, 5, "C:\\Users\\kaout\\irisi5\\projects\\LaboEasySeek\\LaboEasySeek - Copy\\src\\main\\java\\org\\irisi\\laboeasyseek\\temp\\result\\");
        lda.MCMCSampling();

        System.out.println(lda);

    }

    public  Map<List<String>, Integer> process(String fileName) throws IOException {

        fileName = fileName.split("\\.")[0];

        String currentDir = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        String tempFolder = currentDir + separator + "src" + separator + "main" + separator + "resources" + separator + "temp" + separator;
        String mediaFolder = currentDir + separator + "target" + separator + "labo-easy-seek-1.0-SNAPSHOT" + separator + "resources" + separator + "media" + separator;
        String resultFolder = tempFolder + "result" + separator + fileName + separator;

        fileName += ".txt";

        File resultFolderFile = new File(resultFolder);
        if (!resultFolderFile.exists()) {
            resultFolderFile.mkdir();
        }

        ArrayList<String> docLines = new ArrayList<String>();
        FileUtil.readLines(mediaFolder + fileName, docLines, "utf-8");

        ArrayList<String> doclinesAfter = new ArrayList<String>();
        for(String line : docLines){
            //get all word for a document
            ArrayList<String> words = new ArrayList<String>();
            //lemmatization using StanfordCoreNLP
            FileUtil.getlema(line, words);
            //remove noise words
            String text = FileUtil.RemoveNoiseWord(words);
            doclinesAfter.add(text);
        }
//        // write data
        FileUtil.writeLines(tempFolder + fileName, doclinesAfter, "utf-8");


        GibbsSamplingLDA lda = new GibbsSamplingLDA(tempFolder + fileName, "utf-8", 3, 0.1,
                0.01, 500, 4, resultFolder);
        lda.MCMCSampling();

        System.out.println(lda);

        return readLines(resultFolder + "LDAGibbs_topic_word_3.txt");
    }


    public  Map<List<String>, Integer> readLines(String filename) throws IOException {
        List<String> linesTopic1 = new ArrayList<String>();
        List<String> linesTopic2 = new ArrayList<String>();
        List<String> linesTopic3 = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // remove empty lines


                if (line.startsWith("Topic:1")) {
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith("Topic:2")) {
                            break;
                        }
                        if (line.trim().length() > 0) {
                            linesTopic1.add(line);
                        }
                    }
                }
                if (line.startsWith("Topic:2")) {
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith("Topic:3")) {
                            break;
                        }
                        if (line.trim().length() > 0) {
                            linesTopic2.add(line);
                        }
                    }
                }
                if (line.startsWith("Topic:3")) {
                    while ((line = br.readLine()) != null) {
                        if (line.trim().length() > 0) {
                            linesTopic3.add(line);
                        }
                    }
                }

                }

        }   catch (IOException e) {
            e.printStackTrace();
        }


        Map<List<String>, Integer> map = new HashMap<List<String>, Integer>();
        map.put(linesTopic1, 1);
        map.put(linesTopic2, 2);
        map.put(linesTopic3, 3);

        return map;

    }
}






















//public class RawDataProcess {
//
//
////    public static void process() {
//    public static void main(String[] args) throws IOException {
//        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + File.separator + "resources" + File.separator;
//        String temp_path =  path + "temp" + File.separator;
//        String result_path =  temp_path + "result" + File.separator;
////        //read data
//        System.out.println("path: " + path);
//        ArrayList<String> docLines = new ArrayList<String>();
//        String fileName = "en.txt";
//        String rawDataFileName = "rawdata_process_2.txt";
//
////        FileUtil.readLines("C:\\Users\\kaout\\irisi5\\projects\\LaboEasySeek\\LaboEasySeek - Copy\\src\\main\\java\\org\\irisi\\laboeasyseek\\temp\\en.txt", docLines, "utf-8");
//        FileUtil.readLines(temp_path + fileName, docLines, "utf-8");
//        ArrayList<String> doclinesAfter = new ArrayList<String>();
//        for(String line : docLines){
//            //get all word for a document
//            ArrayList<String> words = new ArrayList<String>();
//            //lemmatization using StanfordCoreNLP
//            FileUtil.getlema(line, words);
//            //remove noise words
//            String text = FileUtil.RemoveNoiseWord(words);
//            doclinesAfter.add(text);
//        }
////        // write data
//        FileUtil.writeLines( temp_path + rawDataFileName, doclinesAfter, "utf-8");
//
//
//        GibbsSamplingLDA lda = new GibbsSamplingLDA(temp_path + rawDataFileName, "utf-8", 1, 0.1,
//                0.01, 500, 5, result_path);
//        lda.MCMCSampling();
//
//        System.out.println(lda);
//    }
//}