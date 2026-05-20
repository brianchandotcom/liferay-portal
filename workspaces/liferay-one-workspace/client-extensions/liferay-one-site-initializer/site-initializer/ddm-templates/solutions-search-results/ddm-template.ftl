<#assign
	specificationGroups = cpContentHelper.getCPOptionCategories(themeDisplay.getCompanyId())
/>

<div class="color-neutral-3 d-md-block d-none pb-4">
	<strong class="color-black">
		${entries?size}
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
						productDescription = stringUtil.shorten(htmlUtil.stripHtml(entry.getDescription()!""), 150, "...")
						productId = entry.getCPDefinitionId()
						productImage = cpContentHelper.getDefaultImageFileURL(-1, entry.getCPDefinitionId())
					/>

					<a class="bg-white d-flex flex-column mb-0 overflow-hidden solution-search-results-card text-dark text-decoration-none" href="${cpContentHelper.getFriendlyURL(entry, themeDisplay)}">
						<div class="align-items-center d-flex mb-3 overflow-hidden solution-image-container">
							<img
								alt="${entry.getName()}"
								class="solution-card-image w-100"
								draggable="false"
								loading="lazy"
								src="${productImage}"
							/>
						</div>

						<div class="d-flex flex-column font-size-paragraph-small h-100 justify-content-between p-4">
							<div class="d-flex flex-column">
								<div>
									<span class="font-weight-normal">
										${getSpecificationValue("product-metadata", "developer-name", productId)!''}
									</span>

									<div class="font-weight-semi-bold h2 mt-1 text-truncate" title="${entry.getName()}">
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