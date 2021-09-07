package org.vaccom.vcmgt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaccom.vcmgt.entity.GiayDiDuong;

import java.util.List;

@Repository
public interface GiayDiDuongRepository extends JpaRepository<GiayDiDuong, Long> {
    //Read
    public GiayDiDuong findById(long id);

    public List<GiayDiDuong> findByCmtcccd(String cmt);

    public Page<GiayDiDuong> findByUyBanNhanDanID(int id, Pageable pageable);

    public Page<GiayDiDuong> findByUyBanNhanDanIDAndStatus(int id, int status, Pageable pageable);

    public GiayDiDuong findByChecksum(String checksum);

    public GiayDiDuong findByMaQR(String maQr);

    public long countByCmtcccd(String cmt);

    public long countByUyBanNhanDanID(int id);

    public long countByUyBanNhanDanIDAndStatus(int id, int status);
}
