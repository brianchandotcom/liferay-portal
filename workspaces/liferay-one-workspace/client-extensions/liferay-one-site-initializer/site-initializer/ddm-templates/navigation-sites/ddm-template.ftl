<#function getCustomFieldData navigationMenuItem name>
	<#list (navigationMenuItem.customFields)![] as customField>
		<#if customField.name == name>
			<#assign data = (customField.customValue.data)!"" />

			<#if data?is_sequence>
				<#return (data?first)!"" />
			</#if>

			<#return data />
		</#if>
	</#list>

	<#return "" />
</#function>

<#attempt>
	<#assign navigationMenu = restClient.get("/headless-delivery/v1.0/sites/" + themeDisplay.getScopeGroupId()?c + "/navigation-menus/by-external-reference-code/LO_SITES_NAV?nestedFields=customFields,navigationMenuItems") />
<#recover>
	<#assign navigationMenu = {} />
</#attempt>

<#if (navigationMenu.navigationMenuItems)??>
	<div class="f-navigation-menu-item">
		<#list navigationMenu.navigationMenuItems as sitesNavItem>
			<#assign icon = getCustomFieldData(sitesNavItem, "Menu Item Icon") />

			<div class="d-flex menu-item-wrapper">
				<a class="d-flex justify-content-between menu-item mx-2 text-decoration-none" href="${(sitesNavItem.typeSettings.url)!""}"<#if stringUtil.equals((sitesNavItem.typeSettings.useNewTab)!"", "true")> target="_blank"</#if>>
					<div class="d-flex menu-item-group">
						<div class="menu-item-icon-${icon} mr-1 px-1 text-white">
							<svg class="lexicon-icon lexicon-icon-sites sites-icon" role="presentation" viewBox="0 0 512 512"><use xlink:href="/o/admin-theme/images/clay/icons.svg#sites"></use></svg>
						</div>

						<div class="menu-item-text ml-2">
							${sitesNavItem.name}
						</div>
					</div>
				</a>
			</div>
		</#list>
	</div>
</#if>