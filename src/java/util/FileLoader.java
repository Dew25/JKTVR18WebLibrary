/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import org.imgscalr.Scalr;

/**
 *
 * @author Melnikov
 */
public class FileLoader {
    public String uploadFile(String key, List<Part> fileParts) throws IOException{
        String fileFolder = PropertiesLoader.getFolderPath(key); //load path to folder from property file
                File tempFile;
                File uploadFile;
                new File(fileFolder).mkdirs();//create folders if not exist
                for(Part filePart : fileParts){
                     String pathToUploadFile =  fileFolder+File.separatorChar
                                     +getFileName(filePart);
                     uploadFile = new File(pathToUploadFile);
                     if(key.equals("cover")){
                        String tmpFolder = PropertiesLoader.getFolderPath("tmp");   
                        new File(tmpFolder).mkdirs();//create folders if not exist
                        tempFile = new File(tmpFolder+File.separatorChar
                                 +getFileName(filePart));
                        try(InputStream fileContent = filePart.getInputStream()){
                           Files.copy(
                                   fileContent,tempFile.toPath(), 
                                   StandardCopyOption.REPLACE_EXISTING
                           );

                        }
                        writeToFile(resize(tempFile),pathToUploadFile);
                        tempFile.delete();
                     }else{ //if key = file
                         try(InputStream fileContent = filePart.getInputStream()){
                           Files.copy(
                                   fileContent,uploadFile.toPath(), 
                                   StandardCopyOption.REPLACE_EXISTING
                           );
                        }
                     }
                     
                     return getFileName(filePart);
                }
        return null;
                
    }
    private String getFileName(final Part part){
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part
                .getHeader("content-disposition")
                .split(";")){
            if(content.trim().startsWith("filename")){
                return content
                        .substring(content.indexOf('=')+1)
                        .trim()
                        .replace("\"",""); 
            }
        }
        return null;
    }
    public void writeToFile(byte[] data, String fileName) throws IOException{
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            out.write(data);
        }
    }
    public byte[] resize(File icon) {
        try {
           BufferedImage originalImage = ImageIO.read(icon);
           originalImage= Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH,400);
            //To save with original ratio uncomment next line and comment the above.
            //originalImage= Scalr.resize(originalImage, 153, 128);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return null;
        }
    }
}
