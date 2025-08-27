<style ${nonceAttribute}>
	.price-model-facet {
		border-radius: 10px;
	}

	.price-model-facet .panel a {
		padding: 1.5rem;
		padding-bottom: 0;
	}

	.price-model-facet .collapse-icon .collapse-icon-closed .lexicon-icon,
	.price-model-facet .collapse-icon .collapse-icon-open .lexicon-icon {
		margin-top: 0.3rem;
	}

	.price-model-facet .panel-body {
		padding: 0 1.5rem;
	}

	.price-model-facet .list-unstyled {
		margin-bottom: 0;
	}

	.price-model-facet .options-btn {
		color: #2B3A4B;
		font-size: 14px;
		font-weight: 400;
	}

	.price-model-facet .separator {
		margin: 1rem auto 0;
		width: 90%;
	}
	
	.price-model-facet .view-all-btn {
		color: #2B3A4B;
		font-size: 14px;
		font-weight: 400;
	}
</style>

<#assign appliedFilterCount = 0>
<#list entries?sort_by("displayName") as entry>
  <#if entry.isSelected()>
    <#assign appliedFilterCount++ >
  </#if>
</#list>

<#if cpSpecificationOptionsSearchFacetDisplayContext.getParameterName() != 'developer-name'>
	<#if appliedFilterCount == 0>
    <#assign title = "${cpSpecificationOptionsSearchFacetDisplayContext.getParameterName()?replace('-',' ')}">
	<#else>
    <#assign title = "${cpSpecificationOptionsSearchFacetDisplayContext.getParameterName()?replace('-',' ')} (${appliedFilterCount})">
	</#if>
	<@liferay_ui["panel-container"]
		cssClass="price-model-facet bg-white border-radius-xlarge"
		extended=true
		id="${namespace + 'facetPriceModelPanelContainer'}"
		markupView="lexicon"
		persistState=true
	>
		<@liferay_ui.panel
			collapsible=true
			cssClass="font-size-paragraph-small font-weight-semi-bold search-facet"
			extended=!browserSniffer.isMobile(request)
			id="${cpSpecificationOptionsSearchFacetDisplayContext.getParameterName()}"
			markupView="lexicon"
			persistState=true
			title="${title}">

			<button
				class="btn-unstyled options-btn mb-4" id="${namespace + 'facetAssetSelectAll'}"
					onClick="${namespace}selectAll(event, `${cpSpecificationOptionsSearchFacetDisplayContext.getParameterName()}`)">
				Select All
			</button>

			<button class="btn-unstyled options-btn mb-4 ml-1" onClick="Liferay.Search.FacetUtil.clearSelections(event);">
				Clear
			</button>
			<ul class="list-unstyled">
				<#assign termDisplayContextCount = 1 />
				<#list entries?sort_by("displayName") as entry>
					<#if termDisplayContextCount lte 5>
						<li class="color-neutral-2 facet-value py-1">
							<div class="custom-checkbox custom-control font-weight-normal">
								<label class="facet-checkbox-label" for="${namespace}_term_${entry.getDisplayName()}">
									<input
										${(entry.isSelected())?then("checked","")}
										class="custom-control-input facet-term"
										data-term-id="${entry.getDisplayName()}"
										id="${namespace}_term_${entry.getDisplayName()}"
										name="${namespace}_term_${entry.getDisplayName()}"
										onChange="Liferay.Search.FacetUtil.changeSelection(event);"
										type="checkbox" />

									<span class="custom-control-label font-size-paragraph-small term-name ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')}">
										<span class="custom-control-label-text">
											<#assign displayName = entry.getDisplayName()?replace("-", " ") />

											<#if displayName == 'dxp'>
												DXP
											<#else>
												${htmlUtil.escape(displayName)?capitalize}
											</#if>
										</span>
									</span>
								</label>
							</div>
						</li>
					<#else>
						<li class="color-neutral-2 facet-value py-1 d-none">
							<div class="custom-checkbox custom-control font-weight-normal">
								<label class="facet-checkbox-label" for="${namespace}_term_${entry.getDisplayName()}">
									<input
										${(entry.isSelected())?then("checked","")}
										class="custom-control-input facet-term"
										data-term-id="${entry.getDisplayName()}"
										id="${namespace}_term_${entry.getDisplayName()}"
										name="${namespace}_term_${entry.getDisplayName()}"
										onChange="Liferay.Search.FacetUtil.changeSelection(event);"
										type="checkbox" />

									<span class="custom-control-label font-size-paragraph-small term-name ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')}">
										<span class="custom-control-label-text">
											<#assign displayName = entry.getDisplayName()?replace("-", " ") />

											<#if displayName == 'dxp'>
												DXP
											<#else>
												${htmlUtil.escape(displayName)?capitalize}
											</#if>
										</span>
									</span>
								</label>
							</div>
						</li>
					</#if>
					<#assign termDisplayContextCount++ />
				</#list>
				<#if termDisplayContextCount gt 6>
					<button
						class="btn-unstyled mt-4 view-all-btn"
						id="${cpSpecificationOptionsSearchFacetDisplayContext.getParameterName() + 'facetAssetCategoriesViewAll'}"
						onClick="${namespace}viewAll(event, `${cpSpecificationOptionsSearchFacetDisplayContext.getParameterName()}`, '${namespace + 'facetAssetCategoriesPanel'}')"
					>
						<span>${languageUtil.get(locale, "view-all")}</span>
					</button>
				</#if>
			</ul>
		</@>
		<hr class="separator" />
	</@>
</#if>

<@liferay_aui.script>
		function ${namespace}selectAll(event, parameterName) {
			event.preventDefault();

			var url = new URL(window.location.href);
			var divId = event.target.closest('.collapse').id;
			var checkboxes = document.querySelectorAll('#' + divId + ' .custom-checkbox input[type="checkbox"]');

			if (url.searchParams.size === 0) {
				url.href += '?'
			}

			checkboxes.forEach((checkbox) => {
				if (!checkbox.checked) {
					if (url.searchParams.size > 0) {
						url.href += '&';
					}
					url.href += parameterName + '=' + checkbox.getAttribute('data-term-id');
				}
			});

			window.location.href = url.href;
		}
	
	function ${namespace}viewAll(event, parameterName) {
		event.preventDefault();
	
		const subtreeCategoryTreeElement = document.getElementById(parameterName);

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