<div class="d-flex flex-column form-group-autofit learn-sort-portlet">
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

<style>
	.learn-sort-portlet .form-group-autofit {
		flex-direction: column;
		margin-bottom: 0;
	}

	.learn-sort-portlet .form-group-autofit:hover {
		background-color: var(--color-neutral-1)
	}

	.learn-sort-portlet .form-group-item {
		height: auto; margin-bottom: 0;
	}

	.learn-sort-portlet .form-group-item label {
		display: flex;
		color: var(--gray-600, #6b6c7e);
		font-size: 13px;
		font-weight: 400;
		gap: 12px;align-items: center;
	}

	.learn-sort-portlet #sort-title {
		font-size: 0.875rem;
		font-weight: var(--font-weight-semi-bold, 600);
		text-transform: uppercase;
	}
</style>