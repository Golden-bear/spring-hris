package com.rilo.hris.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

	private String uploadDir;
	private String uploadDir_in;
	private String uploadDir_out;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

	public String getUploadDir_in() {
		return uploadDir_in;
	}

	public void setUploadDir_in(String uploadDir_in) {
		this.uploadDir_in = uploadDir_in;
	}

	public String getUploadDir_out() {
		return uploadDir_out;
	}

	public void setUploadDir_out(String uploadDir_out) {
		this.uploadDir_out = uploadDir_out;
	}
}
