package org.vaccom.vcmgt.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.vaccom.vcmgt.security.impl.RandomString;

import com.liferay.petra.string.StringPool;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Optional;

public class VaccomUtil {
	
	public static final int MOIDANGKY = 0;
	public static final int DANGKYCHINHTHUC = 1;
	public static final int XOADANGKY = 2;
	public static final int DATIEM = 3;
	public static final int DACHUYENSANGPHIEUHEN = 4;
	
	public static final int CHUAKIEMTRA = 0;
	public static final int KIEMTRAKHONGTRUNG = 1;
	public static final int KIEMTRACOTRUNG = 2;
	
	public static final int DANGMO = 0;
	public static final int DADONG = 1;
	
	public static final int DUKIEN = 0;
	public static final int HENGOICHOXACNHAN = 1;
	public static final int HENDAXACNHAN = 2;
	public static final int DACHECKIN = 3;
	
	public static final int DATIEMXONG = 4;
	public static final int CHUADUOCTIEM = 5;
	public static final int XACNHANKHONGDEN = 5;

	public static final int DADUYET = 1;

	
	public static String generateQRCode(String prefix, int unitLength) {
		RandomString random = new RandomString(unitLength);
		String code = random.nextString() + StringPool.DASH + random.nextString() + StringPool.DASH
				+ random.nextString() + StringPool.DASH + random.nextString() + StringPool.DASH + random.nextString();
		return prefix.toLowerCase() + StringPool.DASH + code.toLowerCase();
	}

    /**
     * Converted a code to an base 64 encoded string (as image format) with size (128x128px)
     *
     * @param qrCode
     * @return empty iff any exceptions occur
     */
    public static Optional<String> generateEncodedQRImage(String qrCode) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCode, BarcodeFormat.QR_CODE, 128, 128);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
            return Optional.of(new String(Base64.getEncoder().encode(outputStream.toByteArray())));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
