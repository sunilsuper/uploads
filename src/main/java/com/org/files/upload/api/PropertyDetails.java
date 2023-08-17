package com.org.files.upload.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties("")
public class PropertyDetails {
@Value("${aws.access_key_id}")
public String access_key_id;
@Value("${aws.secret_access_key}")
public String secret_access_key;
@Value("${aws.s3.bucket}")
public String bucket;
@Value("${aws.s3.download_path}")
public String downLoadPath;
@Value("${aws.s3.upload_path}")
public String uploadPath;
@Value("${aws.s3.region}")
public String region;



public PropertyDetails() {
	// TODO Auto-generated constructor stub
}
public String getDownLoadPath() {
	return downLoadPath;
}
public void setDownLoadPath(String downLoadPath) {
	this.downLoadPath = downLoadPath;
}
public String getUploadPath() {
	return uploadPath;
}
public void setUploadPath(String uploadPath) {
	this.uploadPath = uploadPath;
}
public String getAccess_key_id() {
	return access_key_id;
}

public void setAccess_key_id(String access_key_id) {
	this.access_key_id = access_key_id;
}
public String getSecret_access_key() {
	return secret_access_key;
}
public void setSecret_access_key(String secret_access_key) {
	this.secret_access_key = secret_access_key;
}
public String getBucket() {
	return bucket;
}
public void setBucket(String bucket) {
	this.bucket = bucket;
}

public String getRegion() {
	return region;
}
public void setRegion(String region) {
	this.region = region;
}
@Override
public String toString() {
	System.out.println("default active profile:");
	return "PropertyDetails [access_key_id=" + access_key_id + ", secret_access_key=" + secret_access_key + ", bucket="
			+ bucket + ", downLoadPath=" + downLoadPath + ", uploadPath=" + uploadPath + ", region=" + region + "]";
}
}
