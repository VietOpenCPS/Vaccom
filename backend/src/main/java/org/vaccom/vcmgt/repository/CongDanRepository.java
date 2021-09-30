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
    CongDan findById(long ID);

    CongDan findBySoDienThoaiOrCmtcccd(String sdt, String cmt);

    CongDan findByHoVaTenAndSoDienThoai(String hoVaTen, String sdt);

    int countBySoDienThoaiAndCmtcccd(String sdt, String cmt);
}
