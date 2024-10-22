<style>
	.app-container {
		font-size: MEDIUM;
	}

	.app-category {
		display: block;
		flex: 1;
		max-width: 200px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.app-container .app-category {
		background-color: #e6ebf5;
		color: #1c3667;
		padding: 4px 8px;
	}

	.app-container .app-product-type {
		border-color: #2e5aac !important;
		color: #2e5aac;
		min-width: 0;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	@media screen and (max-width: 768px) {
		.app-container {
			font-size: small;
		}
	}

	@media screen and (max-width: 576px) {
		.app-container {
			font-size: x-small;
		}

		.app-container .app-category,
		.app-container .app-product-type {
			padding: 2px 4px;
		}
	}
</style>

<#assign
	PRODUCT_TYPE_CLOUD = "CLOUD"
	PRODUCT_TYPE_DXP = "DXP"
	PRODUCT_TYPE_FREE = "FREE"
	PRODUCT_TYPE_PAID = "PAID"
	VOCABULARY_PRODUCT_CATEGORY = "MARKETPLACE-APP-CATEGORY"
/>

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
	product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&nestedFields=productSpecifications,categories")
	categories = product.categories![]
	productSpecifications = product.productSpecifications![]
/>

<div class="app-container color-neutral-3 d-flex flex-wrap font-size-paragraph-small justify-content-between w-100">
	<div class="d-flex flex-wrap">
		<#if productSpecifications?has_content>

			<#assign productTypes = productSpecifications?filter(item -> stringUtil.equals(item.specificationKey, "type")) />

			<#list productTypes as productType>
				<#if productType.value?upper_case == PRODUCT_TYPE_DXP>
					<#assign
						icon = "dxp-svg"
						type = "DXP App"
					/>
				<#elseif productType.value?upper_case == PRODUCT_TYPE_CLOUD>
					<#assign
						icon = "cloud-svg"
						type = "Cloud App"
					/>
				</#if>

				<#if type?has_content && icon?has_content>
					<div class="align-items-center app-product-type border border-radius-small d-flex mb-1 mr-2 px-2 rounded-lg">
						<div class="app-product-type-icon mr-1">
							<img alt="Icon" class="mb-1" src="/documents/d/${siteName}/${icon}" />
						</div>

						<div class="bg-neutral-8">${type}</div>
					</div>
				</#if>
			</#list>
		</#if>

		<#if categories?has_content>
			<#list categories as category>
				<#if category.vocabulary?replace(" ", "-")?upper_case == VOCABULARY_PRODUCT_CATEGORY>
					<span class="app-category bg-neutral-8 border-radius-small mb-1 mr-2 px-3 rounded-lg" title="${category.name}">
						${category.name}
					</span>
				</#if>
			</#list>
		</#if>
	</div>
</div>