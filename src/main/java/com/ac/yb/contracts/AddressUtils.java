package com.ac.yb.contracts;

import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class AddressUtils extends Contract {
    public static final String[] BINARY_ARRAY = {"60566037600b82828239805160001a607314602a57634e487b7160e01b600052600060045260246000fd5b30600052607381538281f3fe73000000000000000000000000000000000000000030146080604052600080fdfea2646970667358221220dc2d7126a9f9bb055ee299909042e92e798b26fdfcf284a07250a70e2ab2dcfe64736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60566037600b82828239805160001a607314602a5763b95aa35560e01b600052600060045260246000fd5b30600052607381538281f3fe73000000000000000000000000000000000000000030146080604052600080fdfea26469706673582212205ffe25e0a5dc7be22dfd0b650ffd927821412549ea90aeaff1dc47cbbbf4e96864736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    protected AddressUtils(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static AddressUtils load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new AddressUtils(contractAddress, client, credential);
    }

    public static AddressUtils deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(AddressUtils.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}
