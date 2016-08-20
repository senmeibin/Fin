package dao;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import models.Fn03Record;
import ninja.jpa.UnitOfWork;

public class Fn03Dao {
	@Inject
	Provider<EntityManager> entitiyManagerProvider;

	@Transactional
	public Fn03Record add(Fn03Record fn03Record) {
		try{
		    EntityManager entityManager = entitiyManagerProvider.get();
		    entityManager.persist(fn03Record);
		    entityManager.flush();
		    return fn03Record;
		}
		catch(Exception ex){ex.printStackTrace();throw ex;}
	}

	@UnitOfWork
	public List<Fn03Record> search() {
		try{
			EntityManager entityManager = entitiyManagerProvider.get();
			TypedQuery<Fn03Record> query= entityManager.createQuery("SELECT x FROM Fn03Record x", Fn03Record.class);
			return query.getResultList();
		}
	catch(Exception ex){ex.printStackTrace();throw ex;}
	}

	@Transactional
	public void update(List<Fn03Record> fn03Records) {
		try{
		    EntityManager entityManager = entitiyManagerProvider.get();
		    List<Fn03Record> workList = new ArrayList<Fn03Record>();
		    for(Fn03Record rec : fn03Records){
		    	Fn03Record updateObj = entityManager.find(Fn03Record.class,rec.getSeq());
		    	updateObj.setAmount(rec.getAmount());
		    	updateObj.setDisplayFlag(rec.getDisplayFlag());
		    	updateObj.setName(rec.getName());
		    	workList.add(updateObj);
		    }
		    entityManager.flush();
		}
		catch(Exception ex){ex.printStackTrace();throw ex;}
	}
}
