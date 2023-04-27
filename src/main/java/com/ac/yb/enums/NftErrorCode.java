package com.ac.yb.enums;

import lombok.AllArgsConstructor;

/**
 * NFT error code msg
 * @author maochaowu
 * @date 2023/4/20 20:19
 */
@AllArgsConstructor
public enum NftErrorCode {

    DEFAULT_ERROR("000022", "其他异常"),

    ZERO_ADDRESS("003001", "0地址"),

    NOT_VALID_NFT("003002", "不合法的NFT"),

    NOT_OWNER_OR_OPERATOR("003003", "不是本人或操作者"),

    NOT_OWNER_APPROVED_OR_OPERATOR("003004", "不是本人或授权者"),

    NOT_ABLE_TO_RECEIVE_NFT("003005", "不允许通过合约中转"),

    NFT_ALREADY_EXISTS("003006", "NFT已经存在"),

    NOT_OWNER("003007", "NFT所属不是本人"),

    IS_OWNER("003008", "不能授权给本人");

    public String code;
    public String message;

    public static NftErrorCode getNftErrorCode(String code) {
        for (NftErrorCode errorCode : NftErrorCode.values()) {
            if (errorCode.code.equals(code)) {
                return errorCode;
            }
        }
        return DEFAULT_ERROR;
    }
}
