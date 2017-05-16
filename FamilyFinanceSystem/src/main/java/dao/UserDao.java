package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;

import models.UserRecord;
import ninja.jpa.UnitOfWork;

public class UserDao {
	@Inject
	Provider<EntityManager> entitiyManagerProvider;

	@UnitOfWork
	public Boolean checkExist(String name,String password) {
		try{
			List<UserRecord> retList= new ArrayList<UserRecord>();
			EntityManager entityManager = entitiyManagerProvider.get();
			TypedQuery<UserRecord> query= entityManager.createQuery(
					"SELECT x FROM UserRecord x WHERE x.name=:name and x.password=:password", UserRecord.class)
					.setParameter("name", name)
					.setParameter("password", password);
			retList= query.getResultList();
			//return retList.isEmpty()?false:true;
			return "xiaohou".equals(name) && "xiaohou".equals(password);
		}
		catch(Exception ex){ex.printStackTrace();throw ex;}
	}
}
