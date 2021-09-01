package org.vaccom.vcmgt.scheduler;

import com.liferay.portal.kernel.util.Validator;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.vaccom.vcmgt.action.HangChoThongBaoAction;
import org.vaccom.vcmgt.action.MauThongBaoAction;
import org.vaccom.vcmgt.constant.ZaloConstant;
import org.vaccom.vcmgt.entity.HangChoThongBao;
import org.vaccom.vcmgt.entity.MauThongBao;
import org.vaccom.vcmgt.util.ZaloNotificationUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class OneMinute {

    @Autowired
    private HangChoThongBaoAction hangChoThongBaoAction;

    @Autowired
    private MauThongBaoAction mauThongBaoAction;

    private static final Logger log = LoggerFactory.getLogger(OneMinute.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 60000)
    public void doAction() throws IOException {
        log.info("The time is now {}", dateFormat.format(new Date()));

        List<HangChoThongBao> ThongBaoChuaGui = hangChoThongBaoAction.findByIsSentIsReady(false, true);
        for (HangChoThongBao hangChoThongBao : ThongBaoChuaGui) {
            String loaiThongBao = hangChoThongBao.getLoaiThongBao();
            MauThongBao mauThongBao = mauThongBaoAction.findByLoaiThongBao(loaiThongBao);
            if(mauThongBao.isSendZalo() && Validator.isNotNull(mauThongBao.getZaloTemplateId())){
                String template_id = mauThongBao.getZaloTemplateId();
                String phone = hangChoThongBao.getToTelNo();
                String tracking_id = "tracking_id";
                String payload = hangChoThongBao.getPayload();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(ZaloConstant.template_id, template_id);
                jsonObject.put(ZaloConstant.phone, phone);
                jsonObject.put(ZaloConstant.tracking_id, tracking_id);
                Object payloadJson = JSONValue.parse(payload);

                jsonObject.put(ZaloConstant.template_data, (JSONObject) payloadJson);
                log.info(jsonObject.toString());
                Integer code = null;
                try {
                    code = ZaloNotificationUtil.sendNotification(jsonObject);
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
