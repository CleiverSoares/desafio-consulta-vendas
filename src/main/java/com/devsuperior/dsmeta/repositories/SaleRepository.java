package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(s.id, s.date, s.amount, s.seller.name) "
			+ "FROM Sale s "
			+ "WHERE s.date BETWEEN :min AND :max "
			+ "AND UPPER(s.seller.name) LIKE CONCAT('%', UPPER(:name), '%')")
	Page<SaleReportDTO> searchReport(LocalDate min, LocalDate max, String name, Pageable pageable);

	@Query(
		value = "SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(s.seller.name, SUM(s.amount)) "
				+ "FROM Sale s "
				+ "WHERE s.date BETWEEN :min AND :max "
				+ "AND UPPER(s.seller.name) LIKE CONCAT('%', UPPER(:name), '%') "
				+ "GROUP BY s.seller.name",
		countQuery = "SELECT COUNT(DISTINCT s.seller.name) "
				+ "FROM Sale s "
				+ "WHERE s.date BETWEEN :min AND :max "
				+ "AND UPPER(s.seller.name) LIKE CONCAT('%', UPPER(:name), '%')"
	)
	Page<SaleSummaryDTO> searchSummary(LocalDate min, LocalDate max, String name, Pageable pageable);
}
