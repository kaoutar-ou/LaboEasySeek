package org.irisi.laboeasyseek.utils;


import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Part;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class UploadHelper {

    private final int limit_max_size = 10240000;
    private final String limit_type_file = "gif|jpg|png|jpeg|pdf|pptx|docx|xls|xlsx|txt|csv|doc|ppt|rar|zip|json";
    private static  final String path_to =  File.separator+"resources" + File.separator + "media";

    public String processUpload(Part fileUpload,String name) {
        System.out.println("file to upload : "+fileUpload);
        String fileSaveData = "";
        try {
            if (fileUpload.getSize() > 0) {
                String submittedFileName = getFilename(fileUpload);
                if (checkFileType(submittedFileName)) {
                    if (fileUpload.getSize() > this.limit_max_size) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File size too large!", ""));
                    } else {
                        String currentFileName = submittedFileName;
                        String extension = currentFileName.substring(currentFileName.lastIndexOf("."), currentFileName.length());
                        String newfilename = System.currentTimeMillis() + extension;
                        fileSaveData = newfilename;
                        String fileSavePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + this.path_to;
                        System.out.println("fileSavePath : " + fileSavePath);
                        try {
                            byte[] fileContent = new byte[(int) fileUpload.getSize()];
                            InputStream in = fileUpload.getInputStream();
                            in.read(fileContent);

                            File fileToCreate = new File(fileSavePath, newfilename);
                            File folder = new File(fileSavePath);
                            if (!folder.exists()) {
                                folder.mkdirs();
                            }
                            FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
                            fileOutStream.write(fileContent);
                            fileOutStream.flush();
                            fileOutStream.close();
                            fileSaveData = newfilename;
                        } catch (IOException e) {
                            fileSaveData = "noimages.jpg";
                        }
                    }
                } else {
                    fileSaveData = "";
                }
            }
        } catch (Exception ex) {
            fileSaveData = "";
        }
        return fileSaveData;
    }

    private String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                System.out.println("filename : " + filename);
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

    private boolean checkFileType(String fileName) {
        if (fileName.length() > 0) {
            String[] parts = fileName.split("\\.");
            if (parts.length > 0) {
                String extention = parts[parts.length - 1];
                return this.limit_type_file.contains(extention);
            }
        }
        return false;
    }


    public static List<String> generateWordCloud(String fileName) throws IOException {

        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(500);
        frequencyAnalyzer.setMinWordLength(4);
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + path_to +File.separator+ fileName);
        final Dimension dimension = new Dimension(600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));

        wordCloud.setBackgroundColor(Color.WHITE);

        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
        wordCloud.setFontScalar( new SqrtFontScalar(10, 40));
        wordCloud.build(wordFrequencies);
        List <String> list =  Arrays.asList("this", "that", "with", "than", "into", "after", "before", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off",
                "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other",
                "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "can", "will", "just", "don", "should", "now","ceci", "cela", "avec", "que",
                "dans", "après", "avant", "au-dessus", "en dessous", "à", "depuis", "en haut", "en bas ", "in", "out", "on", "off", "over", "under", "encore", "plus loin", "alors",
                "une fois", "ici", "là", "quand", "où", "pourquoi", "comment", "tous", "tout", "les deux", "chacun", "quelques", "plus", "la plupart", "autres", "certains ", "tel",
                "non", "ni", "pas", "seulement", "propre", "même", "alors", "que", "trop", "très", "peut", "va", "juste", "don", "devrait", "maintenant");

        List<String> listWords =   wordFrequencies.stream( ).map(WordFrequency::getWord).filter(word -> !list.contains(word)).limit(10).collect(Collectors.toList());

        wordCloud.writeToFile(FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + path_to +File.separator+ fileName.split("\\.")[0]+".png");
        return listWords;
    }


    public static String pdfToTextFile(String fileName) throws IOException {
        File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + path_to +File.separator+ fileName);
        PDDocument document = PDDocument.load(file);


        PDFTextStripper pdfStripper = new PDFTextStripper();


        String text = pdfStripper.getText(document);
        File file1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + path_to+File.separator+file.getName().replace(".pdf",".txt"));
        file1.createNewFile();
        Writer output = new FileWriter(file1);
        Writer writer = new BufferedWriter(output);
        writer.write(text);
        writer.close();
        System.out.println("Done");


        System.out.println(text);

        //Closing the document
        document.close();
        return file.getName().replace(".pdf",".txt");

    }

}