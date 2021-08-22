package org.vaccom.vcmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.NguoiTiemChung;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface NguoiTiemChungRepository extends JpaRepository<NguoiTiemChung, Long> {
	
	public long countByDiaBanCoSoId(long id);

	public NguoiTiemChung findById(long id);

	public NguoiTiemChung findByCmtcccd(String cmtcccd);
}
