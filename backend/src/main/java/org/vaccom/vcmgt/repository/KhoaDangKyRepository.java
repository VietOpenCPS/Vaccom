package org.vaccom.vcmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.KhoaDangKy;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface KhoaDangKyRepository extends JpaRepository<KhoaDangKy, Long> {

	public KhoaDangKy findByNguoiDungID(long nguoiDungID);

}