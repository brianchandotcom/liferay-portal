<ul class="list-unstyled tab-list">
			<#if entries?has_content>
				<#list entries as entry>
					<li class="facet-value">
						<@clay.button
							cssClass="facet-term btn-unstyled ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')} term-name"
							data\-term\-id="${entry.getFilterValue()}"
							disabled="true"
							displayType="link"
							onClick="Liferay.Search.FacetUtil.changeSelection(event);"
						>
							<span class="term-text">${htmlUtil.escape(entry.getBucketText())}</span>
							<#if entry.isFrequencyVisible()>
								<span class="term-count">${entry.getFrequency()}</span>
							</#if>
						</@clay.button>
					</li>
				</#list>
			</#if>
</ul>

<script>
document.addEventListener("DOMContentLoaded", function() {
	const buttons = document.querySelectorAll('.facet-term');

	buttons.forEach(button => {
		button.addEventListener('click', function(event) {
			event.preventDefault();

			buttons.forEach(btn => {
				btn.classList.remove('facet-term-selected');
				btn.setAttribute('disabled', 'true');
			});

			this.classList.add('facet-term-selected');
			this.removeAttribute('disabled');

			const categoryValue = this.getAttribute('data-term-id');

			const currentURL = new URL(window.location.href);
			currentURL.searchParams.set('category', categoryValue);

			window.location.href = currentURL.toString();
		});
	});
});
</script>