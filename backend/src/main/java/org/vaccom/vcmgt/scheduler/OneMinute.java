package org.vaccom.vcmgt.scheduler;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.vaccom.vcmgt.action.HangChoThongBaoAction;
import org.vaccom.vcmgt.action.UyBanNhanDanAction;
import org.vaccom.vcmgt.constant.SMSConstant;
import org.vaccom.vcmgt.constant.ZaloConstant;
import org.vaccom.vcmgt.entity.HangChoThongBao;
import org.vaccom.vcmgt.entity.UyBanNhanDan;
import org.vaccom.vcmgt.util.SMSUtil;
import org.vaccom.vcmgt.util.VNCharacterUtils;
import org.vaccom.vcmgt.util.ZaloNotificationUtil;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class OneMinute {

    @Value("${server.domain.url}")
    private String domainUrl;


    @Autowired
    private UyBanNhanDanAction uyBanNhanDanaction;

    @Autowired
    private HangChoThongBaoAction hangChoThongBaoAction;

    private static final Logger log = LoggerFactory.getLogger(OneMinute.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final int hourStart = 6;
    private static final int minuteStart = 1;
    private static final int hourEnd = 21;
    private static final int minuteEnd = 59;


    @Scheduled(fixedRate = 60000)
    public void doAction() throws Exception {

        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour_of_day = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if(hour_of_day >= hourStart && hour_of_day <= hourEnd && minute >= minuteStart &&  minute <= minuteEnd){
            log.info("VACCOM SCHEDULER SEND NOTIFICATION START: " + dateFormat.format(new Date()));

            List<HangChoThongBao> ThongBaoChuaGui = hangChoThongBaoAction.findByIsSentIsReady(false, true);
            for (HangChoThongBao hangChoThongBao : ThongBaoChuaGui) {
                try {
                    if (hangChoThongBao.getUyBanNhanDanId() > 0 && Validator.isNotNull(hangChoThongBao.getToTelNo())) {
                        UyBanNhanDan uyBanNhanDan = uyBanNhanDanaction.findById(hangChoThongBao.getUyBanNhanDanId());
                        if (Validator.isNotNull(uyBanNhanDan) && Validator.isNotNull(uyBanNhanDan.getNotificationConfig())) {
                            ObjectMapper mapper = new ObjectMapper();
                            JsonNode configData = null;
                            JsonNode zalo = null;
                            JsonNode sms = null;
                            try {
                                configData = mapper.readTree(uyBanNhanDan.getNotificationConfig());
                                zalo = configData.get("zalo");
                                sms = configData.get("sms");
                            } catch (Exception ex) {
                                log.error(ex.getMessage());
                            }

                            if (Validator.isNotNull(zalo)) {
                                String oaid_access_token = null;
                                String loaiThongBao = null;
                                String template_id = null;
                                String payload = null;
                                JsonNode payloadJson = null;
                                try {
                                    oaid_access_token = zalo.get(ZaloConstant.Access_Token.toLowerCase()).asText();
                                    loaiThongBao = hangChoThongBao.getLoaiThongBao();
                                    payload = hangChoThongBao.getPayload();
                                    payloadJson = mapper.readTree(payload);
                                    if (loaiThongBao.equals(ZaloConstant.Loai_Hen_TiemChung)) {
                                        template_id = zalo.get(ZaloConstant.Loai_Hen_TiemChung.toLowerCase()).asText();
                                    } else if(loaiThongBao.equals(ZaloConstant.Loai_Giay_Di_Duong)){
                                        template_id = zalo.get(ZaloConstant.Loai_Giay_Di_Duong.toLowerCase()).asText();
                                    }
                                } catch (Exception ex) {
                                    log.error(ex.getMessage());
                                }
                                if (Validator.isNotNull(template_id) && Validator.isNotNull(oaid_access_token) && Validator.isNotNull(payloadJson)) {
                                    String phone = hangChoThongBao.getToTelNo();
                                    String tracking_id = "tracking_id";
                                    ObjectNode body = mapper.createObjectNode();
                                    body.put(ZaloConstant.template_id, template_id);
                                    body.put(ZaloConstant.phone, phone);
                                    body.put(ZaloConstant.tracking_id, tracking_id);

                                    JsonNode template_data_json = mapper.readTree(payload);
                                    body.put(ZaloConstant.template_data, template_data_json);
                                    Integer code = 1;
                                    try {
                                        code = ZaloNotificationUtil.sendNotification(body.toString(), oaid_access_token);
                                    } catch (Exception ex) {
                                        log.error(ex.getMessage());
                                    }
                                    // Gửi thành công ZALO ZNS
                                    if (code == 0) {
                                        hangChoThongBao.setSent(true);
                                        hangChoThongBao.setStatus(ZaloConstant.GUI_ZALO_THANH_CONG);
                                        hangChoThongBao.setErrorCodeZalo(code);
                                        hangChoThongBaoAction.update(hangChoThongBao);
                                    } else {

                                        // Gửi thất bại ZNS - chuyển đổi sang gửi SMS
                                        if (Validator.isNotNull(sms)) {
                                            String paramSMS = null;
                                            String toTelNo = hangChoThongBao.getToTelNo();
                                            StringBuilder build = new StringBuilder(toTelNo);
                                            build.delete(0, 2);
                                            String phoneNumber = build.toString();
                                            // Thực hiện gửi tin nhắn SMS
                                            if (loaiThongBao.equals(ZaloConstant.Loai_Hen_TiemChung)) {
                                                String ngayGioSplit = payloadJson.get(ZaloConstant.NgayTiemChung).asText();
                                                String[] splitArray = ngayGioSplit.split(StringPool.SPACE);
                                                String ngayHen = URLEncoder.encode(splitArray[0], "UTF-8");
                                                String gioHen = URLEncoder.encode(splitArray[1], "UTF-8");

                                                paramSMS = SMSConstant.MSISDN + StringPool.EQUAL + phoneNumber + StringPool.AMPERSAND +
                                                        SMSConstant.SMS_TEMPLATE_CODE + StringPool.EQUAL + sms.get(ZaloConstant.Loai_Hen_TiemChung.toLowerCase()).asText() + StringPool.AMPERSAND +
                                                        "param1" + StringPool.EQUAL + URLEncoder.encode(payloadJson.get(ZaloConstant.HoVaTen).asText(), "UTF-8") + StringPool.AMPERSAND +
                                                        "param2" + StringPool.EQUAL + payloadJson.get(ZaloConstant.LanTiem).asText() + StringPool.AMPERSAND +
                                                        "param3" + StringPool.EQUAL + gioHen + StringPool.AMPERSAND +
                                                        "param4" + StringPool.EQUAL + ngayHen + StringPool.AMPERSAND +
                                                        "param5" + StringPool.EQUAL + URLEncoder.encode(payloadJson.get(ZaloConstant.DiaDiem).asText(), "UTF-8");
                                            } else if (loaiThongBao.equals(ZaloConstant.Loai_Giay_Di_Duong)) {
                                                String LinkGiayDiDuong = URLEncoder.encode(domainUrl + "/#/pages/giay-di-duong/" + payloadJson.get(ZaloConstant.QrCodeID).asText(), "UTF-8");

                                                paramSMS = SMSConstant.MSISDN + StringPool.EQUAL + phoneNumber + StringPool.AMPERSAND +
                                                        SMSConstant.SMS_TEMPLATE_CODE + StringPool.EQUAL + sms.get(ZaloConstant.Loai_Giay_Di_Duong.toLowerCase()).asText() + StringPool.AMPERSAND +
                                                        "param1" + StringPool.EQUAL + URLEncoder.encode(VNCharacterUtils.removeAccent(payloadJson.get(ZaloConstant.DonViCap).asText()), "UTF-8")
                                                        + StringPool.AMPERSAND + "param2" + StringPool.EQUAL + URLEncoder.encode(VNCharacterUtils.removeAccent(payloadJson.get("HovaTen").asText()), "UTF-8")
                                                        + StringPool.AMPERSAND + "param3" + StringPool.EQUAL + LinkGiayDiDuong;
                                            }
                                            String smsAccessToken = sms.get(ZaloConstant.Access_Token.toLowerCase()).asText();
                                            if (Validator.isNotNull(smsAccessToken) && Validator.isNotNull(paramSMS)) {
                                                String status = null;
                                                try {
                                                    status = SMSUtil.sendSMSMessage(paramSMS, smsAccessToken);
                                                } catch (Exception ex) {
                                                    log.error(ex.getMessage());
                                                }
                                                if (Validator.isNotNull(status) && status.equals("SUCCESS")) {
                                                    hangChoThongBao.setSent(true);
                                                    hangChoThongBao.setStatus(ZaloConstant.GUI_SMS_THANH_CONG);
                                                    hangChoThongBao.setErrorCodeZalo(code);
                                                    hangChoThongBaoAction.update(hangChoThongBao);
                                                } else {
                                                    hangChoThongBao.setSent(true);
                                                    hangChoThongBao.setReady(false);
                                                    hangChoThongBao.setStatus(ZaloConstant.GUI_THAT_BAI);
                                                    hangChoThongBao.setErrorCodeZalo(code);
                                                    hangChoThongBaoAction.update(hangChoThongBao);
                                                }
                                            }
                                        } else {
                                            hangChoThongBao.setSent(true);
                                            hangChoThongBao.setReady(false);
                                            hangChoThongBao.setStatus(ZaloConstant.GUI_THAT_BAI);
                                            hangChoThongBao.setErrorCodeZalo(code);
                                            hangChoThongBaoAction.update(hangChoThongBao);
                                        }
                                    }
                                } else {
                                    log.error("template_id  "+template_id+" - oaid_access_token "+oaid_access_token+"- payloadJson " + payloadJson);
                                }
                            }
                        } else {
                            log.error("Không tìm thấy cấu hình của UBND có ID = " + hangChoThongBao.getUyBanNhanDanId());
                        }
                    } else {
                        log.error("Không có số điện thoại hoặc UBND ID < 0 bản ghi : " + hangChoThongBao.getId());
                        hangChoThongBao.setSent(true);
                        hangChoThongBao.setReady(false);
                        hangChoThongBao.setStatus(ZaloConstant.GUI_THAT_BAI);
                        hangChoThongBao.setErrorCodeZalo(-1);
                        hangChoThongBaoAction.update(hangChoThongBao);
                    }
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                    log.error("Lỗi khi thực hiện gửi tin nhắn bản ghi hàng đợi : " + hangChoThongBao.getId());
                }
            }
        }



    }
}
