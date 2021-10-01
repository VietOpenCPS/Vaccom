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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.dto.PhieuHenTiemDto;
import org.vaccom.vcmgt.dto.ResultSearchDto;
import org.vaccom.vcmgt.entity.MuiTiemChung;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.entity.PhieuHenTiem;
import org.vaccom.vcmgt.repository.PhieuHenTiemRepository;
import org.vaccom.vcmgt.service.PhieuHenTiemService;

import com.liferay.portal.kernel.util.Validator;



/**
 * @author vaccom
 *
 */
@Service
public class PhieuHenTiemServiceImpl implements PhieuHenTiemService {

	@Autowired
	private PhieuHenTiemRepository phieuHenTiemRepository;
	
	@Autowired
	private EntityManager em;
	
	@Override
	public long countByLichTiemChungId(long id) {
		return phieuHenTiemRepository.countByLichTiemChungId(id);
	}

	@Override
	public PhieuHenTiem findById(long id) {

		return phieuHenTiemRepository.findById(id);
	}
	
	@Override
	public PhieuHenTiem findByMaQR(String maQR) {
		return phieuHenTiemRepository.findByMaQR(maQR);
	}

	@Override
	public List<PhieuHenTiem> findAll() {

		return phieuHenTiemRepository.findAll();
	}

	@Override
	public PhieuHenTiem updatePhieuHenTiem(PhieuHenTiem phieuHenTiem) {

		return phieuHenTiemRepository.save(phieuHenTiem);
	}

	@Override
	public void deleteById(long id) {
		phieuHenTiemRepository.deleteById(id);
	}

	@Override
	public List<PhieuHenTiem> findByLichTiemChungId(long id, int page, int size) {
		if (page < 0 || size < 0) {
			page = 0;
			size = 30;
		}
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<PhieuHenTiem> pases = phieuHenTiemRepository.findByLichTiemChungId(id, pageable);
		return pases.getContent();
	}

	@Override
	public List<PhieuHenTiem> findByNguoiTiemChungId(long id) {
		return phieuHenTiemRepository.findByNguoiTiemChungId(id);
	}
	
	
	@Override
	public long countPhieuHenTiem(Long lichTiemChungId, Long caTiemChungId, int tinhTrangXacNhan) {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<PhieuHenTiem> phieuTiemChungRoot = cq.from(PhieuHenTiem.class);

		cq.select(cb.count(phieuTiemChungRoot));

		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(lichTiemChungId != null && lichTiemChungId > 0) {
			predicates.add(cb.equal(phieuTiemChungRoot.get("lichTiemChungId"), lichTiemChungId));
		}
		
		if(caTiemChungId != null && caTiemChungId > 0) {
			predicates.add(cb.equal(phieuTiemChungRoot.get("caTiemChungId"), caTiemChungId));
		}
		
		predicates.add(cb.equal(phieuTiemChungRoot.get("tinhTrangXacNhan"), tinhTrangXacNhan));

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
	public List<PhieuHenTiem> searchPhieuHenTiem(Long lichTiemChungId, Long caTiemChungId, int tinhTrangXacNhan,
			Integer page, Integer size) {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<PhieuHenTiem> cq = cb.createQuery(PhieuHenTiem.class);

		Root<PhieuHenTiem> phieuTiemChungRoot = cq.from(PhieuHenTiem.class);

		cq.select(phieuTiemChungRoot);

		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(lichTiemChungId != null && lichTiemChungId > 0) {
			predicates.add(cb.equal(phieuTiemChungRoot.get("lichTiemChungId"), lichTiemChungId));
		}
		
		if(caTiemChungId != null && caTiemChungId > 0) {
			predicates.add(cb.equal(phieuTiemChungRoot.get("caTiemChungId"), caTiemChungId));
		}
		
		predicates.add(cb.equal(phieuTiemChungRoot.get("tinhTrangXacNhan"), tinhTrangXacNhan));

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

		orderList.add(cb.asc(phieuTiemChungRoot.get("caTiemChungId")));
		
		cq.orderBy(orderList);

		TypedQuery<PhieuHenTiem> typedQuery = em.createQuery(cq);
		
		int offset = page * size;

		List<PhieuHenTiem> lstPhieuHenTiem = typedQuery.setFirstResult(offset).setMaxResults(size).getResultList();
		
		em.close();

		return lstPhieuHenTiem;
	}

	@Override
	public List<PhieuHenTiem> findByLichTiemChungID(long lichTiemChungId) {
		return phieuHenTiemRepository.findByLichTiemChungID(lichTiemChungId);
	}

	@Override
	public List<PhieuHenTiem> findByTinhTrangXacNhan_LichTiemChungID(int tinhTrangXacNhan, long LichTiemChungID) {
		return phieuHenTiemRepository.findByTinhTrangXacNhan_LichTiemChungID(tinhTrangXacNhan, LichTiemChungID);
	}

	@Override
	public List<PhieuHenTiem> findByKhacTinhTrangXacNhan_LichTiemChungID(int tinhTrangXacNhan, long LichTiemChungID) {
		return phieuHenTiemRepository.findByKhacTinhTrangXacNhan_LichTiemChungID(tinhTrangXacNhan, LichTiemChungID);
	}

	@Override
	public List<PhieuHenTiem> findByLichTiemChungId_TinhTrangXacNhan(long LichTiemChungID, int TinhTrangXacNhan) {
		return phieuHenTiemRepository.findByLichTiemChungId_TinhTrangXacNhan(LichTiemChungID, TinhTrangXacNhan);
	}

	@Override
	public PhieuHenTiem update(PhieuHenTiem phieuHenTiem) {
		return phieuHenTiemRepository.save(phieuHenTiem);
	}

}
