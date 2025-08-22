<style type="text/css">
	.adt-apps-search-results .app-search-results-card:hover {
		color: var(--black);
	}

	.adt-apps-search-results .card-image-title-container .image-container {
		height: 3rem;
	}

	.adt-apps-search-results .card-image-title-container .title-container {
		word-break: break-word;
		word-wrap: break-word;
	}

	.adt-apps-search-results .cards-container .app-search-results-card .card-image-title-container .image-container .app-search-image {
		height: 48px;
		object-fit: contain;
		width: 48px;
	}

	.adt-apps-search-results .labels .category-label-remainder:hover .category-names {
		display: block;
	}

	.app-category-badge {
		border-bottom-left-radius: 10px;
		border-bottom-right-radius: 10px;
		border-top-left-radius: 2px;
		border-top-right-radius: 2px;
		display: inline-block;
		font-size: 11px;
		height: 20px;
		line-height: 20px;
		padding: 0 8px;
		position: absolute;
		right: 32px;
		top: -6px;
	}

	.app-category-batch,
	.app-category-checkout,
	.app-category-fragments,
	.app-category-no-type,
	.app-category-object-action,
	.app-category-other,
	.app-category-payment-methods,
	.app-category-site-initializer,
	.app-category-theme,
	.app-category-workflow-action {
		transition: all 0.3s cubic-bezier(.25, .8, .25, 1);
	}

	.app-category-batch {
		background: #FFE6C6;
		color: #9D4C00;
	}

	.app-category-checkout,
	.app-category-other {
		background: #DAF4C7;
		color: #4E7135;
	}

	.app-category-fragments,
	.app-category-workflow-action {
		background: #DCD7E9;
		color: #503690;
	}

	.app-category-no-type {
		background: #cccccc;
		color: #ffffff;
	}

	.app-category-object-action {
		background-color: #D1ECFA;
		color: #166E9E;
	}

	.app-category-payment-methods {
		background: #D2E6FF;
		color: #2868FF;
	}

	.app-category-site-initializer {
		background: #D1EEDC;
		color: #0E7835;
	}

	.app-category-theme {
		background: #FBE0FF;
		color: #720086;
	}

	.app-search-results-card {
		border: solid 1px #E2E2E4;
		border-radius: 10px;
		box-sizing: border-box;
		cursor: point;
		display: flex;
		height: 289px;
		padding: 16px;
		position: relative;
		transition: all 0.3s cubic-bezier(.25, .8, .25, 1);
	}

	.banner__product-tag {
		background-color: #e6ebf5;
		color: #1c3667;
		font-size: 0.8125rem;
		white-space: nowrap;
		width: fit-content;
	}

	.card-image-title-container {
		height: 48px;
		margin-bottom: 18px;
	}

	.cards-container {
		display: grid;
		grid-column-gap: 1rem;
		grid-row-gap: 1.5rem;
		grid-template-columns: repeat(3, minmax(0, 1fr));
	}

	.developer-name {
		color: #54555F;
		font-size: 13px;
		font-weight: 400;
		line-height: 16px;
	}

	.lfr-layout-structure-item-com-liferay-site-navigation-breadcrumb-web-portlet-sitenavigationbreadcrumbportlet {
		background: #ffffff;
		border-radius: 10px;
		height: 40px;
		padding: 0px 16px;
	}

	.title-container {
		font-size: 18px;
		font-weight: 600;
		line-height: 20px;
	}

	@media screen and (max-width: 599px) {
		.adt-apps-search-results .app-search-results-card {
			height: 281px;
		}

		.adt-apps-search-results .cards-container {
			grid-column-gap: .5rem;
			grid-row-gap: .5rem;
			grid-template-columns: 293px;
			justify-content: center;
		}
	}

	@media screen and (min-width: 600px) and (max-width: 899px) {
		.adt-apps-search-results .cards-container {
			grid-column-gap: .5rem;
			grid-row-gap: 1.5rem;
			grid-template-columns: repeat(2, minmax(0, 1fr));
		}
	}
</style>

<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<#assign
	commerceContext = renderRequest.getAttribute("COMMERCE_CONTEXT")
/>

<div class="adt-apps-search-results">
	<div class="cards-container pb-6">
		<#if entries?has_content>
			<#list entries as entry>
				<#if entry?has_content>
					<#assign
						accountEntryId = commerceContext.getAccountEntry().getAccountEntryId()
						channelId = commerceContext.getCommerceChannelId()
						friendlyURL = cpContentHelper.getFriendlyURL(entry, themeDisplay)
						portalURL = portalUtil.getLayoutURL(themeDisplay)
						productId = entry.getCProductId()
						productName = entry.getName()
						remainingAreasText = []

						product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&images.accountId=-1&nestedFields=productSpecifications,categories,images")
						productImage = cpContentHelper.getDefaultImageFileURL(accountEntryId, entry.getCPDefinitionId())
					/>
					<#if product.categories?has_content && product.productSpecifications?has_content>
						<#assign
							productAreas = product.categories?filter(productCategory -> productCategory.vocabulary?replace(" ", "-") == "marketplace-app-category")![]
							areasListSize = productAreas?size-1
							productSpecifications = product.productSpecifications![]
							productCategories = product.categories?filter(productCategory -> productCategory.vocabulary?replace(" ", "-") == "marketplace-category")![]
						/>
					</#if>

					<#if productCategories[0]?has_content>
						<#assign productCategory = productCategories[0] />
					<#else>
						<#assign productCategory = "" />
					</#if>

					<#if product.description?has_content>
						<#assign productDescription = stringUtil.shorten(htmlUtil.stripHtml(product.description!""), 150, "...") />
					<#else>
						<#assign productDescription = "" />
					</#if>

					<a class="app-search-results-card bg-white border-radius-medium d-flex flex-column mb-0 text-dark text-decoration-none" href=${friendlyURL}>
						<div class="align-items-center card-image-title-container d-flex">
							<div class="image-container mr-2 rounded">
								<img alt="${productName}" class="app-search-image" src="${productImage}" />
							</div>

							<div>
								<span class="d-flex justify-content-end">
									<div>
										<#if productCategory?has_content>
											<#if productCategory.name == 'Other'>
												<div class="app-category-badge"></div>
											<#else>
												<div class="app-category-badge app-category-no-type font-weight-semi-bold
													<#if productCategory.name == 'Theme'> app-category-theme</#if>
													<#if productCategory.name == 'Object action'> app-category-object-action</#if>
													<#if productCategory.name == 'Site Initializer'> app-category-site-initializer</#if>
													<#if productCategory.name == 'Payment methods'> app-category-payment-methods</#if>
													<#if productCategory.name == 'Workflow action'>	app-category-workflow-action</#if>
													<#if productCategory.name == 'Batch'>	app-category-batch</#if>
													<#if productCategory.name == 'Checkout'>	app-category-checkout</#if>
													<#if productCategory.name == 'Fragments'>	app-category-fragments</#if>
												">
												 	${productCategory.name}
												</div>
											</#if>
										</#if>
									</div>
								</span>

								<div class="title-container">
									${productName}
								</div>

								<#if productSpecifications?has_content>
									<#assign productDeveloperName = productSpecifications?filter(item -> item.specificationKey == "developer-name") />

									<#list productDeveloperName as developerNameItem>
										<#if developerNameItem.value?has_content>
											<#assign developerName = developerNameItem.value />
										<#else>
											<#assign developerName = "" />
										</#if>

										<div class="developer-name mt-1">
											${developerName}
										</div>
									</#list>
								</#if>
							</div>
						</div>

						<div class="d-flex flex-column font-size-paragraph-small h-100 justify-content-between">
							<div class="font-weight-normal mb-2 text-break">
								${productDescription}
							</div>

							<div class="d-flex flex-column">
								<#if productSpecifications?has_content>
									<#assign productPriceModels = productSpecifications?filter(item -> item.specificationKey == "price-model") />

									<#list productPriceModels as productPriceModel>
										<#if productPriceModel.value?has_content>
											<#assign priceModel = productPriceModel.value />
										<#else>
											<#assign priceModel = "" />
										</#if>

										<div class="font-weight-semi-bold mb-2 mt-1 text-capitalize">
											${priceModel}
										</div>
									</#list>
								</#if>

								<#if productAreas?has_content>
									<#assign
										principalArea = productAreas[0]
										remainingAreas = productAreas?filter(area -> area.name != principalArea.name)
									/>

									<#list remainingAreas as area>
										<#assign remainingAreasText = remainingAreasText + [area.name] />
									</#list>
								</#if>

								<#if principalArea?has_content>
									<div>
										<span class="banner__product-tag rounded py-1 px-2 mr-2" title="${principalArea.name}">
											${principalArea.name}
										</span>

										<#if areasListSize?has_content && remainingAreasText?has_content>
											<span class="banner__product-tag rounded py-1 px-2" title="${remainingAreasText?join('\n')}">
												+ ${areasListSize}
											</span>
										</#if>
									</div>
								</#if>
							</div>
						</div>
					</a>
				</#if>
			</#list>
		</#if>
	</div>
</div>