package org.vaccom.vcmgt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.constant.DBConstant;
import org.vaccom.vcmgt.entity.QuocGia;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface QuocGiaRepository extends JpaRepository<QuocGia, Long>{
	public QuocGia findByQuocGiaMa(String quocGiaMa);

	@Query(value = "SELECT * FROM " + DBConstant._QUOCGIA_TABLE_NAME, nativeQuery = true)
	public List<QuocGia> findAll();
}	
