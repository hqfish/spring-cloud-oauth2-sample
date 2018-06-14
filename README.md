每个示例都分认证服务和资源服务，尽量和真实环境保持一致

### oauth2-jwt-sign工程说明
jwt认证协义，使用签名，认证和资源服务器签名相同("123")
- jwt-auth-server 认证服务工程
token使用jwt、签名来认证token，如下使用签名“123”
```
public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }
```

- jwt-resource-server 资源服务器
认证 token时不需要和认证服务器有交互，通过token本身就完成了验证。
jwt的签名需要与服务端完成一致

### jwt-auth-server-credentials工程说明
jwt认证协义，使用证书
- wt-auth-server 认证服务工程
token使用jwt证书认证，并且使用redis作为token的存储

- jwt-resource-server 资源服务器
token证书通过/oauth/token_key到认证服务器获取，jwtTokenStore，不会从redis中取token并验证了


### oauth2-jwt工程说明
更接近于实际项目
#### jwt-auth-server 认证服务工程
token使用jwt证书认证，并且使用redis作为token的存储

**核心代码说明：**
- OAuth2AuthorizationServerConfig 认证服务器核心配置 
认证类型、认证协议、token如何存储都是在这里配配置

- MyUserDetailsService 待验证的用户实体初始化
通过用户(username)来初始化UserDetails，主要是初始化密码，以便provider和用户输入的密码做校验。
这里给的是示例代码，直接反回了一个用户。真实环境是根据用户名到数据库中加载正确的密码用于初始化UserDetails。

- SecurityDaoAuthenticationProvider 校验类
在该类里可以拿到用户输入的用户名/密码(UsernamePasswordAuthenticationToken)和数据库中的用户名/密码(UserDetails),在这里做校验，如果不正确可以抛异常。
特殊的策略也可以在这里实现，如密码策略、最大用户数策略等



#### jwt-resource-server 资源服务器
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