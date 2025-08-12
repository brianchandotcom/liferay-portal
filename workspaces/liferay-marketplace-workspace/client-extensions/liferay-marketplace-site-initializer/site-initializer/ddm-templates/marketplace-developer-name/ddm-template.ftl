<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<#if currentURL?has_content>
	<#if currentURL?contains('web')>
		<#assign
			index = 2
			partsUrl = currentURL?split('/')
			siteName = partsUrl[index..index]?join('/')
		/>
	</#if>
</#if>

<#assign channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${scopeGroupId}'") />

<#if channel?has_content>
	<#assign channelId = channel.items[0].id />
</#if>

<#if (CPDefinition_cProductId.getData())??>
	<#assign productId = CPDefinition_cProductId.getData() />
</#if>

<#assign
	product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&nestedFields=productSpecifications")
	productSpecifications = product.productSpecifications![]
	catalogName = product.catalogName
/>

<#if catalogName?has_content>
	<#assign
		publisherDetailsResponse = restClient.get("/c/publisherdetailses?filter=publisherName eq '${catalogName}'")
		redirectPath = "/e/publisher-details/?????"
	/>

	<#if publisherDetailsResponse?has_content>
		<#assign publisherPage = publisherDetailsResponse.items />
			<#if publisherPage?has_content>
				<#assign publisherDetail = publisherPage[0] />
			</#if>
	</#if>
</#if>

<div>
	<#if productSpecifications?has_content>
		<#assign developerNames = productSpecifications?filter(item -> stringUtil.equals(item.specificationKey, "developer-name")) />

		<#if developerNames?has_content>
			<#list developerNames as developerName>
				<#if publisherDetail?has_content>
					<a class="bg-neutral-8" href="${redirectPath}/${publisherDetail.id}">
						${developerName.value}
					</a>
				<#else>
					<a class="bg-neutral-8" 	href="/?developer-name=${developerName.value}">
						${developerName.value}
					</a>
				</#if>
			</#list>
		</#if>
	</#if>
</div>