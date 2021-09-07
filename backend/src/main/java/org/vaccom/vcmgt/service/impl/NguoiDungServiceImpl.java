package org.vaccom.vcmgt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.KhoaDangKy;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.repository.KhoaDangKyRepository;
import org.vaccom.vcmgt.repository.KhoaTruyCapRepository;
import org.vaccom.vcmgt.repository.NguoiDungRepository;
import org.vaccom.vcmgt.service.NguoiDungService;

@Service
@Transactional
public class NguoiDungServiceImpl implements NguoiDungService {

	@Autowired
	private EntityManager em;

	@Autowired
	private NguoiDungRepository nguoiDungRepository;

	@Autowired
	private KhoaDangKyRepository khoaDangKyRepository;

	@Autowired
	private KhoaTruyCapRepository khoaTruyCapRepository;

	@Override
	public NguoiDung updateNguoiDung(NguoiDung nguoiDung) {
		return nguoiDungRepository.save(nguoiDung);
	}

	@Override
	@Transactional
	public NguoiDung addNguoiDung(NguoiDung nguoiDung, KhoaDangKy khoaDangKy) {
		nguoiDung = nguoiDungRepository.save(nguoiDung);
		khoaDangKy.setNguoiDungID(nguoiDung.getId());
		khoaDangKyRepository.save(khoaDangKy);
		return nguoiDung;
	}
	
	@Override
	@Transactional
	public NguoiDung updateNguoiDung(NguoiDung nguoiDung, KhoaDangKy khoaDangKy) {
		khoaDangKyRepository.save(khoaDangKy);
		return nguoiDungRepository.save(nguoiDung);
	}

	@Override
	public NguoiDung findByNguoiTiemChungId(long nguoiTiemChungId) {
		return nguoiDungRepository.findByNguoiTiemChungId(nguoiTiemChungId);
	}

	@Override
	@Transactional
	public void deleteNguoiDung(long nguoiDungId, long khoaDangKyId, long khoaTruyCapId) throws Exception {
		nguoiDungRepository.deleteById(nguoiDungId);

		if (khoaDangKyId > 0) {
			khoaDangKyRepository.deleteById(khoaDangKyId);
		}

		if (khoaTruyCapId > 0) {
			khoaTruyCapRepository.deleteById(khoaTruyCapId);
		}
	}

	@Override
	public long countAll() {
		return nguoiDungRepository.count();
	}

	@Override
	public void deleteNguoiDung(long id) throws Exception {
		nguoiDungRepository.deleteById(id);
	}

	@Override
	public NguoiDung findByID(long id) {
		return nguoiDungRepository.findById(id);
	}

	@Override
	public NguoiDung findByTenDanNhap(String tenDangNhap) {

		return nguoiDungRepository.findByTenDangNhap(tenDangNhap);
	}

	@Override
	public List<NguoiDung> findAll(int page, int size) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<NguoiDung> root = cq.from(NguoiDung.class);
		cq.multiselect(root.get("id"), root.get("tenDangNhap"), root.get("hoVaTen"), root.get("chucDanh"),
				root.get("soDienThoai"), root.get("email"), root.get("diaBanCoSoId"), root.get("coSoYTeId"),
				root.get("quanTriHeThong"), root.get("khoaTaiKhoan"), root.get("nguoiTiemChungId"), root.get("uyBanNhanDanId"))
				.orderBy(cb.asc(root.get("id")));
		
		int offset = page * size;

		List<Tuple> result = em.createQuery(cq).setFirstResult(offset).setMaxResults(size).getResultList();

		List<NguoiDung> lstNguoiDung = new ArrayList<NguoiDung>();

		result.forEach(tuple -> {

			NguoiDung nguoiDung = new NguoiDung();
			nguoiDung.setId(tuple.get(0, Long.class));
			nguoiDung.setChucDanh(tuple.get(3, String.class));
			nguoiDung.setCoSoYTeId(tuple.get(7, Long.class));
			nguoiDung.setDiaBanCoSoId(tuple.get(6, Long.class));
			nguoiDung.setEmail(tuple.get(5, String.class));
			nguoiDung.setHoVaTen(tuple.get(2, String.class));
			nguoiDung.setKhoaTaiKhoan(tuple.get(9, Boolean.class));
			nguoiDung.setNguoiTiemChungId(tuple.get(10, Long.class));
			nguoiDung.setQuanTriHeThong(tuple.get(8, Integer.class));
			nguoiDung.setSoDienThoai(tuple.get(4, String.class));
			nguoiDung.setTenDangNhap(tuple.get(1, String.class));
			nguoiDung.setUyBanNhanDanId(tuple.get(11, Long.class));

			lstNguoiDung.add(nguoiDung);

		});
		return lstNguoiDung;
	}

	@Override
	public int countByDiaBanCoSoId(long id) {
		return nguoiDungRepository.countByDiaBanCoSoId(id);
	}
}
