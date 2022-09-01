package com.mouzetech.mouzefoodapi.domain.service;

import java.util.List;

import com.mouzetech.mouzefoodapi.domain.filter.VendaDiariaFilter;
import com.mouzetech.mouzefoodapi.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> buscarVendasDiarias(VendaDiariaFilter filtro, String offSet);
	
}
