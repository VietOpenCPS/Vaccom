package org.vaccom.vcmgt.util;




import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaccom.vcmgt.constant.ZaloConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ZaloNotificationUtil {


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
            Long error = response.get(ZaloConstant.error).asLong();
            String message = response.get(ZaloConstant.message).asText();
            if(error == 0 && message.equals("Success")){
                return HttpResult;
            } else {
                return HttpURLConnection.HTTP_INTERNAL_ERROR;
            }

        } else {
            return HttpURLConnection.HTTP_INTERNAL_ERROR;
        }
    }


    public static String convertPhoneNumber(String phoneNumber){
        if(phoneNumber == null || phoneNumber.isEmpty()){
            return null;
        }
        phoneNumber = phoneNumber.trim();
        if (phoneNumber.startsWith("0")){
            StringBuilder build = new StringBuilder(phoneNumber);
            build.deleteCharAt(0);
            phoneNumber = "84" + build;
            return phoneNumber;
        } else if (phoneNumber.startsWith("+84")) {
            StringBuilder build = new StringBuilder(phoneNumber);
            build.deleteCharAt(0);
            return build.toString();
        } else if (phoneNumber.startsWith("84")){
            return phoneNumber;
        } else {
            return null;
        }
    }
}
