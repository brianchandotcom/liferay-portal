<#assign
	groupFriendlyURL = themeDisplay.getScopeGroup().getFriendlyURL()
	groupPathFriendlyURLPublic = themeDisplay.getPathFriendlyURLPublic() + groupFriendlyURL
	navigationJSONObject = jsonFactoryUtil.createJSONObject(navigation.getData())
	navigationMenuItems =
		{
			"Analytics Cloud": {
				"image": "/documents/d${groupFriendlyURL}/analytics_c-svg",
				"title": "Analytics Cloud",
				"url": "analytics-cloud"
			},
			"Commerce": {
				"image": "/documents/d${groupFriendlyURL}/commerce_product-svg",
				"title": "Commerce",
				"url": "commerce"
			},
			"DXP": {
				"image": "/documents/d${groupFriendlyURL}/dxp_p-svg",
				"title": "DXP / Portal",
				"url": "dxp"
			},
			"Liferay Cloud": {
				"image": "/documents/d${groupFriendlyURL}/dxp_c-svg",
				"title": "Liferay Cloud",
				"url": "liferay-cloud"
			},
			"Reference": {
				"image": "/documents/d${groupFriendlyURL}/reference-svg",
				"title": "Reference",
				"url": "reference"
			}
		}

	breadcrumbJSONArray = navigationJSONObject.getJSONArray("breadcrumb")
	childrenJSONArray = navigationJSONObject.getJSONArray("children")
	parentJSONObject = navigationJSONObject.getJSONObject("parent")
	productJSONObject = breadcrumbJSONArray.getJSONObject(breadcrumbJSONArray.length()-1)!navigationJSONObject.getJSONObject("self")
	siblingsJSONArray = navigationJSONObject.getJSONArray("siblings")
/>

<div class="learn-article-nav">
	<#if productJSONObject?has_content && productJSONObject.getString("title")?has_content && navigationMenuItems[productJSONObject.getString("title")]?has_content && navigationMenuItems[productJSONObject.getString("title")].title?has_content>
		<div
			class="dropdown learn-article-nav-root learn-dropdown"
		>
			<div class="learn-article-nav-item">
				<div class="d-flex">
					<div class="learn-article-nav-image">
						<img
							class="lexicon-icon lexicon-icon-caret-bottom product-icon"
							role="presentation"
							src='${navigationMenuItems[productJSONObject.getString("title")].image}'
							viewBox="0 0 512 512"
						/>
					</div>

					<span class="learn-article-nav-text">${navigationMenuItems[productJSONObject.getString("title")].title}</span>
				</div>

				<div id="dropdown-icon">
					<svg
						class="lexicon-icon lexicon-icon-caret-bottom"
						role="presentation"
						viewBox="0 0 512 512"
					>
						<use xlink:href="/o/admin-theme/images/clay/icons.svg#caret-bottom"></use>
					</svg>
				</div>
			</div>

			<ul class="dropdown-menu learn-dropdown-menu">
				<#list navigationMenuItems as key, value>
					<li>
						<a
							class="dropdown-item learn-article-nav-item"
							href="/w/${navigationMenuItems[key].url}/index"
							tabindex="4"
						>
							<span class="d-flex">
								<span class="learn-article-nav-image">
									<img
										class="lexicon-icon lexicon-icon-caret-bottom product-icon mt-0 mr-2"
										role="presentation"
										src="${value.image}"height: 25px; margin-left: 5px; max-width: none; width: 25px;
										viewBox="0 0 512 512"
									/>
								</span>
								<span class="learn-article-nav-text">${value.title}</span>
							</span>

							<#if navigationMenuItems[productJSONObject.getString("title")].url == value.url>
								<span>
									<@clay["icon"] symbol="check" />
								</span>
							</#if>
						</a>
					</li>
				</#list>
			</ul>
		</div>
	</#if>

	<div class="learn-article-nav-content">
		<#if parentJSONObject?has_content && parentJSONObject.getString("url")?has_content>
			<div class="learn-article-nav-item learn-article-nav-parent liferay-nav-item p-2">
				<div class="mr-2">
					<a
						href='${parentJSONObject.getString("url")}'
					>
						<svg
							class="lexicon-icon lexicon-icon-angle-left"
							role="presentation"
							viewBox="0 0 512 512"
						>
							<use xlink:href="/o/admin-theme/images/clay/icons.svg#angle-left"></use>
						</svg>
					</a>
				</div>

				<span>${parentJSONObject.getString("title")}</span>
			</div>
		</#if>

		<#if childrenJSONArray.length() gt 0>
			<ul class="m-0 p-2">
				<#list 0..childrenJSONArray.length()-1 as i>
					<li class="learn-article-nav-item">
						<a
							class='liferay-nav-item ${(navigationJSONObject.getJSONObject("self").url == childrenJSONArray.getJSONObject(i).url)?then("selected", "")}'
							href="${childrenJSONArray.getJSONObject(i).url}"
						>
							<span>${childrenJSONArray.getJSONObject(i).getString("title")}</span>
						</a>
					</li>
				</#list>
			</ul>
		<#elseif siblingsJSONArray.length() gt 0>
			<ul class="m-0 p-2">
				<#list 0..siblingsJSONArray.length()-1 as i>
					<li class="learn-article-nav-item">
						<a
							class='liferay-nav-item ${(navigationJSONObject.getJSONObject("self").url == siblingsJSONArray.getJSONObject(i).url)?then("selected", "")}'
							href="${siblingsJSONArray.getJSONObject(i).url}"
						>
							<span>${siblingsJSONArray.getJSONObject(i).getString("title")}</span>
						</a>
					</li>
				</#list>
			</ul>
		</#if>
	</div>
</div>