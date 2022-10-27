<#if entries?has_content>
	<@clay.row>
		<#list entries as entry>
			<@clay.col md="3">
				<div class="results-header">
					<h3>
						${entry.getUnambiguousTitle(entries, themeDisplay.getSiteGroupId(), themeDisplay.getLocale())}
					</h3>
				</div>

				<#assign categories = entry.getCategories() />

				<@displayCategories categories=categories />
			</@clay.col>
		</#list>
	</@clay.row>
</#if>

<#macro displayCategories
	categories
>
	<#if categories?has_content>
		<ul class="categories">
			<#list categories as category>
				<li>
					<#assign categoryURL = renderResponse.createRenderURL() />

					${categoryURL.setParameter("resetCur", "true")}
					${categoryURL.setParameter("categoryId", category.getCategoryId()?string)}

					<a href="${categoryURL}">${category.getName()}</a>

					<#if serviceLocator??>
						<#assign
							assetCategoryService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetCategoryService")

							childCategories = assetCategoryService.getChildCategories(category.getCategoryId())
						/>

						<@displayCategories categories=childCategories />
					</#if>
				</li>
			</#list>
		</ul>
	</#if>
</#macro>