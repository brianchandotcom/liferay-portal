<#attempt>
	<#assign navigationMenu = restClient.get("/headless-delivery/v1.0/sites/" + themeDisplay.getScopeGroupId()?c + "/navigation-menus/by-external-reference-code/LO_FOOTER_NAV?nestedFields=navigationMenuItems") />
<#recover>
	<#assign navigationMenu = {} />
</#attempt>

<#if (navigationMenu.navigationMenuItems)??>
	<#list navigationMenu.navigationMenuItems as footerNavSection>
		<section class="c-mb-10 c-mb-lg-0 c-mb-md-10 c-px-0 col-lg-2 col-md-4 section-title">
			<h3 class="c-mb-4 font-weight-semi-bold">${footerNavSection.name}</h3>

			<ul class="c-p-0 d-flex flex-column list-unstyled text-decoration-none">
				<#list (footerNavSection.navigationMenuItems)![] as footerNavItem>
					<li<#if !footerNavItem?is_last> class="c-mb-3"</#if>>
						<a class="c-p-0" href="${(footerNavItem.typeSettings.url)!""}"<#if stringUtil.equals((footerNavItem.typeSettings.useNewTab)!"", "true")> target="_blank"</#if>>${footerNavItem.name}</a>
					</li>
				</#list>
			</ul>
		</section>
	</#list>
</#if>