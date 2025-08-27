<style ${nonceAttribute}>
	.vocab-facet {
		border-radius: 10px;
	}
	
	.vocab-facet .clear-btn {
		color: #2B3A4B;
		font-size: 14px;
		font-weight: 400;
	}
	
	.vocab-facet .panel a {
		padding: 1.5rem;
		padding-bottom: 0;
	}

	.vocab-facet .collapse-icon .collapse-icon-closed .lexicon-icon,
	.vocab-facet .collapse-icon .collapse-icon-open .lexicon-icon {
		margin-top: 0.3rem;
	}

	.vocab-facet .panel-body {
		padding: 0 1.5rem;
	}

	.vocab-facet .list-unstyled {
		margin-bottom: 0;
	}

	.vocab-facet .separator {
		margin: 1rem auto 0;
		width: 90%;
	}

	.vocab-facet .view-all-btn {
		color: #2B3A4B;
		font-size: 14px;
		font-weight: 400;
	}
</style>

<#assign appliedFilterCount = 0>
<#list entries as entry>
  <#if entry.isSelected()>
    <#assign appliedFilterCount++ >
  </#if>
</#list>
	
<#if appliedFilterCount == 0>
	<#assign title = "${assetCategoriesSearchFacetDisplayContext.getParameterName()?upper_case}">
<#else>
	<#assign title = "${assetCategoriesSearchFacetDisplayContext.getParameterName()?upper_case} (${appliedFilterCount})">
</#if>

<@liferay_ui["panel-container"]
	cssClass="vocab-facet bg-white border-radius-xlarge"	
	extended=true
	id="${namespace + 'facetAssetCategoriesPanelContainer'}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="font-size-paragraph-small font-weight-semi-bold"
		extended=!browserSniffer.isMobile(request)
		id="${namespace + 'facetAssetCategoriesPanel'}"
		markupView="lexicon"
		persistState=true
		title="${title}"
	>
		<button
			class="btn-unstyled clear-btn mb-4" id="${namespace + 'facetAssetCategoriesSelectAll'}"
				onClick="${namespace}selectAll(event)">
		  Select All
		</button>
	  	<button class="btn-unstyled clear-btn mb-4 ml-1" onClick="Liferay.Search.FacetUtil.clearSelections(event);">
		  Clear
	  	</button>
		<ul class="list-unstyled">
			<#assign optionsCount = 1 />
			<#list entries as entry>
				<#if optionsCount lte 5>
						<li class="color-neutral-2 facet-value py-1">
							<div class="custom-checkbox custom-control font-weight-normal">
								<label class="facet-checkbox-label" for="${namespace}_term_${entry.getAssetCategoryId()}">
									<input
										${(entry.isSelected())?then("checked","")}
										class="custom-control-input facet-term"
										data-term-id="${entry.getAssetCategoryId()}"
										disabled
										id="${namespace}_term_${entry.getAssetCategoryId()}"
										name="${namespace}_term_${entry.getAssetCategoryId()}"
										onChange="Liferay.Search.FacetUtil.changeSelection(event);"
										type="checkbox"
									/>
									<span class="custom-control-label font-size-paragraph-small term-name ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')}">
										<span class="custom-control-label-text">
											${htmlUtil.escape(entry.getDisplayName())}
										</span>
									</span>
								</label>
							</div>
						</li>
					<#else>
						<li class="color-neutral-2 facet-value py-1 d-none">
							<div class="custom-checkbox custom-control font-weight-normal">
								<label class="facet-checkbox-label" for="${namespace}_term_${entry.getAssetCategoryId()}">
									<input
										${(entry.isSelected())?then("checked","")}
										class="custom-control-input facet-term"
										data-term-id="${entry.getAssetCategoryId()}"
										disabled
										id="${namespace}_term_${entry.getAssetCategoryId()}"
										name="${namespace}_term_${entry.getAssetCategoryId()}"
										onChange="Liferay.Search.FacetUtil.changeSelection(event);"
										type="checkbox"
									/>

									<span class="custom-control-label font-size-paragraph-small term-name ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')}">
										<span class="custom-control-label-text">
											${htmlUtil.escape(entry.getDisplayName())}
										</span>
									</span>
								</label>
							</div>
						</li>
					</#if>
					<#assign optionsCount++ />
				</#list>
				<#if optionsCount gt 5>
					<button
						class="btn-unstyled mt-4 view-all-btn"
						id="${assetCategoriesSearchFacetDisplayContext.getParameterName() + 'facetAssetCategoriesViewAll'}"
						onClick="${namespace}viewAll(event, '${namespace + 'facetAssetCategoriesPanel'}')"
					>
						<span>${languageUtil.get(locale, "view-all")}</span>
					</button>
				</#if>
		</ul>
	</@>
	<hr class="separator" />
</@>

<@liferay_aui.script>
	function ${namespace}selectAll(event) {
		event.preventDefault();

		var parameterName = `${assetCategoriesSearchFacetDisplayContext.getParameterName()}`;
		var divId = event.target.closest('.collapse').id;
		var checkboxes = document.querySelectorAll('#' + divId + ' .custom-checkbox input[type="checkbox"]');
		var url = new URL(window.location.href);

		if (url.searchParams.size === 0) {
			url.href += '?';
		}

		checkboxes.forEach((checkbox) => {
			if (!checkbox.checked) {
				if (url.searchParams.size > 0) {
					url.href += '&';
				}
				url.href += parameterName + '=' + checkbox.getAttribute('data-term-id');
			}
		});

		window.location.href = url.href
	}
	
	function ${namespace}viewAll(event, dataTarget) {
		event.preventDefault();
	
		const subtreeCategoryTreeElement = document.getElementById(dataTarget);

		if (subtreeCategoryTreeElement) {
			const hiddenItems = subtreeCategoryTreeElement.querySelectorAll('.d-none');
			hiddenItems.forEach(item => {
				item.classList.remove('d-none');
			});

			const viewAllButton = subtreeCategoryTreeElement.querySelector('.view-all-btn');
			if (viewAllButton) {
				viewAllButton.style.display = 'none';
			}
		}
	}
</@>