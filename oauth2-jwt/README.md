### jwt-auth-server 认证服务工程
token使用jwt证书认证，并且使用redis作为token的存储

### jwt-resource-server 资源服务器
token证书通过/oauth/token_key到认证服务器获取，并使用redis存储token
注：资源服务器可以使用jwtTokenStore，就不会从redis中取token并验证了

