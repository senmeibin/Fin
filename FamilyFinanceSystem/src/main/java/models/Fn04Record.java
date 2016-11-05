package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fn04Record {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long seq;
	private String year;
	private String name;
	private int amount;

	public Fn04Record(){}

	public Fn04Record(String year, String name, int amount) {
		super();
		this.year = year;
		this.name = name;
		this.amount = amount;
	}



	public Fn04Record(Long seq, String year, String name, int amount) {
		super();
		this.seq = seq;
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

//	public static List<Fn04Record> parseJsonData(List<HashMap<String,Object>> data){
//		List<Fn04Record> ret = new ArrayList<Fn04Record>();
//		for(HashMap<String,Object> map : data){
//		    Long seq = Long.parseLong(map.get("seq").toString());
//		    String year = map.get("year").toString();
//		    String name = map.get("name").toString();
//		    int amount = Integer.parseInt(map.get("amount").toString());
//		    ret.add(new Fn04Record(seq,year,name,amount));
//		}
//		return ret;
//	}
}
