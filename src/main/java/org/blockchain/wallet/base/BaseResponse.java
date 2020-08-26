package org.blockchain.wallet.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author housirvip
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BaseResponse<T> {

    private Integer code;

    private String message;

    private Long total;

    private T result;
}
