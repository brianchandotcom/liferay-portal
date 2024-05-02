<#if entries?has_content>
	<#assign totalCount = 0 />

	<#list assetCategoriesSearchFacetDisplayContext.getBucketDisplayContexts() as bucket>
		<#assign totalCount = totalCount + bucket.getCount() />
	</#list>

	<ul class="list-unstyled tab-list">
		<li class="facet-value">
			<@clay.button
				cssClass="btn-unstyled facet-clear tab-btn ${assetCategoriesSearchFacetDisplayContext.isNothingSelected()?then('selected-tab-btn', '')}"
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
					cssClass="btn-unstyled facet-term tab-btn term-name ${(entry.isSelected())?then('selected-tab-btn', '')}"
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
</#if>

<@liferay_aui.script>
	function handleStyleTabs(event) {
		const targetButton = event.currentTarget;
		const buttons = document.querySelectorAll('.tab-btn');

		buttons.forEach(button => {
			button.classList.remove('selected-tab-btn');
		});

		targetButton.classList.add('selected-tab-btn');
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