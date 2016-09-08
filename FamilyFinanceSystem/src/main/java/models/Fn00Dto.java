package models;

import java.util.List;

public class Fn00Dto {
	private List<Fn01Sum> fn01SumList;
	private int totalMonthlyAmount;
	private int totalSpecailAmount;
	private int income;
	private int balance;

	public Fn00Dto() {
	}



	public Fn00Dto(List<Fn01Sum> fn01SumList, int totalMonthlyAmount, int totalSpecailAmount, int income, int balance) {
		super();
		this.fn01SumList = fn01SumList;
		this.totalMonthlyAmount = totalMonthlyAmount;
		this.totalSpecailAmount = totalSpecailAmount;
		this.income = income;
		this.balance = balance;
	}

	public List<Fn01Sum> getFn01SumList() {
		return fn01SumList;
	}



	public void setFn01SumList(List<Fn01Sum> fn01SumList) {
		this.fn01SumList = fn01SumList;
	}



	public int getTotalMonthlyAmount() {
		return totalMonthlyAmount;
	}

	public void setTotalMonthlyAmount(int totalMonthlyAmount) {
		this.totalMonthlyAmount = totalMonthlyAmount;
	}

	public int getTotalSpecailAmount() {
		return totalSpecailAmount;
	}

	public void setTotalSpecailAmount(int totalSpecailAmount) {
		this.totalSpecailAmount = totalSpecailAmount;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public void setBalance() {
		this.balance = income - (totalMonthlyAmount + totalSpecailAmount);
	}

	public static class Fn01Sum{
		public String ym;
		public int sumAmount;
		public Fn01Sum(String ym, int sumAmount) {
			super();
			this.ym = ym;
			this.sumAmount = sumAmount;
		}
		public Fn01Sum() {
			super();
		}
		public String getYm() {
			return ym;
		}
		public void setYm(String ym) {
			this.ym = ym;
		}
		public int getSumAmount() {
			return sumAmount;
		}
		public void setSumAmount(int sumAmount) {
			this.sumAmount = sumAmount;
		}

	}



}