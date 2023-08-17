package com.org.files.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ViewFilesControllerImpl {
	@GetMapping(value="/filelist",produces="application/json")
	public ResponseEntity<?> getFileList();

}
