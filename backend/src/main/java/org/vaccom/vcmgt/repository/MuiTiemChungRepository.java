package org.vaccom.vcmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.CoSoYTe;
import org.vaccom.vcmgt.entity.MuiTiemChung;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface MuiTiemChungRepository extends JpaRepository<MuiTiemChung, Long>{

}
