package com.ac.yb.application;

import cn.hutool.core.util.IdUtil;
import com.ac.yb.chain.BcosChainTool;
import com.ac.yb.nft.LimitNftTool;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigInteger;

/**
 * 限制个数的NFT应用
 * @author maochaowu
 * @date 2023/4/19 16:40
 */
@Slf4j
public class LimitNftApplication {
    /**NFT-名称*/
    private static String NFT_NAME = "落霞孤鹜图";
    /**NFT-描述*/
    private static String NFT_SYMBOL = "该画是唐寅的优秀山水画之一，此画描绘的是高岭峻柳，水阁临江，有一人正坐在阁中，观眺落霞孤鹜，一书童相伴其后，整幅画的境界沉静，蕴含文人画气质";
    /**NFT-可发行的个数*/
    private static BigInteger MAX = BigInteger.valueOf(10L);

    /**NFT-合约地址*/
    private static String NFT_ADDRESS = "0x84bd0f85ee54e2cd248615c00833269bc80e616c";

    private static String TRANSFER_ADDRESS = "0x4e5759cafcfdd899c0de255e0868a9d0e150cd93";

    private static String TO_ADDRESS = "0xf36c936ab5fc5c6ed294913d87186d156010bbcc";

    private static String APPROVE_ADDRESS = "0x4fa154dd45d9c63c43c43dbb45fffae36ac25deb";

    private static String ACCOUNT_PWD = "123456";

    public static void main(String[] args) throws IOException {
        //默认账号加载
        LimitNftTool limitNftTool = new LimitNftTool();

        //指定账号加载
        //ClassPathResource classPathResource = new ClassPathResource("account/gm/0xf36c936ab5fc5c6ed294913d87186d156010bbcc");
        //String accountPath = classPathResource.getFile().getPath();
        //LimitNftTool limitNftTool = new LimitNftTool(accountPath, ACCOUNT_PWD);

        //1.部署NFT合约
        //limitNftTool.deployNftContract(NFT_NAME, NFT_SYMBOL, MAX);

        //2.读取合约信息
        //String name = limitNftTool.getNftName(NFT_ADDRESS);
        //String symbol = limitNftTool.getNftSymbol(NFT_ADDRESS);
        //BigInteger limit = limitNftTool.getNftMax(NFT_ADDRESS);
        //log.info("合约Nft名称={},Nft描述={},NFT发行最大个数={}", name, symbol, limit);

        //3.发布token
        String tokenUrl = IdUtil.getSnowflakeNextIdStr();
        Long tokenId = limitNftTool.mint(NFT_ADDRESS, TO_ADDRESS, tokenUrl);
        System.out.println("生成tokenId=" + tokenId);

        //4.获取token对应的url
        //BigInteger tokenId = BigInteger.valueOf(0L);
        //String tokenUrl = limitNftTool.tokenUrl(NFT_ADDRESS, tokenId);
        //System.out.println("tokenId=" + tokenId + " 对应的tokenUrl=" + tokenUrl);

        //5.NFT个数
        //BigInteger nftCount = limitNftTool.balanceOf(NFT_ADDRESS, TO_ADDRESS);
        //System.out.println(TO_ADDRESS + " nft个数为: " + nftCount);

        //6.获取token所属
        //BigInteger tokenId = BigInteger.valueOf(0L);
        //String address = limitNftTool.ownerOf(NFT_ADDRESS, tokenId);
        //System.out.println(tokenId + "所属地址为: " + address);

        //7.转赠
        //BigInteger tokenId = BigInteger.valueOf(0L);
        //limitNftTool.safeTransfer(NFT_ADDRESS, TRANSFER_ADDRESS, TO_ADDRESS, tokenId);

        //8.授权
        //BigInteger tokenId = BigInteger.valueOf(0L);
        //limitNftTool.approve(NFT_ADDRESS, APPROVE_ADDRESS, tokenId);

        //9.获取授权地址
        //BigInteger tokenId = BigInteger.valueOf(0L);
        //String approveAddress = limitNftTool.getApproved(NFT_ADDRESS, tokenId);
        //System.out.println("NFT TOKEN" + tokenId + "授权地址为：" + approveAddress);


        //10.批量授权
        //limitNftTool.setApprovalForAll(NFT_ADDRESS, TO_ADDRESS, true);

        //11.获取批量授权
        //boolean approved = limitNftTool.isApprovedForAll(NFT_ADDRESS, APPROVE_ADDRESS, TO_ADDRESS);
        //System.out.println("批量授权结果：" + approved);

        //结束
        BcosChainTool.destroy();
    }
}
