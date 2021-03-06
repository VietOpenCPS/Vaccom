package org.vaccom.vcmgt.entity;

import org.vaccom.vcmgt.constant.DBConstant;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = DBConstant._HANGCHO_THONGBAO_TABLE_NAME)
public class HangChoThongBao implements Serializable {
    private static final long serialVersionUID = 1L;

    public HangChoThongBao() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "UyBanNhanDanID")
    private long uyBanNhanDanId;

    @Column(name = "LoaiThongBao")
    private String LoaiThongBao;

    @Column(name = "toEmail")
    private String toEmail;

    @Column(name = "toTelNo")
    private String toTelNo;

    @Column(name = "payload")
    private String payload;

    @Column(name = "isReady")
    private boolean isReady;

    @Column(name = "isSent")
    private boolean isSent;

    @Column(name = "status")
    private long status;

    @Column(name = "mappingKey")
    private long mappingKey;

    @Column(name = "errorCodeZalo")
    private int errorCodeZalo;

    @Column(name = "createDate")
    private Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getErrorCodeZalo() {
        return errorCodeZalo;
    }

    public void setErrorCodeZalo(int errorCodeZalo) {
        this.errorCodeZalo = errorCodeZalo;
    }

    public long getMappingKey() {
        return mappingKey;
    }

    public void setMappingKey(long mappingKey) {
        this.mappingKey = mappingKey;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

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

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getToTelNo() {
        return toTelNo;
    }

    public void setToTelNo(String toTelNo) {
        this.toTelNo = toTelNo;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public long getUyBanNhanDanId() {
        return uyBanNhanDanId;
    }

    public void setUyBanNhanDanId(long uyBanNhanDanId) {
        this.uyBanNhanDanId = uyBanNhanDanId;
    }
}
