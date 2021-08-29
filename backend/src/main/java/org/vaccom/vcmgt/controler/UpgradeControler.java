package org.vaccom.vcmgt.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vaccom.vcmgt.entity.VaiTro;
import org.vaccom.vcmgt.service.DynamicQueryService;
import org.vaccom.vcmgt.util.MessageUtil;
import org.vaccom.vcmgt.util.RoleUtil;
import org.vaccom.vcmgt.util.VaccomUtil;
import com.liferay.portal.kernel.util.GetterUtil;

@RestController
@RequestMapping("/rest/v1/upgrade")
public class UpgradeControler {
	@Autowired
	private DynamicQueryService dynamicQueryService;

	@RequestMapping(value = "/db", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> exportNguoiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("query") String query) {
		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check
			
			if (!RoleUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("upgrade.permission_error"));
			}

			return ResponseEntity.ok().body(dynamicQueryService.executeQuery(query));

		} catch (Exception e) {
			_log.error(e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
	}

	private Log _log = LogFactory.getLog(UpgradeControler.class);
}
