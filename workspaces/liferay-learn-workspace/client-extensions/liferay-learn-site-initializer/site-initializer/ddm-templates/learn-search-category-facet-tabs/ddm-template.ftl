<#if entries?has_content>
	<#assign totalCount = 0 />

	<#list assetCategoriesSearchFacetDisplayContext.getBucketDisplayContexts() as bucket>
		<#assign totalCount = totalCount + bucket.getCount() />
	</#list>

	<ul class="list-unstyled tab-list" id="tab-list">
		<li class="facet-value">
			<@clay.button
				cssClass="btn-unstyled facet-clear tab-btn text-center ${assetCategoriesSearchFacetDisplayContext.isNothingSelected()?then('selected-tab-btn', '')}"
				displayType="link"
				onClick="${namespace}updateSelection(event)"
				value="clear"
			>
				<span class="term-text">${languageUtil.get(locale, "all-results", "All Results")}</span>

				<#if entry.isFrequencyVisible()>
					<span class="term-count">${totalCount}</span>
				</#if>
			</@clay.button>
		</li>

		<#list entries as entry>
			<li class="facet-value">
				<@clay.button
					cssClass="btn-unstyled facet-term tab-btn term-name text-center ${(entry.isSelected())?then('selected-tab-btn', '')}"
					data\-term\-id="${entry.getFilterValue()}"
					disabled="true"
					displayType="link"
					onClick="${namespace}updateSelection(event)"
				>
					<span class="term-text">${htmlUtil.escape(entry.getBucketText())}</span>

					<#if entry.isFrequencyVisible()>
						<span class="term-count">${entry.getFrequency()}</span>
					</#if>
				</@clay.button>
			</li>
		</#list>
	</ul>

	<div class="dropdown tab-list" id="tab-list-mobile">
		<button
			aria-expanded="false"
			aria-haspopup="true"
			class="btn btn-unstyled d-inline-block selected-tab-btn"
			data-toggle="liferay-dropdown"
			displayType="button"
			id="dropdownAlignment1"
		>
			<div class="d-flex facet-value-mobile justify-content-center opacity-75">
				<#assign facetCount = 0 />
				<#list entries as entry>
					<#if entry.isSelected()>
						<#assign facetCount++ />

						<span class="term-text">${entry.getBucketText()}</span>
						<#if entry.isFrequencyVisible()>
							<span class="term-count">${entry.getFrequency()}</span>
						</#if>
					</#if>
				</#list>
				<#if facetCount == 0>
					<span class="term-text">All results</span>
					<span class="term-count">${totalCount}</span>
				</#if>
			</div>
		</button>

		<ul
			aria-labelledby="dropdownAlignment1"
			class="dropdown-menu"
			x-placement="bottom-start"
		>
			<li class="align-items-center d-flex ${assetCategoriesSearchFacetDisplayContext.isNothingSelected()?then('selected-item-mobile-tab', '')}">
				<@clay.button
					cssClass="dropdown-item facet-clear nav-link rounded"
					displayType="link"
					onClick="${namespace}updateSelection(event)"
					value="clear"
				>
					<span class="term-text">${languageUtil.get(locale, "all-results", "All Results")}</span>
					<#if entry.isFrequencyVisible()>
						<span class="term-count">${totalCount}</span>
					</#if>
				</@clay.button>
			</li>

			<#list entries as entry>
				<li class="align-items-center d-flex ${(entry.isSelected())?then('selected-item-mobile-tab', '')}">
					<@clay.button
						cssClass="dropdown-item facet-clear nav-link rounded"
						data\-term\-id="${entry.getFilterValue()}"
						displayType="link"
						onClick="${namespace}updateSelection(event)"
					>
						<span class="term-text">${htmlUtil.escape(entry.getBucketText())}</span>
						<#if entry.isFrequencyVisible()>
							<span class="term-count">${entry.getFrequency()}</span>
						</#if>
					</@clay.button>
				</li>
			</#list>
		</ul>
	</div>
</#if>

<@liferay_aui.script>
	function handleStyleTabs(event) {
		const targetButton = event.currentTarget;
		const buttons = document.querySelectorAll('.tab-btn');

		buttons.forEach(button => {
			button.classList.remove('selected-tab-btn');
		});

		if (targetButton.classList.contains('tab-btn')) {
			targetButton.classList.add('selected-tab-btn');
		}
	}

	function ${namespace}updateSelection(event) {
		handleStyleTabs(event);

		const form = event.currentTarget.form;

		if (form) {
			Liferay.Search.FacetUtil.selectTerms(form, []);

			if (event.target.value === "clear") {
				Liferay.Search.FacetUtil.clearSelections(event);
			}
			else {
				Liferay.Search.FacetUtil.changeSelection(event);
			}
		}
	}
</@>