package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import models.Fn04Record;
import ninja.jpa.UnitOfWork;

public class Fn04Dao {
	@Inject
	Provider<EntityManager> entitiyManagerProvider;

	@UnitOfWork
	public List<Fn04Record> search(String year) {
		try{
			List<Fn04Record> ret= new ArrayList<Fn04Record>();
			EntityManager entityManager = entitiyManagerProvider.get();
			TypedQuery<Fn04Record> query= entityManager.createQuery(
					"SELECT x FROM Fn04Record x WHERE x.year=:year ORDER BY x.seq", Fn04Record.class)
					.setParameter("year", year);
			ret= query.getResultList();
			return ret;
		}
		catch(Exception ex){ex.printStackTrace();throw ex;}
	}

	@Transactional
	public List<Fn04Record> update(List<Fn04Record> fn04Records) {
		try{
			List<Fn04Record> retList = new ArrayList<Fn04Record>();
		    EntityManager entityManager = entitiyManagerProvider.get();
		    for(Fn04Record rec : fn04Records){
		    	if(rec.getSeq()==0L){
		    		if(rec.getAmount()!=0){
		    			rec.setSeq(null);
		    			entityManager.persist(rec);
		    			retList.add(rec);
		    		}
		    	}else{
		    		if(rec.getAmount()!=0){
		    			entityManager.merge(rec);
		    			retList.add(rec);
		    		}
		    		else{
		    			entityManager.remove(entityManager.contains(rec) ? rec : entityManager.merge(rec));
		    		}
		    	}
		    }
		    entityManager.flush();
		    return retList;
		}
		catch(Exception ex){ex.printStackTrace();throw ex;}

	}
}
