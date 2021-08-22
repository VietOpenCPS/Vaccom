package org.vaccom.vcmgt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.QuanHuyen;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface QuanHuyenRepository extends JpaRepository<QuanHuyen, Long> {
	public QuanHuyen findByQuanHuyenMa(String quanHuyenMa);

	public List<QuanHuyen> findByTinhThanhID(long tinhThanhID);
}
