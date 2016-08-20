package models;

import java.util.ArrayList;
import java.util.List;

public class Fn02DtoList {

	private String[] name;
	private String[] amount;
	private String header_year;

	public Fn02DtoList(){}

	public Fn02DtoList(String header_year, String[] name,String[] amount) {
		super();
		this.header_year = header_year;
		this.name = name;
		this.amount = amount;
	}

	public List<models.Fn02Record> parseDtoListToRecords(){
		ArrayList<models.Fn02Record> ret = new ArrayList<>();
		for(int i=0;i<name.length;i++){
			if(name[i].trim().isEmpty()){
				// don't update no name item.
				continue;
			}
			Fn02Record rec = new models.Fn02Record(header_year, name[i],Integer.parseInt(amount[i]));
			ret.add(rec);
		}
		return ret;
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

	public String getHeader_year() {
		return header_year;
	}

	public void setHeader_year(String header_year) {
		this.header_year = header_year;
	}


}
