package org.vaccom.vcmgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.GiayDiDuong;
import org.vaccom.vcmgt.repository.GiayDiDuongRepository;
import org.vaccom.vcmgt.service.GiayDiDuongService;

import java.util.List;

@Service
public class GiayDiDuongServiceImpl implements GiayDiDuongService {
    @Autowired
    GiayDiDuongRepository giayDiDuongRepository;

    @Override
    public GiayDiDuong findById(long id) {
        return giayDiDuongRepository.findById(id);
    }

    @Override
    public List<GiayDiDuong> findByUyBanNhanDanID(int id, int page, int size) {
        if (page < 0 || size < 0) {
            page = 0;
            size = 30;
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        return giayDiDuongRepository.findByUyBanNhanDanID(id, pageable).getContent();
    }

    @Override
    public GiayDiDuong findByChecksum(String checksum) {
        GiayDiDuong giayDiDuong = giayDiDuongRepository.findByChecksum(checksum);
        return giayDiDuong;
    }

    @Override
    public GiayDiDuong findByMaQR(String MaQr) {
        return giayDiDuongRepository.findByMaQR(MaQr);
    }

    @Override
    public long countByUyBanNhanDanID(int id) {
        return giayDiDuongRepository.countByUyBanNhanDanID(id);
    }

    @Override
    public List<GiayDiDuong> findByUyBanNhanDanIdAndStatus(int uyBanNhanDanId, int status, int page, int size) {
        if (page < 0 || size < 0) {
            page = 0;
            size = 30;
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        return giayDiDuongRepository.findByUyBanNhanDanIDAndStatus(uyBanNhanDanId, status, pageable).getContent();
    }

    @Override
    public long countByUyBanNhanDanIdAndStatus(int id, int status) {
        return giayDiDuongRepository.countByUyBanNhanDanIDAndStatus(id, status);
    }

    @Override
    public long countAll() {
        return giayDiDuongRepository.count();
    }

    @Override
    public List<GiayDiDuong> findAll(int page, int size) {
        return giayDiDuongRepository.findAll();
    }

    @Override
    public GiayDiDuong save(GiayDiDuong giayDiDuong) {
        return giayDiDuongRepository.save(giayDiDuong);
    }

    @Override
    public void delete(GiayDiDuong giayDiDuong) {
        giayDiDuongRepository.delete(giayDiDuong);
    }
}
