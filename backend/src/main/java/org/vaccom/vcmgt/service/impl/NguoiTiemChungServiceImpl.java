package org.vaccom.vcmgt.service.impl;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.dto.NguoiTiemChungDto;
import org.vaccom.vcmgt.dto.ResultSearchDto;
import org.vaccom.vcmgt.entity.CongDan;
import org.vaccom.vcmgt.entity.MuiTiemChung;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.entity.PhieuHenTiem;
import org.vaccom.vcmgt.repository.MuiTiemChungRepository;
import org.vaccom.vcmgt.repository.NguoiTiemChungRepository;
import org.vaccom.vcmgt.repository.PhieuHenTiemRepository;
import org.vaccom.vcmgt.service.MuiTiemChungService;
import org.vaccom.vcmgt.service.NguoiTiemChungService;

import com.liferay.portal.kernel.util.Validator;
import org.vaccom.vcmgt.util.DatetimeUtil;

@Service
public class NguoiTiemChungServiceImpl implements NguoiTiemChungService {
    @Autowired
    private NguoiTiemChungRepository nguoiTiemChungRepository;

    @Autowired
    private MuiTiemChungRepository muiTiemChungRepository;

    @Autowired
    private PhieuHenTiemRepository phieuHenTiemRepository;

    @Autowired
    private EntityManager em;

    @Override
    public long countByCmtcccd(String cmtcccd) {
        return nguoiTiemChungRepository.countByCmtcccd(cmtcccd);
    }

    @Override
    public long countBySoDienThoai(String sdt) {
        return nguoiTiemChungRepository.countBySoDienThoai(sdt);
    }

    @Override
    public long countByCmtcccd(String cmtcccd, int tinhTrangDangKy) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<NguoiTiemChung> nguoiTiemChungRoot = cq.from(NguoiTiemChung.class);

        cq.select(cb.count(nguoiTiemChungRoot));

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(cb.equal(nguoiTiemChungRoot.get("cmtcccd"), cmtcccd));

        predicates.add(cb.equal(nguoiTiemChungRoot.get("tinhTrangDangKi"), tinhTrangDangKy));


        if (!predicates.isEmpty()) {
            Predicate[] pdc = new Predicate[predicates.size()];
            int count = 0;
            for (Predicate predicate : predicates) {
                pdc[count] = predicate;
                count++;
            }
            Predicate allPredicate = cb.and(pdc);
            //cq.where(pdc);
            cq.where(allPredicate);
        }

        TypedQuery<Long> typedQuery = em.createQuery(cq);

        em.close();

        return typedQuery.getSingleResult();
    }

    @Override
    public long countByDiaBanCoSoId(long id) {
        return nguoiTiemChungRepository.countByDiaBanCoSoId(id);
    }

    @Override
    public long countAll() {
        return nguoiTiemChungRepository.count();
    }

    @Override
    public List<NguoiTiemChung> findByCmtcccd(String cmtcccd) {

        return nguoiTiemChungRepository.findByCmtcccd(cmtcccd);
    }

    @Override
    public NguoiTiemChung findById(long id) {

        Optional<NguoiTiemChung> result = nguoiTiemChungRepository.findById(id);
        if(result.isPresent()) {
            return result.get();
        }

        return null;
    }


    @Override
    public NguoiTiemChung findByMaQR(String MaQR) {
        return nguoiTiemChungRepository.findByMaQR(MaQR);
    }

    @Override
    public NguoiTiemChung updateNguoiTiemChung(NguoiTiemChung nguoiTiemChung) {
        return nguoiTiemChungRepository.save(nguoiTiemChung);
    }

    @Override
    public NguoiTiemChung updateNguoiTiemChung(NguoiTiemChung nguoiTiemChung, boolean createAccount) {
        nguoiTiemChung = nguoiTiemChungRepository.save(nguoiTiemChung);
        if (createAccount) {

        }
        return nguoiTiemChung;
    }

    @Override
    public void deleteNguoiTiemChung(long id) throws Exception {
        nguoiTiemChungRepository.deleteById(id);
    }

    @Override
    public List<NguoiTiemChung> searchNguoiTiemChung(int page, int size) {
        if (page < 0 || size < 0) {
            page = 0;
            size = 30;
        }
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NguoiTiemChung> pases = nguoiTiemChungRepository.findAll(pageable);
        return pases.getContent();

    }

    @Override
    public long countNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki, String hovaten,
                                    Long diabancosoid, String cosoytema, Integer tinhtrangdangki, Integer kiemtratrung, String tinhthanhma, String tinhthanhten, String quanhuyenma
            , String quanhuyenten, String phuongxama, String phuongxaten, Boolean isDatTieuChuan, String loaiThuocTiem, String diachinoio) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<NguoiTiemChung> nguoiTiemChungRoot = cq.from(NguoiTiemChung.class);

        cq.select(cb.count(nguoiTiemChungRoot));

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (Validator.isNotNull(cmtcccd)) {
            // ParameterExpression<String> p = cb.parameter(String.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("cmtcccd"), cmtcccd));
        }

        if (nhomdoituong != null && nhomdoituong >= 0) {
            // ParameterExpression<Integer> p = cb.parameter(Integer.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), nhomdoituong));
        }

        if (Validator.isNotNull(ngaydangki)) {
            // ParameterExpression<String> p = cb.parameter(String.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("ngayDangKi"), ngaydangki));
        }

        if (Validator.isNotNull(hovaten)) {
            // ParameterExpression<String> p = cb.parameter(String.class);
            predicates.add(cb.like(nguoiTiemChungRoot.get("hoVaTen"), "%" + hovaten + "%"));
        }

        if (Validator.isNotNull(tinhthanhma)) {
            predicates.add(cb.like(nguoiTiemChungRoot.get("tinhThanhMa"), "%" + tinhthanhma + "%"));
        }

        if (Validator.isNotNull(tinhthanhten)) {
            predicates.add(cb.like(nguoiTiemChungRoot.get("tinhThanhTen"), "%" + tinhthanhten + "%"));
        }

        if (Validator.isNotNull(quanhuyenma)) {
            predicates.add(cb.like(nguoiTiemChungRoot.get("quanHuyenMa"), "%" + quanhuyenma + "%"));
        }

        if (Validator.isNotNull(quanhuyenten)) {
            predicates.add(cb.like(nguoiTiemChungRoot.get("quanHuyenTen"), "%" + quanhuyenten + "%"));
        }

        if (Validator.isNotNull(phuongxama)) {
            predicates.add(cb.like(nguoiTiemChungRoot.get("phuongXaMa"), "%" + phuongxama + "%"));
        }

        if (Validator.isNotNull(phuongxaten)) {
            predicates.add(cb.like(nguoiTiemChungRoot.get("phuongXaTen"), "%" + phuongxaten + "%"));
        }

        if (diabancosoid != null && diabancosoid >= 0) {
            // ParameterExpression<Long> p = cb.parameter(Long.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("diaBanCoSoId"), diabancosoid));
        }

        if(Validator.isNotNull(diachinoio)){
            predicates.add(cb.like(nguoiTiemChungRoot.get("diaChiNoiO"), "%" + diachinoio + "%"));
        }

        if (Validator.isNotNull(cosoytema)) {
            // ParameterExpression<String> p = cb.parameter(String.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("coSoYTeMa"), cosoytema));
        }

        if (nhomdoituong != null && nhomdoituong >= 0) {
            // ParameterExpression<Integer> p = cb.parameter(Integer.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), nhomdoituong));
        }

        if (tinhtrangdangki != null && tinhtrangdangki >= 0) {
            // ParameterExpression<Integer> p = cb.parameter(Integer.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("tinhTrangDangKi"), tinhtrangdangki));
        }

        if (kiemtratrung != null && kiemtratrung >= 0) {
            // ParameterExpression<Integer> p = cb.parameter(Integer.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("kiemTraTrung"), kiemtratrung));
        }
        if(Validator.isNotNull(loaiThuocTiem) && !isDatTieuChuan){
            List<MuiTiemChung> muiTiemChungs =  muiTiemChungRepository.findMuiTiemChungDatDieuKien("%"+loaiThuocTiem+"%");
            if(Validator.isNotNull(muiTiemChungs)){
                List<Long> congDanDatDieuKien = new ArrayList<>();
                for (MuiTiemChung muiTiemChung: muiTiemChungs) {
                    congDanDatDieuKien.add(muiTiemChung.getCongDanID());
                }
                if(Validator.isNotNull(congDanDatDieuKien)){
                    predicates.add((nguoiTiemChungRoot.get("congDanID").in(congDanDatDieuKien)));
                }
            }
        }

        if(isDatTieuChuan && Validator.isNotNull(loaiThuocTiem)){
            int soNgayTiem = 0;
            if(loaiThuocTiem.toLowerCase().contains("astra")){
                soNgayTiem = 8*7;
            } else if(loaiThuocTiem.toLowerCase().contains("moderna")){
                soNgayTiem = 8*7;
            } else if(loaiThuocTiem.toLowerCase().contains("vero")){
                soNgayTiem = 3*7;
            } else {
                soNgayTiem = 8*7;
            }
            List<MuiTiemChung> muiTiemChungs = muiTiemChungRepository.findMuiTiemChungDatDieuKien("%" + loaiThuocTiem + "%",soNgayTiem );
            List<Long> listCongDan = new ArrayList<>();
            for (MuiTiemChung muitiemchung:muiTiemChungs) {
                listCongDan.add(muitiemchung.getCongDanID());
            }
            predicates.add((nguoiTiemChungRoot.get("congDanID").in(listCongDan)));
        }

        if (!predicates.isEmpty()) {
            Predicate[] pdc = new Predicate[predicates.size()];
            int count = 0;
            for (Predicate predicate : predicates) {
                pdc[count] = predicate;
                count++;
            }
            Predicate allPredicate = cb.and(pdc);
            //cq.where(pdc);
            cq.where(allPredicate);
        }

        TypedQuery<Long> typedQuery = em.createQuery(cq);

        em.close();



        return typedQuery.getSingleResult();
    }

    @Override
    public List<NguoiTiemChung> searchNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki,
                                                     String hovaten, Long diabancosoid, String cosoytema, Integer tinhtrangdangki, Integer kiemtratrung,
                                                     Integer page, Integer size, String tinhthanhma, String tinhthanhten, String quanhuyenma,
                                                     String quanhuyenten, String phuongxama, String phuongxaten, Boolean isDatTieuChuan, String loaiThuocTiem, String diachinoio) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<NguoiTiemChung> cq = cb.createQuery(NguoiTiemChung.class);

        Root<NguoiTiemChung> nguoiTiemChungRoot = cq.from(NguoiTiemChung.class);

        cq.select(nguoiTiemChungRoot);

        // Predicate[] predicates = new Predicate[] {};
        List<Predicate> predicates = new ArrayList<Predicate>();


        if (Validator.isNotNull(cmtcccd)) {
            // ParameterExpression<String> p = cb.parameter(String.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("cmtcccd"), cmtcccd));
        }

        if (nhomdoituong != null && nhomdoituong >= 0) {
            // ParameterExpression<Integer> p = cb.parameter(Integer.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), nhomdoituong));
        }

        if (Validator.isNotNull(ngaydangki)) {
            // ParameterExpression<String> p = cb.parameter(String.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("ngayDangKi"), ngaydangki));
        }

        if (Validator.isNotNull(tinhthanhma)) {
            predicates.add(cb.like(nguoiTiemChungRoot.get("tinhThanhMa"), "%" + tinhthanhma + "%"));
        }

        if (Validator.isNotNull(tinhthanhten)) {
            predicates.add(cb.like(nguoiTiemChungRoot.get("tinhThanhTen"), "%" + tinhthanhten + "%"));
        }

        if (Validator.isNotNull(quanhuyenma)) {
            predicates.add(cb.like(nguoiTiemChungRoot.get("quanHuyenMa"), "%" + quanhuyenma + "%"));
        }

        if (Validator.isNotNull(quanhuyenten)) {
            predicates.add(cb.like(nguoiTiemChungRoot.get("quanHuyenTen"), "%" + quanhuyenten + "%"));
        }

        if (Validator.isNotNull(phuongxama)) {
            predicates.add(cb.like(nguoiTiemChungRoot.get("phuongXaMa"), "%" + phuongxama + "%"));
        }

        if (Validator.isNotNull(phuongxaten)) {
            predicates.add(cb.like(nguoiTiemChungRoot.get("phuongXaTen"), "%" + phuongxaten + "%"));
        }

        if (Validator.isNotNull(hovaten)) {
            // ParameterExpression<String> p = cb.parameter(String.class);
            predicates.add(cb.like(nguoiTiemChungRoot.get("hoVaTen"), "%" + hovaten + "%"));
        }

        if (diabancosoid != null && diabancosoid >= 0) {
            // ParameterExpression<Long> p = cb.parameter(Long.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("diaBanCoSoId"), diabancosoid));
        }

        if (Validator.isNotNull(cosoytema)) {
            // ParameterExpression<String> p = cb.parameter(String.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("coSoYTeMa"), cosoytema));
        }

        if (nhomdoituong != null && nhomdoituong >= 0) {
            // ParameterExpression<Integer> p = cb.parameter(Integer.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), nhomdoituong));
        }

        if (tinhtrangdangki != null && tinhtrangdangki >= 0) {
            // ParameterExpression<Integer> p = cb.parameter(Integer.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("tinhTrangDangKi"), tinhtrangdangki));
        }
        if(Validator.isNotNull(diachinoio)){
            predicates.add(cb.like(nguoiTiemChungRoot.get("diaChiNoiO"), "%"+diachinoio+"%"));
        }

        if (kiemtratrung != null && kiemtratrung >= 0) {
            // ParameterExpression<Integer> p = cb.parameter(Integer.class);
            predicates.add(cb.equal(nguoiTiemChungRoot.get("kiemTraTrung"), kiemtratrung));
        }

        if(Validator.isNotNull(loaiThuocTiem) && !isDatTieuChuan){
            List<MuiTiemChung> muiTiemChungs =  muiTiemChungRepository.findMuiTiemChungDatDieuKien("%"+loaiThuocTiem+"%");
            if(Validator.isNotNull(muiTiemChungs)){
                List<Long> congDanDatDieuKien = new ArrayList<>();
                for (MuiTiemChung muiTiemChung: muiTiemChungs) {
                    congDanDatDieuKien.add(muiTiemChung.getCongDanID());
                }
                if(Validator.isNotNull(congDanDatDieuKien)){
                    predicates.add((nguoiTiemChungRoot.get("congDanID").in(congDanDatDieuKien)));
                }
            }
        }

        if(isDatTieuChuan){
            int soNgayTiem = 0;
            loaiThuocTiem = loaiThuocTiem.toLowerCase();
            List<MuiTiemChung> listMuiDatDieuKien = new ArrayList<>();
            List<MuiTiemChung> list2 = new ArrayList<>();
            if(loaiThuocTiem.toLowerCase().contains("astra")){
                soNgayTiem = 8*7;
                listMuiDatDieuKien= muiTiemChungRepository.findMuiTiemChungDatDieuKien("%astra%", soNgayTiem);
                list2 = muiTiemChungRepository.findMuiTiemChungDatDieuKien("%pfizer%", soNgayTiem);
                listMuiDatDieuKien.addAll(list2);
            } else if(loaiThuocTiem.toLowerCase().contains("moderna")){
                soNgayTiem = 8*7;
                listMuiDatDieuKien = muiTiemChungRepository.findMuiTiemChungDatDieuKien("%moderna%", soNgayTiem);
            } else if(loaiThuocTiem.toLowerCase().contains("vero")){
                soNgayTiem = 3*7;
                listMuiDatDieuKien = muiTiemChungRepository.findMuiTiemChungDatDieuKien("%vero%", soNgayTiem);
            } if(loaiThuocTiem.toLowerCase().contains("pfizer")){
                soNgayTiem = 3*7;
                listMuiDatDieuKien = muiTiemChungRepository.findMuiTiemChungDatDieuKien("%pfizer%", soNgayTiem);
            } else {
                soNgayTiem = 8*7;
                listMuiDatDieuKien = muiTiemChungRepository.findMuiTiemChungDatDieuKien("%"+loaiThuocTiem+"%", soNgayTiem);
            }

            if(Validator.isNotNull(listMuiDatDieuKien)){
                List<Long> congDanDatDieuKien = new ArrayList<>();
                for (MuiTiemChung muiTiemChung: listMuiDatDieuKien) {
                    congDanDatDieuKien.add(muiTiemChung.getCongDanID());
                }
                if(Validator.isNotNull(congDanDatDieuKien)){
                    predicates.add((nguoiTiemChungRoot.get("congDanID").in(congDanDatDieuKien)));
                }
            }
        }


        if (!predicates.isEmpty()) {
            Predicate[] pdc = new Predicate[predicates.size()];
            int count = 0;
            for (Predicate predicate : predicates) {
                pdc[count] = predicate;
                count++;
            }
            Predicate allPredicate = cb.and(pdc);
            //cq.where(pdc);
            cq.where(allPredicate);
        }

        List<Order> orderList = new ArrayList<Order>();

        orderList.add(cb.asc(nguoiTiemChungRoot.get("cmtcccd")));
        orderList.add(cb.asc(nguoiTiemChungRoot.get("ngayDangKi")));
        orderList.add(cb.asc(nguoiTiemChungRoot.get("diaBanCoSoId")));
        cq.orderBy(orderList);

        TypedQuery<NguoiTiemChung> typedQuery = em.createQuery(cq);

        int offset = page * size;
        List<NguoiTiemChung> result = new ArrayList<>();
        result = typedQuery.setFirstResult(offset).setMaxResults(size).getResultList();



//		List<NguoiTiemChung> lstNguoiTiemChung = typedQuery.setFirstResult(offset).setMaxResults(size).getResultList();

        em.close();

        return result;
    }

    @Override
    public ResultSearchDto<NguoiTiemChung> search(NguoiTiemChungDto nguoiTiemChungDto, int page, int size) {

        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<NguoiTiemChung> criteriaQuery = builder.createQuery(NguoiTiemChung.class);

        Root<NguoiTiemChung> nguoiTiemChungRoot = criteriaQuery.from(NguoiTiemChung.class);
        Root<PhieuHenTiem> phieuHenTiemRoot = criteriaQuery.from(PhieuHenTiem.class);
        Root<CongDan> congDanRoot = criteriaQuery.from(CongDan.class);


        criteriaQuery.select(nguoiTiemChungRoot).distinct(true);

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(builder.equal(nguoiTiemChungRoot.get("id"), phieuHenTiemRoot.get("nguoiTiemChungId")));
        predicates.add(builder.equal(nguoiTiemChungRoot.get("congDanID"), congDanRoot.get("id")));

        if (Validator.isNotNull(nguoiTiemChungDto.cmtcccd) && !nguoiTiemChungDto.cmtcccd.isEmpty()) {
            predicates.add(builder.equal(nguoiTiemChungRoot.get("cmtcccd"), nguoiTiemChungDto.cmtcccd));
        }

        if (nguoiTiemChungDto.nhomdoituong > 0) {
            predicates.add(builder.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), nguoiTiemChungDto.nhomdoituong));
        }

        if (nguoiTiemChungDto.caTiemChungId > 0) {
            predicates.add(builder.equal(phieuHenTiemRoot.get("caTiemChungId"), nguoiTiemChungDto.caTiemChungId));
        }

        if (nguoiTiemChungDto.lichTiemChungId > 0) {
            predicates.add(builder.equal(phieuHenTiemRoot.get("lichTiemChungId"), nguoiTiemChungDto.lichTiemChungId));
        }

        if (nguoiTiemChungDto.listtinhtrangxacnhan != null && nguoiTiemChungDto.listtinhtrangxacnhan.size() > 0) {
            predicates.add((phieuHenTiemRoot.get("tinhTrangXacNhan").in(nguoiTiemChungDto.listtinhtrangxacnhan)));
        } else {
            if (nguoiTiemChungDto.typeGet == 0) {
                predicates.add(builder.equal(phieuHenTiemRoot.get("tinhTrangXacNhan"), nguoiTiemChungDto.tinhtrangxacnhan));
            } else {
                predicates.add(builder.notEqual(phieuHenTiemRoot.get("tinhTrangXacNhan"), 0));
            }
        }

        if (nguoiTiemChungDto.soMuiTiem > 0) {
            predicates.add(builder.equal(congDanRoot.get("soMuiTiem"), nguoiTiemChungDto.soMuiTiem));
        }
        if (Validator.isNotNull(nguoiTiemChungDto.loaiThuocTiem)) {

            List<MuiTiemChung> listMui = muiTiemChungRepository.findByLoaiThuocTiem(nguoiTiemChungDto.loaiThuocTiem);

            if (Validator.isNotNull(listMui)) {
                List<Long> listId = new ArrayList<>();
                for (MuiTiemChung muiTiemChung : listMui) {
                    listId.add(muiTiemChung.getCongDanID());
                }
                predicates.add((nguoiTiemChungRoot.get("congDanID")).in(listId));
            }
        }

        if (Validator.isNotNull(nguoiTiemChungDto.ngaydangki) && !nguoiTiemChungDto.ngaydangki.isEmpty()) {
            predicates.add(builder.equal(nguoiTiemChungRoot.get("ngayDangKi"), nguoiTiemChungDto.ngaydangki));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.hovaten) && !nguoiTiemChungDto.hovaten.isEmpty()) {
            predicates.add(builder.like(nguoiTiemChungRoot.get("hoVaTen"), "%" + nguoiTiemChungDto.hovaten + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.tinhthanhma) && !nguoiTiemChungDto.tinhthanhma.isEmpty()) {
            predicates.add(builder.like(nguoiTiemChungRoot.get("tinhThanhMa"), "%" + nguoiTiemChungDto.tinhthanhma + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.tinhthanhten) && !nguoiTiemChungDto.tinhthanhten.isEmpty()) {
            predicates.add(builder.like(nguoiTiemChungRoot.get("tinhThanhTen"), "%" + nguoiTiemChungDto.tinhthanhten + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.quanhuyenma) && !nguoiTiemChungDto.quanhuyenma.isEmpty()) {
            predicates.add(builder.like(nguoiTiemChungRoot.get("quanHuyenMa"), "%" + nguoiTiemChungDto.quanhuyenma + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.quanhuyenten) && !nguoiTiemChungDto.quanhuyenten.isEmpty()) {
            predicates.add(builder.like(nguoiTiemChungRoot.get("quanHuyenTen"), "%" + nguoiTiemChungDto.quanhuyenten + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.phuongxama) && !nguoiTiemChungDto.phuongxama.isEmpty()) {
            predicates.add(builder.like(nguoiTiemChungRoot.get("phuongXaMa"), "%" + nguoiTiemChungDto.phuongxama + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.phuongxaten) && !nguoiTiemChungDto.phuongxaten.isEmpty()) {
            predicates.add(builder.like(nguoiTiemChungRoot.get("phuongXaTen"), "%" + nguoiTiemChungDto.phuongxaten + "%"));
        }
        if (Validator.isNotNull(nguoiTiemChungDto.diachinoio) && !nguoiTiemChungDto.diachinoio.isEmpty()) {
            predicates.add(builder.like(nguoiTiemChungRoot.get("diaChiNoiO"), "%" + nguoiTiemChungDto.diachinoio + "%"));
        }

        if (nguoiTiemChungDto.diabancosoid > 0) {
            predicates.add(builder.equal(nguoiTiemChungRoot.get("diaBanCoSoId"), nguoiTiemChungDto.diabancosoid));
        }

        if (nguoiTiemChungDto.cosoyteid > 0) {
            predicates.add(builder.equal(nguoiTiemChungRoot.get("CoSoYTeId"), nguoiTiemChungDto.cosoyteid));
        }

        if (nguoiTiemChungDto.nhomdoituong > 0) {
            predicates.add(builder.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), nguoiTiemChungDto.nhomdoituong));
        }

        if (nguoiTiemChungDto.tinhtrangdangki > 0) {
            predicates.add(builder.equal(nguoiTiemChungRoot.get("tinhTrangDangKi"), nguoiTiemChungDto.tinhtrangdangki));
        }

        if (nguoiTiemChungDto.kiemtratrung > 0) {
            predicates.add(builder.equal(nguoiTiemChungRoot.get("kiemTraTrung"), nguoiTiemChungDto.kiemtratrung));
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

        TypedQuery<NguoiTiemChung> typedQuery = em.createQuery(criteriaQuery);

        int offset = page * size;

        long total = typedQuery.getResultList().size();
        List<NguoiTiemChung> lstNguoiTiemChung = typedQuery.setFirstResult(offset).setMaxResults(size).getResultList();
        em.close();

        return new ResultSearchDto<NguoiTiemChung>(lstNguoiTiemChung, total);
    }

    @Override
    public ResultSearchDto<CongDan> searchDaTiem(NguoiTiemChungDto nguoiTiemChungDto, int page, int size) {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<CongDan> criteriaQuery = builder.createQuery(CongDan.class);

        Root<CongDan> congdanRoot = criteriaQuery.from(CongDan.class);
        Root<MuiTiemChung> muiTiemChungRoot = criteriaQuery.from(MuiTiemChung.class);

        criteriaQuery.select(congdanRoot).distinct(true);

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(builder.equal(congdanRoot.get("id"), muiTiemChungRoot.get("congDanID")));

        if (Validator.isNotNull(nguoiTiemChungDto.cmtcccd) && !nguoiTiemChungDto.cmtcccd.isEmpty()) {
            predicates.add(builder.equal(congdanRoot.get("cmtcccd"), nguoiTiemChungDto.cmtcccd));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.hovaten) && !nguoiTiemChungDto.hovaten.isEmpty()) {
            predicates.add(builder.like(congdanRoot.get("hoVaTen"), "%" + nguoiTiemChungDto.hovaten + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.tinhthanhma) && !nguoiTiemChungDto.tinhthanhma.isEmpty()) {
            predicates.add(builder.like(congdanRoot.get("tinhThanhMa"), "%" + nguoiTiemChungDto.tinhthanhma + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.tinhthanhten) && !nguoiTiemChungDto.tinhthanhten.isEmpty()) {
            predicates.add(builder.like(congdanRoot.get("tinhThanhTen"), "%" + nguoiTiemChungDto.tinhthanhten + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.quanhuyenma) && !nguoiTiemChungDto.quanhuyenma.isEmpty()) {
            predicates.add(builder.like(congdanRoot.get("quanHuyenMa"), "%" + nguoiTiemChungDto.quanhuyenma + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.quanhuyenten) && !nguoiTiemChungDto.quanhuyenten.isEmpty()) {
            predicates.add(builder.like(congdanRoot.get("quanHuyenTen"), "%" + nguoiTiemChungDto.quanhuyenten + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.phuongxama) && !nguoiTiemChungDto.phuongxama.isEmpty()) {
            predicates.add(builder.like(congdanRoot.get("phuongXaMa"), "%" + nguoiTiemChungDto.phuongxama + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.phuongxaten) && !nguoiTiemChungDto.phuongxaten.isEmpty()) {
            predicates.add(builder.like(congdanRoot.get("phuongXaTen"), "%" + nguoiTiemChungDto.phuongxaten + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.diachinoio) && !nguoiTiemChungDto.diachinoio.isEmpty()) {
            predicates.add(builder.like(congdanRoot.get("diaChiThuongTru"), "%" + nguoiTiemChungDto.diachinoio + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.loaiThuocTiem) && !nguoiTiemChungDto.loaiThuocTiem.isEmpty()) {
            predicates.add(builder.like(muiTiemChungRoot.get("loaiThuocTiem"), "%" + nguoiTiemChungDto.loaiThuocTiem + "%"));
        }

        if (Validator.isNotNull(nguoiTiemChungDto.soMuiTiem) && nguoiTiemChungDto.soMuiTiem > 0) {
            predicates.add(builder.equal(congdanRoot.get("soMuiTiem"), nguoiTiemChungDto.soMuiTiem));
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

        TypedQuery<CongDan> typedQuery = em.createQuery(criteriaQuery);

        int offset = page * size;

        long total = typedQuery.getResultList().size();
        List<CongDan> lstNguoiTiemChung = typedQuery.setFirstResult(offset).setMaxResults(size).getResultList();
        em.close();

        return new ResultSearchDto<CongDan>(lstNguoiTiemChung, total);
    }

    @Override
    public ResultSearchDto<NguoiTiemChung> searchOr(NguoiTiemChungDto nguoiTiemChungDto, int page, int size) {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<NguoiTiemChung> criteriaQuery = builder.createQuery(NguoiTiemChung.class);

        Root<NguoiTiemChung> nguoiTiemChungRoot = criteriaQuery.from(NguoiTiemChung.class);

        criteriaQuery.select(nguoiTiemChungRoot).distinct(true);

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(builder.equal(nguoiTiemChungRoot.get("cmtcccd"), ""));
        predicates.add(builder.isNull(nguoiTiemChungRoot.get("cmtcccd")));

        predicates.add(builder.equal(nguoiTiemChungRoot.get("hoVaTen"), ""));
        predicates.add(builder.isNull(nguoiTiemChungRoot.get("hoVaTen")));

        predicates.add(builder.equal(nguoiTiemChungRoot.get("ngaySinh"), ""));
        predicates.add(builder.isNull(nguoiTiemChungRoot.get("ngaySinh")));

        predicates.add(builder.equal(nguoiTiemChungRoot.get("soDienThoai"), ""));
        predicates.add(builder.isNull(nguoiTiemChungRoot.get("soDienThoai")));

//		predicates.add(builder.equal(nguoiTiemChungRoot.get("nhomDoiTuong"), 0));

        predicates.add(builder.equal(nguoiTiemChungRoot.get("tinhThanhTen"), ""));
        predicates.add(builder.isNull(nguoiTiemChungRoot.get("tinhThanhTen")));

        predicates.add(builder.equal(nguoiTiemChungRoot.get("quanHuyenTen"), ""));
        predicates.add(builder.isNull(nguoiTiemChungRoot.get("quanHuyenTen")));

        predicates.add(builder.equal(nguoiTiemChungRoot.get("phuongXaTen"), ""));
        predicates.add(builder.isNull(nguoiTiemChungRoot.get("phuongXaTen")));

        predicates.add(builder.equal(nguoiTiemChungRoot.get("diaChiNoiO"), ""));
        predicates.add(builder.isNull(nguoiTiemChungRoot.get("diaChiNoiO")));

//		predicates.add(builder.equal(nguoiTiemChungRoot.get("coSoYTeMa"), ""));
//		predicates.add(builder.isNull(nguoiTiemChungRoot.get("coSoYTeMa")));

        Predicate predicateTinhTrangDangki = null;

        if (nguoiTiemChungDto.tinhtrangdangki > 0) {
            predicateTinhTrangDangki = builder.equal(nguoiTiemChungRoot.get("tinhTrangDangKi"), nguoiTiemChungDto.tinhtrangdangki);
        }

        if (!predicates.isEmpty()) {
            Predicate[] pdc = new Predicate[predicates.size()];
            int count = 0;
            for (Predicate predicate : predicates) {
                pdc[count] = predicate;
                count++;
            }

            Predicate predicateOr = builder.or(pdc);

            Predicate allPredicate = null;
            if (predicateTinhTrangDangki != null) {
                allPredicate = builder.and(predicateOr, predicateTinhTrangDangki);
            } else {
                allPredicate = builder.and(predicateOr);
            }

            criteriaQuery.where(allPredicate);
        }

        TypedQuery<NguoiTiemChung> typedQuery = em.createQuery(criteriaQuery);

        int offset = page * size;

        long total = typedQuery.getResultList().size();
        List<NguoiTiemChung> lstNguoiTiemChung = typedQuery.setFirstResult(offset).setMaxResults(size).getResultList();
        em.close();

        return new ResultSearchDto<NguoiTiemChung>(lstNguoiTiemChung, total);
    }

    @Override
    public NguoiTiemChung findByCMTCCCD(String cmtcccd) {
        return nguoiTiemChungRepository.findByCMTCCCD(cmtcccd);
    }

    @Override
    public NguoiTiemChung findBycongDanID(long id) {
        return nguoiTiemChungRepository.findBycongDanID(id);
    }

    @Override
    public List<NguoiTiemChung> searchListChuyenDangKyChinhThuc(NguoiTiemChungDto nguoiTiemChungDto) {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<NguoiTiemChung> criteriaQuery = builder.createQuery(NguoiTiemChung.class);

        Root<NguoiTiemChung> nguoiTiemChungRoot = criteriaQuery.from(NguoiTiemChung.class);
        criteriaQuery.select(nguoiTiemChungRoot);

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(builder.notEqual(nguoiTiemChungRoot.get("hoVaTen"), ""));
        predicates.add(builder.isNotNull(nguoiTiemChungRoot.get("hoVaTen")));

        predicates.add(builder.notEqual(nguoiTiemChungRoot.get("ngaySinh"), ""));
        predicates.add(builder.isNotNull(nguoiTiemChungRoot.get("ngaySinh")));

        predicates.add(builder.notEqual(nguoiTiemChungRoot.get("soDienThoai"), ""));
        predicates.add(builder.isNotNull(nguoiTiemChungRoot.get("soDienThoai")));

        predicates.add(builder.notEqual(nguoiTiemChungRoot.get("tinhThanhTen"), ""));
        predicates.add(builder.isNotNull(nguoiTiemChungRoot.get("tinhThanhTen")));

        predicates.add(builder.notEqual(nguoiTiemChungRoot.get("quanHuyenTen"), ""));
        predicates.add(builder.isNotNull(nguoiTiemChungRoot.get("quanHuyenTen")));

        predicates.add(builder.notEqual(nguoiTiemChungRoot.get("phuongXaTen"), ""));
        predicates.add(builder.isNotNull(nguoiTiemChungRoot.get("phuongXaTen")));

        predicates.add(builder.notEqual(nguoiTiemChungRoot.get("diaChiNoiO"), ""));
        predicates.add(builder.isNotNull(nguoiTiemChungRoot.get("diaChiNoiO")));

        predicates.add(builder.equal(nguoiTiemChungRoot.get("tinhTrangDangKi"), nguoiTiemChungDto.tinhtrangdangki));

        if (!predicates.isEmpty()) {
            Predicate[] pdc = new Predicate[predicates.size()];
            int count = 0;
            for (Predicate predicate : predicates) {
                pdc[count] = predicate;
                count++;
            }
            Predicate allPredicate = builder.and(pdc);
            //cq.where(pdc);
            criteriaQuery.where(allPredicate);
        }

        TypedQuery<NguoiTiemChung> typedQuery = em.createQuery(criteriaQuery);
        List<NguoiTiemChung> lstNguoiTiemChung = typedQuery.getResultList();

        em.close();
        return lstNguoiTiemChung;
    }

}
