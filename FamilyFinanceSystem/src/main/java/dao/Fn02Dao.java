package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import models.Fn02Dto;
import models.Fn02Record;
import ninja.jpa.UnitOfWork;

public class Fn02Dao {
	@Inject
	Provider<EntityManager> entitiyManagerProvider;

	@UnitOfWork
	public List<Fn02Dto> search(String year) {
		try{
			List<Fn02Dto> ret= new ArrayList<Fn02Dto>();
			EntityManager entityManager = entitiyManagerProvider.get();
			TypedQuery<Fn02Record> query= entityManager.createQuery(
					"SELECT x FROM Fn02Record x WHERE x.year=:year ORDER BY x.seq", Fn02Record.class)
					.setParameter("year", year);
			List<Fn02Record> fn02recs= query.getResultList();
			for(Fn02Record rec : fn02recs){
				Fn02Dto fn02Dto = new Fn02Dto(rec.getName(),
											rec.getAmount());
				ret.add(fn02Dto);
			}
			return ret;
		}
	catch(Exception ex){ex.printStackTrace();throw ex;}
	}

	@Transactional
	public List<Fn02Dto> update(String year, List<Fn02Record> fn02Records) {
		try{
		    EntityManager entityManager = entitiyManagerProvider.get();
		    entityManager.createQuery("DELETE FROM Fn02Record x WHERE x.year=:year")
		    			.setParameter("year", year).executeUpdate();
		    List<Fn02Dto> retList = new ArrayList<Fn02Dto>();
		    for(Fn02Record rec : fn02Records){
		    	entityManager.persist(rec);
		    	Fn02Dto dto = new Fn02Dto(rec.getName(),rec.getAmount());
		    	retList.add(dto);
		    }
		    entityManager.flush();
		    return retList;
		}
		catch(Exception ex){ex.printStackTrace();throw ex;}

	}
}
