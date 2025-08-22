<style ${nonceAttribute}>
	.vocab-facet {
		border-radius: 10px;
	}

	.vocab-facet .clear-btn {
		color: #2B3A4B;
		font-size: 14px;
		font-weight: 400;
	}

	.vocab-facet .panel a {
		padding: 1rem;
	}

	.vocab-facet .collapse-icon .collapse-icon-closed .lexicon-icon,
	.vocab-facet .collapse-icon .collapse-icon-open .lexicon-icon {
		margin-top: 0.3rem;
	}

	.vocab-facet .panel-body {
		padding: 0.5rem 1rem 1rem;
	}

	.vocab-facet .list-unstyled {
		margin-bottom: 0;
	}

	.vocab-facet .separator {
		width: 90%;
	}
</style>

<@liferay_ui["panel-container"]
	cssClass="vocab-facet bg-white border-radius-xlarge my-1"
	extended=true
	id="${namespace + 'facetAssetCategoriesPanelContainer'}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="font-size-paragraph-small font-weight-semi-bold"
		extended=!browserSniffer.isMobile(request)
		id="${namespace + 'facetAssetCategoriesPanel'}"
		markupView="lexicon"
		persistState=true
		title="${assetCategoriesSearchFacetDisplayContext.getParameterName()?upper_case}"
	>
		<button
			class="btn-unstyled clear-btn mb-4" id="${namespace + 'facetAssetCategoriesSelectAll'}"
				onClick="${namespace}selectAll(event)">
		  Select All
		</button>
	  	<button class="btn-unstyled clear-btn mb-4 ml-1" onClick="Liferay.Search.FacetUtil.clearSelections(event);">
		  Clear
	  	</button>
		<ul class="list-unstyled">
			<#list entries as entry>
				<li class="color-neutral-2 facet-value py-1">
					<div class="custom-checkbox custom-control font-weight-normal">
						<label class="facet-checkbox-label" for="${namespace}_term_${entry.getAssetCategoryId()}">
							<input
								${(entry.isSelected())?then("checked","")}
								class="custom-control-input facet-term"
								data-term-id="${entry.getAssetCategoryId()}"
								disabled
								id="${namespace}_term_${entry.getAssetCategoryId()}"
								name="${namespace}_term_${entry.getAssetCategoryId()}"
								onChange="Liferay.Search.FacetUtil.changeSelection(event);"
								type="checkbox"
							/>

							<span class="custom-control-label font-size-paragraph-small term-name ${(entry.isSelected())?then('facet-term-selected', 'facet-term-unselected')}">
								<span class="custom-control-label-text">
									${htmlUtil.escape(entry.getDisplayName())}
								</span>
							</span>
						</label>
					</div>
				</li>
			</#list>
		</ul>
	</@>
	<hr class="separator" />
</@>

<@liferay_aui.script>
	function ${namespace}selectAll(event) {
		event.preventDefault();

		var parameterName = `${assetCategoriesSearchFacetDisplayContext.getParameterName()}`;
		var divId = event.target.closest('.collapse').id;
		var checkboxes = document.querySelectorAll('#' + divId + ' .custom-checkbox input[type="checkbox"]');
		var url = new URL(window.location.href);

		if (url.searchParams.size === 0) {
			url.href += '?';
		}

		checkboxes.forEach((checkbox) => {
			if (!checkbox.checked) {
				if (url.searchParams.size > 0) {
					url.href += '&';
				}
				url.href += parameterName + '=' + checkbox.getAttribute('data-term-id');
			}
		});

		window.location.href = url.href

	}
</@>