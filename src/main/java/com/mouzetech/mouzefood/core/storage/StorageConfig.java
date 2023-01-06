package com.mouzetech.mouzefood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.mouzetech.mouzefood.core.storage.StorageProperties.TipoStorage;
import com.mouzetech.mouzefood.domain.infrastructure.service.foto.LocalFotoStorageService;
import com.mouzetech.mouzefood.domain.infrastructure.service.foto.S3FotoStorageService;
import com.mouzetech.mouzefood.domain.service.FotoStorageService;

@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties storageProperties;
	
	@Bean
	@ConditionalOnProperty(name = "mouzefood.storage.tipo-storage", havingValue = "s3")
	public AmazonS3 amazonS3() {
		
		var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(), 
				storageProperties.getS3().getChaveAcessoSecreta());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegiao())
				.build();
	}
	
	@Bean
	public FotoStorageService fotoStorageService() {
		if(TipoStorage.S3.equals(storageProperties.getTipoStorage())) {
			return new S3FotoStorageService();
		} else {
			return new LocalFotoStorageService();
		}
	}
	
}
