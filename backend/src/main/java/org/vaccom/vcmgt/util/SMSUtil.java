package org.vaccom.vcmgt.util;

import com.liferay.petra.string.StringPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaccom.vcmgt.constant.SMSConstant;
import org.vaccom.vcmgt.scheduler.OneMinute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SMSUtil {

    private static final Logger _log = LoggerFactory.getLogger(SMSUtil.class);


    public static String keepAccessToken(String session_id) throws IOException {
        StringBuilder sb = new StringBuilder("http://onesms.mobifone.vn:8082/SMSAPI/SendSMS");
        sb.append(StringPool.QUESTION);
        sb.append(SMSConstant.MSG_NAME).append(StringPool.EQUAL).append("keep_session");
        sb.append(StringPool.AMPERSAND);
        sb.append(SMSConstant.SESSION_ID).append(StringPool.EQUAL).append(session_id);
        String url = sb.toString();

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();


        // Thực hiện đọc
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        if(responseCode == HttpURLConnection.HTTP_OK){
            String responseString = response.toString();
            String[] splits = responseString.split("&");
            for (int i = 0; i < splits.length; i++) {
                if(splits[i].endsWith("SUCCESS")){
                    return "SUCCESS";
                }
            }
        }
        return null;
    }

    public static String getAccessToken(String account, String password) throws IOException {
        StringBuilder sb = new StringBuilder("http://onesms.mobifone.vn:8082/SMSAPI/SendSMS");
        sb.append(StringPool.QUESTION);
        sb.append(SMSConstant.MSG_NAME).append(StringPool.EQUAL).append("login");;
        sb.append(StringPool.AMPERSAND);
        sb.append(SMSConstant.ACCOUNT).append(StringPool.EQUAL).append(account);
        sb.append(StringPool.AMPERSAND);
        sb.append(SMSConstant.PASSWORD).append(StringPool.EQUAL).append(password);
        String url = sb.toString();

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();


        // Thực hiện đọc
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        if(responseCode == HttpURLConnection.HTTP_OK){
            String responseString = response.toString();
            String[] splits = responseString.split("&");
            for (int i = 0; i < splits.length; i++) {
                if(splits[i].startsWith("session_id")){
                    splits[i] = splits[i].replace("session_id=", StringPool.BLANK);
                    return splits[i];
                }
            }
        }
        return null;
    }
    public static String sendSMSMessage(String params, String smsAccessToken) throws IOException {

        String session_id = smsAccessToken;
        StringBuilder sb = new StringBuilder("http://onesms.mobifone.vn:8082/SMSAPI/SendSMS");
        sb.append(StringPool.QUESTION);
        sb.append(SMSConstant.MSG_NAME).append(StringPool.EQUAL).append("send_sms");;
        sb.append(StringPool.AMPERSAND);
        sb.append(SMSConstant.SESSION_ID).append(StringPool.EQUAL).append(session_id);
        sb.append(StringPool.AMPERSAND);
        sb.append(SMSConstant.SMS_ID).append(StringPool.EQUAL).append("1");
        sb.append(StringPool.AMPERSAND);
        sb.append(params);

        String url = sb.toString();
        System.out.println("url : " + url);
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();


        // Thực hiện đọc
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        if(responseCode == HttpURLConnection.HTTP_OK){
            String responseString = response.toString();
            String[] splits = responseString.split("&");
            for (int i = 0; i < splits.length; i++) {
                if(splits[i].endsWith("SUCCESS")){
                    return "SUCCESS";
                }
            }
            _log.error("Có lôi khi gửi SMS : " + responseString);
        }
        return null;
    }



}
