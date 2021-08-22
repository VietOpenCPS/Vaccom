package org.vaccom.vcmgt.action;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.PhuongXa;
import org.vaccom.vcmgt.entity.QuanHuyen;
import org.vaccom.vcmgt.entity.TinhThanh;

@Service
public interface DonViHanhChinhAction {

	public void addDonViHanhChinh(String tinhThanhMa, String tinhThanhTen, String quanHuyenMa, String quanHuyenTen,
			String phuongXaMa, String phuongXaTen) throws Exception;

	public List<TinhThanh> findAll();

	public List<QuanHuyen> findByTinhThanhID(long tinhThanhID);

	public List<PhuongXa> findByQuanHuyenID(long quanHuyenID);

}
