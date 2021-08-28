package org.vaccom.vcmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.CoSoYTe;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface CoSoYTeRepository extends JpaRepository<CoSoYTe, Long> {
	public CoSoYTe findById(long id);

	public CoSoYTe findByMaCoSo(String maCoSo);
}
