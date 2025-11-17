<#assign
	previousReleaseURL = "https://customer.liferay.com/dxp-release-notes?p_p_id=com_liferay_osb_customer_release_tool_web_portlet_ReleaseToolPortlet&_com_liferay_osb_customer_release_tool_web_portlet_ReleaseToolPortlet_product=dxp&_com_liferay_osb_customer_release_tool_web_portlet_ReleaseToolPortlet_tabs1=&_com_liferay_osb_customer_release_tool_web_portlet_ReleaseToolPortlet_productVersion=7.4&_com_liferay_osb_customer_release_tool_web_portlet_ReleaseToolPortlet_fromFixPackVersion=2024.101&_com_liferay_osb_customer_release_tool_web_portlet_ReleaseToolPortlet_toFixPackVersion=2024.105"
	quarterlyReleaseVocabularyId = (restClient.get("/headless-admin-taxonomy/v1.0/sites/${themeDisplay.getSiteGroupId()}/taxonomy-vocabularies/by-external-reference-code/QUARTERLY-RELEASES").id)!
/>

<#if quarterlyReleaseVocabularyId?has_content>
	<#assign releaseCategories = (restClient.get("/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${quarterlyReleaseVocabularyId}/taxonomy-categories?pageSize=4&sort=name:desc").items)! />
</#if>

<style>
	#dropdownReleaseNotes {
		font-size: var(--h2-font-size, 1.375rem) !important;
		font-weight: var(--h2-font-weight) !important;
	}
</style>

<div class="dropdown">
	<button
		aria-expanded="false"
		aria-haspopup="true"
		class="btn dropdown-toggle p-0 text-neutral-0"
		data-toggle="liferay-dropdown"
		id="dropdownReleaseNotes"
		type="button"
	>
		<#if (AssetCategory_name.getData())??>
			${AssetCategory_name.getData()}
		</#if>

		<@clay["icon"] symbol="caret-bottom" />
	</button>

	<ul
		aria-labelledby="dropdownReleaseNotes"
		class="dropdown-menu"
		style="top: 0px; transform: translate3d(0px, 40px, 0px);"
		x-placement="bottom-start"
	>
		<#if releaseCategories?has_content>
			<#list releaseCategories as releaseCategory>
				<#assign friendlyURL = (releaseCategory.taxonomyCategoryProperties?filter(taxonomyCategoryProperty -> stringUtil.equals(taxonomyCategoryProperty.key, "friendlyURL"))?first.value)! />

				<li>
					<a class="dropdown-item" href="${releaseCategory.id}?r=${releaseCategory.id}">
						${releaseCategory.name}

						<#if (AssetCategory_name.getData())?? && releaseCategory.name == AssetCategory_name.getData()>
							<span class="dropdown-item-indicator-end">
								<@clay["icon"] symbol="check" />
							</span>
						</#if>
					</a>
				</li>
			</#list>

			<li>
				<a class="dropdown-item" href="${previousReleaseURL}" target="_blank">
					Previous Release

					<span class="dropdown-item-indicator-end">
						<@clay["icon"] symbol="shortcut" />
					</span>
				</a>
			</li>
		</#if>
	</ul>
</div>