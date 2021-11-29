package com.rilo.hris.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class MyResponseErrorHandler implements ResponseErrorHandler {
	
	private static Logger logger = LoggerFactory.getLogger(MyResponseErrorHandler.class);
	
	public boolean hasError(ClientHttpResponse response) throws IOException {
            // TODO Auto-generated method stub
            if (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                            || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
                    return true;
            } else {
                    return false;
            }
    }
    
	public void handleError(ClientHttpResponse response) throws IOException {
            // TODO Auto-generated method stub
            logger.error("Response error: "+response.getStatusCode()+" "+response.getStatusText());
    }
}
