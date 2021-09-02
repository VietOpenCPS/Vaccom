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
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.repository.NguoiTiemChungRepository;
import org.vaccom.vcmgt.service.NguoiTiemChungService;

import com.liferay.portal.kernel.util.Validator;

@Service
public class NguoiTiemChungServiceImpl implements NguoiTiemChungService {
	@Autowired
	private NguoiTiemChungRepository nguoiTiemChungRepository;

	@Autowired
	private EntityManager em;

	@Override
	public long countByCmtcccd(String cmtcccd) {
		return nguoiTiemChungRepository.countByCmtcccd(cmtcccd);
	}
	
	@Override
	public long countByCmtcccd(String cmtcccd, int tinhTrangDangKy) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<NguoiTiemChung> nguoiTiemChungRoot = cq.from(NguoiTiemChung.class);

		cq.select(cb.count(nguoiTiemChungRoot));

		List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(cb.equal(nguoiTiemChungRoot.get("cmtcccd"), cmtcccd));
		
		predicates.add(cb.equal(nguoiTiemChungRoot.get("tinhTrangDangKi"), tinhTrangDangKy));
		

		if (!predicates.isEmpty()) {
			Predicate[] pdc = new Predicate[predicates.size()];
			int count = 0;
			for (Predicate predicate : predicates) {
				pdc[count] = predicate;
				count++;
			}
			cq.where(pdc);
		}

		TypedQuery<Long> typedQuery = em.createQuery(cq);
		
		em.close();

		return typedQuery.getSingleResult();
	}

	@Override
	public long countByDiaBanCoSoId(long id) {
		return nguoiTiemChungRepository.countByDiaBanCoSoId(id);
	}

	@Override
	public long countAll() {
		return nguoiTiemChungRepository.count();
	}

	@Override
	public List<NguoiTiemChung> findByCmtcccd(String cmtcccd) {

		return nguoiTiemChungRepository.findByCmtcccd(cmtcccd);
	}

	@Override
	public NguoiTiemChung findById(long id) {

		return nguoiTiemChungRepository.findById(id);
	}

	@Override
	public NguoiTiemChung findByMaQR(String MaQR) {
		return nguoiTiemChungRepository.findByMaQR(MaQR);
	}

	@Override
	public NguoiTiemChung updateNguoiTiemChung(NguoiTiemChung nguoiTiemChung) {
		return nguoiTiemChungRepository.save(nguoiTiemChung);
	}

	@Override
	public NguoiTiemChung updateNguoiTiemChung(NguoiTiemChung nguoiTiemChung, boolean createAccount) {
		nguoiTiemChung = nguoiTiemChungRepository.save(nguoiTiemChung);
		if (createAccount) {

		}
		return nguoiTiemChung;
	}

	@Override
	public void deleteNguoiTiemChung(long id) throws Exception {
		nguoiTiemChungRepository.deleteById(id);
	}

	@Override
	public List<NguoiTiemChung> searchNguoiTiemChung(int page, int size) {
		if (page < 0 || size < 0) {
			page = 0;
			size = 30;
		}
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<NguoiTiemChung> pases = nguoiTiemChungRepository.findAll(pageable);
		return pases.getContent();

	}

	@Override
	public long countNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki, String hovaten,
			Long diabancosoid, String cosoytema, Integer tinhtrangdangki, Integer kiemtratrung) {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<NguoiTiemChung> nguoiTiemChungRoot = cq.from(NguoiTiemChung.class);

		cq.select(cb.count(nguoiTiemChungRoot));

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (Validator.isNotNull(cmtcccd)) {
			// ParameterExpression<String> p = cb.parameter(String.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("cmtcccd"), cmtcccd));
		}

		if (nhomdoituong != null && nhomdoituong >= 0) {
			// ParameterExpression<Integer> p = cb.parameter(Integer.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), nhomdoituong));
		}

		if (Validator.isNotNull(ngaydangki)) {
			// ParameterExpression<String> p = cb.parameter(String.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("ngayDangKi"), ngaydangki));
		}

		if (Validator.isNotNull(hovaten)) {
			// ParameterExpression<String> p = cb.parameter(String.class);
			predicates.add(cb.like(nguoiTiemChungRoot.get("hoVaTen"), "%" + hovaten + "%"));
		}

		if (diabancosoid != null && diabancosoid >= 0) {
			// ParameterExpression<Long> p = cb.parameter(Long.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("diaBanCoSoId"), diabancosoid));
		}

		if (Validator.isNotNull(cosoytema)) {
			// ParameterExpression<String> p = cb.parameter(String.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("coSoYTeMa"), cosoytema));
		}

		if (nhomdoituong != null && nhomdoituong >= 0) {
			// ParameterExpression<Integer> p = cb.parameter(Integer.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), nhomdoituong));
		}

		if (tinhtrangdangki != null && tinhtrangdangki >= 0) {
			// ParameterExpression<Integer> p = cb.parameter(Integer.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("tinhTrangDangKi"), tinhtrangdangki));
		}

		if (kiemtratrung != null && kiemtratrung >= 0) {
			// ParameterExpression<Integer> p = cb.parameter(Integer.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("kiemTraTrung"), kiemtratrung));
		}

		if (!predicates.isEmpty()) {
			Predicate[] pdc = new Predicate[predicates.size()];
			int count = 0;
			for (Predicate predicate : predicates) {
				pdc[count] = predicate;
				count++;
			}
			cq.where(pdc);
		}

		TypedQuery<Long> typedQuery = em.createQuery(cq);
		
		em.close();

		return typedQuery.getSingleResult();
	}

	@Override
	public List<NguoiTiemChung> searchNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki,
			String hovaten, Long diabancosoid, String cosoytema, Integer tinhtrangdangki, Integer kiemtratrung,
			Integer page, Integer size) {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<NguoiTiemChung> cq = cb.createQuery(NguoiTiemChung.class);

		Root<NguoiTiemChung> nguoiTiemChungRoot = cq.from(NguoiTiemChung.class);

		cq.select(nguoiTiemChungRoot);

		// Predicate[] predicates = new Predicate[] {};
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (Validator.isNotNull(cmtcccd)) {
			// ParameterExpression<String> p = cb.parameter(String.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("cmtcccd"), cmtcccd));
		}

		if (nhomdoituong != null && nhomdoituong >= 0) {
			// ParameterExpression<Integer> p = cb.parameter(Integer.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), nhomdoituong));
		}

		if (Validator.isNotNull(ngaydangki)) {
			// ParameterExpression<String> p = cb.parameter(String.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("ngayDangKi"), ngaydangki));
		}

		if (Validator.isNotNull(hovaten)) {
			// ParameterExpression<String> p = cb.parameter(String.class);
			predicates.add(cb.like(nguoiTiemChungRoot.get("hoVaTen"), "%" + hovaten + "%"));
		}

		if (diabancosoid != null && diabancosoid >= 0) {
			// ParameterExpression<Long> p = cb.parameter(Long.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("diaBanCoSoId"), diabancosoid));
		}

		if (Validator.isNotNull(cosoytema)) {
			// ParameterExpression<String> p = cb.parameter(String.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("coSoYTeMa"), cosoytema));
		}

		if (nhomdoituong != null && nhomdoituong >= 0) {
			// ParameterExpression<Integer> p = cb.parameter(Integer.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), nhomdoituong));
		}

		if (tinhtrangdangki != null && tinhtrangdangki >= 0) {
			// ParameterExpression<Integer> p = cb.parameter(Integer.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("tinhTrangDangKi"), tinhtrangdangki));
		}

		if (kiemtratrung != null && kiemtratrung >= 0) {
			// ParameterExpression<Integer> p = cb.parameter(Integer.class);
			predicates.add(cb.equal(nguoiTiemChungRoot.get("kiemTraTrung"), kiemtratrung));
		}

		if (!predicates.isEmpty()) {
			Predicate[] pdc = new Predicate[predicates.size()];
			int count = 0;
			for (Predicate predicate : predicates) {
				pdc[count] = predicate;
				count++;
			}
			cq.where(pdc);
		}

		List<Order> orderList = new ArrayList<Order>();

		orderList.add(cb.asc(nguoiTiemChungRoot.get("cmtcccd")));
		orderList.add(cb.asc(nguoiTiemChungRoot.get("ngayDangKi")));
		orderList.add(cb.asc(nguoiTiemChungRoot.get("diaBanCoSoId")));
		cq.orderBy(orderList);

		TypedQuery<NguoiTiemChung> typedQuery = em.createQuery(cq);

		List<NguoiTiemChung> lstNguoiTiemChung = typedQuery.setFirstResult(page).setMaxResults(size).getResultList();
		
		em.close();

		return lstNguoiTiemChung;
	}
}
