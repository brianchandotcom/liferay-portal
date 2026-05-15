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
						developerName = getSpecificationValue("product-metadata", "developer-name", entry.getCPDefinitionId())
						priceModel = getSpecificationValue("pricing-licensing-terms", "price-model", entry.getCPDefinitionId())
						productDescription = stringUtil.shorten(htmlUtil.stripHtml(entry.getDescription()!""), 120, "...")
						productId = entry.getCPDefinitionId()
						productImage = cpContentHelper.getDefaultImageFileURL(commerceContext.getAccountEntry().getAccountEntryId(), productId)
					/>

					<a class="card d-flex flex-column overflow-hidden text-dark text-decoration-none" href="${cpContentHelper.getFriendlyURL(entry, themeDisplay)}">
						<div class="card-image-wrapper align-items-center d-flex justify-content-center w-100">
							<img alt="${entry.getName()}" class="card-product-image" draggable="false" loading="lazy" src="${productImage}" />
						</div>

						<div class="card-body d-flex flex-column">
							<h3 class="card-title">${entry.getName()}</h3>

							<#if developerName?has_content>
								<p class="card-developer">${developerName}</p>
							</#if>

							<p class="card-description">${productDescription}</p>

							<#if priceModel?has_content>
								<span class="card-price mt-auto <#if priceModel?lower_case == 'free'>card-price-free</#if>">
									${priceModel?cap_first}
								</span>
							</#if>
						</div>
					</a>
				</#if>
			</#list>
		</#if>
	</div>
</div>