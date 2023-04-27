package com.ac.yb.contracts;

import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Counters extends Contract {
    public static final String[] BINARY_ARRAY = {"60566037600b82828239805160001a607314602a57634e487b7160e01b600052600060045260246000fd5b30600052607381538281f3fe73000000000000000000000000000000000000000030146080604052600080fdfea2646970667358221220395cd640142aaf5319859e938a66d75a5b5abb08661a81483fad93281cd0518264736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60566037600b82828239805160001a607314602a5763b95aa35560e01b600052600060045260246000fd5b30600052607381538281f3fe73000000000000000000000000000000000000000030146080604052600080fdfea26469706673582212200157ee07fb0eb02d5d2e73dff57d6cc69cf9823dc993d0bba80bb392d5bf913864736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    protected Counters(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static Counters load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Counters(contractAddress, client, credential);
    }

    public static Counters deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Counters.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}
