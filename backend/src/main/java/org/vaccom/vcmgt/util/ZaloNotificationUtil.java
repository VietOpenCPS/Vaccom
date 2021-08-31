package org.vaccom.vcmgt.util;



import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class ZaloNotificationUtil {
    public static final String urlPattern = "https://business.openapi.zalo.me/message/template";
    public static final String oaid_access_token = "FIzhTjkT76exBsTytPOMO3uaP7B6va0jA5ffEB6SHdHAS302vjSxVqTjJ0p5e651LcL2NDYkL1DjQ5D-rhH64szGTmAFlNjUQGHRJCkzK2yfSaX9khD50IaCO7F6nn4QGImPSy394cvk2pyixTGURN9hQoQNgtrvScv56CQmSrHOJK94oxPn9IeELrBEi6qGANjdT8gY764hJYiXYeSxSJXdC6UWXaGlFLHqUQRfJWaIAKj-gUynBZaE1cMBrIyG9pGaVwFfFXv53p1QyBOd9q5t4478hJOHref29TYB7sC";

    public static int sendNotification(JSONObject jsonObject) throws IOException {
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
            return HttpResult;
        } else {
            return connection.getResponseCode();
        }
    }
    public static String sendNotification(String jsonObject) throws IOException {
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
            return sb.toString();
        } else {
            return connection.getResponseMessage();
        }
    }

    public static String convertPhoneNumber(String phoneNumber){
//        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
//        Phonenumber.PhoneNumber swissNumberProto = null;
//        try {
//            swissNumberProto = phoneUtil.parse(phoneNumber, "VI");
//        } catch (NumberParseException e) {
//            System.err.println("NumberParseException was thrown: " + e.toString());
//        }
//        if(swissNumberProto != null ){
//            String phoneInternational = phoneUtil.format(swissNumberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
////            phoneInternational = phoneInternational.replace("+" , StringPool.BLANK);
//            System.out.println("phoneInternational : "  +phoneInternational);
//            return phoneInternational;
//        } else {
//            return phoneNumber;
//        }
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
