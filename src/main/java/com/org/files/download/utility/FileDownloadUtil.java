package com.org.files.download.utility;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
 
public class FileDownloadUtil {
    private Path foundFile;
     
    public Resource getFileAsResource(String dirPath, String fileName) throws IOException {
        Path dirPaths = Paths.get(dirPath);
       int days=1 ;//file to be deleted from temp directory
       
        deleteFilesOlderThanNDays(days,dirPath);
        Files.list(dirPaths).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileName)) {
                foundFile = file;
                return;
            }
        });
 
        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
           
        }
         
        return null;
    }
    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            System.err.println("Error converting the multi-part file to file= "+ex.getMessage());
        }
        return file;
    }
    public static void deleteFilesOlderThanNDays(int days, String dirPath) throws IOException {
        long cutOff = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000);
        Files.list(Paths.get(dirPath))
        .filter(path -> {
            try {
                return Files.isRegularFile(path) && Files.getLastModifiedTime(path).to(TimeUnit.MILLISECONDS) < cutOff;
            } catch (IOException ex) {
                // log here and move on
                return false;
            }
        })
        .forEach(path -> {
            try {
                Files.delete(path);
            } catch (IOException ex) {
                // log here and move on
            }
        });
    }
}