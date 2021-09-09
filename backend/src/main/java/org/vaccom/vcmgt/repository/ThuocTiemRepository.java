package org.vaccom.vcmgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaccom.vcmgt.entity.ThuocTiem;
import org.vaccom.vcmgt.entity.TinhThanh;

@Repository
public interface ThuocTiemRepository extends JpaRepository<ThuocTiem, Long> {

}
