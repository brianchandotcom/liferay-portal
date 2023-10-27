# 1063732432e7a5e5d3cf782ec1652728ef053eb9

On the message of the commit 1063732432e7a5e5d3cf782ec1652728ef053eb9 the file path is not the complete path:

so the correct message on **What** section should be

modules/apps/document-library/document-library-repository-external-api/src/main/java/com/liferay/document/library/repository/external/ExtRepository.java

modules/apps/document-library/document-library-repository-external-api/src/main/java/com/liferay/document/library/repository/external/ExtRepository.java now includes a new method generatePDFPreviews(long userId);

----

# 2681b881ad469095e572928f265cefe2f51cdb16

On the message of the commit 2681b881ad469095e572928f265cefe2f51cdb16 the file path is not the complete path:

so the correct message on **What** section should be

modules/apps/frontend-taglib/frontend-taglib-chart/src/main/resources/META-INF/liferay-chart.tld

----

# 87a3c8bf38374f1987debdcedaed7f9e7a0dfdbc

On the message of the commit 87a3c8bf38374f1987debdcedaed7f9e7a0dfdbc the file path is not the complete path:

so the correct message on **What** section should be

modules/apps/frontend-taglib/frontend-taglib-clay/src/main/java/com/liferay/frontend/taglib/clay/servlet/taglib/base/BaseClayTag.java

----

# 678e4379fb055804a2100169b6310319d8f0d07e

Incorrect format on multiple file breaking change

Correct message should be:
```
LPS-199164 Move XmlRpcUtil, Success, Fault into impl
    
# breaking_change_report
## What portal-kernel/src/com/liferay/portal/kernel.xmlrpc.Success.java
XmlRpcUtil related files are moved from portal-kernel into portal-impl.
## Why
We are merging portal-kernel into portal-impl.
## Alternatives
Make sure to have portal-impl in build dependency and change import statement to use the same classes in portal-impl.
----

# breaking_change_report
## What portal-kernel/src/com/liferay/portal/kernel.xmlrpc.Fault.java
XmlRpcUtil related files are moved from portal-kernel into portal-impl.
## Why
We are merging portal-kernel into portal-impl.
## Alternatives
Make sure to have portal-impl in build dependency and change import statement to use the same classes in portal-impl.
----

# breaking_change_report
## What portal-kernel/src/com/liferay/portal/kernel.xmlrpc.XmlRpcUtil.java
XmlRpcUtil related files are moved from portal-kernel into portal-impl.
## Why
We are merging portal-kernel into portal-impl.
## Alternatives
Make sure to have portal-impl in build dependency and change import statement to use the same classes in portal-impl.
----
```