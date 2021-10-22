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
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.entity.PhieuHenTiem;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface PhieuHenTiemRepository extends JpaRepository<PhieuHenTiem, Long> {
	public PhieuHenTiem findById(long id);

	public long countByLichTiemChungId(long id);
	
	public PhieuHenTiem findByMaQR(String maQR);

	public Page<PhieuHenTiem> findByLichTiemChungId(long id, Pageable pageable);

	public List<PhieuHenTiem> findByNguoiTiemChungId(long id);

	@Query(value="SELECT * FROM " + DBConstant._PHIEUTIEM_TABLE_NAME + " WHERE LichTiemChungID = :lichTiemChungID and NguoiTiemChungID = :nguoiTiemChungID", nativeQuery = true)
	public List<PhieuHenTiem> findExist(@Param(value = "lichTiemChungID") long lichTiemChungID, @Param(value = "nguoiTiemChungID") long nguoiTiemChungID);

	@Query(value="SELECT * FROM " + DBConstant._PHIEUTIEM_TABLE_NAME + " WHERE LichTiemChungID = :lichTiemChungID and TinhTrangXacNhan = :tinhTrangXacNhan", nativeQuery = true)
	public List<PhieuHenTiem> findByLichTiemChungId_TinhTrangXacNhan(@Param(value = "lichTiemChungID") long lichTiemChungID, @Param(value = "tinhTrangXacNhan") int tinhTrangXacNhan);

	@Query(value="SELECT * FROM " + DBConstant._PHIEUTIEM_TABLE_NAME + " WHERE LichTiemChungID = :lichTiemChungID", nativeQuery = true)
	public List<PhieuHenTiem> findByLichTiemChungID(@Param(value = "lichTiemChungID") long lichTiemChungID);

	@Query(value="SELECT * FROM " + DBConstant._PHIEUTIEM_TABLE_NAME + " WHERE TinhTrangXacNhan = :tinhTrangXacNhan AND LichTiemChungID = :lichTiemChungID", nativeQuery = true)
	public List<PhieuHenTiem> findByTinhTrangXacNhan_LichTiemChungID(@Param(value = "tinhTrangXacNhan") int tinhTrangXacNhan, @Param(value = "lichTiemChungID") long lichTiemChungID);

	@Query(value="SELECT * FROM " + DBConstant._PHIEUTIEM_TABLE_NAME + " WHERE TinhTrangXacNhan <> :tinhTrangXacNhan AND LichTiemChungID = :lichTiemChungID", nativeQuery = true)
	public List<PhieuHenTiem> findByKhacTinhTrangXacNhan_LichTiemChungID(@Param(value = "tinhTrangXacNhan") int tinhTrangXacNhan, @Param(value = "lichTiemChungID") long lichTiemChungID);

}
