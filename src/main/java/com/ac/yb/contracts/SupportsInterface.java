package com.ac.yb.contracts;

import java.util.Arrays;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class SupportsInterface extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506301ffc9a760e01b60009081526020527f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c5805460ff1916600117905560d08061005b6000396000f3fe6080604052348015600f57600080fd5b506004361060285760003560e01c806301ffc9a714602d575b600080fd5b60576038366004606b565b6001600160e01b03191660009081526020819052604090205460ff1690565b604051901515815260200160405180910390f35b600060208284031215607c57600080fd5b81356001600160e01b031981168114609357600080fd5b939250505056fea2646970667358221220745909f3a560980f253405c510a2b3cfcf984e27707a8520980f9399cbbf082864736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506301ffc9a760e01b60009081526020527fc2a7148da09a39e4c44d4e2590127bd39e01de9d8999efdaac291c1ffb0d34e4805460ff1916600117905560d08061005b6000396000f3fe6080604052348015600f57600080fd5b506004361060285760003560e01c8063ea7eb79814602d575b600080fd5b60576038366004606b565b6001600160e01b03191660009081526020819052604090205460ff1690565b604051901515815260200160405180910390f35b600060208284031215607c57600080fd5b81356001600160e01b031981168114609357600080fd5b939250505056fea2646970667358221220a5eb2519f749b896104fa71bc104168ef596586ea65381262f8c1d20fe8c3b8764736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"inputs\":[{\"internalType\":\"bytes4\",\"name\":\"_interfaceID\",\"type\":\"bytes4\"}],\"name\":\"supportsInterface\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    protected SupportsInterface(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public Boolean supportsInterface(byte[] _interfaceID) throws ContractException {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes4(_interfaceID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public static SupportsInterface load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new SupportsInterface(contractAddress, client, credential);
    }

    public static SupportsInterface deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(SupportsInterface.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}
