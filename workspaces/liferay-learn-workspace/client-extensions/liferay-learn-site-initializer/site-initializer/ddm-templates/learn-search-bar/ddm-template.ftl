<#assign pageTitle = layout.getName(locale) />

<@liferay_aui.fieldset cssClass="search-bar">
	<@liferay_aui.input
		cssClass="search-bar-empty-search-input"
		name="emptySearchEnabled"
		type="hidden"
		value=searchBarPortletDisplayContext.isEmptySearchEnabled()
	/>

	<div class="input-group">
		<input
			autocomplete="off"
			class="form-control input-group-inset input-group-inset-after search-bar-keywords-input"
			data-qa-id="searchInput"
			id="${namespace + stringUtil.randomId()}"
			name="${htmlUtil.escape(searchBarPortletDisplayContext.getKeywordsParameterName())}"
			placeholder="Search ${pageTitle}"
			title="${languageUtil.get(locale, "Search")}"
			type="text"
			value="${htmlUtil.escape(searchBarPortletDisplayContext.getKeywords())}"
		/>

		<div class="input-group-inset-item input-group-inset-item-after">
			<button
				class="btn btn-unstyled"
				aria\-label="${languageUtil.get(locale, 'search')}"
				type="submit"
			>
				<svg class="lexicon-icon lexicon-icon-search" role="presentation" viewBox="0 0 512 512">
					<g>
						<path class="lexicon-icon-outline" d="M499.2,455.5L377.7,333.4c146-241.1-148.1-435.8-318.2-274c-165.1,185.9,41.6,460.6,273.4,319l121.5,118.8C489.5,535.8,534.4,490.8,499.2,455.5z M206.2,63.6c191.9,0,198.1,289,0,289C13.3,352.6,18.8,63.6,206.2,63.6z">
						</path>
					</g>
				</svg>
			</button>
		</div>
	</div>
</@>