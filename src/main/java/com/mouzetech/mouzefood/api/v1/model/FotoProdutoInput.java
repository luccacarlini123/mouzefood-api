package com.mouzetech.mouzefood.api.v1.model;

import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.mouzetech.mouzefood.core.validator.FileContentType;
import com.mouzetech.mouzefood.core.validator.FileSize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {
	
	@ApiModelProperty(hidden = true)
	@NotNull
	@FileSize(max = "8000KB")
	@FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	private MultipartFile arquivo;
	
	@ApiModelProperty(value = "Descrição da foto", required = true, example = "tbonesteak123")
	@NotNull
	private String descricao;
}