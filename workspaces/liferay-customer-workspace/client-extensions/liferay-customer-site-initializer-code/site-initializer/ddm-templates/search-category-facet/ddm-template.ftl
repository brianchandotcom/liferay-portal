<#macro panel_item
	categories
	selectedMap
	title
>
	<#assign parentId = stringUtil.replace(title, ' ', '') />

	<@liferay_ui["panel-container"]
		extended=true
		id="${namespace}_${parentId}_facetAssetCategoriesPanelContainer"
		markupView="lexicon"
		persistState=true
	>
		<@liferay_ui.panel
			collapsible=true
			cssClass="bg-brand-primary-lighten-5 mb-4 p-3 search-facet search-facet-display-vocabulary"
			id="${namespace}_${parentId}_facetAssetCategoriesPanel"
			markupView="lexicon"
			persistState=true
			title="${title}"
		>
			<div class="action-buttons">
				<@clay.button
					cssClass="btn-unstyled facet-clear-btn mb-3 mr-1 text-body text-decoration-none"
					displayType="link"
					id="${namespace}_${parentId}_facetAssetCategoriesSelectAll"
					onClick="${namespace}selectAll(event)"
				>
					<span>${languageUtil.get(locale, "select-all")}</span>
				</@clay.button>

				<@clay.button
					cssClass="btn-unstyled facet-clear-btn mb-3 text-body text-decoration-none"
					displayType="link"
					id="${namespace}_${parentId}_facetAssetCategoriesClear"
					onClick="${namespace}clearSelections(event)"
				>
					<span>${languageUtil.get(locale, "clear")}</span>
				</@clay.button>
			</div>

			<div class="collapse show" id="${namespace}_${parentId}_categoryItem">
				<#if categories.items?size gt 8>
					<input
						class="form-control mb-3 pb-2 pl-3 pr-3 pt-2"
						id="${namespace}_${parentId}_search"
						onInput="${namespace}searchCategories(event)"
						placeholder='${languageUtil.get(locale, "search")}'
						type="text"
					/>
				</#if>

				<ul class="m-0 p-0">
					<#list categories.items as item>
						<#assign isSelected = ((selectedMap[item.id?string]!{}).selected!false) />

						<li class="m-0 category-item <#if item_index gte 8>d-none</#if>">
							<span class="autofit-row">
								<span class="autofit-col autofit-col-expand">
									<label class="align-items-center d-flex font-weight-normal" style="cursor: pointer;">
										<input
											autocomplete="off"
											${isSelected?then("checked", "")}
											class="facet-term mr-1"
											data-term-id="${item.id}"
											data-term-name="${item.name}"
											data-term-param="category"
											data-term-value="${item.id}"
											onChange="Liferay.Search.FacetUtil.changeSelection(event);"
											type="checkbox"
										/>

										<span>
											${item.name}
										</span>
									</label>
								</span>
							</span>
						</li>
					</#list>
				</ul>

				<#if categories.items?size gt 8>
					<@clay.button
						cssClass="btn-unstyled facet-clear-btn view-all-btn mt-3 text-body text-decoration-none"
						displayType="link"
						id="${namespace}_${parentId}_facetAssetCategoriesViewAll"
						onClick="${namespace}viewAll('${namespace}_${parentId}_categoryItem', event)"
					>
						<span>${languageUtil.get(locale, "view-all")}</span>
					</@clay.button>
				</#if>
			</div>
		</@>
	</@>

	<#list categories.items as item>
		<#if item.numberOfTaxonomyCategories gt 0>
			<#assign isSelected = ((selectedMap[item.id?string]!{}).selected!false) />

			<#if isSelected>
				<#assign childCategories = restClient.get("/headless-admin-taxonomy/v1.0/taxonomy-categories/${item.id}/taxonomy-categories") />
				<@panel_item
					categories = childCategories
					selectedMap = selectedMap
					title = item.name
				/>
			</#if>
		</#if>
	</#list>
</#macro>

<#assign selectedMap = {} />

<#list assetCategoriesSearchFacetDisplayContext.getParameterValues() as categoryId>
	<#attempt>
		<#assign category = restClient.get("/headless-admin-taxonomy/v1.0/taxonomy-categories/" + categoryId) />

		<#if category?has_content>
			<#assign currentCategory = category />

			<#list 0..9 as _>
				<#if currentCategory?has_content>
					<#assign selectedMap = selectedMap + {currentCategory.id?string: {"selected": true}} />

					<#if currentCategory.parentTaxonomyCategory?has_content && (currentCategory.parentTaxonomyCategory.id?string) != "0">
						<#assign
							parentId = currentCategory.parentTaxonomyCategory.id
							currentCategory = restClient.get("/headless-admin-taxonomy/v1.0/taxonomy-categories/" + parentId)
						/>

					<#else>
						<#break />
					</#if>
				<#else>
					<#break />
				</#if>
			</#list>
		</#if>
	<#recover>
	</#attempt>
</#list>

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
				selectedMap = selectedMap
				title = languageUtil.get(locale, "type")
			/>
		</#if>
	</#list>
</#if>

<@liferay_aui.script>
	function ${namespace}clearSelections(event) {
		var panel = event.target.closest('.search-facet-display-vocabulary');

		if (panel) {
			var checkboxes = panel.querySelectorAll(
				'input[type="checkbox"]:checked'
			);

			checkboxes.forEach((checkbox) => {
				checkbox.checked = false;
				var changeEvent = new Event('change', {
					bubbles: true,
				});
				checkbox.dispatchEvent(changeEvent);
			});
		}
	}

	function ${namespace}searchCategories(event) {
		var searchInput = event.target;
		var searchTerm = searchInput.value.toLowerCase();
		var panel = searchInput.closest('.search-facet-display-vocabulary');

		if (panel) {
			var categoryItems = panel.querySelectorAll('.category-item');
			var viewAllButton = panel.querySelector('.view-all-btn');

			if (viewAllButton) {
				viewAllButton.style.display = 'none';
			}

			categoryItems.forEach(function(item) {
				var label = item.querySelector('label');
				var categoryName = label.textContent || label.innerText;

				if (categoryName.toLowerCase().indexOf(searchTerm) > -1) {
					item.classList.remove('d-none');
					item.style.display = '';
				} else {
					item.style.display = 'none';
				}
			});
		}
	}

	function ${namespace}selectAll(event) {
		var panel = event.target.closest('.search-facet-display-vocabulary');

		if (panel) {
			var checkboxes = panel.querySelectorAll(
				'input[type="checkbox"]'
			);

			checkboxes.forEach((checkbox) => {
				if (!checkbox.checked) {
					checkbox.checked = true;
					var changeEvent = new Event('change', {
						bubbles: true,
					});
					checkbox.dispatchEvent(changeEvent);
				}
			});
		}
	}

	function ${namespace}viewAll(dataTarget, event) {
		const categoryElement = document.getElementById(dataTarget);

		if (categoryElement) {
			const hiddenItems = categoryElement.querySelectorAll('.d-none');

			hiddenItems.forEach((item) => {
				item.classList.remove('d-none');
			});

			if (event && event.target) {
				const viewAllButton = event.target.closest('.view-all-btn');

				if (viewAllButton) {
					viewAllButton.style.display = 'none';
				}
			}
		}
	}
</@>

<style>
input[type=checkbox] {
	accent-color: var(--color-brand-primary)
}

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