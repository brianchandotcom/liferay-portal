<style>
	.app-container {
		border-color: #2e5aac !important;
		color: #2e5aac;
		font-size: MEDIUM;
	}

	.app-category {
		flex: 1;
		font-size: 11px;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.app-container .app-category {
		background-color: #e6ebf5;
		color: #1c3667;
		font-weight: 600;
		height: 20px;
		padding: 3px 8px;
	}

	.app-container .app-product-type {
		font-size: 11px;
		font-weight: 600;
		height: 20px;
		min-width: 0;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.app-details-category-badge .app-type-badge {
		right: 2px !important;
		top: 0px !important;
		position: relative !important;
	}

	.diamond-icon-container {
		color: #C9C9CF;
		height: 4px;
		width: 4px;
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

<#assign channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=siteGroupId eq '${scopeGroupId}'") />

<#if channel?has_content>
	<#assign channelId = channel.items[0].id />
</#if>

<#if (CPDefinition_cProductId.getData())??>
	<#assign productId = CPDefinition_cProductId.getData() />
</#if>

<#assign
	product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&nestedFields=categories")

	categories = product.categories![]

	marketplaceAppCategories = categories?filter(category -> category.vocabulary?upper_case?replace(" ", "-", "r") == "MARKETPLACE-APP-CATEGORY")
	marketplaceCategory = categories?filter(category -> category.vocabulary?upper_case?replace(" ", "-", "r") == "MARKETPLACE-CATEGORY")?first!""
/>

<div class="app-container color-neutral-3 d-flex flex-wrap font-size-paragraph-small justify-content-between w-100">
	<div class="app-details-category-badge d-flex">
		<#if marketplaceCategory?has_content>
			<#assign badgeType = marketplaceCategory.name?lower_case?replace(" ", "-", "r")?replace("/", "-", "r") />

			<#if stringUtil.equals(marketplaceCategory.name, "Other")>
				<div></div>
			<#else>
				<a class="text-decoration-none text-reset" href="/applications?marketplaceCategory=${marketplaceCategory.name?url}">
					<span class="app-type-badge ${badgeType} d-flex align-items-center bg-neutral-8 border-radius-small mb-1 mr-2 px-3 rounded-lg" title="${marketplaceCategory.name}">
						${marketplaceCategory.name}
					</span>
				</a>
			</#if>
		</#if>

		<#if marketplaceAppCategories?has_content>
			<#if marketplaceCategory?has_content && !stringUtil.equals(marketplaceCategory.name, "Other")>
				<span class="align-items-center d-flex justify-content-between">
					<span class="align-items-center d-flex diamond-icon-container justify-content-between mr-3">
						<@clay["icon"] symbol="diamond" />
					</span>
				</span>
			</#if>

			<#list marketplaceAppCategories as marketplaceAppCategory>
				<a class="text-decoration-none text-reset" href="/applications?marketplaceAppCategory=${marketplaceAppCategory.name?url}">
					<span class="app-category bg-neutral-8 border-radius-small mb-1 mr-2 px-3 rounded-lg" title="${marketplaceAppCategory.name}">
						${marketplaceAppCategory.name}
					</span>
				</a>
			</#list>
		</#if>
	</div>
</div>