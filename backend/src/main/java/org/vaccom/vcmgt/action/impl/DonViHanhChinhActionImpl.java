package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.DonViHanhChinhAction;
import org.vaccom.vcmgt.entity.PhuongXa;
import org.vaccom.vcmgt.entity.QuanHuyen;
import org.vaccom.vcmgt.entity.TinhThanh;
import org.vaccom.vcmgt.service.PhuongXaService;
import org.vaccom.vcmgt.service.QuanHuyenService;
import org.vaccom.vcmgt.service.TinhThanhService;

@Service
public class DonViHanhChinhActionImpl implements DonViHanhChinhAction {

	@Autowired
	private TinhThanhService tinhThanhService;

	@Autowired
	private QuanHuyenService quanHuyenService;

	@Autowired
	private PhuongXaService phuongXaService;

	@Override
	public void addDonViHanhChinh(String tinhThanhMa, String tinhThanhTen, String quanHuyenMa, String quanHuyenTen,
			String phuongXaMa, String phuongXaTen) throws Exception {
		TinhThanh tinhThanh = tinhThanhService.findByTinhThanhMa(tinhThanhMa);
		// add Tinh Thanh
		if (tinhThanh == null) {
			tinhThanh = new TinhThanh();
			tinhThanh.setTinhThanhMa(tinhThanhMa);
			tinhThanh.setTinhThanhTen(tinhThanhTen);
			tinhThanh = tinhThanhService.updateTinhThanh(tinhThanh);
		}

		QuanHuyen quanHuyen = quanHuyenService.findByQuanHuyenMa(quanHuyenMa);
		// add quan huyen
		if (quanHuyen == null) {
			quanHuyen = new QuanHuyen();
			quanHuyen.setQuanHuyenMa(quanHuyenMa);
			quanHuyen.setQuanHuyenTen(quanHuyenTen);
			quanHuyen.setTinhThanhID(tinhThanh.getId());
			quanHuyen = quanHuyenService.updateQuanHuyen(quanHuyen);
		}

		PhuongXa phuongXa = phuongXaService.findByPhuongXaMa(phuongXaMa);

		// add phuong xa
		if (phuongXa == null) {
			phuongXa = new PhuongXa();
			phuongXa.setPhuongXaMa(phuongXaMa);
			phuongXa.setPhuongXaTen(phuongXaTen);
			phuongXa.setQuanHuyenID(quanHuyen.getId());
			phuongXa = phuongXaService.updatePhuongXa(phuongXa);
		}

	}

	@Override
	public List<TinhThanh> findAll() {

		return tinhThanhService.findAll();
	}

	@Override
	public List<QuanHuyen> findByTinhThanhID(long tinhThanhID) {
		return quanHuyenService.findByTinhThanhID(tinhThanhID);
	}

	@Override
	public List<PhuongXa> findByQuanHuyenID(long quanHuyenID) {
		return phuongXaService.findByQuanHuyenID(quanHuyenID);
	}

}
