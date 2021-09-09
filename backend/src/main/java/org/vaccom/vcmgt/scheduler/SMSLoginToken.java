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
import org.vaccom.vcmgt.constant.ZaloConstant;
import org.vaccom.vcmgt.entity.UyBanNhanDan;
import org.vaccom.vcmgt.service.UyBanNhanDanService;
import org.vaccom.vcmgt.util.SMSUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class SMSLoginToken {


    @Autowired
    private UyBanNhanDanService uyBanNhanDanService;

    private static final Logger log = LoggerFactory.getLogger(SMSLoginToken.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Scheduled(fixedRate = 540000)
    public void doAction() throws IOException {
        log.info("VACCOM SMS GET TOKEN START: " +  dateFormat.format(new Date()));

        List<UyBanNhanDan> uyBanNhanDanList = uyBanNhanDanService.findByNotificationConfigExceptNull();

        if (Validator.isNotNull(uyBanNhanDanList)){
            for (UyBanNhanDan uyBanNhanDan : uyBanNhanDanList) {
                if(!uyBanNhanDan.getNotificationConfig().isEmpty() && uyBanNhanDan.getNotificationConfig() != ""){
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode configData = null;
                    JsonNode sms = null;
                    try {
                        configData = mapper.readTree(uyBanNhanDan.getNotificationConfig());
                        sms = configData.get("sms");
                    } catch (Exception ex){
                        log.error(ex.getMessage());
                    }
                    if(Validator.isNotNull(configData) && Validator.isNotNull(sms)){
                        String account = sms.get(ZaloConstant.account).asText();
                        String password = sms.get(ZaloConstant.password).asText();
                        String access_token = sms.get(ZaloConstant.Access_Token.toLowerCase()).asText();
                        String access_token_old = access_token;

                        if(Validator.isNull(access_token)){
                            String accessToken = SMSUtil.getAccessToken(account, password);
                            access_token = accessToken;
                        } else {
                            String status = SMSUtil.keepAccessToken(access_token);
                            if(Validator.isNull(status)){
                                String accessTokenNew = SMSUtil.getAccessToken(account, password);
                                access_token = accessTokenNew;
                            }
                        }
                        if(!access_token_old.equals(access_token)){
                            ((ObjectNode) sms).remove(ZaloConstant.Access_Token.toLowerCase());
                            ((ObjectNode) sms).put(ZaloConstant.Access_Token.toLowerCase(), access_token);
                            ((ObjectNode) configData).remove("sms");
                            ((ObjectNode) configData).put("sms", sms);
                            uyBanNhanDan.setNotificationConfig(configData.toString());
                            uyBanNhanDanService.save(uyBanNhanDan);
                        }
                    }
                }

            }
        }



    }
}
