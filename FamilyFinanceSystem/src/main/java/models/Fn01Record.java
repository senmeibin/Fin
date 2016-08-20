package models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity @IdClass(Fn01Record.Fn01RecordKey.class)
public class Fn01Record {
	@Id
	private String ym;
	@Id
	private Long seq;

	private int amount;

	public Fn01Record(){}

	public Fn01Record(String ym, Long seq, int amount) {
		super();
		this.ym = ym;
		this.seq = seq;
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public static class Fn01RecordKey implements Serializable{
		String ym;
		Long seq;
		public Fn01RecordKey() {
			super();
			ym="";
			seq=0l;
		}
		public Fn01RecordKey(String ym, Long seq) {
			super();
			this.ym = ym;
			this.seq = seq;
		}
	}
}

//class Fn01RecordKey implements Serializable{
//	String ym;
//	Long seq;
//	public Fn01RecordKey() {
//		super();
//		ym="";
//		seq=0l;
//	}
//	public Fn01RecordKey(String ym, Long seq) {
//		super();
//		this.ym = ym;
//		this.seq = seq;
//	}
//}
