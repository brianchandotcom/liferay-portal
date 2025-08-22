<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<h2>${languageUtil.get(locale, "description", "Description")}</h2>

<#if description.getData()?has_content>
	<div class="description-content pt-4">
		${description.getData()}
	</div>
</#if>

<#assign
	channel = restClient.get(
	"/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${scopeGroupId}'"
	)

	channelId = (channel.items[0].id)!
	productId = (CPDefinition_cProductId.getData())!"0"
/>

<#if channel?has_content && (CPDefinition_cProductId.getData())?has_content>

	<#assign
		product = restClient.get(
		"/headless-commerce-delivery-catalog/v1.0/channels/" + channelId +
		"/products/" + productId +
		"?accountId=-1&nestedFields=categories,productSpecifications,skus&skus.accountId=-1&skus.currencyCode=USD"
		)

		productSpecifications = product.productSpecifications![]
		appVideoUrl = productSpecifications?filter(
		spec -> stringUtil.equals(spec.specificationKey, "app-storefront-video-url")
		)
		videoUrl = (appVideoUrl[0].value)!""
	/>

	<#if appVideoUrl?has_content>
		<script>
			setTimeout(function () {
				Liferay.fire('plyr:play', { videoURL: "${videoUrl}" });
			}, 100);
		</script>
	</#if>
</#if>

<div>
	<#if _CUSTOM_FIELD_License.getData()?has_content>
		<h2>${languageUtil.get(locale, "license", "License")}</h2>

		<div class="description-content pt-4">
			${_CUSTOM_FIELD_License.getData()}
		</div>
	</#if>
</div>