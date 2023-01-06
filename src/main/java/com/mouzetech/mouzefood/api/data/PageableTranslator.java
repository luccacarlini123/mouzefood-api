package com.mouzetech.mouzefood.api.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableTranslator {

	public static Pageable translate(Pageable pageable, Map<String, String> fieldMappins) {
		var orders = pageable.getSort().stream()
			.filter(order -> fieldMappins.containsKey(order.getProperty()))
			.map(order -> new Sort.Order(order.getDirection(), fieldMappins.get(order.getProperty())))
			.collect(Collectors.toList());
		
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
	}
	
}
