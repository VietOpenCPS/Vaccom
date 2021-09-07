package org.vaccom.vcmgt.scheduler;

import com.liferay.portal.kernel.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.vaccom.vcmgt.config.SMSConfig;
import org.vaccom.vcmgt.util.SMSUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class SMSLoginToken {
    @Autowired
    private SMSConfig smsConfig;

    private static final Logger log = LoggerFactory.getLogger(OneMinute.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    static class AccessToken {
        private volatile static String accessToken = null;
        public static String getAccessToken(){
            return accessToken;
        }
        public static void setAccessToken(String accessTokenNew){
            accessToken = accessTokenNew;
        }
    }
    public static String getAccessToken(){
        return SMSLoginToken.AccessToken.getAccessToken();
    }


    @Scheduled(fixedRate = 540000)
    public void doAction() throws IOException {
        log.info("VACCOM SMS GET TOKEN START: " +  dateFormat.format(new Date()));
        String account = "0901800013";
        String password = "epa123456";
        if(Validator.isNull(getAccessToken())){

            String accessToken = SMSUtil.getAccessToken(account, password);
            AccessToken.setAccessToken(accessToken);
        } else {
            String accessToken = getAccessToken();
            String status = SMSUtil.keepAccessToken(accessToken);
            if(Validator.isNull(status)){
                String accessTokenNew = SMSUtil.getAccessToken(account, password);
                AccessToken.setAccessToken(accessTokenNew);
            }
        }


    }
}
