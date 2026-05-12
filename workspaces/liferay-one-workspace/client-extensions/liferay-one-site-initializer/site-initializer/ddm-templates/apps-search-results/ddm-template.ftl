<#assign
	commerceContext = renderRequest.getAttribute("COMMERCE_CONTEXT")
	specificationGroups = cpContentHelper.getCPOptionCategories(themeDisplay.getCompanyId())
/>

<#function getSpecificationValue specificationGroupKey specificationKey productId defaultValue="">
	<#local specificationGroup = specificationGroups?filter(specificationGroup -> specificationGroup.getKey() == specificationGroupKey) />

	<#if specificationGroup?has_content>
		<#local specifications = cpContentHelper.getCategorizedCPDefinitionSpecificationOptionValues(productId, specificationGroup?first.getCPOptionCategoryId()) />

		<#local specification = specifications?filter(productSpecification ->
			stringUtil.equals(productSpecification.getCPSpecificationOption().getKey(), specificationKey)) />

		<#return (specification?first.value)!defaultValue />
	</#if>

	<#return defaultValue />
</#function>

<div class="apps-search-results">
	<div class="cards-container pb-6">
		<#if entries?has_content>
			<#list entries as entry>
				<#if entry?has_content>
					<#assign
						productDescription = stringUtil.shorten(htmlUtil.stripHtml(entry.getDescription()!""), 150, "...")
						productId = entry.getCPDefinitionId()
						productImage = cpContentHelper.getDefaultImageFileURL(commerceContext.getAccountEntry().getAccountEntryId(), productId)
					/>

					<a class="app-search-results-card bg-white border-radius-medium d-flex flex-column mb-0 text-dark text-decoration-none" href="${cpContentHelper.getFriendlyURL(entry, themeDisplay)}">
						<div class="align-items-center card-image-title-container d-flex">
							<div class="image-container mr-2 rounded">
								<img alt="${entry.getName()}" class="app-search-image" draggable="false" loading="lazy" src="${productImage}" />
							</div>

							<div>
								<div class="product-title">
									${entry.getName()}
								</div>

								<div class="developer-name mt-1">
									${getSpecificationValue("product-metadata", "developer-name", productId)!''}
								</div>
							</div>
						</div>

						<div class="d-flex flex-column font-size-paragraph-small h-100 justify-content-between">
							<div class="font-weight-normal mb-2 text-break">
								${productDescription}
							</div>

							<div class="d-flex flex-column">
								<div class="font-weight-semi-bold mb-2 mt-1 text-capitalize">
									${getSpecificationValue("pricing-licensing-terms", "price-model", productId)!''}
								</div>
							</div>
						</div>
					</a>
				</#if>
			</#list>
		</#if>
	</div>
</div>

<style type="text/css">
	.apps-search-results .app-search-results-card:hover {
		color: var(--black);
	}

	.apps-search-results .card-image-title-container .image-container {
		height: 3rem;
	}

	.apps-search-results .card-image-title-container .product-title {
		word-break: break-word;
		word-wrap: break-word;
	}

	.apps-search-results .cards-container .app-search-results-card .card-image-title-container .image-container .app-search-image {
		height: 48px;
		object-fit: contain;
		width: 48px;
	}

	.apps-search-results .labels .category-label-remainder:hover .category-names {
		display: block;
	}

	.app-search-results-card {
		border: solid 1px #E2E2E4;
		border-radius: 10px;
		box-sizing: border-box;
		cursor: pointer;
		display: flex;
		height: 289px;
		padding: 24px;
		position: relative;
		transition: all 0.3s cubic-bezier(.25, .8, .25, 1);
	}

	.app-search-results-card:hover {
		background: #FBFCFE !important;
		border: solid 1px #BBD2FF;
		box-shadow: 0 6px 6px #3C3C3C0F;
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
		grid-column-gap: 1.5rem;
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

	.product-title {
		font-size: 18px;
		font-weight: 600;
		line-height: 20px;
	}

	@media screen and (max-width: 599px) {
		.apps-search-results .app-search-results-card {
			height: 281px;
		}

		.apps-search-results .cards-container {
			grid-column-gap: .5rem;
			grid-row-gap: .5rem;
			grid-template-columns: 293px;
			justify-content: center;
		}
	}

	@media screen and (min-width: 600px) and (max-width: 899px) {
		.apps-search-results .cards-container {
			grid-column-gap: .5rem;
			grid-row-gap: 1.5rem;
			grid-template-columns: repeat(2, minmax(0, 1fr));
		}
	}
</style>