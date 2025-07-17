<div class="sort-filter">
	<div class="form-group-autofit sort-filter-container">
		<div
			aria-controls="sort-filter-items"
			aria-expanded="true"
			class="form-group-item form-group-item-label form-group-item-shrink sort-collapse-trigger sort-group-container"
			data-target="#sort-filter-items"
			data-toggle="collapse"
			onclick="toggleCollapse('sort-filter-items');"
			role="button"
			tabindex="0"
		>
			<span class="text-truncate-inline">
				<span class="text-truncate sort-title">
					${languageUtil.get(locale, "sort-by")}
				</span>
			</span>

			<div class="collapse-icon">
				<div class="collapse-icon-open">
					<svg
						aria-hidden="true"
						class="lexicon-icon lexicon-icon-angle-down"
						focusable="false"
						viewBox="0 0 512 512"
					>
						<g>
							<path
								class="lexicon-icon-outline"
								d="M272.8,375.2L475,173.3c23-23.6-11.9-59.9-35.1-36L256,320.3L72.2,137.4c-23.5-24-58.2,12.5-35.2,36.1l202.1,201.7C249.2,385.4,263.5,384.5,272.8,375.2z"
							></path>
						</g>
					</svg>
				</div>

				<div class="collapse-icon-closed">
					<svg
						aria-hidden="true"
						class="lexicon-icon lexicon-icon-angle-right"
						focusable="false"
						viewBox="0 0 512 512"
					>
						<g>
							<path
								class="lexicon-icon-outline"
								d="M375.2,239.2L173.3,37c-23.6-23-59.9,11.9-36,35.1l183,183.9L137.4,439.8c-24,23.5,12.5,58.2,36.1,35.2l201.7-202.1C385.4,262.8,384.5,248.5,375.2,239.2z"
							></path>
						</g>
					</svg>
				</div>
			</div>
		</div>
	</div>

	<div class="collapse form-check form-group-item show" id="sort-filter-items">
		<#if entries?has_content>
			<#list entries as entry>
				<label>
					<input
						class="form-check-input"
						name="inlineRadioOptions"
						onchange="handleChangeSort(event);"
						type="radio"
						value="${entry.getField()}"
						${entry.isSelected()?then("checked","")}
					>
					<span class="form-check-label-text">
						${entry.getLanguageLabel()}
					</span>
				</label>
			</#list>
		</#if>
	</div>
</div>

<script>
	function toggleCollapse(dataTargetId) {
		const dataTargetElements = document.querySelectorAll(
			'[data-target="#' + dataTargetId + '"]'
		);

		dataTargetElements.forEach((element) => {
			element.classList.toggle('collapsed');
			const isExpanded = element.getAttribute('aria-expanded') === 'true';
			element.setAttribute('aria-expanded', !isExpanded);
		});

		const targetElement = document.getElementById(dataTargetId);
		if (targetElement) {
			targetElement.classList.toggle('show');
		}
	}

	function handleChangeSort(event) {
		const selectedOptionValue = event.currentTarget.value;
		const urlParams = new URLSearchParams(window.location.search);

		urlParams.set('sort', selectedOptionValue);
		window.location.search = urlParams;
	}
</script>

<style>
	.sort-filter #sort-filter-items.collapse:not(.show) {
		display: none !important;
	}

	.lexicon-icon {
		height: 14px;
		width: 14px;
	}

	.sort-collapse-trigger {
		align-items: center;
		cursor: pointer;
		display: flex;
		justify-content: space-between;
		width: 100%;
	}

	.sort-collapse-trigger .text-truncate-inline {
		flex-grow: 1;
		margin-right: 10px;
	}

	.sort-collapse-trigger .collapse-icon .collapse-icon-open,
	.sort-collapse-trigger .collapse-icon .collapse-icon-closed {
		display: none;
		flex-shrink: 0;
	}

	.sort-collapse-trigger.collapsed .collapse-icon .collapse-icon-closed {
		display: block;
	}

	.sort-collapse-trigger:not(.collapsed) .collapse-icon .collapse-icon-open {
		display: block;
	}

	.sort-filter-container {
		display: flex;
		flex-direction: column;
		margin-bottom: 0;
	}

	#sort-filter-items {
		display: flex;
		flex-direction: column;
		gap: 16px;
		margin-top: 12px;
		margin-left: 8px;
	}

	#sort-filter-items label {
		align-items: center;
		color: var(--color-neutral-10, #282934);
		display: flex;
		font-weight: 400;
		margin-bottom: 0;
	}

	.sort-group-container {
		display: flex;
		flex-direction: row !important;
		padding-bottom: 0 !important;
		width: 100% !important;
	}

	.sort-group-container,
	.text-truncate {
		margin-left: 4px;
	}

	.sort-title {
		color: var(--color-neutral-10, #282934);
		font-size: 18px;
		font-weight: 600;
		margin-top: 8px;
	}
</style>