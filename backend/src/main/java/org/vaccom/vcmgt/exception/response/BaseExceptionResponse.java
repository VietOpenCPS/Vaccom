package org.vaccom.vcmgt.exception.response;

import com.liferay.petra.string.StringPool;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author TrinhNX
 * @since 8/27/2021
 */
@Setter
@Getter
@AllArgsConstructor
public class BaseExceptionResponse {
    private String message;
    public static final BaseExceptionResponse EMPTY = new BaseExceptionResponse(StringPool.BLANK);
}
