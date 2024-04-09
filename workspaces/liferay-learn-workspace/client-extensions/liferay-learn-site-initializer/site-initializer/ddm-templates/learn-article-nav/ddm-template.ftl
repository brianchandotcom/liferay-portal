<#assign
	groupFriendlyURL = themeDisplay.getScopeGroup().getFriendlyURL()
	groupPathFriendlyURLPublic = themeDisplay.getPathFriendlyURLPublic() + groupFriendlyURL
	navigationJSONObject = jsonFactoryUtil.createJSONObject(navigation.getData())
	navigationMenuItems =
	{
	"Analytics Cloud": {
	"title": "Analytics Cloud",
	"url": "analytics-cloud",
	"image": "/documents/d${groupFriendlyURL}/analytics_c-svg"
	},
	"Commerce": {
	"title": "Commerce",
	"url": "commerce",
	"image": "/documents/d${groupFriendlyURL}/commerce_product-svg"
	},
	"DXP": {
	"title": "DXP / Portal",
	"url": "dxp",
	"image": "/documents/d${groupFriendlyURL}/dxp_p-svg"
	},
	"Liferay Cloud": {
	"title": "Liferay Cloud",
	"url": "liferay-cloud",
	"image": "/documents/d${groupFriendlyURL}/dxp_c-svg"
	},
	"Reference": {
	"title": "Reference",
	"url": "reference",
	"image": "/documents/d${groupFriendlyURL}/reference-svg"
	}
	}

	breadcrumbJSONArray = navigationJSONObject.getJSONArray("breadcrumb")
	childrenJSONArray = navigationJSONObject.getJSONArray("children")
	parentJSONObject = navigationJSONObject.getJSONObject("parent")
	productJSONObject = breadcrumbJSONArray.getJSONObject(breadcrumbJSONArray.length()-1)
	siblingsJSONArray = navigationJSONObject.getJSONArray("siblings")
/>

<div class="learn-article-nav">
	<#if navigationMenuItems[productJSONObject.getString("title")]?has_content && navigationMenuItems[productJSONObject.getString("title")].title?has_content>
		<div class="dropdown learn-article-nav-root">
			<div
				class="learn-article-nav-item"
				data-toggle="liferay-dropdown"
			>
				<span
					class="learn-article-nav-text"
					id="dropdown-products"
				>
					<img
						class="lexicon-icon lexicon-icon-caret-bottom product-icon"
						role="presentation"
						src='${navigationMenuItems[productJSONObject.getString("title")].image}'
						viewBox="0 0 512 512"
					/>

					<span>${navigationMenuItems[productJSONObject.getString("title")].title}</span>
				</span>
			</div>

			<div class="dropdown-menu">
				<#list navigationMenuItems as key, value>
					<a
						class="learn-article-nav-item"
						href="/w/${navigationMenuItems[key].url}/index"
						tabindex="4"
					>
						<span class="learn-article-nav-text">
							<img
								class="lexicon-icon lexicon-icon-caret-bottom product-icon mt-0 mr-2"
								role="presentation"
								src="${value.image}"height: 25px; margin-left: 5px; max-width: none; width: 25px;
								viewBox="0 0 512 512"
							/>

							<span>${value.title}</span>
						</span>
					</a>
				</#list>
			</div>
		</div>
	</#if>

	<div class="learn-article-nav-content">
		<#if parentJSONObject?has_content>
			<a
				class="learn-article-nav-item learn-article-nav-parent"
				href='${parentJSONObject.getString("url")}'
				id="parentTitle"
			>
				<svg
					class="lexicon-icon lexicon-icon-angle-left"
					role="presentation"
					viewBox="0 0 512 512"
				>
					<use xlink:href="#angle-left" />
				</svg>

				<span>${parentJSONObject.getString("title")}</span>
			</a>
		</#if>

		<#if childrenJSONArray.length() gt 0>
			<ul>
				<#list 0..childrenJSONArray.length()-1 as i>
					<li class="learn-article-nav-item ${(navigationJSONObject.getJSONObject("self").url == childrenJSONArray.getJSONObject(i).url)?then("selected", "")}">
						<a
							class="liferay-nav-item"
							href="${childrenJSONArray.getJSONObject(i).url}"
						>
							<span>${childrenJSONArray.getJSONObject(i).getString("title")}</span>
						</a>
					</li>
				</#list>
			</ul>
		<#elseif siblingsJSONArray.length() gt 0>
			<#list 0..siblingsJSONArray.length()-1 as i>
				<li class="learn-article-nav-item ${(navigationJSONObject.getJSONObject("self").url == siblingsJSONArray.getJSONObject(i).url)?then("selected", "")}">
					<a
						class="liferay-nav-item"
						href="${siblingsJSONArray.getJSONObject(i).url}"
					>
						<span>${siblingsJSONArray.getJSONObject(i).getString("title")}</span>
					</a>
				</li>
			</#list>
		</#if>
	</div>
</div>