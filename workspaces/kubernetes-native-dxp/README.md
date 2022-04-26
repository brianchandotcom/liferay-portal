## Kubernetes Native DXP

1. Make sure you have [minikube](https://minikube.sigs.k8s.io/docs/start/), [skaffold](https://skaffold.dev/docs/install/) are installed.

1. Run the `run_local.sh` script in the workspace root:

```bash
./run_local.sh
```

### Debug DXP

DXP is ready debugging on port `8000`. Open a port-forward to a dxp pod:
```bash
kubectl port-forward $DXP_POD 8000 &
```

Then connect with a remote debugger.

### Telnet to Gogo Shell

Gogo shell is available to connect at port `11311`. Open a port-forward to a dxp pod

```bash
kubectl port-forward $DXP_POD 11311 &
```

Then telnet to that port:

```bash
telnet localhost 11311
```

### Logs

To follow logs of all the containers run the `logs.sh` script:

```bash
./logs.sh
```

### Add support for operating on Configurations to DXP Gogo

Paste the following into gogo. This adds all the methods of the `ConfigurationAdmin` service as commands under the context `cm`.

```bash
addcommand cm (service (servicereference "org.osgi.service.cm.ConfigurationAdmin"))
```

After that you can observe configurations as follows:

```bash
# save the intersting PID
PID=com.liferay.kubernetes.config.KubernetesConfigurationAgent

# list configurations with this pid
listconfigurations "(service.pid=$PID)"

# get a matching configuration directly
(listconfigurations "(service.pid=$PID)") 0

# get the configuration after all plugins are applied (as DS would see it)
((listconfigurations "(service.pid=$PID)") 0) processedProperties null

# OR (if the configuration exists)
(configuration com.liferay.kubernetes.config.KubernetesConfigurationAgent) processedProperties null

# PS: listconfigirations does not create a configuration if it's missing, `configuration` command does
```

## SANDBOX

https://console.innovationspoc.liferay.sh/projects/innovationspoc1-dev/overview

```bash
LCP_SECRET_CI_CUSTOMER_USER=customer
LCP_SECRET_CI_CUSTOMER_PASSWORD=e4JsdMa9KPDoBhh2au1JbiqLzxGxm6
```



## Trusted Communication Demo

In this demo we have the following entities:

* DXP acting as the **OAuth authorization server**
* React Remote App as the **User Agent Application**
* Custom Rest Service acting as **resource server**

Below is a rough outine of the process could work:

1. Create an OAuth 2 Application in DXP enabling PKCE and JWT Bearer, Trusted Application, and Token inspection
2. A React Remote App initiates an Authorization Code flow to receive an OAuth access_token
3. Then react remote app sends this access_token with the request to the resource server.
4. Resource server is configured with DXP OAuth2 application client_id and uses the introspection API to verify the token.

### Manual process for obtaining and validating OAuth2 token

1. Create a DXP OAuth2 Application
   1. Go to OAuth 2 Administration page
   2. Click "+"
   3. Set Name, website URL, and Callback URI (doesn't matter at this point)
   4. For allowed authorization types PKCE and JWT Bearer
   5. Enable Trusted Application
   6. Enable Token Introspection
2. Obtaining Access Token
   1. Open Insomnia REST Testing tool
   2. Under OAuth2  tab, add the following details
      1. grant_type: authorization_code
      2. authorization_url: http://dxp.localdev.me:8080/o/oauth2/authorize
      3. token_url: http://dxp.localdev.me:8080/o/oauth2/token
      4. client_id: <the-id-from-dxp-oauth2-application>
      5. Use PKCE
      6. Code challenge method: SHA-256
      7. Click `Fetch Tokens`
3. Validating Access Token
   1. create a new POST method test
   2. Use url: http://dxp.localdev.me:8080/o/oauth2/introspect
   3. Under POST body options use `Form URL Encoded` using these form values
      1. token=<access_token>
      2. client_id=<client_id>
      3. token_type_hint=access_token
      4. Click `Send`

## TLS Configuration

To setup TLS we need to generate a CA and domain certs. The reason for this is well explained [here](https://github.com/BenMorel/dev-certificates/).

### Generate a CA

From the `tls` directory execute the following:
```bash
$ ./create-ca.sh
```

Import the CA into your browser [as described](https://github.com/BenMorel/dev-certificates/#import-the-ca-in-your-browser).

### Generate a wildcard domain Certificate

From the `tls` directory again execute the following:

```bash
$ ./create-certificate.sh *.localdev.me
```

### Setup the Ingress to use the Certificate

[Minikube TLS certificate with ingress addon](https://minikube.sigs.k8s.io/docs/tutorials/custom_cert_ingress/)

Add the certificate to Minikube by executing:

```bash
$ kubectl -n kube-system create secret tls mkcert --key tls/localdev.me.key --cert tls/localdev.me.crt
```

Configure ingress addon

```bash
$ minikube addons configure ingress
-- Enter custom cert(format is "namespace/secret"): kube-system/mkcert
✅  ingress was successfully configured
```

Enable ingress addon (disable first when already enabled)

```bash
$ minikube addons disable ingress
🌑  "The 'ingress' addon is disabled

$ minikube addons enable ingress
🔎  Verifying ingress addon...
🌟  The 'ingress' addon is enabled
```

Verify if custom certificate was enabled

```bash
$ kubectl -n ingress-nginx get deployment ingress-nginx-controller -o yaml | grep "kube-system"
- --default-ssl-certificate=kube-system/mkcert
```

### Enable Minikube Ingress with TLS

Add a rule for each service hostname.

Since the certificate is a wildcard, as long as all services are sub-domains match the wildcard domain, TLS will function for them.

```bash
kubectl create ingress common-ingress --class=nginx \
	--rule="dxp.localdev.me/*=dxp-service:80,tls" \
	--rule="customrestservice.localdev.me/*=customrestservice:80,tls" \
	--rule="remote-app-a.localdev.me/*=remote-app-a:80,tls"
```