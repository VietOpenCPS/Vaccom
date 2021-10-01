package org.vaccom.vcmgt.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.constant.DBConstant;
import org.vaccom.vcmgt.entity.MuiTiemChung;
/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface MuiTiemChungRepository extends JpaRepository<MuiTiemChung, Long> {
	
	public long countByCoSoYTeId(long id);

	public long countBycongDanID(long id);

	public MuiTiemChung findById(long id);

	public List<MuiTiemChung> findByCmtcccd(String cmtcccd);

	public Page<MuiTiemChung> findByCoSoYTeId(long id, Pageable pageable);

	public List<MuiTiemChung> findByCongDanID(long id);

	@Query(value="SELECT * FROM " + DBConstant._MUITIEMCHUNG_TABLE_NAME + " WHERE loaiThuocTiem like %:loaiThuocTiem%", nativeQuery = true)
	public List<MuiTiemChung> findByLoaiThuocTiem(@Param("loaiThuocTiem") String loaiThuocTiem);

	@Query(value="SELECT * FROM t_muitiemchung tn WHERE (CongDan_ID,LanTiem) IN ( SELECT CongDan_ID, MAX(LanTiem) FROM t_muitiemchung GROUP BY CongDan_ID ) and LoaiThuocTiem LIKE :loaiThuocTiem and DATEDIFF((select current_date()), (SELECT STR_TO_DATE(NgayTiemChung , '%d/%m/%Y'))) > :soNgayTiem", nativeQuery = true)
	public List<MuiTiemChung> findMuiTiemChungDatDieuKien(@Param("loaiThuocTiem") String loaiThuocTiem,@Param("soNgayTiem") int soNgayTiem);

	@Query(value="SELECT * FROM t_muitiemchung tn WHERE (CongDan_ID,LanTiem) IN ( SELECT CongDan_ID, MAX(LanTiem) FROM t_muitiemchung GROUP BY CongDan_ID ) and LoaiThuocTiem LIKE :loaiThuocTiem", nativeQuery = true)
	public List<MuiTiemChung> findMuiTiemChungDatDieuKien(@Param("loaiThuocTiem") String loaiThuocTiem);
}
