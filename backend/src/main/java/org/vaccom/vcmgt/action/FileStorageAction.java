package org.vaccom.vcmgt.action;

import java.io.File;

import org.springframework.core.io.Resource;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import org.vaccom.vcmgt.property.FileStorageProperties;

@Service
public interface FileStorageAction {

	public void FileStorageService(FileStorageProperties fileStorageProperties);

	public String storeFile(MultipartFile file);
	
	public File storeFile(MultipartFile file, String tmp);

	public Resource loadFileAsResource(String fileName);

}
