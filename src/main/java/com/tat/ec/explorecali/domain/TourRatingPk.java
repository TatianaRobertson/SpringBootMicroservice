package com.tat.ec.explorecali.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class TourRatingPk implements Serializable {

	@ManyToOne
	private Tour tour;
	
	@Column(insertable=false, updatable= false, nullable=false)
	private Integer customerId;

	public TourRatingPk() {
		super();
	}

	public TourRatingPk(Tour tour, Integer customerId) {
		super();
		this.tour = tour;
		this.customerId = customerId;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	
	
	
}
