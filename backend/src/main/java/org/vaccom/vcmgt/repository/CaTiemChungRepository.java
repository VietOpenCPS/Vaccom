package org.vaccom.vcmgt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.CaTiemChung;


/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface CaTiemChungRepository extends JpaRepository<CaTiemChung, Long> {
	
	public long countById(long id);
	public CaTiemChung findById(long id);

}
