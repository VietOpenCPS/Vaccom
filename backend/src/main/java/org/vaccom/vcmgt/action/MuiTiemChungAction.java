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
	
	public long countCoSoYTeMa(String coSoYTeMa);

	public MuiTiemChung addMuiTiemChung(String reqBody) throws Exception;

	public boolean deleteById(long id) throws Exception;

	public MuiTiemChung findById(long id);

	public List<MuiTiemChung> findByCmtcccd(String cmtcccd);

	public List<MuiTiemChung> findCoSoYTeMa(String coSoYTeMa, int page, int size);

	public MuiTiemChung updateMuiTiemChung(long id, String reqBody) throws Exception;
}
