<style>
	.container-release-highlights-search {
		a.features-container:hover, a.features-container.hover {
			background-color: var(--color-action-primary-hover-10, #EDF3FE);
			border: 0.0625rem solid var(--link-hover-color, #0053F0);
			color: var(--link-hover-color, #0053F0);
			text-decoration: none;
		}

		.empty-container {
			background-color: var(--color-neutral-0);
			border-color: var(--color-neutral-3);
			border-radius: var(--border-radius);
			border-style: solid;
			border-width: 0.0625rem;
			margin-top: 1.5rem;
			padding: 1.5rem 1.5rem 0.0625rem 1.5rem;
		}

		.empty-message-title {
			color: var(--color-neutral-10, #282934);
			font-size: 1.125rem;
			font-weight: 600;
			line-height: 1.25rem;
		}

		.empty-result-message {
			align-items: center;
			margin: 5rem auto;
		}

		.features-box {
			padding: 0.25rem 0.25rem 0.25rem 0.625rem;
		}

		.features-container {
			background-color: var(--color-brand-primary-lighten-6, #FBFCFE);
			border: 0.0625rem solid var(--color-brand-primary-lighten-5, #E7EFFF);
			border-radius: 0.625rem;
			color: var(--link-hover-color, #0053F0);
			font-size: 0.813rem;
			font-weight: 600;
			line-height: 1rem;
			padding: 0.75rem;
			word-break: break-word;
		}

		.features-container > svg {
			fill: var(--link-color, #0B5FFF);
			height: 0.75rem;
			width: 0.75rem;
		}

		.features-container a {
			text-decoration: none;
		}

		.features-container span {
			padding-left: 0.5rem;
		}

		.features-header {
			color: var(--color-neutral-8, #54555F);
			font-size: 0.813rem;
			margin-bottom: 0.5rem;
			padding: 0.25rem 0.25rem 0.25rem 0.625rem;
		}

		.icon-container {
			background-color: #E7EFFF;
			border-radius: 0.5rem;
			margin-bottom: 2rem;
			max-width: 9rem;
		}

		.pagination-bar {
			align-items: flex-start;
			display: flex;
			gap: 1rem;
		}

		.pagination-bar .dropdown.pagination-items-per-page {
			align-items: flex-start;
			display: flex;
		}

		.pagination-bar .dropdown.pagination-items-per-page .dropdown-toggle.page-link {
			align-items: center;
			border-width: 0rem;
			color: var(--color-action-neutral-default, #2B3A4B);
			display: flex;
			font-size: 0.875rem;
			font-style: normal;
			font-weight: 600;
			gap: 0.25rem;
			justify-content: center;
			line-height: 1rem;
			text-align: center;
		}

		.pagination-bar .dropdown.pagination-items-per-page .dropdown-toggle.page-link .c-inner {
			align-items: center;
			display: flex;
			height: 1rem;
			justify-content: center;
		}

		.pagination-bar .dropdown.pagination-items-per-page .dropdown-toggle.page-link .c-inner .lexicon-icon.lexicon-icon-caret-double-l {
			align-items: center;
			display: flex;
			flex-shrink: 0;
			height: 1rem;
			justify-content: center;
			width: 1rem;
		}

		.pagination-bar .pagination {
			align-items: flex-start;
			display: flex;
			gap: 0.5rem;
			justify-content: center;
		}

		.pagination-bar .pagination .page-item .page-link {
			align-items: center;
			border-radius: 0.375rem;
			border-width: 0rem;
			color: var(--color-action-neutral-default, #2B3A4B);
			display: flex;
			font-size: 0.875rem;
			font-style: normal;
			font-weight: 600;
			gap: 0.25rem;
			justify-content: center;
			line-height: 1rem;
			min-width: 2rem;
			padding: 0.5rem;
			text-align: center;
		}

		.pagination-bar .pagination .page-item.active .page-link {
			background: var(--color-action-neutral-active-20, #D5D8DB);
		}

		.pagination-bar .pagination-results {
			align-items: flex-start;
			color: var(--color-neutral-10, #282934);
			display: flex;
			font-size: 0.8125rem;
			font-style: normal;
			font-weight: 400;
			gap: 0.25rem;
			line-height: 1rem;
			margin-top: 0.5rem;
		}

		.search-icon>svg {
			fill: var(--link-color, #0B5FFF);
			height: 2rem;
			margin: 1.5rem 3.5rem;
			width: 2rem;
		}

		.search-results .product-capabilities {
			padding-bottom: 1rem;
		}

		.search-results .search-results-entry {
			align-items: flex-start;
			display: flex;
			flex-direction: column;
			gap: 0.25rem;
		}

		.search-results .search-results-entry .search-results-entry-title {
			color: var(--color-neutral-10, #282934);
			border-radius: 0.625rem;
			font-size: 1.438rem;
			font-style: normal;
			font-weight: 700 !important;
			line-height: 1.5rem;
			padding: 0.5rem 1rem 0rem 0.625rem;
		}

		.search-results .search-results-entry .search-results-entry-title .search-results-entry-content {
			color: var(--color-neutral-10, #282934);
			font-size: 1rem;
			font-style: normal;
			font-weight: 400;
			line-height: 1.5rem;
			margin-top: 1rem;
		}

		.search-results .search-results-entry .search-results-entry-title:hover {
			background: var(--color-action-primary-hover-10, #EDF3FE);
			padding: 0.5rem 1rem 0rem 0.625rem;
		}

		.search-results .solid {
			border-top: 0.0625rem solid var(--color-neutral-2, #E2E2E4);
		}
	}
</style>

<div class="container-release-highlights-search">
	<div class="pt-2 search-results" id="searchResults">
		<#if entries?has_content>
			<#list entries as searchEntry>
				<#assign restArticle = restClient.get("/headless-delivery/v1.0/structured-contents/${searchEntry.getClassPK()}?fields=contentFields,relatedContents,taxonomyCategoryBriefs,title&nestedFields=embeddedTaxonomyCategory") />

				<#if restArticle?has_content>
					<div class="align-items-stretch pt-2 search-results-entry">
						<div class="font-weight-bold search-results-entry-title text-decoration-none unstyled">
							<div class="product-capabilities">
								<#list restArticle.taxonomyCategoryBriefs as taxonomyCategoryBrief>
									<#assign taxonomyVocabularyName = taxonomyCategoryBrief.embeddedTaxonomyCategory.parentTaxonomyVocabulary.name />

									<#if taxonomyVocabularyName == "Product Capabilities">
										<span class="font-weight-normal label label-secondary label-tonal-info m-0 px-2 text-paragraph-sm">
											${taxonomyCategoryBrief.taxonomyCategoryName}
										</span>
									</#if>
								</#list>
							</div>

							${restArticle.title}

							<div class="description search-results-entry-content">
								<#list restArticle.contentFields as fieldData>
									<#if fieldData.contentFieldValue.data?has_content && validator.isNotNull(fieldData.contentFieldValue.data)>
										<#assign webContentData = fieldData.contentFieldValue.data />

										<div>
											${webContentData}
										</div>
									</#if>
								</#list>
							</div>
						</div>

						<#if restArticle.relatedContents?has_content>
							<div class="pb-2">
								<div class="features-header">
									${languageUtil.get(locale, "features", "Features")}:
								</div>

								<#list restArticle.relatedContents as relatedContents>
									<#assign
										relatedContentsId = relatedContents.id
										relatedContentsTitle = relatedContents.title
									/>

									<div class="d-flex features-box">
										<a class="features-container openSidetab" data-request-id="${relatedContentsId}" href="javascript:;">
											<@clay["icon"] symbol="check-square" />

											<span>
												${relatedContentsTitle}
											</span>
										</a>
									</div>
								</#list>
							</div>
						</#if>
					</div>

					<hr class="solid">
				</#if>
			</#list>
		<#else>
			<div class="empty-container">
				<div class="d-flex empty-result-message flex-column">
					<div class="icon-container">
						<div class="search-icon">
							<@clay["icon"] symbol="search-experiences" />
						</div>
					</div>

					<span class="empty-message-title text-center">
						${languageUtil.get(locale, "no-highlights-found-for-the-selected-product-capabilities", "No highlights found for the selected product capabilities.")}
					</span>
				</div>
			</div>
		</#if>
	</div>
</div>