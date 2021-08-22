package org.vaccom.vcmgt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.constant.DBConstant;
import org.vaccom.vcmgt.entity.DanToc;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface DanTocRepository extends JpaRepository<DanToc, Long> {
	public DanToc findByDanTocMa(String danTocMa);

	@Query(value = "SELECT * FROM " + DBConstant._DANTOC_TABLE_NAME, nativeQuery = true)
	public List<DanToc> findAll();
}
