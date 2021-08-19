package org.vaccom.vcmgt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.constant.DBConstant;
import org.vaccom.vcmgt.entity.PhuongXa;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface PhuongXaRepository extends JpaRepository<PhuongXa, Long> {
	public PhuongXa findByPhuongXaMa(String phuongXaMa);

	@Query(value = "SELECT * FROM " + DBConstant._PHUONGXA_TABLE_NAME
			+ " p WHERE p.PhuongXaMa = ?1 AND q.QuanHuyenID = ?2", nativeQuery = true)
	public List<PhuongXa> findByPhuongXaMa_QuanHuyenID(String phuongXaMa, long quanHuyenID);
}
