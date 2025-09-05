package com.devsuperior.dsmeta.dto;

public class SaleSummaryDTO {

	private String sellerName;
	private Double totalAmount;

	public SaleSummaryDTO(String sellerName, Double totalAmount) {
		this.sellerName = sellerName;
		this.totalAmount = totalAmount;
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}
}


