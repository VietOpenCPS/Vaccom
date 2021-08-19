package org.vaccom.vcmgt.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserDetailService {

	public UserDetails findUserDetailByUserName(String userName);

}
