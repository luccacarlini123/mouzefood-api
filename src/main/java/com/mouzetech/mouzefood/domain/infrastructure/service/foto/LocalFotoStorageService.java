package com.mouzetech.mouzefood.domain.infrastructure.service.foto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.mouzetech.mouzefood.core.storage.StorageProperties;
import com.mouzetech.mouzefood.domain.exception.StorageException;
import com.mouzetech.mouzefood.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService {
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path path = getArquivoPath(novaFoto.getNomeArquivo());
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(path));
		} catch (IOException e) {
			throw new StorageException("Não foi possível armazenar o arquivo", e.getCause());			
		}
	}

	private Path getArquivoPath(String nomeArquivo) {
		return storageProperties.getLocal().getDiretorioFotos()
				.resolve(Path.of(nomeArquivo));
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			Path path = getArquivoPath(nomeArquivo);
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new StorageException("Não foi possível excluir o arquivo", e.getCause());
		}		
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		try {
			Path path = getArquivoPath(nomeArquivo);
			
			FotoRecuperada fotoRecuperada = FotoRecuperada.builder().inputStream(Files.newInputStream(path)).build();
			
			return fotoRecuperada;
		} catch (IOException e) {
			throw new StorageException("Não foi possível recuperar o arquivo", e.getCause());
		}
	}
}