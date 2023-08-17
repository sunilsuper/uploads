package com.org.files.upload.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.org.files.download.utility.FileDownloadUtil;
import com.org.files.impl.FileDownloadControllerImpl;
import com.org.files.s3bucket.S3Util;

@RestController
public class FileDownloadController implements FileDownloadControllerImpl {
	S3Util ss = new S3Util();
	@Autowired
	PropertyDetails entity;

	public ResponseEntity<?> downloadFile(String fileName) {
		FileDownloadUtil downloadUtil = new FileDownloadUtil();
		S3ObjectInputStream resObj;
		Resource resource = null;
		try {
			
			resObj = ss.fileDownload(fileName,  entity);

			resource = downloadUtil.getFileAsResource(entity.getDownLoadPath(),fileName);
		} catch (IOException e) {
			return ResponseEntity.internalServerError().build();
		}finally
		
		{
			
		}

		if (resObj == null) {
			return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
		}

		String contentType = "application/octet-stream";
		String headerValue = "attachment; filename=\"" + fileName + "\"";
		
		  return ResponseEntity.ok()
		 .contentType(MediaType.parseMediaType(contentType))
		  .header(HttpHeaders.CONTENT_DISPOSITION, headerValue) .body(resource);
		 
		// return new ResponseEntity<>("File downloaded sucessfully", HttpStatus.OK);
	}
}