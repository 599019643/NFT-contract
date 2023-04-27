package com.ac.yb.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.DynamicBytes;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class ERC721 extends Contract {
    public static final String[] BINARY_ARRAY = {};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_approved\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_operator\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"bool\",\"name\":\"_approved\",\"type\":\"bool\"}],\"name\":\"ApprovalForAll\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_approved\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"getApproved\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_operator\",\"type\":\"address\"}],\"name\":\"isApprovedForAll\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"ownerOf\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"safeTransferFrom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"},{\"internalType\":\"bytes\",\"name\":\"_data\",\"type\":\"bytes\"}],\"name\":\"safeTransferFrom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_operator\",\"type\":\"address\"},{\"internalType\":\"bool\",\"name\":\"_approved\",\"type\":\"bool\"}],\"name\":\"setApprovalForAll\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_SAFETRANSFERFROM = "safeTransferFrom";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    protected ERC721(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._approved = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeApprovalEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(APPROVAL_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeApprovalEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(APPROVAL_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeApprovalForAllEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(APPROVALFORALL_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeApprovalForAllEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(APPROVALFORALL_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeTransferEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(TRANSFER_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeTransferEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(TRANSFER_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public TransactionReceipt approve(String _approved, BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_approved), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] approve(String _approved, BigInteger _tokenId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_approved), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForApprove(String _approved, BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_approved), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getApproveInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_APPROVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public BigInteger balanceOf(String _owner) throws ContractException {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public String getApproved(BigInteger _tokenId) throws ContractException {
        final Function function = new Function(FUNC_GETAPPROVED, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public Boolean isApprovedForAll(String _owner, String _operator) throws ContractException {
        final Function function = new Function(FUNC_ISAPPROVEDFORALL, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_owner), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_operator)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public String ownerOf(BigInteger _tokenId) throws ContractException {
        final Function function = new Function(FUNC_OWNEROF, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt safeTransferFrom(String _from, String _to, BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_from), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_to), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] safeTransferFrom(String _from, String _to, BigInteger _tokenId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_from), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_to), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSafeTransferFrom(String _from, String _to, BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_from), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_to), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple3<String, String, BigInteger> getSafeTransferFromAddressAddressUint256Input(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, String, BigInteger>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue()
                );
    }

    public TransactionReceipt safeTransferFrom(String _from, String _to, BigInteger _tokenId, byte[] _data) {
        final Function function = new Function(
                FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_from), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_to), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId), 
                new org.fisco.bcos.sdk.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] safeTransferFrom(String _from, String _to, BigInteger _tokenId, byte[] _data, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_from), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_to), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId), 
                new org.fisco.bcos.sdk.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSafeTransferFrom(String _from, String _to, BigInteger _tokenId, byte[] _data) {
        final Function function = new Function(
                FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_from), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_to), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId), 
                new org.fisco.bcos.sdk.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple4<String, String, BigInteger, byte[]> getSafeTransferFromAddressAddressUint256BytesInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SAFETRANSFERFROM, 
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

    public TransactionReceipt setApprovalForAll(String _operator, Boolean _approved) {
        final Function function = new Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_operator), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_approved)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] setApprovalForAll(String _operator, Boolean _approved, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_operator), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_approved)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetApprovalForAll(String _operator, Boolean _approved) {
        final Function function = new Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_operator), 
                new org.fisco.bcos.sdk.abi.datatypes.Bool(_approved)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, Boolean> getSetApprovalForAllInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, Boolean>(

                (String) results.get(0).getValue(), 
                (Boolean) results.get(1).getValue()
                );
    }

    public TransactionReceipt transferFrom(String _from, String _to, BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_from), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_to), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] transferFrom(String _from, String _to, BigInteger _tokenId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_from), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_to), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForTransferFrom(String _from, String _to, BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Address(_from), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(_to), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple3<String, String, BigInteger> getTransferFromInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, String, BigInteger>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue()
                );
    }

    public static ERC721 load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ERC721(contractAddress, client, credential);
    }

    public static ERC721 deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(ERC721.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class ApprovalEventResponse {
        public TransactionReceipt.Logs log;

        public String _owner;

        public String _approved;

        public BigInteger _tokenId;
    }

    public static class ApprovalForAllEventResponse {
        public TransactionReceipt.Logs log;

        public String _owner;

        public String _operator;

        public Boolean _approved;
    }

    public static class TransferEventResponse {
        public TransactionReceipt.Logs log;

        public String _from;

        public String _to;

        public BigInteger _tokenId;
    }
}
