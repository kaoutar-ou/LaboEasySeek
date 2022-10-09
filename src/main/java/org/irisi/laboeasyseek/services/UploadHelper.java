package org.irisi.laboeasyseek.services;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Part;

public class UploadHelper {

    private final int limit_max_size = 10240000;
    private final String limit_type_file = "gif|jpg|png|jpeg|pdf|pptx|docx|xls|xlsx|txt|csv|doc|ppt|rar|zip";
    private final String path_to =  File.separator+"resources" + File.separator + "media";

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



}