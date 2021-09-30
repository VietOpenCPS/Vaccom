package org.vaccom.vcmgt.action;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.MuiTiemChung;

/**
 * @author vaccom
 *
 */
@Service
public interface MuiTiemChungAction {

	public long countByCoSoYTeId(long id);

	public MuiTiemChung addMuiTiemChung(String reqBody) throws Exception;

	public boolean deleteById(long id) throws Exception;

	public MuiTiemChung findById(long id);

	public List<MuiTiemChung> findByCmtcccd(String cmtcccd);
	
	public long countByCongDan_ID(long id);

	public List<MuiTiemChung> findByCongDan_ID(long id);

	public List<MuiTiemChung> findByCoSoYTeId(long id, int page, int size);

	public MuiTiemChung updateMuiTiemChung(long id, String reqBody) throws Exception;

	public MuiTiemChung update(MuiTiemChung muiTiemChung);

	public MuiTiemChung create(MuiTiemChung muiTiemChung);
}
