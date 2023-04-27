package com.ac.yb.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.DynamicBytes;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.generated.Bytes4;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class ERC721TokenReceiver extends Contract {
    public static final String[] BINARY_ARRAY = {};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_operator\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"},{\"internalType\":\"bytes\",\"name\":\"_data\",\"type\":\"bytes\"}],\"name\":\"onERC721Received\",\"outputs\":[{\"internalType\":\"bytes4\",\"name\":\"\",\"type\":\"bytes4\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_ONERC721RECEIVED = "onERC721Received";

    protected ERC721TokenReceiver(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt onERC721Received(String _operator, String _from, BigInteger _tokenId, byte[] _data) {
        final Function function = new Function(
                FUNC_ONERC721RECEIVED, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_operator), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_from), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId), 
                new org.fisco.bcos.sdk.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] onERC721Received(String _operator, String _from, BigInteger _tokenId, byte[] _data, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ONERC721RECEIVED, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_operator), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_from), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId), 
                new org.fisco.bcos.sdk.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForOnERC721Received(String _operator, String _from, BigInteger _tokenId, byte[] _data) {
        final Function function = new Function(
                FUNC_ONERC721RECEIVED, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_operator), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_from), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId), 
                new org.fisco.bcos.sdk.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple4<String, String, BigInteger, byte[]> getOnERC721ReceivedInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ONERC721RECEIVED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple4<String, String, BigInteger, byte[]>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(), 
                (byte[]) results.get(3).getValue()
                );
    }

    public Tuple1<byte[]> getOnERC721ReceivedOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ONERC721RECEIVED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes4>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public static ERC721TokenReceiver load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ERC721TokenReceiver(contractAddress, client, credential);
    }

    public static ERC721TokenReceiver deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(ERC721TokenReceiver.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}
