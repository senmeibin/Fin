package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import models.Fn00Dto;
import models.Fn00Dto.Fn01Sum;
import models.Fn04SumRecord;
import ninja.jpa.UnitOfWork;

public class Fn00Dao {
	@Inject
	Provider<EntityManager> entitiyManagerProvider;

	@UnitOfWork
	public Fn00Dto search(String year) {
		try{
			Fn00Dto ret = new Fn00Dto();
			EntityManager entityManager = entitiyManagerProvider.get();
			// set fn01SumList
			Query query = entityManager.createNativeQuery(
					"SELECT ym, SUM(amount) FROM fn01record GROUP BY ym HAVING ym like :ym ORDER BY ym")
					.setParameter("ym", year+"%");
			@SuppressWarnings("unchecked")
			List<Object[]> result = query.getResultList();
			if(result == null || result.isEmpty()){
				return ret;
			}
			List<Fn01Sum> fn01SumList = new ArrayList<Fn01Sum>();
			int totalMonthlyAmount=0;
			for(Object[] row : result){
				fn01SumList.add(new Fn01Sum(row[0].toString(),Integer.parseInt(row[1].toString())));
				totalMonthlyAmount+=Integer.parseInt(row[1].toString());
			}
			ret.setFn01SumList(fn01SumList);
			// set totalMonthlyAmount
			ret.setTotalMonthlyAmount(totalMonthlyAmount);
			// set totalSpecailAmount
			query = entityManager.createNativeQuery(
					"SELECT SUM(amount) FROM fn02record WHERE year = :year")
					.setParameter("year", year);
			Object result2 = query.getSingleResult();
			if(result2!=null){
				ret.setTotalSpecailAmount(Integer.parseInt(result2.toString()));
			}else{
				ret.setTotalSpecailAmount(0);
			}
			// set Income
			query = entityManager.createNativeQuery(
					"SELECT SUM(amount) FROM fn04record WHERE year = :year")
					.setParameter("year", year);
			Object result4 = query.getSingleResult();
			if(result4!=null){
				ret.setIncome(Integer.parseInt(result4.toString()));
			}else{
				ret.setIncome(0);
			}

			// set balance
			ret.setBalance();
			return ret;
		}
	catch(Exception ex){ex.printStackTrace();throw ex;}
	}

	@Transactional
	public void update(Fn04SumRecord fn04rec) {
		try{
		    EntityManager entityManager = entitiyManagerProvider.get();
		    entityManager.merge(fn04rec);
		    entityManager.flush();
		}
		catch(Exception ex){ex.printStackTrace();throw ex;}
	}
}
