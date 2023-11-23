<style>
	.dropdown-item:active{
		background-color:#f8f9fa;
	}
	.product-icon {
		width: 1.5rem;
		height: 1.5rem;
	}
	.t-56 {
		top: 55.5px!important;
	}
	.t-109 {
		top: 109.5px!important;
	}

</style>

<#assign taxonomyVocabularies = restClient.get("/headless-admin-taxonomy/v1.0/sites/${themeDisplay.getSiteGroupId()}/taxonomy-vocabularies").items />
<#if taxonomyVocabularies?has_content>
	<#list taxonomyVocabularies as taxonomyVocabulary>
		<#if stringUtil.equals(taxonomyVocabulary.externalReferenceCode, "CAPABILITY")>
			<#if taxonomyVocabulary.id?has_content>
				<#assign capabilityId = taxonomyVocabulary.id />
	  		</#if>
		</#if>
	</#list>
</#if>

<#if capabilityId?has_content>
	<#assign capabilitiesFieldsMap = {} />
	<#list restClient.get("/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${capabilityId}/taxonomy-categories").items as capability>
		<#assign capabilitiesFieldsMap = capabilitiesFieldsMap +
			{
				capability.name:
				{
					"description": capability.description,
					"id": capability.id
		  		}
	  		}
		/>
	</#list>
</#if>
<div class="adt-navigation">
	<#list entries as navPrimaryItem>
		<#if navPrimaryItem.hasChildren()>
			<#assign
				columns = "12"
				dropdownType = "dropdown"
				menuType = ""
				menuWidth = "width:200px; overflow-x:hidden;"
				topPosition = "t-56"
			/>
			<#if navPrimaryItem.getName() == "Capabilities">
				<#assign
					columns = "3"
					dropdownType = "dropdown-wide dropdown-wide-container"
					menuType = "dropdown-menu-center p-5"
					menuWidth = "width:95%;"
					topPosition = "t-109"
				/>
			</#if>
			<div class="${dropdownType}">
				<div
					class="adt-nav-item w-100"
					data-toggle="liferay-dropdown"
				>
					<div class="adt-nav-text d-flex">
						<span
							aria-expanded="false"
							aria-haspopup="true"
							class="adt-nav-title text-truncate"
							id=${navPrimaryItem.getName()}
						>
							${navPrimaryItem.getName()}
							<svg class="lexicon-icon lexicon-icon-caret-bottom" role="presentation" viewBox="0 0 512 512">
								<use xlink:href="/o/admin-theme/images/clay/icons.svg#caret-bottom"></use>
							</svg>
						</span>
					</div>
				</div>

				<div
					aria-labelledby=${navPrimaryItem.getName()}
					class="dropdown-menu ${menuType} ${topPosition}"
					style="position: absolute; will-change: transform; border-radius:13px; ${menuWidth}"
				>
					<div class="row">
						<#list navPrimaryItem.getChildren() as navSecondaryItem>
							<div class="dropdown-item col-sm-${columns}" style="border-radius:13px;">
								<#if capabilitiesFieldsMap?has_content && navPrimaryItem.getName() == "Capabilities">
									<#assign categoryFields = capabilitiesFieldsMap[navSecondaryItem.getName()] />

									<a class="adt-submenu-item-link text-decoration-none" href="/search?category=${categoryFields['id']}" style="color: black;" tabindex="4">
										<h5 class="pl-3 pt-2">
											${navSecondaryItem.getName()}
										</h5>
										<#if categoryFields["description"]?has_content>
											<div
												class="pl-3"
												style="padding-top: 0.5rem;"
											>
												${categoryFields["description"]}
											</div>
										</#if>
									</a>
								<#else>
									<#assign
										customFields = navSecondaryItem.getExpandoAttributes()!{}
										navItemIconId = customFields["Svg Sprite Map Id"]!""
									/>

									<a class="adt-submenu-item-link text-decoration-none" href="${navSecondaryItem.getRegularURL()}" style="color: black;" tabindex="4">
										<div class="pl-2 pt-3">
											<img
												alt="${navSecondaryItem.getName()} Logo"
												class="lexicon-icon lexicon-icon-caret-bottom product-icon"
												role="presentation"
												src="${navItemIconId}"
												viewBox="0 0 512 512"
											/>

											<b class="pl-2">${navSecondaryItem.getName()}</b>
										</div>
									</a>
								</#if>
							</div>
						</#list>
					</div>
				</div>
			</div>
		<#else>
			<a class="adt-nav-item w-100" href="${navPrimaryItem.getRegularURL()}">
				<div class="adt-nav-text d-flex pr-3">
					<span class="adt-nav-title text-truncate">
			  			${navPrimaryItem.getName()}
					</span>
				</div>
	  		</a>
		</#if>
	</#list>
</div>