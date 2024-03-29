package com.mouzetech.mouzefood.domain.infrastructure.service.foto;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mouzetech.mouzefood.core.storage.StorageProperties;
import com.mouzetech.mouzefood.domain.exception.StorageException;
import com.mouzetech.mouzefood.domain.service.FotoStorageService;

public class S3FotoStorageService implements FotoStorageService {
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Autowired
	private AmazonS3 amazonS3;

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
		
		FotoRecuperada fotoRecuperada = FotoRecuperada.builder().url(url.toString()).build();
		
		return fotoRecuperada;
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			var caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
			var objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(novaFoto.getContentType());
			objectMetaData.setContentLength(novaFoto.getSize());
			
			var putObject = new PutObjectRequest(storageProperties.getS3().getBucket(), 
					caminhoArquivo, 
					novaFoto.getInputStream(), 
					objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObject);
		} catch(Exception e) {
			throw new StorageException("Não possível enviar o arquivo para Amazon S3", e);
		}
	}
	
	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
			
			var deleteObject = new DeleteObjectRequest(storageProperties.getS3().getBucket(), caminhoArquivo);
			
			amazonS3.deleteObject(deleteObject);
		} catch(Exception e) {
			throw new StorageException("Não possível excluir o arquivo da Amazon S3", e);
		}
	}

}