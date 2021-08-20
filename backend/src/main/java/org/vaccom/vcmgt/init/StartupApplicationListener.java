package org.vaccom.vcmgt.init;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.vaccom.vcmgt.action.NguoiDungAction;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	private Log _log = LogFactory.getLog(StartupApplicationListener.class);
	
	@Autowired
	private NguoiDungAction nguoiDungAction;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		_log.info("Startup: " + System.currentTimeMillis());
	
		try {
			initNguoiDung();
		} catch (Exception e) {
			_log.error(e);
		}
	}
	
	private void initNguoiDung() throws Exception {
		nguoiDungAction.initNguoiDung();
	}
}
