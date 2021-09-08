package org.vaccom.vcmgt.action;

import org.vaccom.vcmgt.entity.ThuocTiem;

import java.util.List;

public interface ThuocTiemAction {
    public List<ThuocTiem> findAll();
    public long count();
}
