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
	productJSONObject = breadcrumbJSONArray.getJSONObject(breadcrumbJSONArray.length()-1)
/>

<div class="col-12 col-md-2 mobile-nav-hide p-0">
	<div class="doc-nav-wrapper-inner">
		<#if navigationMenuItems[productJSONObject.getString("title")]?has_content && navigationMenuItems[productJSONObject.getString("title")].title?has_content>
			<div class="dropdown">
				<div
					class="adt-nav-item bg-color-1 br-5 ml-0 w-100"
					data-toggle="liferay-dropdown"
				>
					<div
						class="adt-nav-text align-items-center br-5 d-flex justify-content-between p-3"
						id="dropdown-products"
					>
						<div>
							<span
								aria-expanded="false"
								aria-haspopup="true"
								class="adt-nav-title align-items-center d-flex"
							>
								<div
									class="align-items-center br-20 d-flex mr-1"
									id="productIcon"
								>
									<img
										class="lexicon-icon lexicon-icon-caret-bottom product-icon mt-0 p-2"
										role="presentation"
										src='${navigationMenuItems[productJSONObject.getString("title")].image}'
										viewBox="0 0 512 512"
									/>
								</div>

								<div>${navigationMenuItems[productJSONObject.getString("title")].title}</div>
							</span>
						</div>

						<div>
							<svg
								class="lexicon-icon lexicon-icon-caret-bottom"
								role="presentation"
								viewBox="0 0 512 512"
							>
								<use xlink:href="/o/admin-theme/images/clay/icons.svg#caret-bottom"></use>
							</svg>
						</div>
					</div>
				</div>

				<div class="br-13 dropdown-menu m-0 p-0">
					<div class="m-0 p-3 row">
						<#list navigationMenuItems as key, value>
							<a
								class="adt-submenu-item-link color-black text-decoration-none"
								href="${groupPathFriendlyURLPublic}/w/${navigationMenuItems[key].url}/index"
								tabindex="4"
							>
								<div class="align-items-center br-13 br-5 col-sm-12 d-flex dropdown-item justify-content-between ml-0 mr-0">
									<div>
										<div class="align-items-center d-flex">
											<div
												class="align-items-center br-20 d-flex mr-1"
												id="productsIcon"
											>
												<img
													class="lexicon-icon lexicon-icon-caret-bottom product-icon mt-0 mr-2"
													role="presentation"
													src="${value.image}"height: 25px; margin-left: 5px; max-width: none; width: 25px;
													viewBox="0 0 512 512"
												/>
											</div>

											<b>${value.title}</b>
										</div>
									</div>

									<#if navigationMenuItems[productJSONObject.getString("title")].url == value.url>
										<div>
											<@clay["icon"] symbol="check" />
										</div>
									</#if>
								</div>
							</a>
						</#list>
					</div>
				</div>
			</div>
		</#if>

		<#if childrenJSONArray.length() gt 0>
			<div class="bg-color-1 br-5 doc-nav mt-3">
				<#if !breadcrumbJSONArray?has_content>
					<div class="align-items-center d-flex">
						<div class="m-2">
							<a
								class="br-5 p-2"
								href='${breadcrumbJSONArray.getJSONObject(0).getString("url")}'
								id="backLink"
							>
								<svg
									class="lexicon-icon lexicon-icon-angle-left"
									role="presentation"
									viewBox="0 0 512 512"
								>
									<use xlink:href="#angle-left" />
								</svg>
							</a>
						</div>

						<div class="align-self-center">
							<a
								class="pl-0 pr-0"
								id="parentTitle"
							>
								${breadcrumbJSONArray.getJSONObject(0).getString("title")}
							</a>
						</div>
					</div>
				</#if>

				<ul class="current">
					<#if childrenJSONArray.length() gt 0>
						<#list 0..childrenJSONArray.length()-1 as i>
							<li class="br-5 d-flex side-nav ${breadcrumbJSONArray?has_content?then("toctree", "")} ${(navigationJSONObject.getJSONObject("self").url == childrenJSONArray.getJSONObject(i).url)?then("current-level", "other-level")}">
								<a
									class="align-items-center br-5 d-flex internal justify-content-between p-2 reference"
									href="${childrenJSONArray.getJSONObject(i).url}"
								>
									${childrenJSONArray.getJSONObject(i).getString("title")}

									<#if breadcrumbJSONArray.length() lt 1>
										<div class="d-flex">
											<svg
												class="lexicon-icon lexicon-icon-angle-left"
												role="presentation"
												viewBox="0 0 512 512"
											>
												<use xlink:href="#angle-left" />
											</svg>
										</div>
									</#if>
								</a>
							</li>
						</#list>
					</#if>
				</ul>
			</div>
		</#if>
	</div>
</div>