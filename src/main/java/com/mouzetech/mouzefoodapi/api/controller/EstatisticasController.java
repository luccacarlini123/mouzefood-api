package com.mouzetech.mouzefoodapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefoodapi.api.ApiLinkBuilder;
import com.mouzetech.mouzefoodapi.api.model.assembler.EstatisticaModel;
import com.mouzetech.mouzefoodapi.domain.filter.VendaDiariaFilter;
import com.mouzetech.mouzefoodapi.domain.model.dto.VendaDiaria;
import com.mouzetech.mouzefoodapi.domain.service.VendaQueryService;
import com.mouzetech.mouzefoodapi.domain.service.VendaReportService;
import com.mouzetech.mouzefoodapi.openapi.controller.EstatisticasControllerOpenApi;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@Autowired
	private ApiLinkBuilder apiLinkBuilder;
	
	@GetMapping
	public EstatisticaModel buscarEndpointsDeEstatisticas() {
		var estatisticaModel = new EstatisticaModel();
		
		estatisticaModel.add(apiLinkBuilder.linkToVendasDiarias("vendas-diarias"));
		
		return estatisticaModel;
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> buscarVendasDiarias(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String offSet) {
		return vendaQueryService.buscarVendasDiarias(filtro, offSet);
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> gerarRelatiorioVendasDiarias(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String offSet) {
		byte[] pdf = vendaReportService.emitirVendasDiarias(filtro, offSet);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(pdf);
	}
}