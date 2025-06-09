package com.liferay.stream.hub.client;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.resource.v1_0.UserAccountResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserResource {

    @Cacheable("userRoles")
    public List<String> getUserAccountRoles(Long userId) throws Exception {
        String authorization = _liferayOAuth2AccessTokenManager.getAuthorization(_mainOauthServerApplicationName);

        UserAccountResource userAccountResource = UserAccountResource.builder().header(
                "Authorization", authorization
        ).endpoint(_lxcDXPMainDomain,_lxcDXPServerProtocol).build();

        UserAccount userAccount =  userAccountResource.getUserAccount(userId);

        List<String> userRoles = Arrays.stream(userAccount.getRoleBriefs()).map(role->role.getName()).collect(Collectors.toUnmodifiableList());

        return userRoles;
    }

    @Autowired
    private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

    @Value("${com.liferay.lxc.dxp.mainDomain}")
    private String _lxcDXPMainDomain;

    @Value("${com.liferay.lxc.dxp.server.protocol}")
    private String _lxcDXPServerProtocol;

    @Value("${main.liferay.server.oauth.application}")
    private String _mainOauthServerApplicationName;

}
