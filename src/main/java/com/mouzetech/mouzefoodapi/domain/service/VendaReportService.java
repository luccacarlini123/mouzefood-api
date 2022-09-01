package com.mouzetech.mouzefoodapi.domain.service;

import com.mouzetech.mouzefoodapi.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String offSet);
}