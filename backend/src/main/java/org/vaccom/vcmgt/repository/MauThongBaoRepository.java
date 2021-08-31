package org.vaccom.vcmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.constant.DBConstant;
import org.vaccom.vcmgt.entity.HangChoThongBao;
import org.vaccom.vcmgt.entity.MauThongBao;

import java.util.List;

@Repository
@Transactional
public interface MauThongBaoRepository extends JpaRepository<MauThongBao, Long> {

    public void deleteById(long id);

    public MauThongBao findById(long id);

    @Query(value="SELECT * FROM " + DBConstant._MAU_THONGBAO_TABLE_NAME + " WHERE LoaiThongBao = :loaiThongBao", nativeQuery = true)
    MauThongBao findByLoaiThongBao(@Param("loaiThongBao") String loaiThongBao);

}
