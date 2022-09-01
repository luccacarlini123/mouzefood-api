package com.mouzetech.mouzefoodapi.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefoodapi.domain.model.Restaurante;
import com.mouzetech.mouzefoodapi.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/por-nome-entre-taxa-frete")
	public List<Restaurante> buscarPorNomeAndTaxaFreteBetween(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.find(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/find-criteria")
	public List<Restaurante> findCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.findCriteria(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/por-cozinha-id-entre-taxa-frete/{cozinhaId}")
	public List<Restaurante> buscarPorCozinhaIdEntreTaxaFrete(@PathVariable("cozinhaId") Long cozinhaId, @RequestParam BigDecimal taxaInicial, @RequestParam BigDecimal taxaFinal){
		return restauranteRepository.buscarPorCozinhaIdEntreTaxaFrete(cozinhaId, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/com-frete-gratis")
	public List<Restaurante> buscarComFreteGratisNomeSemelhante(@RequestParam("nome") String nome){

		return restauranteRepository.findComFreteGratis(nome);
	}
	
	
}
