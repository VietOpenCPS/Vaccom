package org.vaccom.vcmgt.action;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.CongDan;
@Service
public interface CongDanAction {
    public CongDan findByCongDanId(long id);
    public CongDan update(CongDan congDan);

    public void deleteById(long id);
}
