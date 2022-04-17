# Custom Rest Service

## Enable the JVM to communicate over TLS

In order for processes to communicate over TLS with others each needs to have access to either the specific TLS certificate of the target or the CA which signed the cert. In general it's much simpler to add the CA since there may be multiple hosts with their own domains. This process shows how to add the CA created for localdev in the Java process.

Copy the `ca.crt` from project `tsl` directory into this project (in order for Dockerfile to see the file it must be a local relative path), then add the following to the `Dockerfile`:

```dockerfile
COPY ca.crt $JAVA_HOME/lib/security
RUN \
	cd $JAVA_HOME/lib/security \
	&& keytool -keystore cacerts -storepass changeit \
		-noprompt -trustcacerts -importcert -alias project-cert -file ca.crt
```

With this the JVM will be able to initial TLS communications with other services (in particular DXP).