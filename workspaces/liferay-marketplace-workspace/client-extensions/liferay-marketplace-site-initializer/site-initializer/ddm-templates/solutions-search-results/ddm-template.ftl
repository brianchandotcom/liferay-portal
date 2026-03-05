<#assign
	cpDataSourceResult = cpSearchResultsDisplayContext.getCPDataSourceResult()
	commerceContext = renderRequest.getAttribute("COMMERCE_CONTEXT")
	specificationGroups = cpContentHelper.getCPOptionCategories(themeDisplay.getCompanyId())
/>

<div class="color-neutral-3 d-md-block d-none pb-4">
	<strong class="color-black">
		${cpDataSourceResult.length}
	</strong>

	Solutions Available
</div>

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

<div class="adt-solutions-search-results">
	<div class="cards-container pb-6">
		<#if entries?has_content>
			<#list entries as entry>
				<#if entry?has_content>
					<#assign
						portalURL = portalUtil.getLayoutURL(themeDisplay)
						productDescription = stringUtil.shorten(htmlUtil.stripHtml(entry.getDescription()!""), 150, "...")
						productId = entry.getCPDefinitionId()
						productImage = cpContentHelper.getDefaultImageFileURL(commerceContext.getAccountEntry().getAccountEntryId(), entry.getCPDefinitionId())
					/>

					<a class="bg-white d-flex flex-column mb-0 text-dark text-decoration-none solution-search-results-card" href="${cpContentHelper.getFriendlyURL(entry, themeDisplay)}">
						<div class="align-items-center d-flex image-container mb-3">
							<img
								alt="${entry.getName()}"
								class="solution-search-image"
								draggable="false"
								loading="lazy"
								src=${productImage}
							/>
						</div>

						<div class="d-flex flex-column font-size-paragraph-small h-100 justify-content-between p-4">
							<div class="card-image-title-container d-flex flex-column">
								<div>
									<span class="developer-name-text">
										${getSpecificationValue("product-metadata", "developer-name", productId)!''}
									</span>

									<div class="font-weight-semi-bold h2 mt-1 product-name" title="${entry.getName()}">
										${entry.getName()}
									</div>
								</div>

								<div class="font-weight-normal mb-2">
									${productDescription}
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
	.adt-solutions-search-results .cards-container {
		display: grid;
		grid-column-gap: 1rem;
		grid-row-gap: 1.5rem;
		grid-template-columns: repeat(3, minmax(0, 1fr));
	}

	.banner__product-tag {
		background-color: #e6ebf5;
		color: #1c3667;
		font-size: 0.8125rem;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		width: fit-content;
	}

	.developer-name-text {
		font-size: 16px;
		font-weight: 400;
		line-height: 24px;
		letter-spacing: 0;
		text-align: left;
	}

	.image-container {
		min-height: 200px;
		min-width: 293px;
		overflow: hidden;
	}

	.product-name {
		text-overflow: ellipsis;
		overflow: hidden;
		white-space: nowrap;
	}

	.solution-search-image {
		min-height: 200px;
		min-width: 293px;
		object-fit: contain;
	}

	.solution-search-results-card {
		border-radius: 10px;
		border: 1px solid #E7EFFF;
		height: 462px;
		overflow: hidden;
		width: 293px;
	}

	.adt-solutions-search-results .labels .category-names {
		background-color: #2c3a4b;
		bottom: 26px;
		display: none;
		right: 0;
		width: 14.5rem;
	}

	.adt-solutions-search-results .labels .category-names::after {
		border-left: 9px solid transparent;
		border-right: 9px solid transparent;
		border-top: 8px solid var(--neutral-1);
		bottom: -7px;
		content: '';
		left: 0;
		margin: 0 auto;
		position: absolute;
		right: 0;
		width: 0;
	}

	.solution-search-results-card .card-image-title-container .developer-name {
		color: #545d69;
	}

	.adt-solutions-search-results .labels .category-label {
		background-color: #ebeef2;
		color: #545D69;
		font-size: smaller;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.adt-solutions-search-results .labels .category-label-remainder:hover .category-names {
		display: block;
	}

	.adt-solutions-search-results .solutions-search-results-card:hover {
		color: var(--black);
	}

	.productSpec {
		color: #545d69;
	}

	@media screen and (max-width: 599px) {
		.adt-solutions-search-results .cards-container {
			grid-row-gap: 1rem;
			grid-template-columns: 288px;
			justify-content: center;
		}
	}

	@media screen and (min-width: 600px) and (max-width: 899px) {
		.adt-solutions-search-results .cards-container {
			grid-template-columns: repeat(2, minmax(0, 1fr));
		}
	}
</style>