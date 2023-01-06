package com.mouzetech.mouzefood.domain.service;

import java.util.List;

import com.mouzetech.mouzefood.domain.filter.VendaDiariaFilter;
import com.mouzetech.mouzefood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> buscarVendasDiarias(VendaDiariaFilter filtro, String offSet);
	
}
