package org.vaccom.vcmgt.constant;

import lombok.experimental.UtilityClass;

import java.util.Locale;

/**
 * @author TrinhNX
 * @since 8/27/2021
 */
@UtilityClass
public class AppConstant {

    public static final Locale DEFAULT_LOCALE = new Locale("vi_VN");

    public enum AuthorizationType {
        BEARER
    }
}
