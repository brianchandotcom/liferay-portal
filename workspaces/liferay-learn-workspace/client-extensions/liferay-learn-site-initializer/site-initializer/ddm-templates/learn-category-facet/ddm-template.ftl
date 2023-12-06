<#macro treeview_item
	cssClassTreeItem = ""
	frequency = 0
	id = ""
	frequencyVisible = true
	name = ""
	selectable = false
	selected = false
	termDisplayContexts = ""
	termDisplayContextClass = ""
	vocabularyName = ""
>
	<li class="treeview-item ${termDisplayContextClass}" role="none">
		<div
			aria-controls="${namespace}treeItem${id}"
			aria-expanded="true"
			class="treeview-link ${cssClassTreeItem}"
			data-target="#${namespace}treeItem${id}"
			data-toggle="collapse"
			onClick="${namespace}toggleTreeItem('${namespace}treeItem${id}');"
			role="treeitem"
			tabindex="0"
		>
			<span class="c-inner" tabindex="-2">
				<span class="autofit-row">
					<#if selectable>
						<span class="autofit-col autofit-col-expand">
							<div class="custom-checkbox custom-control">
								<label>
									<input
										autocomplete="off"
										${selected?then("checked", "")}
										class="custom-control-input facet-term ${vocabularyName}"
										data-term-id=${id}
										disabled
										onChange="Liferay.Search.FacetUtil.changeSelection(event);"
										type="checkbox"
									/>

									<span class="custom-control-label">
										<span class="custom-control-label-text">
											${name}

											<#if frequencyVisible>
												(${frequency})
											</#if>
										</span>
									</span>
								</label>
							</div>
						</span>
					<#else>
						<span class="autofit-col autofit-col-expand">
							<span class="component-text">
								<span
									class="text-truncate-inline"
									title="${name}"
								>
									<span class="text-truncate">
										${name}

										<#if frequencyVisible>
											(${frequency})
										</#if>
									</span>
								</span>
							</span>
						</span>
					</#if>

					<#if termDisplayContexts?has_content>
						<span class="autofit-col">
							<@clay.button
								aria\-controls="${namespace}treeItem${id}"
								aria\-expanded="true"
								cssClass="btn btn-monospaced component-expander"
								data\-target="#${namespace}treeItem${id}"
								data\-toggle="collapse"
								displayType="link"
								tabindex="-1"
							>
								<span class="c-inner" tabindex="-2">
									<@clay["icon"] symbol="angle-up" />

									<@clay["icon"]
										cssClass="component-expanded-d-none"
										symbol="angle-right"
									/>
								</span>
							</@clay.button>
						</span>
					</#if>
				</span>
			</span>
		</div>

		<#if termDisplayContexts?has_content>
			<div class="actionBtns">
				<@clay.button
					cssClass="btn-unstyled facet-clear-btn"
					displayType="link"
					id="${vocabularyName + '_facetAssetCategoriesSelectAll'}"
					onClick="selectAll(id)"
				>
					<strong>${languageUtil.get(locale, "select-all")}</strong>
				</@clay.button>

				<#list termDisplayContexts as termDisplayContext>
					<#if termDisplayContext.isSelected()>
						<@clay.button
							cssClass="btn-unstyled facet-clear-btn"
							displayType="link"
							id="${vocabularyName + '_facetAssetCategoriesClear'}"
							onClick="clearSelections(id)"
						>
							<strong>${languageUtil.get(locale, "clear")}</strong>
						</@clay.button>
						<#break>
					</#if>
				</#list>
			</div>

			<div class="collapse show" id="${namespace}treeItem${id}">
				<ul class="treeview-group" role="group">
					<#assign
						termDisplayContextCount = 1
						hasTermDisplayContextHidden = false
					/>

					<#list termDisplayContexts as termDisplayContext>
						<#assign hideClass = "" />

						<#if termDisplayContextCount gt 8 && !termDisplayContext.isSelected()>
							<#assign
								hideClass = "${vocabularyName}-class d-none"
								hasTermDisplayContextHidden = true
							/>
						</#if>

						<@treeview_item
							cssClassTreeItem="tree-item-category"
							frequency=termDisplayContext.getFrequency()
							frequencyVisible=termDisplayContext.isFrequencyVisible()
							id=termDisplayContext.getFilterValue()
							name=htmlUtil.escape(termDisplayContext.getBucketText())
							selectable=true
							selected=termDisplayContext.isSelected()
							termDisplayContextClass=hideClass
							vocabularyName=vocabularyName
						/>

						<#assign termDisplayContextCount++ />
					</#list>

					<#if (termDisplayContextCount gt 8) && hasTermDisplayContextHidden>
						<@clay.button
							cssClass="btn-unstyled facet-clear-btn view-all-btn"
							displayType="link"
							id="${vocabularyName + '_facetAssetCategoriesViewAll'}"
							onClick="viewAll(id)"
						>
							<strong>${languageUtil.get(locale, "view-all")}</strong>
						</@clay.button>
					</#if>
				</ul>
			</div>
		</#if>
	</li>
</#macro>

<#assign vocabularyNames = assetCategoriesSearchFacetDisplayContext.getVocabularyNames()![] />

<#if vocabularyNames?has_content>
	<#list vocabularyNames as vocabularyName>
		<ul class="treeview treeview-light treeview-nested treeview-vocabulary-display" role="tree">
			<@treeview_item
				cssClassTreeItem="tree-item-vocabulary"
				frequencyVisible=false
				id=vocabularyName + vocabularyName?index
				name="${htmlUtil.escape(vocabularyName)}"
				termDisplayContexts=assetCategoriesSearchFacetDisplayContext.getBucketDisplayContexts(vocabularyName)
				vocabularyName="${stringUtil.replace(vocabularyName, ' ', '')}"
			/>
		</ul>
	</#list>
</#if>

<@liferay_aui.script>
	function ${namespace}toggleTreeItem(dataTarget) {
		const dataTargetElements = document.querySelectorAll("[data-target=\"#" + dataTarget + "\"]");

		dataTargetElements.forEach(
			element => {
				if (element.classList.contains('collapsed')) {
					element.classList.remove('collapsed');
					element.setAttribute('aria-expanded', true);
				}
				else {
					element.classList.add('collapsed');
					element.setAttribute('aria-expanded', false);
				}
			}
		);

		const subtreeCategoryTreeElement = document.getElementById(dataTarget);

		if (subtreeCategoryTreeElement) {
			if (subtreeCategoryTreeElement.classList.contains('show')) {
				subtreeCategoryTreeElement.classList.remove('show');
			}
			else {
				subtreeCategoryTreeElement.classList.add('show');
			}
		}
	}

	function clearSelections(id) {
		const selections = document.getElementsByClassName(id.split('_')[0]);

		for (selection of selections) {
			if (selection.checked) {
				selection.checked = false;
			}
		}

		Liferay.Search.FacetUtil.changeSelection(event);
	}

	function load() {
		const viewAllBtns = document.getElementsByClassName('view-all-btn');

		for (viewAllBtn of viewAllBtns) {
			if(sessionStorage.getItem(viewAllBtn.id)) {
				viewAllBtn.click();
			}
		}
	}

	load();

	function selectAll(id) {
		const selections = document.getElementsByClassName(id.split('_')[0]);

		for (selection of selections) {
			if (!selection.checked) {
				selection.checked = true;
			}
		}

		Liferay.Search.FacetUtil.changeSelection(event);
	}

	function viewAll(id) {
		const selections = document.getElementsByClassName(id.split('_')[0]+'-class');

		for (selection of selections) {
			if (!selection.checked) {
				selection.classList.remove('d-none');
			}
		}

		document.getElementById(id).classList.add('d-none');

		sessionStorage.setItem(id, true);
	}
</@>

<style>
	.autofit-col .c-inner {
		color: #282934;
		flex-shrink: 0;
		height: 16px;
		width: 16px;
	}

	.btn-unstyled.facet-clear-btn {
		color: var(--action-neutral-default, #2B3A4B);
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 13px;
		font-style: normal;
		font-weight: 400;
		line-height: 16px;
	}

	.custom-checkbox.custom-control .custom-control-label {
		display: flex;
	}

	.custom-checkbox.custom-control .custom-control-label .custom-control-label-text{
		width: fit-content;
	}

	.custom-control-label:hover:before {
	box-shadow: 0px 0px 0px 8px #EDF3FE;
	}

	.custom-control-label .text-truncate-inline {
		color: var(--neutral-10, #282934);
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 13px;
		font-style: normal;
		font-weight: 400;
		line-height: 16px;
	}

	.text-truncate-inline .text-truncate {
		color: var(--neutral-10, #282934);
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 18px;
		font-style: normal;
		font-weight: 600;
		line-height: 20px;
	}

	.treeview.treeview-light.treeview-nested.treeview-vocabulary-display {
		align-items: flex-start;
		background: var(--neutral-01, #F7F7F8);
		border-radius: 10px;
		display: flex;
		flex-direction: column;
		gap: 15px;
		margin-top: 40px;
		padding: 16px;
		width: 350px;
	}

	.treeview.treeview-light.treeview-nested.treeview-vocabulary-display .treeview-item {
		align-items: flex-start;
		align-self: stretch;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}
</style>