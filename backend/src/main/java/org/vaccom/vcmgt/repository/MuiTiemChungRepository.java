package org.vaccom.vcmgt.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.MuiTiemChung;
/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface MuiTiemChungRepository extends JpaRepository<MuiTiemChung, Long> {
	
	public MuiTiemChung findById(long id);

	public List<MuiTiemChung> findByCmtcccd(String cmtcccd);

	public List<MuiTiemChung> findByCoSoYTeMa(String coSoYTeMa);
	
	public Page<MuiTiemChung> findByCoSoYTeMa(String coSoYTeMa, Pageable pageable);
}
