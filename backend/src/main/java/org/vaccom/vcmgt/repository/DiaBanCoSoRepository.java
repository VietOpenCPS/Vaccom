package org.vaccom.vcmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.CoSoYTe;
import org.vaccom.vcmgt.entity.DiaBanCoSo;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface DiaBanCoSoRepository extends JpaRepository<DiaBanCoSo, Long>{

}
