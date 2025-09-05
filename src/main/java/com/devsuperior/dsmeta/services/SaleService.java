package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}


	public Page<SaleReportDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate max = parseOrDefault(maxDate, LocalDate.now());
		LocalDate min = parseOrDefault(minDate, max.minusYears(1L));
		String nameFilter = name == null ? "" : name.trim();
		return repository.searchReport(min, max, nameFilter, pageable);
	}

	public Page<SaleSummaryDTO> getSummary(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate max = parseOrDefault(maxDate, LocalDate.now());
		LocalDate min = parseOrDefault(minDate, max.minusYears(1L));
		String nameFilter = name == null ? "" : name.trim();
		return repository.searchSummary(min, max, nameFilter, pageable);
	}

	private LocalDate parseOrDefault(String dateStr, LocalDate defaultValue) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return defaultValue;
		}
		try {
			return LocalDate.parse(dateStr);
		} catch (DateTimeParseException e) {
			return defaultValue;
		}
	}
}
