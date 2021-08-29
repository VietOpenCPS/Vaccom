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

    public enum AuthorityType {
        ADMIN, // Nguoi quan tri
        OFFICER_REGION, // Can bo dia ban
        OFFICER_CARE, // Can bo y te
        USER, // Nguoi tiem chung
        VISITOR // Khach vang lai
    }
}
