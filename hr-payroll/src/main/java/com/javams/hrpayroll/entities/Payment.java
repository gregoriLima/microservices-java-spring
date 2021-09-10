package com.javams.hrpayroll.entities;

import java.io.Serializable;

public class Payment implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	private Double dailyIncomeMoney;
	private Integer days;
	
	public Payment() {
		
	}

	public Payment(String name, Double dailyIncomeMoney, Integer days) {
		super();
		this.name = name;
		this.dailyIncomeMoney = dailyIncomeMoney;
		this.days = days;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDailyIncomeMoney() {
		return dailyIncomeMoney;
	}

	public void setDailyIncomeMoney(Double dailyIncomeMoney) {
		this.dailyIncomeMoney = dailyIncomeMoney;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
	
	public Double getTotal() {
		return days * dailyIncomeMoney;
	}
	
}
