package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fn03Record {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long seq;
    private String name;
    private int amount;
    private String displayFlag;

	public Fn03Record(){}

	public Fn03Record(String name, int amount, String displayFlag) {
		super();
		this.name = name;
		this.amount = amount;
		this.displayFlag = displayFlag;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
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

	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}
}