package com.org.files.upload.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.org.files.impl.ViewFilesControllerImpl;
import com.org.files.s3bucket.S3Util;
import com.org.files.upload.response.FileUploadResponse;

@RestController
public class ViewFilesController implements ViewFilesControllerImpl {
	/*
	 * @Value("${spring.profiles.active}") private String activeProfile;
	 */
	@Autowired
	private PropertyDetails entity;

	@Override
	public ResponseEntity<?> getFileList() {
		S3Util ss = new S3Util();
		FileUploadResponse res = ss.getAllObject(entity);
		
		

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
