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

<div class="apps-search-results">
	<div class="cards-container">
		<#if entries?has_content>
			<#list entries as entry>
				<#if entry?has_content>
					<#assign productId = entry.getCPDefinitionId() />
				</#if>

				<#assign
					capabilities = getSpecificationValues("product-metadata", "liferay-products-capabilities", productId)
					categories = getSpecificationValues("product-metadata", "liferay-products-categories", productId)
					developerName = getSpecificationValue("product-metadata", "developer-name", productId)
					productDescription = stringUtil.shorten(htmlUtil.stripHtml(entry.getDescription()!""), 150, "...")
					productImage = cpContentHelper.getDefaultImageFileURL(-1, productId)!""
				/>

				<a class="product-card" href="${cpContentHelper.getFriendlyURL(entry, themeDisplay)}">
					<div class="card-image-wrapper marketplace-product-card">
						<img alt="${entry.getName()}" class="card-product-image" draggable="false" loading="lazy" src="${productImage}" />
					</div>

					<div class="card-body">
						<div class="card-header-section">
							<h3 class="card-product-title">${entry.getName()}</h3>

							<p class="card-developer-name">${developerName!''}</p>

							<div class="card-tags">
								<#if categories?has_content>
									<#list categories as category>
										<span class="card-tag">${category}</span>
									</#list>
								</#if>
							</div>
						</div>

						<#if capabilities?has_content>
							<ul class="card-bullet-list">
								<#list capabilities as tag>
									<#if tag?trim?has_content>
										<li class="card-bullet-item">
											<span class="card-bullet-icon">
												<svg fill="none" height="14" viewBox="0 0 14 14" width="14" xmlns="http://www.w3.org/2000/svg">
													<path d="M2.5 7L5.5 10L11.5 4" stroke="#0B5FFF" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" />
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
			</#list>
		</#if>
	</div>
</div>

<style type="text/css">
	.apps-search-results .cards-container {
		display: grid;
		gap: 1.5rem;
		grid-template-columns: repeat(3, minmax(0, 1fr));
		padding-bottom: 4rem;
	}

	.apps-search-results .product-card {
		background: #ffffff;
		border-radius: 12px;
		border: 1px solid #E2E2E4;
		box-sizing: border-box;
		color: #1c1c24;
		cursor: pointer;
		display: flex;
		flex-direction: column;
		gap: 0;
		overflow: hidden;
		text-decoration: none;
		transition: border-color 0.2s ease, box-shadow 0.2s ease, background 0.2s ease;
		width: 392px;
	}

	.apps-search-results .product-card:hover {
		background: #FBFCFE;
		border-color: #BBD2FF;
		box-shadow: 0 6px 16px rgba(60, 60, 60, 0.08);
		color: #1c1c24;
	}

	.apps-search-results .card-image-wrapper {
		align-items: center;
		background-position: center;
		background-repeat: no-repeat;
		background-size: cover;
		display: flex;
		height: 221px;
		justify-content: center;
		width: 100%;
	}

	.apps-search-results .card-product-image {
		height: 120px;
		object-fit: contain;
		position: relative;
		width: 120px;
		z-index: 1;
	}

	.apps-search-results .card-body {
		display: flex;
		flex-direction: column;
		gap: 0.75rem;
		padding: 20px 24px 24px;
		text-align: left;
	}

	.apps-search-results .card-header-section {
		display: flex;
		flex-direction: column;
		gap: 2px;
	}

	.apps-search-results .card-product-title {
		color: #1c1c24;
		font-size: 1.125rem;
		font-weight: 700;
		line-height: 1.3;
		margin: 0 0 2px;
		word-break: break-word;
	}

	.apps-search-results .card-developer-name {
		color: #54555F;
		font-size: 0.8125rem;
		font-weight: 400;
		line-height: 1.4;
		margin: 0 0 8px;
	}

	.apps-search-results .card-tags {
		align-items: flex-start;
		display: flex;
		flex-wrap: wrap;
		gap: 6px;
	}

	.apps-search-results .card-tag {
		background-color: #e6ebf5;
		border-radius: 4px;
		color: #2E5AAC;
		display: inline-flex;
		flex: 0 0 auto;
		font-size: 13px;
		font-weight: 400;
		line-height: 16px;
		padding: 4px 8px;
		white-space: nowrap;
		width: fit-content;
	}

	.apps-search-results .card-description {
		color: #3c3c4e;
		font-size: 0.875rem;
		font-weight: 400;
		line-height: 1.55;
		margin: 0;
	}

	.apps-search-results .card-bullet-list {
		display: flex;
		flex-direction: column;
		gap: 6px;
		list-style: none;
		margin: 0;
		padding: 0;
	}

	.apps-search-results .card-bullet-item {
		align-items: flex-start;
		color: #3c3c4e;
		display: flex;
		font-size: 0.8125rem;
		gap: 8px;
		line-height: 1.5;
	}

	.apps-search-results .card-bullet-icon {
		align-items: center;
		background-color: #E7EFFF;
		border-radius: 50%;
		display: inline-flex;
		flex-shrink: 0;
		height: 20px;
		justify-content: center;
		margin-top: 1px;
		width: 20px;
	}

	/* ── Responsive ── */
	@media screen and (max-width: 599px) {
		.apps-search-results .cards-container {
			gap: 0.75rem;
			grid-template-columns: 1fr;
		}

		.apps-search-results .card-image-wrapper {
			height: 140px;
		}
	}

	@media screen and (min-width: 600px) and (max-width: 899px) {
		.apps-search-results .cards-container {
			gap: 1rem;
			grid-template-columns: repeat(2, minmax(0, 1fr));
		}
	}

	.marketplace-product-card {
		background:
			linear-gradient(80deg, rgba(255, 255, 255, 1), transparent 50%),
			linear-gradient(0deg, rgba(255, 255, 255, 1), transparent 80%),
			radial-gradient(circle, #ffffff 35%, rgba(243, 229, 254, 1));
		background-position: center;
		background-repeat: no-repeat;
		background-size: cover;
		overflow: hidden;
		position: relative;
		width: 100%;
	}

	.marketplace-product-card>* {
		position: relative;
		z-index: 1;
	}

	.marketplace-product-card::after {
		background: radial-gradient(circle, rgba(202, 209, 247, 1), transparent 40%);
		content: "";
		height: 100vh;
		pointer-events: none;
		position: absolute;
		right: 0;
		top: 0;
		transform: translate3d(55%, -50%, 0);
		width: 100vw;
		z-index: 0;
	}

	@media (max-width: 1024px) {
		.marketplace-product-card::after {
			background: radial-gradient(circle, rgba(202, 209, 247, 0.9), transparent 50%);
			transform: translate3d(30%, -40%, 0);
		}
	}

	@media (max-width: 600px) {
		.marketplace-product-card::after {
			background: radial-gradient(circle, rgba(202, 209, 247, 0.6), transparent 60%);
			height: 80vh;
			transform: translate3d(0%, -30%, 0);
			width: 120vw;
		}
	}
</style>