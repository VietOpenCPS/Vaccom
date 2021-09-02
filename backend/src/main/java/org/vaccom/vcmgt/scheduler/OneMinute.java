package org.vaccom.vcmgt.scheduler;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liferay.portal.kernel.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.vaccom.vcmgt.action.HangChoThongBaoAction;
import org.vaccom.vcmgt.config.ZaloConfig;
import org.vaccom.vcmgt.constant.ZaloConstant;
import org.vaccom.vcmgt.entity.HangChoThongBao;
import org.vaccom.vcmgt.util.ZaloNotificationUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class OneMinute {

    @Autowired
    private ZaloConfig zaloConfig;

    @Autowired
    private HangChoThongBaoAction hangChoThongBaoAction;

    private static final Logger log = LoggerFactory.getLogger(OneMinute.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 60000)
    public void doAction() throws IOException {
        String oaid_access_token = zaloConfig.getAccessToken();

        log.info("The time is now {}", dateFormat.format(new Date()));

        List<HangChoThongBao> ThongBaoChuaGui = hangChoThongBaoAction.findByIsSentIsReady(false, true);
        for (HangChoThongBao hangChoThongBao : ThongBaoChuaGui) {
            String loaiThongBao = hangChoThongBao.getLoaiThongBao();
            String template_id = null;
            if (loaiThongBao.equals(ZaloConstant.Loai_Hen_TiemChung)){
                template_id = zaloConfig.getHenTiemChung();
            } else if(loaiThongBao.equals(ZaloConstant.Loai_Giay_Di_Duong)){
                template_id = zaloConfig.getGiayDiDuong();
            }

            if(Validator.isNotNull(template_id)){
                String phone = hangChoThongBao.getToTelNo();
                String tracking_id = "tracking_id";
                String payload = hangChoThongBao.getPayload();

                ObjectMapper mapper = new ObjectMapper();
                ObjectNode body = mapper.createObjectNode();
                body.put(ZaloConstant.template_id, template_id);
                body.put(ZaloConstant.phone, phone);
                body.put(ZaloConstant.tracking_id, tracking_id);

                JsonNode template_data_json = mapper.readTree(payload);
                body.put(ZaloConstant.template_data, template_data_json);



                log.info(body.toString());
                Integer code = null;
                try {
                    code = ZaloNotificationUtil.sendNotification(body.toString(), oaid_access_token);
                } catch (Exception ex){
                    log.error(ex.getMessage());
                }
                if(code!= null){
                    hangChoThongBao.setSent(true);
                    hangChoThongBaoAction.update(hangChoThongBao);
                }
            }
        }
    }
}
