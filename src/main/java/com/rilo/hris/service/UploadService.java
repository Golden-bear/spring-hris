package com.rilo.hris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.rilo.hris.property.FileStorageProperties;
import com.rilo.hris.util.FileStorageException;
import com.rilo.hris.util.MyFileNotFoundException;

@Service
public class UploadService {

	private Path fileStorageLocation;
	
	 @Autowired
	 FileStorageProperties fileStorageProperties;
	
	@Autowired
    public UploadService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
	
	
	public String storeFile(MultipartFile file, String type) {
        // Normalize file name
		//cleanPath = ngambil original name file
		
		try {
			String uniq = UUID.randomUUID().toString();
			String fileName =  uniq + StringUtils.cleanPath(file.getOriginalFilename());

	        if(fileName.contains("..")) {
	            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	        }
	        
	        if(type.contentEquals("file")){
	        	this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
	        }else if(type.contentEquals("foto_checkin")) {
	        	this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir_in()).toAbsolutePath().normalize();
	        }else if(type.contentEquals("foto_checkout")) {
				this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir_out()).toAbsolutePath().normalize();
			}
	        try {
	        	Files.createDirectories(this.fileStorageLocation);
	        } catch (Exception ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
	        
	        Path targetLocation = this.fileStorageLocation.resolve(fileName);
	        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	        return fileName;
		}catch(IOException ex) {
			 throw new FileStorageException("Could not store file " + type + ". Please try again!", ex);
		}
        
    }
	
	 public Resource loadFileAsResource(String fileName) {
	        try {  
	            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
	            Resource resource = new UrlResource(filePath.toUri());
	            if(resource.exists()) {
	                return resource;
	            } else {
	                throw new MyFileNotFoundException("File not found " + fileName);
	            }
	        } catch (MalformedURLException ex) {
	            throw new MyFileNotFoundException("File not found " + fileName, ex);
	        }
	    }
	
}
