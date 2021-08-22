package org.vaccom.vcmgt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.PhuongXa;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface PhuongXaRepository extends JpaRepository<PhuongXa, Long> {
	public PhuongXa findByPhuongXaMa(String phuongXaMa);

	public List<PhuongXa> findByQuanHuyenID(long quanHuyenID);
}
