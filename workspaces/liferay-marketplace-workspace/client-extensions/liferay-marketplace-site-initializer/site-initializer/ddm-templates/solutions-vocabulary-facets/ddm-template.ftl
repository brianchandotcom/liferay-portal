<style>
	.vocab-facet {
		border-radius: 10px;
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
</style>

<@liferay_ui["panel-container"]
	cssClass="vocab-facet bg-white border-radius-xlarge my-2"
	extended=true
	id="${namespace + 'facetAssetCategoriesPanelContainer'}"
	markupView="lexicon"
	persistState=true
>
	<@liferay_ui.panel
		collapsible=true
		cssClass="font-size-paragraph-small font-weight-semi-bold search-facet"
		extended=!browserSniffer.isMobile(request)
		id="${namespace + 'facetAssetCategoriesPanel'}"
		markupView="lexicon"
		persistState=true
		title="${assetCategoriesSearchFacetDisplayContext.getParameterName()?upper_case}"
	>
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
</@>