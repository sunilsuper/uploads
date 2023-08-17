package com.org.files.upload.api;
 
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.org.files.impl.FileUploadControllerImpl;
import com.org.files.impl.S3UtilImpl;
import com.org.files.s3bucket.S3Util;
import com.org.files.upload.response.FileUploadResponse;
 
@RestController
public class FileUploadController implements FileUploadControllerImpl {
	
	@Autowired
	private PropertyDetails entity;
	S3UtilImpl awsUtil=new S3Util();
	String message = "Your file has been uploaded successfully!";
    public ResponseEntity<FileUploadResponse> uploadFile(
            MultipartFile multipartFile)
                    throws IOException {
    	
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
       
		String  bucket=entity.getBucket();
		awsUtil.uploadFile(fileName, multipartFile.getInputStream(), entity);
       
       // String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
                 FileUploadResponse response = new FileUploadResponse();
                 ArrayList<String> fileList=	new ArrayList<String>();
         		fileList.add(fileName);
         		response.setFileName(fileList);
         		response.setMessage(message);
        
        response.setSize(size);
        //response.setDownloadUri("/downloadFile/" + filecode);
         
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}