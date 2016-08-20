package models;

import java.util.ArrayList;
import java.util.List;

public class Fn01DtoList {

	private String[] seq;
	private String[] name;
	private String[] amount;
	private String header_ym;

	public Fn01DtoList(){}

	public Fn01DtoList(String header_ym, String[] seq, String[] name,String[] amount) {
		super();
		this.header_ym = header_ym;
		this.seq = seq;
		this.name = name;
		this.amount = amount;
	}

	public List<models.Fn01Record> parseDtoListToRecords(){
		ArrayList<models.Fn01Record> ret = new ArrayList<>();
		for(int i=0;i<seq.length;i++){
			Fn01Record rec = new models.Fn01Record(header_ym, Long.parseLong(seq[i]),Integer.parseInt(amount[i]));
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

	public String getHeader_ym() {
		return header_ym;
	}

	public void setHeader_ym(String header_ym) {
		this.header_ym = header_ym;
	}



}
