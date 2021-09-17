package org.vaccom.vcmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.constant.DBConstant;
import org.vaccom.vcmgt.entity.CaTiemChung;
import org.vaccom.vcmgt.entity.CongDan;
import org.vaccom.vcmgt.entity.HangChoThongBao;

import java.util.List;

@Repository
@Transactional
public interface CongDanRepository extends JpaRepository<CongDan, Long> {


    @Query(value="SELECT * FROM " + DBConstant._CONGDAN_TABLE_NAME + " WHERE id = :ID", nativeQuery = true)
    CongDan findByCongDanId(@Param("ID") long ID);
}
