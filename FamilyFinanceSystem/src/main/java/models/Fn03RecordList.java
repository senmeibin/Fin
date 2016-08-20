package models;

import java.util.ArrayList;
import java.util.List;

public class Fn03RecordList {

	private String[] seq;
	private String[] name;
	private String[] amount;
	private String[] displayFlag;

	public Fn03RecordList(){}

	public Fn03RecordList(String[] seq, String[] name,String[] amount, String[] displayFlag) {
		super();
		this.seq = seq;
		this.name = name;
		this.amount = amount;
		this.displayFlag = displayFlag;
	}

	public List<models.Fn03Record> parseList(){
		ArrayList<models.Fn03Record> ret = new ArrayList<>();
		for(int i=0;i<seq.length;i++){
			Fn03Record rec = new models.Fn03Record(name[i],Integer.parseInt(amount[i]),displayFlag[i]);
			rec.setSeq(Long.parseLong(seq[i]));
			ret.add(rec);
		}
		return ret;
	}

	public String[] getSeq() {
		return seq;
	}

	public void setSeq(String[] seq) {
		this.seq = seq;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getAmount() {
		return amount;
	}

	public void setAmount(String[] amount) {
		this.amount = amount;
	}

	public String[] getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(String[] displayFlag) {
		this.displayFlag = displayFlag;
	}

}
