/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.Part;

/**
 *
 * @author Melnikov
 */
public class FileLoader {
    public String uploadFile(String key, List<Part> fileParts) throws IOException{
        String imagesFolder = PropertiesLoader.getFolderPath(key);
                new File(imagesFolder).mkdirs();
                for(Part filePart : fileParts){
                     String path =  imagesFolder+File.separatorChar
                                     +getFileName(filePart);
                     File uploadFile = new File(path);
                     try(InputStream fileContent = filePart.getInputStream()){
                        Files.copy(
                                fileContent,uploadFile.toPath(), 
                                StandardCopyOption.REPLACE_EXISTING
                        );

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
}
