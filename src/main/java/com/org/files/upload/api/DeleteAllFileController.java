package com.org.files.upload.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.org.files.impl.DeleteAllFileControllerImpl;
import com.org.files.impl.S3UtilImpl;
import com.org.files.s3bucket.S3Util;
import com.org.files.upload.Request.RequestEntity;
import com.org.files.upload.response.FileUploadResponse;

@RestController
public class DeleteAllFileController implements DeleteAllFileControllerImpl{
	PropertyDetails entity;
	S3UtilImpl awsUtil=new S3Util();
	@Override
	public ResponseEntity<?>  DeleteAllFile(RequestEntity requestObj) {
		// TODO Auto-generated method stub
		FileUploadResponse ur=new FileUploadResponse();
		
		try {	
		List<String> fileList=	requestObj.getFileName();
		
		//fileList.addAll(requestObj.getFileName());
		ur.setFileName(fileList);
		//File directory = new File(folderPath);
	boolean flag=	awsUtil.deleteMultipleFile(fileList, entity);
		if(flag)
			{
			
			 ur.setMessage("File deleted successfully");
			}else 
			{
				ur.setMessage("File director does not exist");
				
			};
		
			
	}catch (Exception e) {
		// TODO: handle exception
		ur.setMessage("Exception Occured while doing delete:"+e.getMessage());
	}
		return new ResponseEntity<>(ur, HttpStatus.OK);
	}
	



}
