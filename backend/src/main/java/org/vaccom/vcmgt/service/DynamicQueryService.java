package org.vaccom.vcmgt.service;

import org.springframework.stereotype.Service;

@Service
public interface DynamicQueryService {
	public boolean executeQuery(String query);
}
