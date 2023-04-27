package com.ac.yb.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionEncoder;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.DynamicBytes;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
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
public class NFToken extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040523480156200001157600080fd5b506040516200133e3803806200133e833981016040819052620000349162000244565b600060208181527f67be87c3ff9960ca1e9cfac5cab2ff4747269cf9ed20c9b7306235ac35a491c58054600160ff1991821681179092556380ac58cd60e01b9093527ff7815fccbf112960a73756e185887fedcb9fc64ca0a16cc5923b7960ed780800805490931681179092558351620000b29291850190620000d1565b508051620000c8906002906020840190620000d1565b505050620002eb565b828054620000df90620002ae565b90600052602060002090601f0160209004810192826200010357600085556200014e565b82601f106200011e57805160ff19168380011785556200014e565b828001600101855582156200014e579182015b828111156200014e57825182559160200191906001019062000131565b506200015c92915062000160565b5090565b5b808211156200015c576000815560010162000161565b634e487b7160e01b600052604160045260246000fd5b600082601f8301126200019f57600080fd5b81516001600160401b0380821115620001bc57620001bc62000177565b604051601f8301601f19908116603f01168101908282118183101715620001e757620001e762000177565b816040528381526020925086838588010111156200020457600080fd5b600091505b8382101562000228578582018301518183018401529082019062000209565b838211156200023a5760008385830101525b9695505050505050565b600080604083850312156200025857600080fd5b82516001600160401b03808211156200027057600080fd5b6200027e868387016200018d565b935060208501519150808211156200029557600080fd5b50620002a4858286016200018d565b9150509250929050565b600181811c90821680620002c357607f821691505b60208210811415620002e557634e487b7160e01b600052602260045260246000fd5b50919050565b61104380620002fb6000396000f3fe608060405234801561001057600080fd5b50600436106100b45760003560e01c806342842e0e1161007157806342842e0e1461016b5780636352211e1461017e57806370a0823114610191578063a22cb465146101b2578063b88d4fde146101c5578063e985e9c5146101d857600080fd5b806301ffc9a7146100b9578063081812fc146100fb578063095ea7b314610126578063150704011461013b57806317d7de7c1461015057806323b872dd14610158575b600080fd5b6100e66100c7366004610cf5565b6001600160e01b03191660009081526020819052604090205460ff1690565b60405190151581526020015b60405180910390f35b61010e610109366004610d19565b610214565b6040516001600160a01b0390911681526020016100f2565b610139610134366004610d4e565b610296565b005b610143610438565b6040516100f29190610dc5565b6101436104ca565b610139610166366004610dd8565b6104d9565b610139610179366004610dd8565b610694565b61010e61018c366004610d19565b6106b4565b6101a461019f366004610e14565b61070c565b6040519081526020016100f2565b6101396101c0366004610e2f565b610770565b6101396101d3366004610e6b565b6107dc565b6100e66101e6366004610f06565b6001600160a01b03918216600090815260066020908152604080832093909416825291909152205460ff1690565b6000818152600360209081526040808320548151808301909252600682526518181998181960d11b9282019290925283916001600160a01b03166102745760405162461bcd60e51b815260040161026b9190610dc5565b60405180910390fd5b506000838152600460205260409020546001600160a01b031691505b50919050565b60008181526003602052604090205481906001600160a01b0316338114806102e157506001600160a01b038116600090815260066020908152604080832033845290915290205460ff165b6040518060400160405280600681526020016530303330303360d01b8152509061031e5760405162461bcd60e51b815260040161026b9190610dc5565b50600083815260036020908152604091829020548251808401909352600683526518181998181960d11b918301919091528491906001600160a01b03166103785760405162461bcd60e51b815260040161026b9190610dc5565b50600084815260036020908152604091829020548251808401909352600683526506060666060760d31b918301919091526001600160a01b03908116919087168214156103d85760405162461bcd60e51b815260040161026b9190610dc5565b5060008581526004602052604080822080546001600160a01b0319166001600160a01b038a811691821790925591518893918516917f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92591a4505050505050565b60606002805461044790610f39565b80601f016020809104026020016040519081016040528092919081815260200182805461047390610f39565b80156104c05780601f10610495576101008083540402835291602001916104c0565b820191906000526020600020905b8154815290600101906020018083116104a357829003601f168201915b5050505050905090565b60606001805461044790610f39565b60008181526003602052604090205481906001600160a01b03163381148061051757506000828152600460205260409020546001600160a01b031633145b8061054557506001600160a01b038116600090815260066020908152604080832033845290915290205460ff165b604051806040016040528060068152602001650c0c0ccc0c0d60d21b815250906105825760405162461bcd60e51b815260040161026b9190610dc5565b50600083815260036020908152604091829020548251808401909352600683526518181998181960d11b918301919091528491906001600160a01b03166105dc5760405162461bcd60e51b815260040161026b9190610dc5565b50600084815260036020908152604091829020548251808401909352600683526530303330303760d01b918301919091526001600160a01b0390811691908816821461063b5760405162461bcd60e51b815260040161026b9190610dc5565b5060408051808201909152600681526530303330303160d01b60208201526001600160a01b0387166106805760405162461bcd60e51b815260040161026b9190610dc5565b5061068b8686610825565b50505050505050565b6106af838383604051806020016040528060008152506108b0565b505050565b600081815260036020908152604091829020548251808401909352600683526518181998181960d11b918301919091526001600160a01b031690816102905760405162461bcd60e51b815260040161026b9190610dc5565b60408051808201909152600681526530303330303160d01b60208201526000906001600160a01b0383166107535760405162461bcd60e51b815260040161026b9190610dc5565b50506001600160a01b031660009081526005602052604090205490565b3360008181526006602090815260408083206001600160a01b03871680855290835292819020805460ff191686151590811790915590519081529192917f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31910160405180910390a35050565b61081e85858585858080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152506108b092505050565b5050505050565b600081815260036020908152604080832054600490925290912080546001600160a01b03191690556001600160a01b03166108608183610b4f565b61086a8383610bf8565b81836001600160a01b0316826001600160a01b03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef60405160405180910390a4505050565b60008281526003602052604090205482906001600160a01b0316338114806108ee57506000828152600460205260409020546001600160a01b031633145b8061091c57506001600160a01b038116600090815260066020908152604080832033845290915290205460ff165b604051806040016040528060068152602001650c0c0ccc0c0d60d21b815250906109595760405162461bcd60e51b815260040161026b9190610dc5565b50600084815260036020908152604091829020548251808401909352600683526518181998181960d11b918301919091528591906001600160a01b03166109b35760405162461bcd60e51b815260040161026b9190610dc5565b50600085815260036020908152604091829020548251808401909352600683526530303330303760d01b918301919091526001600160a01b03908116919089168214610a125760405162461bcd60e51b815260040161026b9190610dc5565b5060408051808201909152600681526530303330303160d01b60208201526001600160a01b038816610a575760405162461bcd60e51b815260040161026b9190610dc5565b50610a628787610825565b610a74876001600160a01b0316610ca0565b15610b4557604051630a85bd0160e11b81526000906001600160a01b0389169063150b7a0290610aae9033908d908c908c90600401610f6e565b6020604051808303816000875af1158015610acd573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610af19190610fab565b60408051808201909152600681526530303330303560d01b60208201529091506001600160e01b03198216630a85bd0160e11b14610b425760405162461bcd60e51b815260040161026b9190610dc5565b50505b5050505050505050565b600081815260036020908152604091829020548251808401909352600683526530303330303760d01b918301919091526001600160a01b03848116911614610baa5760405162461bcd60e51b815260040161026b9190610dc5565b506001600160a01b0382166000908152600560205260408120805460019290610bd4908490610fde565b9091555050600090815260036020526040902080546001600160a01b031916905550565b600081815260036020908152604091829020548251808401909352600683526518181998181b60d11b918301919091526001600160a01b031615610c4f5760405162461bcd60e51b815260040161026b9190610dc5565b50600081815260036020908152604080832080546001600160a01b0319166001600160a01b038716908117909155835260059091528120805460019290610c97908490610ff5565b90915550505050565b6000813f7fc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a4708115801590610cd45750808214155b949350505050565b6001600160e01b031981168114610cf257600080fd5b50565b600060208284031215610d07576000","80fd5b8135610d1281610cdc565b9392505050565b600060208284031215610d2b57600080fd5b5035919050565b80356001600160a01b0381168114610d4957600080fd5b919050565b60008060408385031215610d6157600080fd5b610d6a83610d32565b946020939093013593505050565b6000815180845260005b81811015610d9e57602081850181015186830182015201610d82565b81811115610db0576000602083870101525b50601f01601f19169290920160200192915050565b602081526000610d126020830184610d78565b600080600060608486031215610ded57600080fd5b610df684610d32565b9250610e0460208501610d32565b9150604084013590509250925092565b600060208284031215610e2657600080fd5b610d1282610d32565b60008060408385031215610e4257600080fd5b610e4b83610d32565b915060208301358015158114610e6057600080fd5b809150509250929050565b600080600080600060808688031215610e8357600080fd5b610e8c86610d32565b9450610e9a60208701610d32565b935060408601359250606086013567ffffffffffffffff80821115610ebe57600080fd5b818801915088601f830112610ed257600080fd5b813581811115610ee157600080fd5b896020828501011115610ef357600080fd5b9699959850939650602001949392505050565b60008060408385031215610f1957600080fd5b610f2283610d32565b9150610f3060208401610d32565b90509250929050565b600181811c90821680610f4d57607f821691505b6020821081141561029057634e487b7160e01b600052602260045260246000fd5b6001600160a01b0385811682528416602082015260408101839052608060608201819052600090610fa190830184610d78565b9695505050505050565b600060208284031215610fbd57600080fd5b8151610d1281610cdc565b634e487b7160e01b600052601160045260246000fd5b600082821015610ff057610ff0610fc8565b500390565b6000821982111561100857611008610fc8565b50019056fea2646970667358221220915fb504b76a151b61b8e365a2ef0ae81c3db3da090a0779076fa7bb62f9d11a64736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040523480156200001157600080fd5b506040516200135538038062001355833981016040819052620000349162000244565b600060208181527fc2a7148da09a39e4c44d4e2590127bd39e01de9d8999efdaac291c1ffb0d34e48054600160ff1991821681179092556380ac58cd60e01b9093527ff73ef30811bc15bea6cfb185c827ebaa692a5388568148f4ed42515e870760d3805490931681179092558351620000b29291850190620000d1565b508051620000c8906002906020840190620000d1565b505050620002eb565b828054620000df90620002ae565b90600052602060002090601f0160209004810192826200010357600085556200014e565b82601f106200011e57805160ff19168380011785556200014e565b828001600101855582156200014e579182015b828111156200014e57825182559160200191906001019062000131565b506200015c92915062000160565b5090565b5b808211156200015c576000815560010162000161565b63b95aa35560e01b600052604160045260246000fd5b600082601f8301126200019f57600080fd5b81516001600160401b0380821115620001bc57620001bc62000177565b604051601f8301601f19908116603f01168101908282118183101715620001e757620001e762000177565b816040528381526020925086838588010111156200020457600080fd5b600091505b8382101562000228578582018301518183018401529082019062000209565b838211156200023a5760008385830101525b9695505050505050565b600080604083850312156200025857600080fd5b82516001600160401b03808211156200027057600080fd5b6200027e868387016200018d565b935060208501519150808211156200029557600080fd5b50620002a4858286016200018d565b9150509250929050565b600181811c90821680620002c357607f821691505b60208210811415620002e55763b95aa35560e01b600052602260045260246000fd5b50919050565b61105a80620002fb6000396000f3fe608060405234801561001057600080fd5b50600436106100b45760003560e01c80639cb97a52116100715780639cb97a5214610189578063ad8a97311461019c578063b0c3a734146101af578063cc8be70e146101c2578063d757c091146101e3578063ea7eb798146101eb57600080fd5b80630cad19ba146100b95780631f2d4860146100d75780632ffdc5f8146100ec5780634c00105f146100ff57806356297c3a1461014b5780635711734b1461015e575b600080fd5b6100c1610218565b6040516100ce9190610d40565b60405180910390f35b6100ea6100e5366004610d76565b6102aa565b005b6100ea6100fa366004610da0565b610458565b61013b61010d366004610ddc565b6001600160a01b03918216600090815260066020908152604080832093909416825291909152205460ff1690565b60405190151581526020016100ce565b6100ea610159366004610e0f565b6104c4565b61017161016c366004610eaa565b61050d565b6040516001600160a01b0390911681526020016100ce565b610171610197366004610eaa565b61056c565b6100ea6101aa366004610ec3565b6105e2565b6100ea6101bd366004610ec3565b6107a1565b6101d56101d0366004610eff565b6107c1565b6040519081526020016100ce565b6100c1610826565b61013b6101f9366004610f33565b6001600160e01b03191660009081526020819052604090205460ff1690565b60606001805461022790610f50565b80601f016020809104026020016040519081016040528092919081815260200182805461025390610f50565b80156102a05780601f10610275576101008083540402835291602001916102a0565b820191906000526020600020905b81548152906001019060200180831161028357829003601f168201915b5050505050905090565b60008181526003602052604090205481906001600160a01b0316338114806102f557506001600160a01b038116600090815260066020908152604080832033845290915290205460ff165b6040518060400160405280600681526020016530303330303360d01b8152509061033c57604051636381e58960e11b81526004016103339190610d40565b60405180910390fd5b50600083815260036020908152604091829020548251808401909352600683526518181998181960d11b918301919091528491906001600160a01b031661039757604051636381e58960e11b81526004016103339190610d40565b50600084815260036020908152604091829020548251808401909352600683526506060666060760d31b918301919091526001600160a01b03908116919087168214156103f857604051636381e58960e11b81526004016103339190610d40565b5060008581526004602052604080822080546001600160a01b0319166001600160a01b038a811691821790925591518893918516917fd1e45707b3f71c77903b61f04c900f772db264b9bf618f1cc3308fb516eb616991a4505050505050565b3360008181526006602090815260408083206001600160a01b03871680855290835292819020805460ff191686151590811790915590519081529192917f34cc9ec6b85c217ac6bd5f7b86411e4e4e40d816d5d17725c4dec1f0901d9074910160405180910390a35050565b61050685858585858080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525061083592505050565b5050505050565b600081815260036020908152604091829020548251808401909352600683526518181998181960d11b918301919091526001600160a01b0316908161056657604051636381e58960e11b81526004016103339190610d40565b50919050565b6000818152600360209081526040808320548151808301909252600682526518181998181960d11b9282019290925283916001600160a01b03166105c457604051636381e58960e11b81526004016103339190610d40565b5050506000908152600460205260409020546001600160a01b031690565b60008181526003602052604090205481906001600160a01b03163381148061062057506000828152600460205260409020546001600160a01b031633145b8061064e57506001600160a01b038116600090815260066020908152604080832033845290915290205460ff165b604051806040016040528060068152602001650c0c0ccc0c0d60d21b8152509061068c57604051636381e58960e11b81526004016103339190610d40565b50600083815260036020908152604091829020548251808401909352600683526518181998181960d11b918301919091528491906001600160a01b03166106e757604051636381e58960e11b81526004016103339190610d40565b50600084815260036020908152604091829020548251808401909352600683526530303330303760d01b918301919091526001600160a01b0390811691908816821461074757604051636381e58960e11b81526004016103339190610d40565b5060408051808201909152600681526530303330303160d01b60208201526001600160a01b03871661078d57604051636381e58960e11b81526004016103339190610d40565b506107988686610ad9565b50505050505050565b6107bc83838360405180602001604052806000815250610835565b505050565b60408051808201909152600681526530303330303160d01b60208201526000906001600160a01b03831661080957604051636381e58960e11b81526004016103339190610d40565b50506001600160a01b031660009081526005602052604090205490565b60606002805461022790610f50565b60008281526003602052604090205482906001600160a01b03163381148061087357506000828152600460205260409020546001600160a01b031633145b806108a157506001600160a01b038116600090815260066020908152604080832033845290915290205460ff165b604051806040016040528060068152602001650c0c0ccc0c0d60d21b815250906108df57604051636381e58960e11b81526004016103339190610d40565b50600084815260036020908152604091829020548251808401909352600683526518181998181960d11b918301919091528591906001600160a01b031661093a57604051636381e58960e11b81526004016103339190610d40565b50600085815260036020908152604091829020548251808401909352600683526530303330303760d01b918301919091526001600160a01b0390811691908916821461099a57604051636381e58960e11b81526004016103339190610d40565b5060408051808201909152600681526530303330303160d01b60208201526001600160a01b0388166109e057604051636381e58960e11b81526004016103339190610d40565b506109eb8787610ad9565b6109fd876001600160a01b0316610b64565b15610acf576040516309281b4360e01b81526000906001600160a01b038916906309281b4390610a379033908d908c908c90600401610f85565b6020604051808303816000875af1158015610a56573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610a7a9190610fc2565b60408051808201909152600681526530303330303560d01b60208201529091506001600160e01b03198216630a85bd0160e11b14610acc57604051636381e58960e11b81526004016103339190610d40565b50505b5050505050505050565b600081815260036020908152604080832054600490925290912080546001600160a01b03191690556001600160a01b0316610b148183610ba0565b610b1e8383610c4a565b81836001600160a01b0316826001600160a01b03167f18f84334255a242551aa98c68047b5da8063eab9fbeaec1eddeea280044b9ff160405160405180910390a4505050565b6000813f7fc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a4708115801590610b985750808214155b949350505050565b600081815260036020908152604091829020548251808401909352600683526530303330303760d01b918301919091526001600160a01b03848116911614610bfc57604051636381e58960e11b81526004016103339190610d40565b506001600160a01b0382166000908152600560205260408120805460019290610c26908490610ff5565b9091555050600090815260036020526040902080546001600160a01b031916905550565b600081815260036020908152604091829020548251808401909352600683526518181998181b60d11b918301919091526001600160a01b031615610ca257604051636381e58960e11b81526004016103339190610d40565b50600081815260036020908152604080832080546001600160a01b0319166001600160a01b038716908117909155835260059091528120805460019290610cea90849061100c565b90915550505050565b6000815180845260005b81811015610d19","57602081850181015186830182015201610cfd565b81811115610d2b576000602083870101525b50601f01601f19169290920160200192915050565b602081526000610d536020830184610cf3565b9392505050565b80356001600160a01b0381168114610d7157600080fd5b919050565b60008060408385031215610d8957600080fd5b610d9283610d5a565b946020939093013593505050565b60008060408385031215610db357600080fd5b610dbc83610d5a565b915060208301358015158114610dd157600080fd5b809150509250929050565b60008060408385031215610def57600080fd5b610df883610d5a565b9150610e0660208401610d5a565b90509250929050565b600080600080600060808688031215610e2757600080fd5b610e3086610d5a565b9450610e3e60208701610d5a565b935060408601359250606086013567ffffffffffffffff80821115610e6257600080fd5b818801915088601f830112610e7657600080fd5b813581811115610e8557600080fd5b896020828501011115610e9757600080fd5b9699959850939650602001949392505050565b600060208284031215610ebc57600080fd5b5035919050565b600080600060608486031215610ed857600080fd5b610ee184610d5a565b9250610eef60208501610d5a565b9150604084013590509250925092565b600060208284031215610f1157600080fd5b610d5382610d5a565b6001600160e01b031981168114610f3057600080fd5b50565b600060208284031215610f4557600080fd5b8135610d5381610f1a565b600181811c90821680610f6457607f821691505b602082108114156105665763b95aa35560e01b600052602260045260246000fd5b6001600160a01b0385811682528416602082015260408101839052608060608201819052600090610fb890830184610cf3565b9695505050505050565b600060208284031215610fd457600080fd5b8151610d5381610f1a565b63b95aa35560e01b600052601160045260246000fd5b60008282101561100757611007610fdf565b500390565b6000821982111561101f5761101f610fdf565b50019056fea2646970667358221220cbde34cd22188a2c897bff76e8e4f46d80c5372d29a5ac197f920dba8ce7823464736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"string\",\"name\":\"nftName\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"nftSymbol\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_approved\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_operator\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"bool\",\"name\":\"_approved\",\"type\":\"bool\"}],\"name\":\"ApprovalForAll\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_approved\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"getApproved\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getName\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getSymbol\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"symbol\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_operator\",\"type\":\"address\"}],\"name\":\"isApprovedForAll\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"ownerOf\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"safeTransferFrom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"},{\"internalType\":\"bytes\",\"name\":\"_data\",\"type\":\"bytes\"}],\"name\":\"safeTransferFrom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_operator\",\"type\":\"address\"},{\"internalType\":\"bool\",\"name\":\"_approved\",\"type\":\"bool\"}],\"name\":\"setApprovalForAll\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes4\",\"name\":\"_interfaceID\",\"type\":\"bytes4\"}],\"name\":\"supportsInterface\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_GETNAME = "getName";

    public static final String FUNC_GETSYMBOL = "getSymbol";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_SAFETRANSFERFROM = "safeTransferFrom";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

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

    protected NFToken(String contractAddress, Client client, CryptoKeyPair credential) {
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

    public String getName() throws ContractException {
        final Function function = new Function(FUNC_GETNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public String getSymbol() throws ContractException {
        final Function function = new Function(FUNC_GETSYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
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

    public Boolean supportsInterface(byte[] _interfaceID) throws ContractException {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes4(_interfaceID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
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

    public static NFToken load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new NFToken(contractAddress, client, credential);
    }

    public static NFToken deploy(Client client, CryptoKeyPair credential, String nftName, String nftSymbol) throws ContractException {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(nftName), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(nftSymbol)));
        return deploy(NFToken.class, client, credential, getBinary(client.getCryptoSuite()), encodedConstructor);
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
