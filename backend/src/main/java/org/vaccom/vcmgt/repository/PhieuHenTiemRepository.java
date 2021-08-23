package org.vaccom.vcmgt.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.PhieuHenTiem;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface PhieuHenTiemRepository extends JpaRepository<PhieuHenTiem, Long> {
	public PhieuHenTiem findById(long id);

	public int countByLichTiemChungId(long id);

	public Page<PhieuHenTiem> findByLichTiemChungId(long id, Pageable pageable);

	public List<PhieuHenTiem> findByNguoiTiemChungId(long id);
}
