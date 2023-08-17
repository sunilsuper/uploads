package com.org.files.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.org.files.upload.api.PropertyDetails;
import com.org.files.upload.response.FileUploadResponse;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.S3Exception;

public interface S3UtilImpl {
	
	 
	void uploadFile(String fileName, InputStream inputStream,PropertyDetails entity) throws S3Exception, AwsServiceException, SdkClientException, IOException;
	void deleteSingleFile(String fileName,PropertyDetails entity);
	boolean deleteMultipleFile(List<String> fileNameList,PropertyDetails entity) ;
	FileUploadResponse getAllObject(PropertyDetails entity);
	S3ObjectInputStream fileDownload(String fileName,PropertyDetails entity) throws IOException ; 
}
