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

    @Query(value="SELECT * FROM " + DBConstant._HANGCHO_THONGBAO_TABLE_NAME + " WHERE status = :status", nativeQuery = true)
    List<HangChoThongBao> findByStatus(@Param("status") long status);

    @Query(value="SELECT * FROM " + DBConstant._HANGCHO_THONGBAO_TABLE_NAME + " WHERE isSent = :isSent and isReady = :isReady", nativeQuery = true)
    List<HangChoThongBao> findByIsSentIsReady(@Param("isSent") Boolean isSent, @Param("isReady") Boolean isReady);

    @Query(value="SELECT * FROM " + DBConstant._HANGCHO_THONGBAO_TABLE_NAME + " WHERE isSent = :isSent and LoaiThongBao = :loaiThongBao", nativeQuery = true)
    HangChoThongBao findByIsSentTypeThongBao(@Param("isSent") Boolean isSent, @Param("loaiThongBao") String loaiThongBao);


    @Query(value="SELECT * FROM " + DBConstant._HANGCHO_THONGBAO_TABLE_NAME + " WHERE status = :status and LoaiThongBao = :loaiThongBao and UyBanNhanDanID = :uyBanNhanDanId", nativeQuery = true)
    List<HangChoThongBao> findByStatus_LoaiThongBao(@Param("status") long status, @Param("loaiThongBao") String loaiThongBao, @Param("uyBanNhanDanId") long uyBanNhanDanId);


    @Query(value="SELECT * FROM " + DBConstant._HANGCHO_THONGBAO_TABLE_NAME + " WHERE toTelNo = :toTelNo and LoaiThongBao = :loaiThongBao", nativeQuery = true)
    HangChoThongBao findByPhone_LoaiThongBao(@Param("toTelNo") String toTelNo, @Param("loaiThongBao") String loaiThongBao);

    @Query(value="SELECT * FROM " + DBConstant._HANGCHO_THONGBAO_TABLE_NAME + " WHERE mappingKey = :MappingKey and LoaiThongBao = :loaiThongBao", nativeQuery = true)
    HangChoThongBao findByLoaiThongBao_mappingKey(@Param("MappingKey") long mappingKey, @Param("loaiThongBao") String loaiThongBao);

    @Query(value="SELECT * FROM " + DBConstant._HANGCHO_THONGBAO_TABLE_NAME + " WHERE isSent = :isSent and isReady = :isReady and DATE(createDate) = :createDate", nativeQuery = true)
    List<HangChoThongBao> findByIsSentIsReadyCreateDate(@Param("isSent") Boolean isSent, @Param("isReady") Boolean isReady, @Param("createDate") String createDate);

}
