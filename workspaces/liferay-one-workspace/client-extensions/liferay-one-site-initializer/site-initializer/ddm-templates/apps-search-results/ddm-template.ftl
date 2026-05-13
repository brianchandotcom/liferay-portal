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

<div class="card-grid">
	<div class="cards-container">
		<#if entries?has_content>
			<#list entries as entry>
				<#if entry?has_content>
					<#assign
						productDescription = stringUtil.shorten(htmlUtil.stripHtml(entry.getDescription()!""), 150, "...")
						productId = entry.getCPDefinitionId()
						productImage = cpContentHelper.getDefaultImageFileURL(commerceContext.getAccountEntry().getAccountEntryId(), productId)
					/>

					<a class="card d-flex flex-column overflow-hidden text-dark text-decoration-none" href="${cpContentHelper.getFriendlyURL(entry, themeDisplay)}">
						<div class="card-image-wrapper d-flex align-items-center justify-content-center w-100">
							<img alt="${entry.getName()}" class="card-product-image" draggable="false" loading="lazy" src="${productImage}" />
						</div>

						<div class="card-body d-flex flex-column">
							<h3 class="card-title">${entry.getName()}</h3>

							<p class="card-subtitle">${getSpecificationValue("product-metadata", "developer-name", productId)!''}</p>

							<div class="font-weight-normal mb-2 text-break">
								${productDescription}
							</div>

							<div class="font-weight-semi-bold mb-2 mt-1 text-capitalize">
								${getSpecificationValue("pricing-licensing-terms", "price-model", productId)!''}
							</div>
						</div>
					</a>
				</#if>
			</#list>
		</#if>
	</div>
</div>