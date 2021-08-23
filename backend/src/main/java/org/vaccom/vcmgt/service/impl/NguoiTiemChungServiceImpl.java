package org.vaccom.vcmgt.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
	public long countAll() {
		return nguoiTiemChungRepository.count();
	}

	@Override
	public NguoiTiemChung findByCmtcccd(String cmtcccd) {

		return nguoiTiemChungRepository.findByCmtcccd(cmtcccd);
	}

	@Override
	public NguoiTiemChung findById(long id) {

		return nguoiTiemChungRepository.findById(id);
	}

	@Override
	public NguoiTiemChung updateNguoiTiemChung(NguoiTiemChung nguoiTiemChung) {

		return nguoiTiemChungRepository.save(nguoiTiemChung);
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
	public long countByDiaBanCoSoId(long id) {
		return nguoiTiemChungRepository.countByDiaBanCoSoId(id);
	}

	@Override
	public long countNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki, String hovaten,
			Long diabancosoid, String cosoytema) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<NguoiTiemChung> nguoiTiemChungRoot = cq.from(NguoiTiemChung.class);

		cq.select(cb.count(nguoiTiemChungRoot));

		if (Validator.isNotNull(cmtcccd)) {
			cq.where(cb.equal(nguoiTiemChungRoot.get("cmtcccd"), cmtcccd));
		}

		if (nhomdoituong != null) {
			cq.where(cb.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), nhomdoituong));
		}

		if (Validator.isNotNull(ngaydangki)) {
			cq.where(cb.equal(nguoiTiemChungRoot.get("ngayDangKi"), ngaydangki));
		}

		if (Validator.isNotNull(hovaten)) {
			cq.where(cb.like(nguoiTiemChungRoot.get("hoVaTen"), hovaten));
		}

		if (diabancosoid != null) {
			cq.where(cb.equal(nguoiTiemChungRoot.get("diaBanCoSoId"), diabancosoid));
		}

		if (Validator.isNotNull(cosoytema)) {
			cq.where(cb.equal(nguoiTiemChungRoot.get("coSoYTeMa"), cosoytema));
		}

		TypedQuery<Long> typedQuery = em.createQuery(cq);

		return typedQuery.getSingleResult();
	}

	@Override
	public List<NguoiTiemChung> searchNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki,
			String hovaten, Long diabancosoid, String cosoytema, Integer page, Integer size) {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<NguoiTiemChung> cq = cb.createQuery(NguoiTiemChung.class);

		Root<NguoiTiemChung> nguoiTiemChungRoot = cq.from(NguoiTiemChung.class);
		cq.select(nguoiTiemChungRoot);

		if (Validator.isNotNull(cmtcccd)) {
			cq.where(cb.equal(nguoiTiemChungRoot.get("cmtcccd"), cmtcccd));
		}

		if (nhomdoituong != null) {
			cq.where(cb.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), nhomdoituong));
		}

		if (Validator.isNotNull(ngaydangki)) {
			cq.where(cb.equal(nguoiTiemChungRoot.get("ngayDangKi"), ngaydangki));
		}

		if (Validator.isNotNull(hovaten)) {
			cq.where(cb.like(nguoiTiemChungRoot.get("hoVaTen"), hovaten));
		}

		if (diabancosoid != null) {
			cq.where(cb.equal(nguoiTiemChungRoot.get("diaBanCoSoId"), diabancosoid));
		}

		if (Validator.isNotNull(cosoytema)) {
			cq.where(cb.equal(nguoiTiemChungRoot.get("coSoYTeMa"), cosoytema));
		}

		TypedQuery<NguoiTiemChung> typedQuery = em.createQuery(cq);

		List<NguoiTiemChung> lstNguoiTiemChung = typedQuery.setFirstResult(page).setMaxResults(size).getResultList();

		return lstNguoiTiemChung;
	}
}
