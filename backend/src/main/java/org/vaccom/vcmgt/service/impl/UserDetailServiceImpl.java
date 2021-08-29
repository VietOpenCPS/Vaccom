package org.vaccom.vcmgt.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.repository.NguoiDungRepository;
import org.vaccom.vcmgt.service.UserDetailService;
import org.vaccom.vcmgt.util.VaccomUtil;

/**
 * @author trungnt
 *
 */
@Service
public class UserDetailServiceImpl implements UserDetailService {

	@Autowired
	private NguoiDungRepository nguoiDungRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails findUserDetailByUserName(String userName) {

		NguoiDung nguoiDung = nguoiDungRepository.findByTenDangNhap(userName);
		if (nguoiDung == null) {
			throw new UsernameNotFoundException(userName);
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		grantedAuthorities.add(new SimpleGrantedAuthority(VaccomUtil.getRoleName(nguoiDung.getVaiTro())));

		return new org.springframework.security.core.userdetails.User(nguoiDung.getTenDangNhap(), nguoiDung.getMatKhau(),
				grantedAuthorities);
	}

}
