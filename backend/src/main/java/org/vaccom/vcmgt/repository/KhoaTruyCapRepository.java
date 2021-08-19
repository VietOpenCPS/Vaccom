package org.vaccom.vcmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.KhoaTruyCap;

/**
 * @author vaccom
 *
 */
@Repository
@Transactional
public interface KhoaTruyCapRepository extends JpaRepository<KhoaTruyCap, Long> {
	
	public KhoaTruyCap findByDoiTuong(String doiTuong);

	public KhoaTruyCap findByKhoaTruyCap(String khoaTruyCap);

}