package org.vaccom.vcmgt.repository;

import java.util.List;

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
	LichTiemChung findById(long id);

	List<LichTiemChung> findByCoSoYTeId(long id);
}
