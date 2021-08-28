package org.vaccom.vcmgt.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.LichTiemChung;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface LichTiemChungRepository extends JpaRepository<LichTiemChung, Long> {
	
	public long countByCoSoYTeId(long id);
	
	public LichTiemChung findById(long id);

	public List<LichTiemChung> findByCoSoYTeId(long id);
	
	public Page<LichTiemChung> findByCoSoYTeId(long id, Pageable pageable);
	
}
