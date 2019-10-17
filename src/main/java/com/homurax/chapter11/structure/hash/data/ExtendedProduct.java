package com.homurax.chapter11.structure.hash.data;

import lombok.Data;

@Data
public class ExtendedProduct extends Product {

	private String buyer;
	private short value;

	public ExtendedProduct(Product source, String buyer, short value) {
		this.setId(source.getId());
		this.setAsin(source.getAsin());
		this.setTitle(source.getTitle());
		this.setGroup(source.getGroup());
		this.setSalesRank(source.getSalesRank());
		this.setSimilar(source.getSimilar());
		this.setCategories(source.getCategories());
		this.setReviews(source.getReviews());
		this.buyer=buyer;
		this.value=value;
	}
}
