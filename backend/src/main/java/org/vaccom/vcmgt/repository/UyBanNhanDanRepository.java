package org.vaccom.vcmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaccom.vcmgt.entity.PhuongXa;
import org.vaccom.vcmgt.entity.UyBanNhanDan;

@Repository
public interface UyBanNhanDanRepository extends JpaRepository<UyBanNhanDan, Long> {
    public UyBanNhanDan findById(long id);
}
