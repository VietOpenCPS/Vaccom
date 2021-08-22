package org.vaccom.vcmgt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.DiaBanCoSo;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface DiaBanCoSoRepository extends JpaRepository<DiaBanCoSo, Long> {
	
	public int countByCoSoYTeId(long id);

	public DiaBanCoSo findById(long id);
	
	public List<DiaBanCoSo> findByCoSoYTeId(long id);
}
