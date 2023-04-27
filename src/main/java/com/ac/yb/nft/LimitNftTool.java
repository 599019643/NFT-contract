package com.ac.yb.nft;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.ac.yb.chain.BcosChainTool;
import com.ac.yb.contracts.LimitToken;
import com.ac.yb.enums.NftErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderService;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.math.BigInteger;
import java.util.List;

/**
 * 不限制个数的NFT工具类
 * @author maochaowu
 * @date 2023/4/19 16:09
 */
@Slf4j
public class LimitNftTool {

    private String accountPath;
    private String accountPwd;

    public LimitNftTool() {

    }

    public LimitNftTool(String accountPath, String accountPwd) {
        this.accountPath = accountPath;
        this.accountPwd = accountPwd;
    }

    /**
     * 部署NFT合约
     * @author maochaowu
     * @date 2023/4/19 16:13
     * @param nftName       nft名称
     * @param nftSymbol     nft描述
     * @param max           nft发行总量
     * @return java.lang.String  合约地址
     */
    public String deployNftContract(String nftName, String nftSymbol, BigInteger max) {
        Assert.notBlank(nftName, () -> new RuntimeException("nft名称不能为空"));
        Assert.notBlank(nftSymbol, () -> new RuntimeException("nft描述不能为空"));
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        if (StrUtil.isNotBlank(accountPath)) {
            cryptoSuite.loadAccount("p12", accountPath, accountPwd);
        }
        CryptoKeyPair credential = cryptoSuite.getCryptoKeyPair();
        LimitToken limitToken = null;
        try {
            limitToken = LimitToken.deploy(client, credential, nftName, nftSymbol, max);
            String contractAddress = limitToken.getContractAddress();
            log.info("NFT合约部署成功,nftName={},nftSymbol={},合约地址={}", nftName, nftSymbol, contractAddress);
            return contractAddress;
        } catch (ContractException e) {
            log.error("部署NFT合约出现异常,nftName={},nftSymbol={}", nftName, nftSymbol, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }

    /**
     * 生成NFT(铸币)
     * @author maochaowu
     * @date 2023/4/19 17:51
     * @param contractAddress      合约地址
     * @param toAddress            NFT归属人地址
     * @param nftUrl               NFT路径
     * @return java.lang.Long
     */
    public Long mint(String contractAddress, String toAddress, String nftUrl) {
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        Assert.notBlank(contractAddress, () -> new RuntimeException("nft合约地址不能为空"));
        LimitToken limitToken = load(client, contractAddress);
        try {
            TransactionReceipt transactionReceipt = limitToken.mint(toAddress, nftUrl);
            log.info("生成NFT返回回执信息:{},nftUrl={}", transactionReceipt, nftUrl);
            TransactionDecoderInterface decoder = new TransactionDecoderService(client.getCryptoSuite());
            TransactionResponse response = decoder.decodeReceiptStatus(transactionReceipt);
            int returnCode = response.getReturnCode();
            if (0 != returnCode) {
                String detailMessage = response.getReceiptMessages();
                NftErrorCode nftErrorCode = NftErrorCode.getNftErrorCode(detailMessage);
                log.error("执行NFT生成失败,返回信息为={},message={}", nftErrorCode.code, nftErrorCode.message);
                return null;
            } else {
                List<LimitToken.TransferEventResponse> transferEventResponses = limitToken.getTransferEvents(transactionReceipt);
                transferEventResponses.forEach(event -> {
                    log.info("生成NFT返回transfer事件：_from={},_to={},token={}", event._from, event._to, event._tokenId);
                });
                Tuple1<BigInteger> tuple1 = limitToken.getMintOutput(transactionReceipt);
                return tuple1.getValue1().longValue();
            }
        } catch (Exception e) {
            log.error("生成NFT失败,合约地址={},toAddress={},nftUrl={}", contractAddress, toAddress, nftUrl, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }

    /**
     * 批量授权NFT中所有权限
     * @author maochaowu
     * @date 2023/4/22 11:22
     * @param contractAddress   合约地址
     * @param operator          授权操作对象
     * @param approved          true|赋予权限、false|撤销权限
     */
    public void setApprovalForAll(String contractAddress, String operator, boolean approved) {
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        Assert.notBlank(contractAddress, () -> new RuntimeException("nft合约地址不能为空"));
        LimitToken limitToken = load(client, contractAddress);
        try {
            TransactionReceipt transactionReceipt = limitToken.setApprovalForAll(operator, approved);
            TransactionDecoderInterface decoder = new TransactionDecoderService(client.getCryptoSuite());
            TransactionResponse response = decoder.decodeReceiptStatus(transactionReceipt);
            int returnCode = response.getReturnCode();
            if (0 != returnCode) {
                String detailMessage = response.getReceiptMessages();
                NftErrorCode nftErrorCode = NftErrorCode.getNftErrorCode(detailMessage);
                log.error("执行NFT批量授权失败,返回信息为={},message={}", nftErrorCode.code, nftErrorCode.message);
            } else {
                List<LimitToken.ApprovalForAllEventResponse> approveAllEventResponses = limitToken.getApprovalForAllEvents(transactionReceipt);
                approveAllEventResponses.forEach(event -> {
                    log.info("执行NFT批量授权返回approve事件：owner={},operator={},approved={}", event._owner, event._operator, event._approved);
                });
            }
        } catch (Exception e) {
            log.error("设置NFT批量操作对象失败,合约地址={},operator={},approved={}", contractAddress, operator, approved, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }

    /**
     * 判断操作对象是否为所属人的授权对象
     * @author maochaowu
     * @date 2023/4/22 11:27
     * @param contractAddress  合约地址
     * @param owner            所属人
     * @param operator         授权操作对象
     * @return boolean
     */
    public boolean isApprovedForAll(String contractAddress, String owner, String operator) {
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        Assert.notBlank(contractAddress, () -> new RuntimeException("nft合约地址不能为空"));
        LimitToken limitToken = load(client, contractAddress);
        try {
            return limitToken.isApprovedForAll(owner, operator);
        } catch (Exception e) {
            log.error("NFT授权出现异常,to={},tokenId={}", owner, operator, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }

    /**
     * 授权对象
     * @author maochaowu
     * @date 2023/4/21 12:21
     * @param contractAddress    合约地址
     * @param to                 授权对象
     * @param tokenId            Nft token
     */
    public void approve(String contractAddress, String to, BigInteger tokenId) {
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        Assert.notBlank(contractAddress, () -> new RuntimeException("nft合约地址不能为空"));
        LimitToken limitToken = load(client, contractAddress);
        try {
            TransactionReceipt transactionReceipt = limitToken.approve(to, tokenId);
            TransactionDecoderInterface decoder = new TransactionDecoderService(client.getCryptoSuite());
            TransactionResponse response = decoder.decodeReceiptStatus(transactionReceipt);
            int returnCode = response.getReturnCode();
            if (0 != returnCode) {
                String detailMessage = response.getReceiptMessages();
                NftErrorCode nftErrorCode = NftErrorCode.getNftErrorCode(detailMessage);
                log.error("执行NFT授权失败,返回信息为={},message={}", nftErrorCode.code, nftErrorCode.message);
            } else {
                List<LimitToken.ApprovalEventResponse> approveEventResponses = limitToken.getApprovalEvents(transactionReceipt);
                approveEventResponses.forEach(event -> {
                    log.info("执行NFT授权返回approve事件：_to={},token={}", event._approved, event._tokenId);
                });
            }
        } catch (Exception e) {
            log.error("NFT授权出现异常,to={},tokenId={}", to, tokenId, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }

    /**
     * 获取合约授权对象地址
     * @author maochaowu
     * @date 2023/4/21 12:29
     * @param contractAddress    合约地址
     * @param tokenId            Nft token
     * @return java.lang.String
     */
    public String getApproved(String contractAddress, BigInteger tokenId) {
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        Assert.notBlank(contractAddress, () -> new RuntimeException("nft合约地址不能为空"));
        LimitToken limitToken = load(client, contractAddress);
        try {
            return limitToken.getApproved(tokenId);
        } catch (ContractException e) {
            log.error("获取NFT授权出现异常,tokenId={}", tokenId, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }

    /**
     * NFT转移
     * @author maochaowu
     * @date 2023/4/20 17:46
     * @param contractAddress   合约地址
     * @param from              NFT拥有者
     * @param to                转移对象
     * @param tokenId           Nft token
     */
    public void safeTransfer(String contractAddress, String from, String to, BigInteger tokenId) {
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        Assert.notBlank(contractAddress, () -> new RuntimeException("nft合约地址不能为空"));
        LimitToken limitToken = load(client, contractAddress);
        try {
            TransactionReceipt transactionReceipt = limitToken.safeTransferFrom(from, to, tokenId);
            log.info("执行NFT转移返回transactionReceipt信息为:{}", transactionReceipt);
            TransactionDecoderInterface decoder = new TransactionDecoderService(client.getCryptoSuite());
            TransactionResponse response = decoder.decodeReceiptStatus(transactionReceipt);
            int returnCode = response.getReturnCode();
            if (0 != returnCode) {
                String detailMessage = response.getReceiptMessages();
                NftErrorCode nftErrorCode = NftErrorCode.getNftErrorCode(detailMessage);
                log.error("执行NFT转移失败,返回信息为={},message={}", nftErrorCode.code, nftErrorCode.message);
            } else {
                List<LimitToken.TransferEventResponse> transferEventResponses = limitToken.getTransferEvents(transactionReceipt);
                transferEventResponses.forEach(event -> {
                    log.info("生成NFT返回transfer事件：_from={},_to={},token={}", event._from, event._to, event._tokenId);
                });
            }
        } catch (Exception e) {
            log.error("NFT转移出现异常,from={},to={},tokenId={}", from, to, tokenId, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }

    /**
     * 获取token所属地址
     * @author maochaowu
     * @date 2023/4/20 17:21
     * @param contractAddress     合约地址
     * @param tokenId             Nft token
     * @return java.lang.String
     */
    public String ownerOf(String contractAddress, BigInteger tokenId) {
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        Assert.notBlank(contractAddress, () -> new RuntimeException("nft合约地址不能为空"));
        LimitToken limitToken = load(client, contractAddress);
        try {
            return limitToken.ownerOf(tokenId);
        } catch (ContractException e) {
            log.error("获取NFT归属出现异常,tokenId={}", tokenId, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }

    /**
     * 获取用户NFT个数
     * @author maochaowu
     * @date 2023/4/20 12:37
     * @param contractAddress   合约地址
     * @param toAddress         NFT归属人地址
     * @return java.math.BigInteger
     */
    public BigInteger balanceOf(String contractAddress, String toAddress) {
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        Assert.notBlank(contractAddress, () -> new RuntimeException("nft合约地址不能为空"));
        LimitToken limitToken = load(client, contractAddress);
        try {
            return limitToken.balanceOf(toAddress);
        } catch (ContractException e) {
            log.error("获取NFT个数出现异常,toAddress={}", toAddress, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }

    /**
     * 获取NFT对应url
     * @author maochaowu
     * @date 2023/4/26 15:38
     * @param contractAddress  合约地址
     * @param tokenId          Nft token
     * @return java.lang.String
     */
    public String tokenUrl(String contractAddress, BigInteger tokenId) {
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        Assert.notBlank(contractAddress, () -> new RuntimeException("nft合约地址不能为空"));
        LimitToken limitToken = load(client, contractAddress);
        try {
            String tokenUrl = limitToken.tokenUrl(tokenId);
            return tokenUrl;
        } catch (ContractException e) {
            log.error("获取NFT对应url信息失败,合约地址={}", contractAddress, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }

    /**
     * 获取NFT合约发行最大个数
     * @author maochaowu
     * @date 2023/4/27 9:55
     * @param contractAddress  合约地址
     * @return java.math.BigInteger
     */
    public BigInteger getNftMax(String contractAddress) {
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        Assert.notBlank(contractAddress, () -> new RuntimeException("nft合约地址不能为空"));
        LimitToken limitToken = load(client, contractAddress);
        try {
            return limitToken.limit();
        } catch (ContractException e) {
            log.error("获取NFT合约发行最大个数信息失败,合约地址={}", contractAddress, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }

    /**
     * 根据合约地址获取NFT合约名称
     * @author maochaowu
     * @date 2023/4/19 16:38
     * @param contractAddress 合约地址
     * @return java.lang.String
     */
    public String getNftName(String contractAddress) {
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        Assert.notBlank(contractAddress, () -> new RuntimeException("nft合约地址不能为空"));
        LimitToken limitToken = load(client, contractAddress);
        try {
            return limitToken.getName();
        } catch (ContractException e) {
            log.error("获取NFT合约名称信息失败,合约地址={}", contractAddress, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }


    /**
     * 根据合约地址获取NFT合约描述
     * @author maochaowu
     * @date 2023/4/19 16:38
     * @param contractAddress 合约地址
     * @return java.lang.String
     */
    public String getNftSymbol(String contractAddress) {
        BcosSDK bcosSdk = BcosChainTool.bcosSDK();
        Client client = bcosSdk.getClient(1);
        Assert.notBlank(contractAddress, () -> new RuntimeException("nft合约地址不能为空"));
        LimitToken limitToken = load(client, contractAddress);
        try {
            return limitToken.getSymbol();
        } catch (ContractException e) {
            log.error("获取NFT合约名称信息失败,合约地址={}", contractAddress, e);
            throw new RuntimeException();
        } finally {
            BcosChainTool.destroy(limitToken);
        }
    }

    /**
     * 根据合约地址加载合约
     * @author maochaowu
     * @date 2023/4/19 16:33
     * @param client 区块链sdk
     * @param address 合约地址
     * @return com.ac.yb.contracts.LimitToken
     */
    private LimitToken load(Client client, String address) {
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        if (StrUtil.isNotBlank(accountPath)) {
            cryptoSuite.loadAccount("p12", accountPath, accountPwd);
        }
        CryptoKeyPair credential = cryptoSuite.getCryptoKeyPair();
        log.info("操作人账号地址:{}", credential.getAddress());
        return LimitToken.load(address, client, credential);
    }


}
