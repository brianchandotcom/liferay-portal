<#macro panel_item
	categories
	title
>
	<@liferay_ui["panel-container"]
		extended=true
		id="${title + 'facetAssetCategoriesPanelContainer'}"
		markupView="lexicon"
		persistState=true
	>
		<@liferay_ui.panel
			collapsible=true
			cssClass="bg-brand-primary-lighten-5 mb-4 p-3 search-facet search-facet-display-vocabulary"
			id="${title + 'facetAssetCategoriesPanel'}"
			markupView="lexicon"
			persistState=true
			title="${title}"
		>
			<div class="action-buttons">
				<@clay.button
					cssClass="btn-unstyled c-mb-3 facet-clear-btn"
					displayType="link"
					id="${namespace + 'facetAssetCategoriesSelectAll'}"
					onClick="${namespace}selectAll(event)"
				>
					<span>${languageUtil.get(locale, "select-all")}</span>
				</@clay.button>

				<@clay.button
					cssClass="btn-unstyled c-mb-3 facet-clear-btn"
					displayType="link"
					id="${namespace + 'facetAssetCategoriesClear'}"
					onClick="Liferay.Search.FacetUtil.clearSelections(event);"
				>
					<span>${languageUtil.get(locale, "clear")}</span>
				</@clay.button>
			</div>

			<#assign parentId = stringUtil.replace(title, ' ', '') />

			<div class="collapse show" id="${namespace}categoryItem${parentId}">
				<ul class="m-0 p-0">
					<#list categories.items as item>
						<li class="m-0 category-item">
							<span class="autofit-row">
								<span class="autofit-col autofit-col-expand">
									<div class="custom-checkbox custom-control">
										<label>
											<input
												autocomplete="off"
												class="custom-control-input facet-term"
												data-term-id=${item.id}
												disabled
												onChange="Liferay.Search.FacetUtil.changeSelection(event);"
												type="checkbox"
											/>

											<span class="custom-control-label">
												<span class="custom-control-label-text">
													${item.name}
												</span>
											</span>
										</label>
									</div>
								</span>
							</span>
						</li>
					</#list>
				</ul>

				<#if categories.items?size gt 8>
					<@clay.button
						cssClass="btn-unstyled facet-clear-btn view-all-btn c-mt-3"
						displayType="link"
						id="${parentId + 'facetAssetCategoriesViewAll'}"
						onClick="${namespace}viewAll('${namespace}categoryItem${parentId}')"
					>
						<span>${languageUtil.get(locale, "view-all")}</span>
					</@clay.button>
				</#if>
			</div>
		</@>
	</@>

	<#list categories.items as item>
		<#if item.numberOfTaxonomyCategories gt 0>
			<#assign childCategories = restClient.get("/headless-admin-taxonomy/v1.0/taxonomy-categories/${item.id}/taxonomy-categories") />
			<@panel_item
				categories = childCategories
				title = item.name
			/>
		</#if>
	</#list>
</#macro>

<#assign vocabularyNames = assetCategoriesSearchFacetDisplayContext.getVocabularyNames()![] />

<#if vocabularyNames?has_content>
	<#list vocabularyNames as vocabularyName>
		<#assign
			assetCategoryId = assetCategoriesSearchFacetDisplayContext.getBucketDisplayContexts(vocabularyName)[0].getAssetCategoryId()

			category = restClient.get("/headless-admin-taxonomy/v1.0/taxonomy-categories/${assetCategoryId}")
			categories = restClient.get("/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${category.parentTaxonomyVocabulary.id}/taxonomy-categories")
		/>

		<#if categories?has_content>
			<@panel_item
				categories = categories
				title = vocabularyName
			/>
		</#if>
	</#list>
</#if>

<@liferay_aui.script>
	function ${namespace}selectAll(event) {

		var divId = event.target.closest('.collapse').id;
		var checkboxes = document.querySelectorAll('#' + divId + ' .custom-checkbox input[type="checkbox"]');

		checkboxes.forEach((checkbox) => {
			checkbox.checked = true;
			var changeEvent = new Event('change');
			checkbox.dispatchEvent(changeEvent);
		});
	}

	function ${namespace}viewAll(dataTarget) {
		const categoryElement = document.getElementById(dataTarget);

		if (categoryElement) {
			const hiddenItems = categoryElement.querySelectorAll('.d-none');
			hiddenItems.forEach(item => {
				item.classList.remove('d-none');
			});

			const viewAllButton = categoryElement.querySelector('.view-all-btn');
			if (viewAllButton) {
				viewAllButton.style.display = 'none';
			}
		}
	}
</@>

<style>
.panel ul {
	list-style: none;
}

.panel-body, .panel-header {
	padding: 0;
}

.panel-header .collapse-icon-closed, .panel-header .collapse-icon-open {
	top: var(--spacer-1);
}

.panel-title {
	font-size: var(--h5-font-size, 0.875rem);
	font-weight: var(--h5-font-weight);
	text-transform: none;
}
</style>