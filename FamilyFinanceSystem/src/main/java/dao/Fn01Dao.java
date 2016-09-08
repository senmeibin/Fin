package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import models.Fn01Dto;
import models.Fn01Record;
import models.Fn03Record;
import ninja.jpa.UnitOfWork;

public class Fn01Dao {
	@Inject
	Provider<EntityManager> entitiyManagerProvider;

	@UnitOfWork
	public List<Fn01Dto> search(String ym) {
		try{
			List<Fn01Dto> ret= new ArrayList<Fn01Dto>();
			EntityManager entityManager = entitiyManagerProvider.get();
			TypedQuery<Fn01Record> query= entityManager.createQuery(
					"SELECT x FROM Fn01Record x WHERE x.ym=:ym ORDER BY x.seq", Fn01Record.class)
					.setParameter("ym", ym);
			List<Fn01Record> fn01recs= query.getResultList();
			for(Fn01Record rec : fn01recs){
				Query query2 = entityManager.createQuery(
					"SELECT x.name FROM Fn03Record x Where x.seq=:seq")
					.setParameter("seq", rec.getSeq());
				Fn01Dto fn01Dto = new Fn01Dto(rec.getYm(),
											rec.getSeq(),
											query2.getSingleResult().toString(),
											rec.getAmount());
				ret.add(fn01Dto);
			}
			return ret;
		}
	catch(Exception ex){ex.printStackTrace();throw ex;}
	}

	@Transactional
	public List<Fn01Dto> newRecords(String ym) {
		try{
			List<Fn01Dto> ret= new ArrayList<Fn01Dto>();
			EntityManager entityManager = entitiyManagerProvider.get();
			entityManager.createQuery("DELETE FROM Fn01Record x WHERE x.ym=:ym")
										.setParameter("ym", ym).executeUpdate();
			TypedQuery<Fn03Record> query= entityManager.createQuery(
					"SELECT x FROM Fn03Record x Where x.displayFlag='Y' ORDER BY x.seq", Fn03Record.class);
			List<Fn03Record> fn03recs= query.getResultList();
			for(Fn03Record rec : fn03recs){
				Fn01Record fn01rec = new Fn01Record(ym,rec.getSeq(),rec.getAmount());
				entityManager.persist(fn01rec);
				Fn01Dto fn01Dto = new Fn01Dto(ym,
											rec.getSeq(),
											rec.getName(),
											rec.getAmount());
				ret.add(fn01Dto);
			}
		    entityManager.flush();
		    return ret;
		}
		catch(Exception ex){ex.printStackTrace();throw ex;}
	}

	@Transactional
	public void update(List<Fn01Record> fn01Records) {
		try{
		    EntityManager entityManager = entitiyManagerProvider.get();
		    List<Fn01Record> workList = new ArrayList<Fn01Record>();
		    for(Fn01Record rec : fn01Records){
		    	Fn01Record.Fn01RecordKey searchKey = new Fn01Record.Fn01RecordKey(rec.getYm(), rec.getSeq());
		    	Fn01Record updateObj = entityManager.find(Fn01Record.class, searchKey);
		    	updateObj.setAmount(rec.getAmount());
		    	workList.add(updateObj);
		    }
		    entityManager.flush();
		}
		catch(Exception ex){ex.printStackTrace();throw ex;}

	}
}
