package com.org.files.s3bucket;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.org.files.impl.S3UtilImpl;
import com.org.files.upload.api.PropertyDetails;
import com.org.files.upload.response.FileUploadResponse;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3Util implements S3UtilImpl {
	// private static final String BUCKET = "sunildemoj";

	public void uploadFile(String fileName, InputStream inputStream, PropertyDetails entity)
			throws S3Exception, AwsServiceException, SdkClientException, IOException {
		String accesskey = entity.getAccess_key_id();
		String secretkey = entity.getSecret_access_key();
		String bucketName = entity.getBucket();
		// String regions = entity.getRegion();
		AwsCredentials credentials = new AwsCredentials() {

			@Override
			public String secretAccessKey() {
				// TODO Auto-generated method stub
				return secretkey;
			}

			@Override
			public String accessKeyId() {
				// TODO Auto-generated method stub
				return accesskey;
			}
		};
		S3Client client = S3Client.builder().credentialsProvider(new AwsCredentialsProvider() {

			@Override
			public AwsCredentials resolveCredentials() {
				// TODO Auto-generated method stub
				return credentials;
			}
		}).build();
		// AmazonS3 s3client = getS3client();
		PutObjectRequest request = PutObjectRequest.builder().bucket(bucketName).key(fileName).build();
		client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
	}

	public void deleteSingleFile(String fileName, PropertyDetails entity) {
		String BucketName = entity.getBucket();
		// String key = fileName;
		try {
			AmazonS3 s3client = getS3client(entity);

			// DeleteObjectRequest request =
			// DeleteObjectRequest.builder().bucket(bucket).key(key).build();

			// client.deleteObject(request);
			s3client.deleteObject(BucketName, fileName);
		} catch (Exception e) {
			System.out.println("Exception while deleting file: " + e.getMessage());
		}
		System.out.println("File deleted successfully " + fileName);
	}

	public boolean deleteMultipleFile(List<String> fileNameList, PropertyDetails entity) {
		String bucket = entity.getBucket();
		S3Client client = S3Client.builder().build();
		List<ObjectIdentifier> listObjects = new ArrayList<>();
		for (String fileName : fileNameList) {
			listObjects.add(ObjectIdentifier.builder().key(fileName).build());
		}
		DeleteObjectsRequest request = DeleteObjectsRequest.builder().bucket(bucket)
				.delete(Delete.builder().objects(listObjects).build()).build();
		DeleteObjectsResponse response = client.deleteObjects(request);
		return response.hasDeleted();
	}

	public FileUploadResponse getAllObject(PropertyDetails entity) {
		String bucketName = entity.getBucket();
		FileUploadResponse res = new FileUploadResponse();
		AmazonS3 s3client = getS3client(entity);
		ObjectListing objects = s3client.listObjects(bucketName);
		List<String> fileList = new ArrayList<String>();
		for (S3ObjectSummary os : objects.getObjectSummaries()) {
			System.out.println("fileName:" + os.getKey() + ",Size:" + os.getSize());
			fileList.add(os.getKey());
		}
		res.setFileName(fileList);
		return res;
	}

	public S3ObjectInputStream fileDownload(String fileName, PropertyDetails entity) throws IOException {
		String bucketName = entity.getBucket();
		String key = fileName;
		String folderPath = entity.getDownLoadPath();
		// S3Client client = S3Client.builder().build();
		AmazonS3 s3client = getS3client(entity);
		com.amazonaws.services.s3.model.S3Object s3object = s3client.getObject(bucketName, fileName);
		S3ObjectInputStream inputStream = s3object.getObjectContent();
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(folderPath + key));

		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		// response.close();
		outputStream.close();
		return inputStream;
	}

	public static AmazonS3 getS3client(PropertyDetails entity) {

		String accesskey = entity.getAccess_key_id();
		String secretkey = entity.getSecret_access_key();
		String regions = entity.getRegion();
		AWSCredentials credentials = new BasicAWSCredentials(accesskey, secretkey);
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(regions) // Regions.EU_WEST_2;
				.build();
		return s3client;
	}

	public void uploadFilefromPath(String fileName, InputStream inputStream, PropertyDetails entity)
			throws S3Exception, AwsServiceException, SdkClientException, IOException {
		AmazonS3 s3client = getS3client(entity);

		String bucketName = entity.getBucket();
		String uploadPath = entity.getUploadPath();
		s3client.putObject(bucketName, fileName, new File(uploadPath + fileName));
	}
}