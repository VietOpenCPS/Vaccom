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
	
	public long countByCoSoYTeId(long id);
<<<<<<< HEAD

	public long countBycongDanID(long id);

	public MuiTiemChung findById(long id);

	public List<MuiTiemChung> findByCmtcccd(String cmtcccd);

	public List<MuiTiemChung> findBycongDanID(long id);
	
=======
	
//	public long countByNguoiTiemChungId(long id);
	
	public MuiTiemChung findById(long id);

	public List<MuiTiemChung> findByCmtcccd(String cmtcccd);
	
//	public List<MuiTiemChung> findByNguoiTiemChungId(long id);

>>>>>>> c77610a423fd162f2c6330b12ab2549c1cf645d0
	public Page<MuiTiemChung> findByCoSoYTeId(long id, Pageable pageable);

	public List<MuiTiemChung> findByCongDanID(long id);
}
