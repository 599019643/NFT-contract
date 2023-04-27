package com.ac.yb.chain;

import cn.hutool.core.util.ObjectUtil;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.eventsub.EventSubscribe;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Bcos Chain工具类
 * @author maochaowu
 * @date 2023/4/19 16:14
 */
public class BcosChainTool {

    public static BcosSDK bcosSDK = null;

    public static final Object MONITOR = new Object();

    /**
     * 获取区块链Sdk
     * @author maochaowu
     * @date 2023/4/19 16:18
     * @return org.fisco.bcos.sdk.BcosSDK
     */
    public static BcosSDK bcosSDK() {
        if (ObjectUtil.isNotNull(bcosSDK)) {
            return bcosSDK;
        }
        synchronized (MONITOR) {
            if (ObjectUtil.isNotNull(bcosSDK)) {
                return bcosSDK;
            }
            ClassPathResource classPathResource = new ClassPathResource("config.toml");
            try {
                String configPath = classPathResource.getFile().getPath();
                bcosSDK = BcosSDK.build(configPath);
                return bcosSDK;
            } catch (IOException e) {
                throw new IllegalStateException("区块链装配异常", e);
            }
        }
    }

    /**
     * 关闭合约
     * @author maochaowu
     * @date 2023/4/19 16:48
     * @param contract 合约
     */
    public static void destroy(Contract contract) {
        if (null != contract) {
            try {
                Field field = Contract.class.getDeclaredField("eventSubscribe");
                field.setAccessible(true);
                EventSubscribe eventSubscribe = (EventSubscribe) field.get(contract);
                eventSubscribe.stop();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * 关闭客户端
     * @author maochaowu
     * @date 2023/4/4 16:35
     */
    public static void destroy() {
        if (ObjectUtil.isNotNull(bcosSDK)) {
            bcosSDK.stopAll();
        }
    }
}
