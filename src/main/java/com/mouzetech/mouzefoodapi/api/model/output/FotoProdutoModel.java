package com.mouzetech.mouzefoodapi.api.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "fotos")
@Getter
@Setter
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel>{
	
	@ApiModelProperty(example = "c8f89367-c836-4e84-93f6-5df53f1c82c1_bad488ba-f0dd-4f35-b388-f8934f19dc17_T-BoneSteak.jpg")
	private String nomeArquivo;
	
	@ApiModelProperty(example = "tbonesteak123")
	private String descricao;
	
	@ApiModelProperty(example = "image/jpeg")
	private String contentType;
	
	@ApiModelProperty(example = "1035449")
	private Long tamanho;
}