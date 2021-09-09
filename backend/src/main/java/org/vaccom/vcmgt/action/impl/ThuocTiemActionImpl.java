package org.vaccom.vcmgt.action.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.ThuocTiemAction;
import org.vaccom.vcmgt.entity.ThuocTiem;
import org.vaccom.vcmgt.repository.ThuocTiemRepository;

import java.util.List;

@Service
public class ThuocTiemActionImpl implements ThuocTiemAction {
    @Autowired
    ThuocTiemRepository thuocTiemRepository;

    @Override
    public List<ThuocTiem> findAll() {
        return thuocTiemRepository.findAll();
    }

    @Override
    public long count() {
        return thuocTiemRepository.count();
    }
}
