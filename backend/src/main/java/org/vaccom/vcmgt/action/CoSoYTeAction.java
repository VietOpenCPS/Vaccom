package org.vaccom.vcmgt.action;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.CoSoYTe;

/**
 * @author vaccom
 *
 */
@Service
public interface CoSoYTeAction {

	public CoSoYTe addCoSoYTe(String reqBody) throws Exception;

	public CoSoYTe addCoSoYTe(String tinhThanhTen, String tinhThanhMa, String quanHuyenTen, String quanHuyenMa,
			String phuongXaTen, String phuongXaMa, String maCoSo, String tenCoSo, String diaChiCoSo,
			String nguoiDaiDien, String soDienThoai) throws Exception;

	public boolean deleteById(long id) throws Exception;

	public CoSoYTe findById(long id);

	public CoSoYTe findByMaCoSoYTe(String maCoSoYTe);

	public List<CoSoYTe> findAll(int page, int size);

	public List<CoSoYTe> findAll();

	public CoSoYTe updateCoSoYTe(long id, String reqBody) throws Exception;
}
