package org.vaccom.vcmgt.dto;

import java.util.List;

public class ResultSearchDto<T> {
    public List<T> datas;

    public long total;

    public ResultSearchDto (List<T> list, long all) {
        datas = list;
        total = all;
    }
}
