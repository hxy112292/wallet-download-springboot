package org.blockchain.wallet.base;

import org.blockchain.wallet.constant.Constant;

/**
 * @author housirvip
 */
public class ResultResponse<T> extends BaseResponse<T> {

    public ResultResponse(T result) {

        super(Constant.SUCCESS_CODE, null, null, result);
    }
}
