<#assign
	commerceContext = renderRequest.getAttribute("COMMERCE_CONTEXT")
	scopeGroupId = themeDisplay.getScopeGroupId()
	specificationGroups = cpContentHelper.getCPOptionCategories(themeDisplay.getCompanyId())
/>

<#function getSpecificationValue specificationGroupKey specificationKey productId defaultValue="">
	<#local specificationGroup=specificationGroups?filter(specificationGroup -> specificationGroup.getKey() == specificationGroupKey) />
		<#if specificationGroup?has_content>
			<#local specifications=cpContentHelper.getCategorizedCPDefinitionSpecificationOptionValues(productId, specificationGroup?first.getCPOptionCategoryId()) />
			<#local specification=specifications?filter(productSpecification -> stringUtil.equals(productSpecification.getCPSpecificationOption().getKey(), specificationKey)) />

			<#return (specification?first.value)!defaultValue />
		</#if>

		<#return defaultValue />
</#function>

<#function getSpecificationValues specificationGroupKey specificationKey productId>
	<#local specificationGroup=specificationGroups?filter(specificationGroup -> specificationGroup.getKey() == specificationGroupKey) />
		<#if specificationGroup?has_content>
			<#local specifications=cpContentHelper.getCategorizedCPDefinitionSpecificationOptionValues( productId, specificationGroup?first.getCPOptionCategoryId() ) />
			<#local specificationsFiltered=specifications?filter(productSpecification -> stringUtil.equals(productSpecification.getCPSpecificationOption().getKey(), specificationKey)) />

			<#if specificationsFiltered?has_content>
				<#return specificationsFiltered?map(item -> item.value) />
			</#if>
		</#if>
	<#return [] />
</#function>

<div class="card-grid">
	<div class="cards-container">
		<#if entries?has_content>
			<#list entries as entry>
				<#if entry?has_content>
					<#assign
						capabilities = getSpecificationValues("product-metadata", "liferay-products-capabilities", entry.getCPDefinitionId())
						categories = getSpecificationValues("product-metadata", "liferay-products-categories", entry.getCPDefinitionId())
						developerName = getSpecificationValue("product-metadata", "developer-name", entry.getCPDefinitionId())
						productImage = cpContentHelper.getDefaultImageFileURL(-1, entry.getCPDefinitionId())!""
					/>

					<a class="card d-flex flex-column overflow-hidden text-dark text-decoration-none" href="${cpContentHelper.getFriendlyURL(entry, themeDisplay)}">
						<div class="card-image-wrapper d-flex align-items-center justify-content-center w-100">
							<img alt="${entry.getName()}" class="card-product-image" draggable="false" loading="lazy" src="${productImage}" />
						</div>

						<div class="card-body d-flex flex-column">
							<div class="d-flex flex-column">
								<h3 class="card-title">${entry.getName()}</h3>

								<p class="card-subtitle">${developerName!''}</p>

								<#if categories?has_content>
									<div class="d-flex flex-wrap">
										<#list categories as category>
											<span class="badge badge-info mr-1 mb-1">${category}</span>
										</#list>
									</div>
								</#if>
							</div>

							<#if capabilities?has_content>
								<ul class="card-bullet-list list-unstyled mb-0">
									<#list capabilities as tag>
										<#if tag?trim?has_content>
											<li class="card-bullet-item d-flex align-items-start small">
												<span class="card-bullet-icon d-flex align-items-center flex-shrink-0 justify-content-center text-primary">
													<svg fill="none" height="14" viewBox="0 0 14 14" width="14" xmlns="http://www.w3.org/2000/svg">
														<path d="M2.5 7L5.5 10L11.5 4" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" />
													</svg>
												</span>
												<span>${tag}</span>
											</li>
										</#if>
									</#list>
								</ul>
							</#if>
						</div>
					</a>
				</#if>
			</#list>
		</#if>
	</div>
</div>