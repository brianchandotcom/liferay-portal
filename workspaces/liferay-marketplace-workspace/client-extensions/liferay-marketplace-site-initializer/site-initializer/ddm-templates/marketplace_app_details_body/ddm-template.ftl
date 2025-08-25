<#assign
	channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${themeDisplay.getScopeGroupId()}'")

	product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/" + channel.items[0].id + "/products/" + CPDefinition_cProductId.getData() + "?accountId=-1&nestedFields=categories,productSpecifications,skus&skus.accountId=-1&skus.currencyCode=USD")
	productSpecifications = product.productSpecifications![]

	licenseSpecifications = productSpecifications?filter(spec -> stringUtil.equals(spec.specificationKey, "license"))
	storefrontVideoURLSpecifications = productSpecifications?filter(spec -> stringUtil.equals(spec.specificationKey, "app-storefront-video-url"))

	licenseValue = (licenseSpecifications[0].value)!""
	storefrontVideoURLValue = (storefrontVideoURLSpecifications[0].value)!""
/>

<h2>${languageUtil.get(locale, "description")}</h2>

<#if description.getData()?has_content>
	<div class="description-content pt-4">
		${description.getData()}
	</div>
</#if>

<div>
	<#if licenseSpecifications?has_content>
		<h2>${languageUtil.get(locale, "license")}</h2>

		<div class="description-content pt-4">
			${licenseValue}
		</div>
	</#if>
</div>

<#if storefrontVideoURLSpecifications?has_content>
	<script>
		setTimeout(function () {
			Liferay.fire('plyr:play', { videoURL: "${storefrontVideoURLValue}" });
		}, 100);
	</script>
</#if>