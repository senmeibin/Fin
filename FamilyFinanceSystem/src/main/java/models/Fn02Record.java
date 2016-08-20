package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fn02Record {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long seq;
	private String year;
	private String name;
	private int amount;

	public Fn02Record(){}

	public Fn02Record(String year, String name, int amount) {
		super();
		this.year = year;
		this.name = name;
		this.amount = amount;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
