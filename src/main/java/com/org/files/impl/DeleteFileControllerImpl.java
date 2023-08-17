package com.org.files.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

public interface DeleteFileControllerImpl {
	@PutMapping(value="/delete/{fileName}",produces="application/json")
	public ResponseEntity<?>  DeleteFile(@PathVariable("fileName") String fileName);
}