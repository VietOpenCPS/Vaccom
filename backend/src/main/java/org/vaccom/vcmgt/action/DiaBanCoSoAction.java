package org.vaccom.vcmgt.action;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.DiaBanCoSo;

/**
 * @author vaccom
 *
 */
@Service
public interface DiaBanCoSoAction {

	public DiaBanCoSo addDiaBanCoSo(String reqBody) throws Exception;

	public DiaBanCoSo addDiaBanCoSo(String tenDiaBan, String tinhThanhMa, String tinhThanhTen, String quanHuyenMa,
			String quanHuyenpTen, String phuongXaMa, String phuongXaTen, long coSoYTeId) throws Exception;

	public boolean deleteById(long id) throws Exception;

	public DiaBanCoSo findById(long id);

	public List<DiaBanCoSo> findByCoSoYTeId(long id);

	public List<DiaBanCoSo> findAll();

	public DiaBanCoSo updateDiaBanCoSo(long id, String reqBody) throws Exception;
}
