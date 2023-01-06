package com.mouzetech.mouzefood.domain.service;

import com.mouzetech.mouzefood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String offSet);
}