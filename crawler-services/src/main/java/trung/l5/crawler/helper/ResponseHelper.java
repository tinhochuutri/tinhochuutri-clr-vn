package trung.l5.crawler.helper;

import trung.l5.crawler.model.AppResponse;

/**
 * Tao ket qua tra ve cho consumer
 *
 * @author TrungNTH
 * @version 1.0
 * @since Jul 2, 2014
 */
public class ResponseHelper {

    private static final int CODE_SUCCESS = 200;
    private static final int CODE_FAIL = 201;

    public static AppResponse makeSuccess(Object data) {
        return new AppResponse(CODE_SUCCESS, null, data);
    }

    public static AppResponse makeFailure(String message, Object data) {
        return new AppResponse(CODE_FAIL, message, data);
    }

    public static AppResponse makeFailure(String message) {
        return makeFailure(message, null);
    }

    public static AppResponse makeFailure(Object data) {
        return makeFailure(null, data);
    }
}
