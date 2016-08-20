package models;

public class Fn01Dto {
	private String ym;
	private Long seq;
	private String name;
	private int amount;

	public Fn01Dto() {
	}

	public Fn01Dto(String ym, Long seq, String name, int amount) {
		super();
		this.ym = ym;
		this.seq = seq;
		this.name = name;
		this.amount = amount;
	}

	public String getYm() {
		return ym;
	}

	public void setYm(String ym) {
		this.ym = ym;
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

}
