package com.org.files.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.org.files.upload.Request.RequestEntity;

public interface DeleteAllFileControllerImpl {
	@DeleteMapping(value="/deleteMultiple",produces="application/json")
	ResponseEntity<?> DeleteAllFile(@RequestBody RequestEntity requestObj);

}
