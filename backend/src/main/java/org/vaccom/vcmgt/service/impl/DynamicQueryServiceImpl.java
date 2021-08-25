package org.vaccom.vcmgt.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.service.DynamicQueryService;

@Service
public class DynamicQueryServiceImpl implements DynamicQueryService {
	private Log _log = LogFactory.getLog(DynamicQueryServiceImpl.class);
	@Autowired
	private EntityManager em;

	@Override
	@Modifying
	public boolean executeQuery(String query) {
		//Transaction tx = null;
		Session session = null;

		try {
			Query q = em.createNativeQuery(query);
			q.executeUpdate();
			//session = em.unwrap(org.hibernate.Session.class);
			//tx = session.beginTransaction();
			// SessionFactory factory = session.getSessionFactory();
			//session.createQuery(query);
			//tx.commit();
			em.getTransaction().begin();
			em.close();
			return true;
		} catch (Exception e) {
			_log.error(e);
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
