<#macro renderNavItems
	items
	depth=0
>
	<ul class="lo-side-nav__list">
		<#list items as navItem>
			<#assign
				isActive = navItem.isSelected() || navItem.isChildSelected()
				url = navItem.isBrowsable()?then(navItem.getURL(), "#")
			/>

			<li>
				<a
					class="lo-side-nav__item-link${isActive?then(' lo-side-nav__item-link--active', '')}"
					href="${url}"
				>
					${navItem.getName()}
				</a>

				<#if navItem.hasBrowsableChildren() && (displayDepth != 1)>
					<@renderNavItems
						depth = depth+1
						items = navItem.getChildren()
					/>
				</#if>
			</li>
		</#list>
	</ul>
</#macro>

<#if entries?has_content>
	<nav class="lo-side-nav">
		<@renderNavItems items = entries />
	</nav>
<#elseif themeDisplay.isSignedIn()>
	<div class="alert alert-info">
		<@liferay.language key="there-are-no-menu-items-to-display" />
	</div>
</#if>