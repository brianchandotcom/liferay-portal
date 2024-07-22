<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<#assign channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${scopeGroupId}'") />

<#if channel?has_content>
	<#assign channelId = channel.items[0].id />
</#if>

<#if (CPDefinition_cProductId.getData())??>
	<#assign productId = CPDefinition_cProductId.getData() />
</#if>

<#assign
	product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&images.accountId=-1&nestedFields=images")
	productImage = product.images?filter(item -> item.tags?seq_contains("app icon"))![]
/>

<#if productImage?has_content>
	<#assign productThumbnail = productImage[0].src?split("/o") />
	<#if productThumbnail?has_content && productThumbnail?size gte 2>
		<#assign productThumbnail1 = "/o/${productThumbnail[1]}"!"" />
	<#else>
		<#assign productThumbnail1 = "/o/commerce-media/default/?groupId=${scopeGroupId}" />
	</#if>
<#else>
	<#if product.urlImage?has_content>
		<#assign productThumbnail = product.urlImage?split("/o") />
		<#if productThumbnail?has_content && productThumbnail?size gte 2>
			<#assign productThumbnail1 = "/o/${productThumbnail[1]}"!"" />
		<#else>
			<#assign productThumbnail1 = "/o/commerce-media/default/?groupId=${scopeGroupId}" />
		</#if>
	<#else>
		<#assign productThumbnail1 = "/o/commerce-media/default/?groupId=${scopeGroupId}" />
	</#if>
</#if>

${productThumbnail1}