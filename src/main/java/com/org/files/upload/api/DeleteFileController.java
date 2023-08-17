package com.org.files.upload.api;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.org.files.impl.DeleteFileControllerImpl;
import com.org.files.impl.S3UtilImpl;
import com.org.files.s3bucket.S3Util;
import com.org.files.upload.response.FileUploadResponse;

@RestController
public class DeleteFileController implements DeleteFileControllerImpl{
	S3UtilImpl awsUtil=new S3Util();
	@Autowired
	private PropertyDetails entity;
	@Override
	public ResponseEntity<?>  DeleteFile(String fileName) {
		// TODO Auto-generated method stub
		FileUploadResponse ur=new FileUploadResponse();
		boolean flag=false;
		
		try {	
		ArrayList<String> fileList=	new ArrayList<String>();
		fileList.add(fileName);
		ur.setFileName(fileList);
		try
		{
			awsUtil.deleteSingleFile(fileName, entity);
			flag=true;
		}catch(Exception e)
		{
			ur.setMessage("Exception occured while delete file..."+e.getMessage());
		}
			
			//	ur.setMessage("File does not exist");
			
		/*
		 * List<String> fileList= Stream.of(new File(folderPath).listFiles())
		 * .filter(file -> !file.isDirectory()) .map(File::getName)
		 * .collect(Collectors.toList());
		 */
			if(flag==true)
			{
			ur.setMessage("File deleted successfully");
			}
	}catch (Exception e) {
		// TODO: handle exception
		ur.setMessage("Exception Occured while doing delete:"+e.getMessage());
	}
		return new ResponseEntity<>(ur, HttpStatus.OK);
	}
	



}
