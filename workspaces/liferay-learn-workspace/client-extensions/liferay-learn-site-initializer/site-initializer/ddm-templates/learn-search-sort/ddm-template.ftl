<div class="form-group-autofit">
	<div class="c-pb-3 form-group-item form-group-item-label form-group-item-shrink">
		<label>
			<span class="text-truncate-inline">
				<span class="text-truncate" id="sort-title">
					${languageUtil.get(locale, "sort-by")}
				</span>
			</span>
		</label>
	</div>

	<div class="form-group-item">
		<#if entries?has_content>
			<#list entries as entry>
				<label>
					<input
						type="radio"
						class="sort-term"
						name="sortSelection"
						value="${entry.getField()}"
						onChange="handleChangeSort(event);"
						${entry.isSelected()?then("checked","")}
					>
						${entry.getLanguageLabel()}
				</label>
			</#list>
		</#if>
	</div>
</div>

<script>
	function handleChangeSort(event) {
		const urlParams = new URLSearchParams(window.location.search);
		const selectedOptionValue = event.currentTarget.value;

		if (selectedOptionValue === 'title+') {
			urlParams.set('sort', 'title+');
			window.location.search = urlParams;
		}

		if (selectedOptionValue === 'title-') {
			urlParams.set('sort', 'title-');
			window.location.search = urlParams;
		}

		if (selectedOptionValue === 'publishedDate-') {
			urlParams.set('sort', 'publishedDate-');
			window.location.search = urlParams;
		}

		if (selectedOptionValue === '') {
			urlParams.set('sort', '');
			window.location.search = urlParams;
		}
	}
</script>