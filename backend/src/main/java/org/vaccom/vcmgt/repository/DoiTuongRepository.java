package org.vaccom.vcmgt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.constant.DBConstant;
import org.vaccom.vcmgt.entity.DoiTuong;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface DoiTuongRepository extends JpaRepository<DoiTuong, Long>{
	public DoiTuong findByDoiTuongMa(String quocGiaMa);

	@Query(value = "SELECT * FROM " + DBConstant._DOITUONG_TABLE_NAME, nativeQuery = true)
	public List<DoiTuong> findAll();
}	
