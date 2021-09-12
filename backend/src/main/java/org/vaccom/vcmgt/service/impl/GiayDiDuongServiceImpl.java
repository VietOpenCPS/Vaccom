package org.vaccom.vcmgt.service.impl;

import com.liferay.portal.kernel.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.dto.GiayDiDuongDto;
import org.vaccom.vcmgt.dto.ResultSearchDto;
import org.vaccom.vcmgt.entity.GiayDiDuong;
import org.vaccom.vcmgt.repository.GiayDiDuongRepository;
import org.vaccom.vcmgt.service.GiayDiDuongService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class GiayDiDuongServiceImpl implements GiayDiDuongService {
    @Autowired
    GiayDiDuongRepository giayDiDuongRepository;

    @Autowired
    private EntityManager em;
    
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

    @Override
    public ResultSearchDto<GiayDiDuong> search(GiayDiDuongDto giayDiDuongDto) {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<GiayDiDuong> criteriaQuery = builder.createQuery(GiayDiDuong.class);

        Root<GiayDiDuong> giayDiDuongRoot = criteriaQuery.from(GiayDiDuong.class);

        criteriaQuery.select(giayDiDuongRoot);

        List<Predicate> predicates = new ArrayList<Predicate>();


        if (Validator.isNotNull(giayDiDuongDto.cmtcccd) && !giayDiDuongDto.cmtcccd.isEmpty()) {
            predicates.add(builder.equal(giayDiDuongRoot.get("cmtcccd"), giayDiDuongDto.cmtcccd));
        }

        if (Validator.isNotNull(giayDiDuongDto.hoVaTen) && !giayDiDuongDto.hoVaTen.isEmpty()) {
            predicates.add(builder.like(giayDiDuongRoot.get("hoVaTen"), "%" + giayDiDuongDto.hoVaTen + "%"));
        }

        if (Validator.isNotNull(giayDiDuongDto.noiCtTenCoQuan) && !giayDiDuongDto.noiCtTenCoQuan.isEmpty()) {
            predicates.add(builder.like(giayDiDuongRoot.get("noiCtTenCoQuan"), "%" + giayDiDuongDto.noiCtTenCoQuan + "%"));
        }

        if(giayDiDuongDto.uyBanNhanDanID > 0) {
            predicates.add(builder.equal(giayDiDuongRoot.get("uyBanNhanDanID"), giayDiDuongDto.uyBanNhanDanID));
        }

        if(giayDiDuongDto.status >= 0) {
            predicates.add(builder.equal(giayDiDuongRoot.get("status"), giayDiDuongDto.status));
        }

        if (!predicates.isEmpty()) {
            Predicate[] pdc = new Predicate[predicates.size()];
            int count = 0;
            for (Predicate predicate : predicates) {
                pdc[count] = predicate;
                count++;
            }

            Predicate allPredicate = builder.and(pdc);
                    criteriaQuery.where(allPredicate);
        }

        TypedQuery<GiayDiDuong> typedQuery = em.createQuery(criteriaQuery);

        long total = typedQuery.getResultList().size();
        List<GiayDiDuong> lstGiayDiDuong;

        if(giayDiDuongDto.size == -1) {
            lstGiayDiDuong = typedQuery.getResultList();
        } else {
            int offset = giayDiDuongDto.page * giayDiDuongDto.size;
            lstGiayDiDuong = typedQuery.setFirstResult(offset).setMaxResults(giayDiDuongDto.size).getResultList();
        }

        em.close();

        return new ResultSearchDto<GiayDiDuong>(lstGiayDiDuong, total);
    }
}
