package org.vaccom.vcmgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.entity.NguoiDungPrincipal;
import org.vaccom.vcmgt.service.NguoiDungService;
import org.vaccom.vcmgt.service.UserDetailService;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserDetailService userDetailService;

	@Autowired
	NguoiDungService nguoiDungService;

	@Transactional
	public UserDetails loadUserById(Long id) {
		NguoiDung nguoiDung = nguoiDungService.findByID(id);
		return NguoiDungPrincipal.create(nguoiDung);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDetailService.findUserDetailByUserName(username);
	}
}