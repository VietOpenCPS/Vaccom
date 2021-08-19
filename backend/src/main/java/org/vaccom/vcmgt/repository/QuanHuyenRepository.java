package org.vaccom.vcmgt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.constant.DBConstant;
import org.vaccom.vcmgt.entity.QuanHuyen;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface QuanHuyenRepository extends JpaRepository<QuanHuyen, Long> {
	public QuanHuyen findByQuanHuyenMa(String quanHuyenMa);

	@Query(value = "SELECT * FROM " + DBConstant._QUANHUYEN_TABLE_NAME
			+ " q WHERE q.QuanHuyenMa = ?1 AND q.TinhThanhID = ?2", nativeQuery = true)
	public List<QuanHuyen> findByQuanHuyenMa_TinhThanhID(String quanHuyenMa, long tinhThanhID);
}
