package com.yuhq.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;


@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserDetailsService userDetailsService;

    //告诉Spring Security Token的生成方式
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter())
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

    @Bean
    public RedisTokenStore tokenStore() {
        System.out.println(connectionFactory+"---------");
        return new RedisTokenStore(connectionFactory);
    }


    //使用keystore.jks证书来编码 JWT 中的 OAuth2 令牌
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "password".toCharArray())
                .getKeyPair("selfsigned");
        converter.setKeyPair(keyPair);
        return converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()                           // 使用in-memory存储客户端信息
                .withClient("client")        // client_id                client_credentials模式下的用户名
                .secret("secret")                   // client_secret            client_credentials模式下的密码
                .authorizedGrantTypes("client_credentials")     // 该client允许的授权类型
                .scopes("app")                     // 允许的授权范围
                .autoApprove(true)                 // 登录后绕过批准询问(/oauth/confirm_access)
        .and()
                .withClient("web_app")
//                .secret("secret")
                .scopes("openid")
                .authorizedGrantTypes("implicit", "refresh_token", "password", "authorization_code")
                .accessTokenValiditySeconds(120)    //设置access_token超时时间
                .refreshTokenValiditySeconds(50000) //设置refresh_token超时时间
                .autoApprove(true);

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()") //allow check token
                //允许表单校验client_credentials授权类型，即可以通过
                //http://localhost:8081/oauth/token?grant_type=client_credentials&client_id=client&client_secret=secret&scope=app
                //这种方式获取token，否则会报错
                .allowFormAuthenticationForClients();
    }

}
