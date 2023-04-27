pragma solidity ^0.8.0;

import "./NFToken.sol";
import "./Counters.sol";

/**
 * @dev 不限制个数的NFT.
 */
contract NoLimitToken is NFToken {

    using Counters for Counters.Counter;

    Counters.Counter private _tokenIds;

    mapping(uint256 => string) internal idToUrl;

    address  internal creator;

    constructor (string memory nftName, string memory nftSymbol) NFToken(nftName, nftSymbol){
        creator = msg.sender;
    }

    /**
      * @dev 生成一个Token.
     */
    function mint(address to, string memory tokenURI) external returns (uint256) {
        require(creator == msg.sender, NOT_OWNER_OR_OPERATOR);
        uint256 tokenId = _tokenIds.current();
        _mint(to, tokenId);
        idToUrl[tokenId] = tokenURI;
        _tokenIds.increment();
        return tokenId;
    }

    function tokenUrl(
        uint256 _tokenId
    )
    external
    view
    returns (string memory tokenURI)
    {
        address _owner = idToOwner[_tokenId];
        require(_owner != address(0), NOT_VALID_NFT);
        return idToUrl[_tokenId];
    }
}
