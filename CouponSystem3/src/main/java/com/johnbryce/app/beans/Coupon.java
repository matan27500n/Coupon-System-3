package com.johnbryce.app.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupons")
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int companyID;
	@Enumerated(EnumType.ORDINAL)
	private Category categoryID;
	private String title;
	private String description;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date start_date;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date end_date;
	private int amount;
	private double price;
	@Column(length = 100000)
	private String image;

	public Coupon(int company_id, Category category_id, String title, String description, Date start_date,
			Date end_date, int amount, double price, String image) {
		this.companyID = company_id;
		this.categoryID = category_id;
		this.title = title;
		this.description = description;
		this.start_date = start_date;
		this.end_date = end_date;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

}
