package org.vaccom.vcmgt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.CaTiemChung;
import org.vaccom.vcmgt.repository.CaTiemChungRepository;
import org.vaccom.vcmgt.service.CaTiemChungService;


/**
 * @author vaccom
 *
 */
@Service
public class CaTiemChungServiceImpl implements CaTiemChungService{
	
	@Autowired
	private EntityManager em;

	@Autowired
	private CaTiemChungRepository caTiemChungRepository;
	
	@Override
	public void deleteById(long id) {
		caTiemChungRepository.deleteById(id);
	}

	@Override
	public CaTiemChung findById(long id) {
		return caTiemChungRepository.findById(id);
	}

	@Override
	public long countCaTiemChung(Long lichTiemChungId, Long diaBanCoSoId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<CaTiemChung> caTiemChungRoot = cq.from(CaTiemChung.class);

		cq.select(cb.count(caTiemChungRoot));

		// Predicate[] predicates = new Predicate[] {};
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (lichTiemChungId != null && lichTiemChungId >= 0) {
			
			predicates.add(cb.equal(caTiemChungRoot.get("lichTiemChungId"), lichTiemChungId));
		}


		if (diaBanCoSoId != null && diaBanCoSoId >= 0) {
			
			predicates.add(cb.equal(caTiemChungRoot.get("diaBanCoSoId"), diaBanCoSoId));
		}
		
		if (!predicates.isEmpty()) {
			Predicate[] pdc = new Predicate[predicates.size()];
			int count = 0;
			for (Predicate predicate : predicates) {
				pdc[count] = predicate;
				count++;
			}
			Predicate allPredicate = cb.and(pdc);
			//cq.where(pdc);
			cq.where(allPredicate);
		}
		
		TypedQuery<Long> typedQuery = em.createQuery(cq);
		
		em.close();

		return typedQuery.getSingleResult();
	}

	@Override
	public List<CaTiemChung> searchCaTiemChung(Long lichTiemChungId, Long diaBanCoSoId, int page, int size) {
	
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<CaTiemChung> cq = cb.createQuery(CaTiemChung.class);

		Root<CaTiemChung> caTiemChungRoot = cq.from(CaTiemChung.class);

		cq.select(caTiemChungRoot);

		// Predicate[] predicates = new Predicate[] {};
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (lichTiemChungId != null && lichTiemChungId >= 0) {
			
			predicates.add(cb.equal(caTiemChungRoot.get("lichTiemChungId"), lichTiemChungId));
		}


		if (diaBanCoSoId != null && diaBanCoSoId >= 0) {
			
			predicates.add(cb.equal(caTiemChungRoot.get("diaBanCoSoId"), diaBanCoSoId));
		}

		
		if (!predicates.isEmpty()) {
			Predicate[] pdc = new Predicate[predicates.size()];
			int count = 0;
			for (Predicate predicate : predicates) {
				pdc[count] = predicate;
				count++;
			}
			Predicate allPredicate = cb.and(pdc);
			//cq.where(pdc);
			cq.where(allPredicate);
		}

		List<Order> orderList = new ArrayList<Order>();

		orderList.add(cb.desc(caTiemChungRoot.get("lichTiemChungId")));
		orderList.add(cb.asc(caTiemChungRoot.get("diaBanCoSoId")));
		cq.orderBy(orderList);

		TypedQuery<CaTiemChung> typedQuery = em.createQuery(cq);
		
		int offset = page * size;

		List<CaTiemChung> lstCaTiemChung = typedQuery.setFirstResult(offset).setMaxResults(size).getResultList();
		
		em.close();

		return lstCaTiemChung;
	}

	@Override
	public CaTiemChung updateCaTiemChung(CaTiemChung caTiemChung) {
		
		return caTiemChungRepository.save(caTiemChung);
	}
			
}
