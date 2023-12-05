# 3aa30f7e03264d3798731f301853ec4f952c3637

The commit message does not include the full path. The correct message should be:

```
COMMERCE-12579 Use new find method. Also rename hasDirectReplacement.

# breaking
 
## What modules/apps/commerce/commerce-product-content-api/src/main/java/com/liferay/commerce/product/content/helper/CPContentHelper.java

modules/apps/commerce/commerce-product-content-api/src/main/java/com/liferay/commerce/product/content/helper/CPContentHelper.java has method hasDirectReplacement renamed to isDirectReplacement

## Why

The method now checks whether the sku is a replacement of another product rather than checking whether it has replacements

```

----

# 1063732432e7a5e5d3cf782ec1652728ef053eb9

On the message of the commit 1063732432e7a5e5d3cf782ec1652728ef053eb9 the file path is not the complete path:

so the correct message on **What** section should be

modules/apps/document-library/document-library-repository-external-api/src/main/java/com/liferay/document/library/repository/external/ExtRepository.java

```
LPS-197315 add new param to the addExtRepositoryFileEntry method with the fileName of the file Entry

# breaking

## What modules/apps/document-library/document-library-repository-external-api/src/main/java/com/liferay/document/library/repository/external/ExtRepository.java

com/liferay/document/library/repository/external/ExtRepository.java has changed the method ExtRepositoryFileEntry addExtRepositoryFileEntry(String extRepositoryParentFolderKey, String mimeType, String title, String description, String changeLog, InputStream inputStream) to ExtRepositoryFileEntry addExtRepositoryFileEntry(String extRepositoryParentFolderKey, String fileName, String mimeType, String title, String description, String changeLog, InputStream inputStream) adding the new parameter fileName.

## Why
   
this change is required to create the files on the sharepoint external repository with the file source file name.

```

----

# 2681b881ad469095e572928f265cefe2f51cdb16

On the message of the commit 2681b881ad469095e572928f265cefe2f51cdb16 the file path is not the complete path:

so the correct message on **What** section should be

modules/apps/frontend-taglib/frontend-taglib-chart/src/main/resources/META-INF/liferay-chart.tld

```
LPS-198114 Removes unused taglib and sample since those are not used in portal and have dependencies with soy

# breaking

## What modules/apps/frontend-taglib/frontend-taglib-chart/src/main/resources/META-INF/liferay-chart.tld

We are deleting all taglibs under liferay chart module

## Why

The DXP support from soy was removed as part of https://liferay.atlassian.net/browse/LPS-122954 and the following classes; TemplateRendererTag and ComponentRendererTag, were deprecated as part of https://liferay.atlassian.net/browse/LPS-122966

```

----

# 87a3c8bf38374f1987debdcedaed7f9e7a0dfdbc

On the message of the commit 87a3c8bf38374f1987debdcedaed7f9e7a0dfdbc the file path is not the complete path:

so the correct message on **What** section should be

modules/apps/frontend-taglib/frontend-taglib-clay/src/main/java/com/liferay/frontend/taglib/clay/servlet/taglib/base/BaseClayTag.java

```
LPS-198462 Removes unused BaseClayTag
   
# breaking
   
## What modules/apps/frontend-taglib/frontend-taglib-clay/src/main/java/com/liferay/frontend/taglib/clay/servlet/taglib/base/BaseClayTag.java
    
We are deleting BaseClayTag.java
    
## Why
    
It is an internal class and it doesn't have any usage in portal

```

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

----

# ab4a450c1d7ffe215a8d56379c787fb34c1ea41b

Incorrect format on multiple file breaking change

Correct message should be:
```
LPS-198859 Remove ThreadLocalDistributor, no usage
    
# breaking_change_report
## What portal-kernel/src/com/liferay/portal/kernel/util/ThreadLocalDistributor.java
ThreadLocalDistributor is being removed.
## Why
ThreadLocalDistributor has no current usage.
----

# breaking_change_report
## What portal-kernel/src/com/liferay/portal/kernel/util/ThreadLocalDistributorRegistry.java
ThreadLocalDistributorRegistry is being removed.
## Why
ThreadLocalDistributor has no current usage.
----
```
----

# 0bfc1206ac4a93ec401be491f9553ac94ecea0ed

Missing breaking change

Correct message should be:
```
LPS-200453 Make PortletToolbar not a spring bean and provide the instance through filed INSTANCE.
    
# breaking_change_report

## What portal-kernel/src/com/liferay/portal/kernel/portlet/toolbar/PortletToolbar.java

PortletToolbar constructor changed to private.

## Why

PortletToolbar is being removed from util-spring, it needs a static INSTANCE field inside to replace existing usages.

## Alternatives

Directly use PortletToolbar.INSTANCE to get the instance of PortletToolbar.
----
```
----

# f971716348b82b1ea6747ae3c011b40616bb5884

Missing breaking change

Correct message should be:
```
LPS-198809 Remove ModelSearchRegistrarHelper, not used anymore

# breaking_change_report
## What modules/apps/portal-search/portal-search-spi/src/main/java/com/liferay/portal/search/spi/model/registrar/ModelSearchRegistrarHelper.java
ModelSearchRegistrarHelper is removed.
## Why
The self bootstraping style *SearchRegistrar has been changed to service collecting of ModelSearchConfigurator
----

# breaking_change_report
## What modules/apps/portal-search/portal-search-spi/src/main/java/com/liferay/portal/search/spi/model/registrar/contributor/ModelSearchDefinitionContributor.java
ModelSearchDefinitionContributor is removed.
## Why
The self bootstraping style *SearchRegistrar has been changed to service collecting of ModelSearchConfigurator
## Alternatives
Rewrite *SearchRegistrar to become an osgi service of type ModelSearchConfigurator. Move all previous ModelSearchConfigurator setter call parameter as corresponding ModelSearchConfigurator getter return value.
----
```
----

# ce0cf3a6fab17cb1ac42b17f8bce790cbf176317

Incorrect format on multiple file breaking change

Correct message should be:
```
LPS-201086 Merge AuditMessageFactoryUtil/AuditMessageFactoryImpl into AuditMessageFactory

# breaking_change_report
## What portal-kernel/src/com/liferay/portal/kernel/audit/AuditMessageFactory.java
AuditMessageFactoryUtil and AuditMessageFactoryImpl logic are merged into AuditMessageFactory
## Why
This interface/impl/util separation does not have any value. Merge it into single class to avoid the unnecessary module.
----

# breaking_change_report
## What portal-kernel/src/com/liferay/portal/kernel/audit/AuditMessageFactoryUtil.java
AuditMessageFactoryUtil and AuditMessageFactoryImpl logic are merged into AuditMessageFactory
## Why
This interface/impl/util separation does not have any value. Merge it into single class to avoid the unnecessary module.
----
```
----

# 258a63398ddedbdba27e1b193c83c30031509725

Incorrect the format of file path line

Correct message should be:
```
LPS-200073 Remove class AssetEntriesFacet

# breaking
## What portal-kernel/src/com/liferay/portal/kernel/search/facet/AssetEntriesFacet.java
Class is removed.
## Why
This class has been deprecated since 7.1.x, its only usage in rules_user_custom_attribute_content.drl is replaced by FacetImpl.
----
```
----

# f46f1e49076f31484ad6cceede099bb16c9ef911
Incorrect format on breaking change

Correct message should be:
```
# breaking
## What portal-kernel/src/com/liferay/portal/kernel/dao/orm/IndexableActionableDynamicQuery.java
setIndexWriterHelper() method is being removed.
## Why
This setter was added for the class UserIndexer (see 73427a8). UserIndexer has been deprecated and removed from the portal though.
----
```
----

# 76c2d3b68c19a1b33f18e9221d83f34310daed45

Typo in file path.

Correct message should be:
```
LPS-196035 Avoid needing to regenerate after every screenName or emailAddress change. Use the immutable userId field for WebDAV access.

# breaking_change_report
## What portal-impl/src/com/liferay/portal/model/impl/UserImpl.java
WebDAV clients can no longer use the user's screenName or emailAddress, nor the user's regular password when authenticating via Digest Auth.
## Why
WebDAV (or Digest Auth more generally) now requires each user to generate a separate password for this access, and it requires the user to take specific Account Settings UI actions to do so. Previously a simple web login would suffice. To avoid unexpected WebDav access rejections, we decided to simplify the the UX and use userId.
----
```