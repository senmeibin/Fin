package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Fn04SumRecord {
    @Id
	private String year;
	private int amount;

	public Fn04SumRecord(){}

	public Fn04SumRecord(String year, int amount) {
		super();
		this.year = year;
		this.amount = amount;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
