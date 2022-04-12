## Kubernetes Native DXP

> **Note:** I've abbreviated all `kubectl` commands to `k` for brevity.

### Create Kubernetes Cluster

1. Create a new Kubernetes cluster, for example with Minikube, making sure to leave enough room to run DXP. DXP is setup for 4CPUs and 4Gb memory so make sure your cluster supports that plus a bit for other workloads.

   ```bash
   minikube start --cpus 8 --memory 16364
   ```

2. in the root of the project execute the `./run_local.sh` script which should build and orchestrate the environment.

### Find the DXP Pod

```bash
POD=$(k get pods --field-selector="status.phase=Running" -o json | jq -r '.items[].metadata.name')
```

### Debug DXP

DXP is setup for debug, to connect a debugger find the DXP pod and open a port forward.

```bash
k get pods

k port-forward $POD 8000:8000
```

### Telnet to Gogo Shell

```bash
k port-forward $POD 11311:11311
```

#### Add support for operating on Configurations to DXP Gogo

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

Deploy a couple of needed OSGi bundles into DXP deployment pod

```bash
k cp \
	~/.m2/repository/org/apache/commons/commons-collections4/4.4/commons-collections4-4.4.jar \
	$POD:/opt/liferay/osgi/modules/
k cp \
	~/.m2/repository/org/apache/felix/org.apache.felix.configadmin.plugin.interpolation/1.2.2/org.apache.felix.configadmin.plugin.interpolation-1.2.2.jar \
	$POD:/opt/liferay/osgi/modules/
k cp \
	kubernetes-native-dxp/modules/kubernetes-config/build/libs/com.liferay.kubernetes.config-1.0.0.jar \
	$POD:/opt/liferay/osgi/modules/

# if needed
k cp \
	kubernetes-native-dxp/modules/logservice-command/build/libs/com.liferay.logservice.command-1.0.0.jar \
	$POD:/opt/liferay/osgi/modules/
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
      2. authorization_url: http://localhost:8080/o/oauth2/authorize
      3. token_url: http://localhost:8080/o/oauth2/token
      4. client_id: <the-id-from-dxp-oauth2-application>
      5. Use PKCE
      6. Code challenge method: SHA-256
      7. Click `Fetch Tokens`
3. Validating Access Token
   1. create a new POST method test
   2. Use url: http://localhost:8080/o/oauth2/introspect
   3. Under POST body options use `Form URL Encoded` using these form values
      1. token=<access_token>
      2. client_id=<client_id>
      3. token_type_hint=access_token
      4. Click `Send`