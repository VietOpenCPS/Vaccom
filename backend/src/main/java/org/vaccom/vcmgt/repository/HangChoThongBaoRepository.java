package org.vaccom.vcmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.constant.DBConstant;
import org.vaccom.vcmgt.entity.HangChoThongBao;

import java.util.List;

@Repository
@Transactional
public interface HangChoThongBaoRepository extends JpaRepository<HangChoThongBao, Long> {

    @Query(value="SELECT * FROM " + DBConstant._HANGCHO_THONGBAO_TABLE_NAME + " WHERE isSent = :isSent", nativeQuery = true)
    List<HangChoThongBao> findByIsSent(@Param("isSent") Boolean isSent);

    @Query(value="SELECT * FROM " + DBConstant._HANGCHO_THONGBAO_TABLE_NAME + " WHERE isSent = :isSent and isReady = :isReady", nativeQuery = true)
    List<HangChoThongBao> findByIsSentIsReady(@Param("isSent") Boolean isSent, @Param("isReady") Boolean isReady);

    @Query(value="SELECT * FROM " + DBConstant._HANGCHO_THONGBAO_TABLE_NAME + " WHERE isSent = :isSent and LoaiThongBao = :loaiThongBao", nativeQuery = true)
    HangChoThongBao findByIsSentTypeThongBao(@Param("isSent") Boolean isSent, @Param("loaiThongBao") String loaiThongBao);

    @Query(value="SELECT * FROM " + DBConstant._HANGCHO_THONGBAO_TABLE_NAME + " WHERE toTelNo = :toTelNo and LoaiThongBao = :loaiThongBao", nativeQuery = true)
    HangChoThongBao findByPhone_LoaiThongBao(@Param("toTelNo") String toTelNo, @Param("loaiThongBao") String loaiThongBao);

    @Query(value="SELECT * FROM " + DBConstant._HANGCHO_THONGBAO_TABLE_NAME + " WHERE mappingKey = :mappingKey and LoaiThongBao = :loaiThongBao", nativeQuery = true)
    HangChoThongBao findByLoaiThongBao_mappingKey(@Param("mappingKey") long mappingKey, @Param("loaiThongBao") String loaiThongBao);

}
