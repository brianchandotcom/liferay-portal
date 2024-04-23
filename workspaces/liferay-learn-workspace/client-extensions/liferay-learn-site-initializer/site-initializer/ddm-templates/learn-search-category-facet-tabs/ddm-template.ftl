<#if entries?has_content>
	<#assign totalCount = 0 />

	<#list assetCategoriesSearchFacetDisplayContext.getBucketDisplayContexts() as bucket>
		<#assign totalCount = totalCount + bucket.getCount() />
	</#list>

	<ul class="list-unstyled tab-list">
		<li class="facet-value">
			<@clay.button
				cssClass="facet-clear btn-unstyled ${assetCategoriesSearchFacetDisplayContext.isNothingSelected()?then('selected-tab-btn', '')}"
				displayType="link"
				onClick="Liferay.Search.FacetUtil.clearSelections(event);"
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
					cssClass="facet-term btn-unstyled term-name ${(entry.isSelected())?then('selected-tab-btn', '')}"
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
	function ${namespace}updateSelection(event) {
		const form = event.currentTarget.form;

		if (!form) {
			return;
		}

		Liferay.Search.FacetUtil.selectTerms(form, []);

		Liferay.Search.FacetUtil.changeSelection(event);
	}
</@>