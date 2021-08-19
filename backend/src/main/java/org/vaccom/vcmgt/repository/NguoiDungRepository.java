package org.vaccom.vcmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.NguoiDung;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long> {

	public NguoiDung findByID(long id);

	public NguoiDung findByTenDangNhap(String tenDangNhap);

	public NguoiDung findByKhoaTaiKhoan(boolean khoaTaiKhoan);

	public NguoiDung findByQuanTriHeThong(String quanTriHeThong);

}