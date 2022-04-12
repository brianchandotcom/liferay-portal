CustomService.ConfigMap.md

startsWith - `com.liferay.oauth2.application.factory.headless.server.configuration.v1.Oauth2ApplicationHeadlessServerConfiguration`
add:
```yaml
            env:
              - name: LIFERAY_OAUTH2_HEADLESS_SERVER_CLIENT_ID
                valueFrom:
                  configMapKeyRef:
                    key: liferay_oauth2_headless_server_client_id
                    name: <serviceid>.extension.liferay.com
              - name: LIFERAY_OAUTH2_HEADLESS_SERVER_CLIENT_SECRET
                valueFrom:
                  configMapKeyRef:
                    key: liferay_oauth2_headless_server_client_secret
                    name: <serviceid>.extension.liferay.com
              - name: LIFERAY_OAUTH2_AUTHORIZATION_URI
                valueFrom:
                  configMapKeyRef:
                    key: liferay_oauth2_authorization_uri
                    name: <serviceid>.extension.liferay.com
              - name: LIFERAY_OAUTH2_INTROSPECTION_URI
                valueFrom:
                  configMapKeyRef:
                    key: liferay_oauth2_introspection_uri
                    name: <serviceid>.extension.liferay.com
              - name: LIFERAY_OAUTH2_REDIRECT_URIS
                valueFrom:
                  configMapKeyRef:
                    key: liferay_oauth2_redirect_uris
                    name: <serviceid>.extension.liferay.com
              - name: LIFERAY_OAUTH2_TOKEN_URI
                valueFrom:
                  configMapKeyRef:
                    key: liferay_oauth2_token_uri
                    name: <serviceid>.extension.liferay.com
              - name: LIFERAY_SERVICE_URI
                valueFrom:
                  configMapKeyRef:
                    key: liferay_service_uri
                    name: <serviceid>.extension.liferay.com
```

startsWith - `com.liferay.oauth2.application.factory.user.agent.configuration.v1.OAuth2ApplicationUserAgentConfiguration`

add:
```yaml
            env:
              - name: LIFERAY_OAUTH2_USER_AGENT_CLIENT_ID
                valueFrom:
                  configMapKeyRef:
                    key: liferay_oauth2_user_agent_client_id
                    name: <serviceid>.extension.liferay.com
              - name: LIFERAY_OAUTH2_AUTHORIZATION_URI
                valueFrom:
                  configMapKeyRef:
                    key: liferay_oauth2_authorization_uri
                    name: <serviceid>.extension.liferay.com
              - name: LIFERAY_OAUTH2_INTROSPECTION_URI
                valueFrom:
                  configMapKeyRef:
                    key: liferay_oauth2_introspection_uri
                    name: <serviceid>.extension.liferay.com
              - name: LIFERAY_OAUTH2_REDIRECT_URIS
                valueFrom:
                  configMapKeyRef:
                    key: liferay_oauth2_redirect_uris
                    name: <serviceid>.extension.liferay.com
              - name: LIFERAY_OAUTH2_TOKEN_URI
                valueFrom:
                  configMapKeyRef:
                    key: liferay_oauth2_token_uri
                    name: <serviceid>.extension.liferay.com
              - name: LIFERAY_SERVICE_URI
                valueFrom:
                  configMapKeyRef:
                    key: liferay_service_uri
                    name: <serviceid>.extension.liferay.com
```