package org.vaccom.vcmgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.UyBanNhanDan;
import org.vaccom.vcmgt.repository.UyBanNhanDanRepository;
import org.vaccom.vcmgt.service.UyBanNhanDanService;

import java.util.List;

@Service
public class UyBanNhanDanServiceImpl implements UyBanNhanDanService {

    @Autowired
    UyBanNhanDanRepository uyBanNhanDanRepository;

    @Override
    public UyBanNhanDan findById(long id) {
        return uyBanNhanDanRepository.findById(id);
    }

    @Override
    public List<UyBanNhanDan> findAll(int page, int size) {
        if (page < 0 || size < 0) {
            page = 0;
            size = 30;
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        return uyBanNhanDanRepository.findAll(pageable).getContent();
    }

    @Override
    public long countAll() {
        return uyBanNhanDanRepository.count();
    }

    @Override
    public UyBanNhanDan save(UyBanNhanDan uyBanNhanDan) {
        return uyBanNhanDanRepository.save(uyBanNhanDan);
    }

    @Override
    public void delete(UyBanNhanDan uyBanNhanDan) {
        uyBanNhanDanRepository.delete(uyBanNhanDan);
    }

    @Override
    public List<UyBanNhanDan> findByNotificationConfigExceptNull() {
        return uyBanNhanDanRepository.findByNotificationConfigExceptNull();
    }
}
