package org.blockchain.wallet.base;

import org.blockchain.wallet.constant.Constant;

/**
 * @author housirvip
 */
public class PageResponse<T> extends BaseResponse<T> {

    public PageResponse(T result, long total) {

        super(Constant.SUCCESS_CODE, null, total, result);
    }
}
