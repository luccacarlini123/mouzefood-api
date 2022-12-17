package com.mouzetech.mouzefoodapi.api;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceUriHelper {

	public static void addCreatedUriInResponseHeader(Object representationId) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(representationId).toUri();
		
		HttpServletResponse servletResponse = 
				( (ServletRequestAttributes)  RequestContextHolder.getRequestAttributes()).getResponse();
		
		servletResponse.addHeader(HttpHeaders.LOCATION, uri.toString());
	}	
}