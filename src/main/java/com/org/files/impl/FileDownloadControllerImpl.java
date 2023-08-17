package com.org.files.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface FileDownloadControllerImpl {
	@GetMapping("/downloadFile/{fileName}")
	public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName);
}
