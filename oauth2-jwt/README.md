### jwt-auth-server 认证服务工程
token使用jwt证书认证，并且使用redis作为token的存储

### jwt-resource-server 资源服务器
token证书通过/oauth/token_key到认证服务器获取，并使用redis存储token
注：资源服务器可以使用jwtTokenStore，就不会从redis中取token并验证了

### Password认证
http://localhost:8081/oauth/token?grant_type=password&username=user_1&password=123456
返回结果
```
{
    "access_token": "eyJhbGciOi...",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIU...",
    "expires_in": 59,
    "scope": "openid",
    "jti": "bda3520f-df6a-4462-a85d-e1fde23802a7"
}
```

资源服务器访问
- http://localhost:8082/aa/1 没有添加认证
- http://localhost:8082/bb/1?access_token=eyJhbGci...添加access_token信息
或者 http://localhost:8082/bb/1 添加request header信息:Authorization: Bearer eyJhbGc...