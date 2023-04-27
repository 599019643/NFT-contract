pragma solidity ^0.8.0;

import "./NFToken.sol";
import "./Counters.sol";

/**
 * @dev 限制个数的NFT.
 */
contract LimitToken is NFToken {

    using Counters for Counters.Counter;

    Counters.Counter private _tokenIds;

    mapping(uint256 => string) internal idToUrl;

    address  internal creator;
    uint256  internal _max;

    constructor (string memory nftName, string memory nftSymbol, uint256 max) NFToken(nftName, nftSymbol){
        require(max > 0, LOW_OVERFLOW);
        creator = msg.sender;
        _max = max;
    }

    /**
      * @dev 生成一个Token.
     */
    function mint(address to, string memory tokenURI) external returns (uint256) {
        require(creator == msg.sender, NOT_OWNER_OR_OPERATOR);
        uint256 tokenId = _tokenIds.current();
        require(_max > tokenId, HAS_EXCEED);
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

    function limit()
    external
    view
    returns (uint256)
    {
        return _max;
    }
}
