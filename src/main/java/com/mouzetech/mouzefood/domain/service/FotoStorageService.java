package com.mouzetech.mouzefood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	FotoRecuperada recuperar(String nomeArquivo);
	
	void armazenar(NovaFoto novaFoto);
	
	void remover(String nomeArquivo);
	
	default String gerarNomeArquivo(String nomeArquivo) {
		return UUID.randomUUID().toString() + "_" + nomeArquivo;
	}
	
	default void substituir(NovaFoto novaFoto, String nomeArquivo) {
		armazenar(novaFoto);
		
		if(StringUtils.isNotBlank(nomeArquivo)) {
			remover(nomeArquivo);
		}
	}
	
	@Getter
	@Builder
	class NovaFoto{
		private String nomeArquivo;
		private String contentType;
		private InputStream inputStream;
		private Long size;
	}
	
	@Getter
	@Builder
	class FotoRecuperada{
		private InputStream inputStream;
		private String url;
		
		public boolean temUrl() {
			return url != null;
		}
		
		public boolean teminputStream() {
			return inputStream != null;
		}
		
	}
}