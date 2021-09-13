package org.vaccom.vcmgt.util;




import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.petra.string.StringPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaccom.vcmgt.constant.ZaloConstant;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ZaloNotificationUtil {
    private static final Logger _log = LoggerFactory.getLogger(ZaloNotificationUtil.class);


    public static final String urlPattern = "https://business.openapi.zalo.me/message/template";


    public static int sendNotification(String jsonObject, String oaid_access_token) throws IOException {
        String jsonObjectParams = jsonObject.toString();

        URL url = new URL(urlPattern);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Charset", "utf-8");
        connection.setRequestProperty("access_token", oaid_access_token);
        connection.setReadTimeout(60 * 1000);

        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(jsonObjectParams);
        wr.flush();

        //display what returns the POST request

        StringBuilder sb = new StringBuilder();
        int HttpResult = connection.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode response = mapper.readTree(sb.toString());
            int error = response.get(ZaloConstant.error).asInt();
            return error;

        } else {
            return HttpURLConnection.HTTP_INTERNAL_ERROR;
        }
    }


    public static String convertPhoneNumber(String phoneNumber){
        if(phoneNumber == null || phoneNumber.isEmpty()){
            return null;
        }
        phoneNumber = phoneNumber.trim();
        phoneNumber = phoneNumber.replace(StringPool.SPACE, StringPool.BLANK);

        if (phoneNumber.startsWith("0")){
            StringBuilder build = new StringBuilder(phoneNumber);
            build.deleteCharAt(0);
            phoneNumber = "84" + build;
            return phoneNumber;
        } else if (phoneNumber.startsWith("+84")) {
            StringBuilder build = new StringBuilder(phoneNumber);
            build.deleteCharAt(0);
            return build.toString();
        } else if (phoneNumber.startsWith("84") && phoneNumber.length() == 11){
            return phoneNumber;
        } else if (phoneNumber.startsWith("9")){
            StringBuilder build = new StringBuilder(phoneNumber);
            phoneNumber = "84" + build;
            return phoneNumber;
        } else if (phoneNumber.startsWith("8") && phoneNumber.length() == 9){
            StringBuilder build = new StringBuilder(phoneNumber);
            phoneNumber = "84" + build;
            return phoneNumber;
        } else if (phoneNumber.startsWith("7")){
            StringBuilder build = new StringBuilder(phoneNumber);
            phoneNumber = "84" + build;
            return phoneNumber;
        } else if (phoneNumber.startsWith("5")){
            StringBuilder build = new StringBuilder(phoneNumber);
            phoneNumber = "84" + build;
            return phoneNumber;
        } else if (phoneNumber.startsWith("3")){
            StringBuilder build = new StringBuilder(phoneNumber);
            phoneNumber = "84" + build;
            return phoneNumber;
        } else {
            return null;
        }
    }
}
