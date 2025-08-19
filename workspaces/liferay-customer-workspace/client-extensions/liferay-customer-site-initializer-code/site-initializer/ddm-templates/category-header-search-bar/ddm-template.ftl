<#ftl output_format="HTML">

<style>
	.cp-category-header-container {
		align-items: center;
		background-image: url('/documents/d/customer-portal/search_bar-png');
		background-size: cover;
		height: 120px;
		justify-content: space-between;

		.search-bar {
			max-width: 100%;
			width: 320px;
		}

		.search-bar-keywords-input {
			text-overflow: ellipsis;
		}

		h1, h3 {
			color: var(--color-neutral-0, #FFFFFF);
			font-weight: var(--font-weight-semi-bold);
			text-align: center;
		}
	}

	.landing-page-mode {
		background-image: url('/documents/d/customer-portal/search_bar_landing_page-png');
		height: 280px;
		justify-content: center;

		.search-bar {
			width: 700px;
		}
	}
</style>

<#assign displayObject = (request.getAttribute("LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER").getDisplayObject())!{} />

<#if displayObject?has_content>
	<#assign
		currentCategoryId = displayObject.categoryId
		mainCategoryId = displayObject.treePath?split("/")[1]!""
		mainCategory = restClient.get("/headless-admin-taxonomy/v1.0/taxonomy-categories/" + mainCategoryId)!{}
	/>
</#if>

<#if mainCategory?has_content>
	<#assign isLandingPage = currentCategoryId?string == mainCategoryId />

	<div class="cp-category-header-container d-flex ${isLandingPage?then('flex-column landing-page-mode','')} mb-5 p-5">
		<${isLandingPage?then("h1", "h3")}>
			${mainCategory.name}
		</${isLandingPage?then("h1", "h3")}>

		<@liferay_aui.fieldset cssClass="search-bar">
			<@liferay_aui.input
				cssClass="search-bar-empty-search-input"
				name="emptySearchEnabled"
				type="hidden"
				value=searchBarPortletDisplayContext.isEmptySearchEnabled()
			/>

			<div class="input-group ${searchBarPortletDisplayContext.isLetTheUserChooseTheSearchScope()?then("search-bar-scope","search-bar-simple")}">
				<#if searchBarPortletDisplayContext.isLetTheUserChooseTheSearchScope()>
					<div class="input-group-item input-group-item-shrink input-group-prepend">
						<@clay["button"]
							aria\-label="${languageUtil.get(locale, 'search')}"
							cssClass="search-bar-submit-button"
							disabled=true
							displayType="secondary"
							icon="search"
							type="submit"
						/>
					</div>

					<@liferay_aui.select
						cssClass="search-bar-scope-select"
						disabled=true
						label=""
						name=htmlUtil.escape(searchBarPortletDisplayContext.getScopeParameterName())
						title="scope"
						useNamespace=false
						wrapperCssClass="input-group-item input-group-item-shrink input-group-prepend search-bar-search-select-wrapper"
					>
						<@liferay_aui.option
							label="this-site"
							selected=searchBarPortletDisplayContext.isSelectedCurrentSiteSearchScope()
							value=searchBarPortletDisplayContext.getCurrentSiteSearchScopeParameterString()
						/>

						<#if searchBarPortletDisplayContext.isAvailableEverythingSearchScope()>
							<@liferay_aui.option
								label="everything"
								selected=searchBarPortletDisplayContext.isSelectedEverythingSearchScope()
								value=searchBarPortletDisplayContext.getEverythingSearchScopeParameterString()
							/>
						</#if>
					</@>

					<#assign data = {
						"test-id": "searchInput"
					} />

					<@liferay_aui.input
						autoFocus=true
						autocomplete="off"
						cssClass="search-bar-keywords-input"
						data=data
						disabled=true
						label=""
						name=htmlUtil.escape(searchBarPortletDisplayContext.getKeywordsParameterName())
						placeholder="${languageUtil.get(locale, 'search')} ${mainCategory.name}"
						title=languageUtil.get(locale, "search")
						type="text"
						useNamespace=false
						value=htmlUtil.escape(searchBarPortletDisplayContext.getKeywords())
						wrapperCssClass="input-group-append input-group-item search-bar-keywords-input-wrapper"
					/>
				<#else>
					<div class="input-group-item search-bar-keywords-input-wrapper">
						<input
							autocomplete="off"
							class="form-control input-group-inset input-group-inset-before search-bar-keywords-input"
							data-qa-id="searchInput"
							disabled=true
							id="${namespace + stringUtil.randomId()}"
							name="${htmlUtil.escape(searchBarPortletDisplayContext.getKeywordsParameterName())}"
							placeholder="${languageUtil.get(locale, 'search')} ${mainCategory.name}"
							title="${languageUtil.get(locale, "search")}"
							type="text"
							value="${htmlUtil.escape(searchBarPortletDisplayContext.getKeywords())}"
						/>

						<div class="input-group-inset-item input-group-inset-item-before">
							<@clay["button"]
								aria\-label="${languageUtil.get(locale, 'search')}"
								cssClass="search-bar-submit-button"
								disabled=true
								displayType="unstyled"
								icon="search"
								type="submit"
							/>
						</div>

						<@liferay_aui.input
							name=htmlUtil.escape(searchBarPortletDisplayContext.getScopeParameterName())
							type="hidden"
							value=searchBarPortletDisplayContext.getScopeParameterValue()
						/>
					</div>
				</#if>
			</div>
		</@>
	</div>
</#if>

<#if mainCategoryId??>
	<script>
		window.addEventListener('load', () => {
			const currentUrl = new URL(window.location);

			currentUrl.searchParams.set('category', ${mainCategoryId});

			window.history.replaceState({}, '', currentUrl);
		});
	</script>
</#if>