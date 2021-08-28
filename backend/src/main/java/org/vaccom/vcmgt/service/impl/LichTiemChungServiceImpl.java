package org.vaccom.vcmgt.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.LichTiemChung;
import org.vaccom.vcmgt.repository.LichTiemChungRepository;
import org.vaccom.vcmgt.service.LichTiemChungService;

/**
 * @author vaccom
 */
@Service
public class LichTiemChungServiceImpl implements LichTiemChungService {

    @Autowired
    private LichTiemChungRepository lichTiemChungRepository;

    @Override
    public LichTiemChung findById(long id) {

        return lichTiemChungRepository.findById(id);
    }

    @Override
    public long countAll() {
        return lichTiemChungRepository.count();
    }

    @Override
    public long countCoSoYTeId(long id) {
        return lichTiemChungRepository.countByCoSoYTeId(id);
    }

    @Override
    public LichTiemChung updateLichTiemChung(LichTiemChung lichTiemChung) {

        return lichTiemChungRepository.save(lichTiemChung);
    }

    @Override
    public void deleteById(long id) {
        lichTiemChungRepository.deleteById(id);
    }

    @Override
    public List<LichTiemChung> findAll(int page, int size) {

        if (page < 0 || size < 0) {
            page = 0;
            size = 30;
        }
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<LichTiemChung> pases = lichTiemChungRepository.findAll(pageable);
        return pases.getContent();
    }

    @Override
    public List<LichTiemChung> findByCoSoYTeId(long id) {
        return lichTiemChungRepository.findByCoSoYTeId(id);
    }

    @Override
    public List<LichTiemChung> findByCoSoYTeId(long id, int page, int size) {
        if (page < 0 || size < 0) {
            page = 0;
            size = 30;
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<LichTiemChung> pases = lichTiemChungRepository.findByCoSoYTeId(id, pageable);
        return pases.getContent();
    }

    @Override
    public Optional<LichTiemChung> findByQRCode(String qrCode) {
        return lichTiemChungRepository.findByMaQR(qrCode);
    }
}
