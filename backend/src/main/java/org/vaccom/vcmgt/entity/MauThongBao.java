package org.vaccom.vcmgt.entity;

import org.vaccom.vcmgt.constant.DBConstant;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = DBConstant._MAU_THONGBAO_TABLE_NAME)
public class MauThongBao implements Serializable  {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MauThongBao() {

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "LoaiThongBao")
    private String LoaiThongBao;

    @Column(name = "emailSubject")
    private String emailSubject;

    @Column(name="emailBody")
    private String emailBody;

    @Column(name="textMessage")
    private String textMessage;

    @Column(name="zaloTemplateId")
    private String zaloTemplateId;

    @Column(name="sendSMS")
    private boolean sendSMS;

    @Column(name="sendEmail")
    private boolean sendEmail;

    @Column(name="sendZalo")
    private boolean sendZalo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoaiThongBao() {
        return LoaiThongBao;
    }

    public void setLoaiThongBao(String loaiThongBao) {
        LoaiThongBao = loaiThongBao;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getZaloTemplateId() {
        return zaloTemplateId;
    }

    public void setZaloTemplateId(String zaloTemplateId) {
        this.zaloTemplateId = zaloTemplateId;
    }

    public boolean isSendSMS() {
        return sendSMS;
    }

    public void setSendSMS(boolean sendSMS) {
        this.sendSMS = sendSMS;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public boolean isSendZalo() {
        return sendZalo;
    }

    public void setSendZalo(boolean sendZalo) {
        this.sendZalo = sendZalo;
    }
}
