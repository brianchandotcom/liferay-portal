<style>
	.adt-av-title {
		font-weight: 600;
	}

	.dropdown-full .adt-nav-item {
		background-color: rgba(0, 0, 0, 0);
		border: none;
		border-radius: 0 !important;
	}

	.dropdown-full .adt-nav-item .adt-nav-text {
		align-items: center;
		display: flex;
		justify-content: flex-start;
	}

	.dropdown-full .dropdown-menu > .row {
		margin: 0 auto;
		max-width: var(--container-max-xl);
		padding: var(--spacer-4);
	}

	.dropdown-full.show .adt-nav-item {
		border-bottom: 1px solid white;
		border-radius: var(--border-radius-lg);
	}

	.dropdown-full.show .adt-nav-item .adt-nav-text .lexicon-icon {
		transform: rotate(180deg);
	}

	.dropdown-menu {
		border: none;
		border-radius: 0px 0px var(--border-radius-lg) var(--border-radius-lg);
		box-shadow: 0px 10px 5px rgba(0, 0, 0, 0.15);
	}

	.dropdown-menu .row .dropdown-item-div {
		flex-basis: 25%;
	}

	.dropdown-menu .products-section.row .dropdown-item-div {
		flex-basis: 33%;
	}

	.dropdown-menu .row .dropdown-item-div .dropdown-item {
		border-radius: var(--border-radius-lg);
	}

	.dropdown-menu .row .dropdown-item-div .dropdown-item:hover {
		background: var(--color-action-primary-hover-10, #EDF3FE);
	}

	.dropdown-menu .row .dropdown-item-div .dropdown-item:hover .title {
		color: var(--color-brand-primary-darken-1, #0053F0);
	}

	.dropdown-menu .row .dropdown-item-div .dropdown-item .icon {
		height: 32px;
		width: 32px;
	}

	.dropdown-menu .row .dropdown-item-div .dropdown-item .subtitle {
		color: var(--color-neutral-8, #54555F);
		font-size: 13px;
		font-weight: 400;
		line-height: 16px;
	}

	.dropdown-menu .row .dropdown-item-div .dropdown-item .title {
		color: var(--color-neutral-10, #282934);
		font-size: 18px;
		font-weight: 600;
		line-height: 20px;
	}


	.responsive-text {
		display: -webkit-box;
		overflow: hidden;
		text-overflow: ellipsis;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
	}
</style>

<#assign
	taxonomyVocabularyId = restClient.get("/headless-admin-taxonomy/v1.0/sites/${themeDisplay.getSiteGroupId()}/taxonomy-vocabularies/by-external-reference-code/CAPABILITY").id
	taxonomyVocabulary = {}
/>

<#list restClient.get("/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${taxonomyVocabularyId}/taxonomy-categories?sort=name").items as taxonomyCategory>
	<#assign icons = taxonomyCategory.taxonomyCategoryProperties?filter(property -> stringUtil.equals(property.key, "icon")) />

	<#if icons?size != 0>
		<#assign icon = icons[0].value />
	</#if>

	<#assign taxonomyVocabulary = taxonomyVocabulary + {
			taxonomyCategory.name:
				{
					"description": taxonomyCategory.description,
					"icon": icon!"documents/d${themeDisplay.getScopeGroup().getFriendlyURL()}/blank_icon",
					"id": taxonomyCategory.id
				}
		}
	>
</#list>

<div class="adt-navigation">
	<#assign groupFriendlyURL = portalUtil.getGroupFriendlyURL(themeDisplay.getLayoutSet(), themeDisplay, true, false) />

	<#list entries as navPrimaryItem>
		<#if navPrimaryItem.hasChildren()>
			<#assign
				columns = "4"
				customFields = navPrimaryItem.getExpandoAttributes()!{}
				navItemType = customFields["Primary Nav Item Type"]!""
				cssClassName = "products-section"
			/>

			<#if stringUtil.equals(navItemType, "CAPABILITY")>
				<#assign
					columns = "3"
					cssClassName = ""
				/>
			</#if>

			<div class="dropdown-full nav-item">
				<button
					class="adt-nav-item w-100"
					data-toggle="liferay-dropdown"
				>
					<div class="adt-nav-text">
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
				</button>

				<div
					aria-labelledby=${navPrimaryItem.getName()}
					class="dropdown-menu"
				>
					<div class="row ${cssClassName}">
						<#list navPrimaryItem.getChildren() as navSecondaryItem>
							<#if taxonomyVocabulary?has_content && stringUtil.equals(navItemType, "CAPABILITIES")>
								<#if taxonomyVocabulary[navSecondaryItem.getName()]?has_content>
									<div class="dropdown-item-div col-12 col-lg-${columns} m-0 p-0">
										<#assign capabilityFields = taxonomyVocabulary[navSecondaryItem.getName()] />

										<a class="d-flex dropdown-item p-3 text-decoration-none" href="${groupFriendlyURL}/search?category=${capabilityFields['id']}" tabindex="4">
											<img
												alt="${navSecondaryItem.getName()} icon"
												class="icon mr-3"
												src="${capabilityFields["icon"]}"
											/>

											<div>
												<h5 class="responsive-text title">
													${navSecondaryItem.getName()}
												</h5>

												<#if capabilityFields["description"]?has_content>
													<p class="pt-2 responsive-text subtitle">
														${capabilityFields["description"]}
													</p>
												</#if>
											</div>
										</a>
									</div>
								</#if>
							<#else>
								<div class="dropdown-item-div col-12 col-lg-${columns} m-0 p-0">
									<#assign
										customFields = navSecondaryItem.getExpandoAttributes()!{}
										navItemDescription = customFields["Description"]!""
										navItemIcon = customFields["Icon URL"]!""
									/>

									<a class="d-flex dropdown-item maxh-90 p-3 text-decoration-none" href="${groupFriendlyURL}${navSecondaryItem.getRegularURL()}" tabindex="4">
										<img
											alt="${navSecondaryItem.getName()} icon"
											class="icon mr-3"
											src="${navItemIcon}"
										/>

										<div>
											<h5 class="responsive-text title">
												${navSecondaryItem.getName()}
											</h5>

											<p class="pt-2 responsive-text subtitle">
												${navItemDescription}
											</p>
										</div>
									</a>
								</div>
							</#if>
						</#list>
					</div>
				</div>
			</div>
		<#else>
			<a class="adt-nav-item w-100" href="${groupFriendlyURL}${navPrimaryItem.getRegularURL()}">
				<div class="adt-nav-text d-flex pr-3">
					<span class="adt-nav-title text-truncate">
			  			${navPrimaryItem.getName()}
					</span>
				</div>
	  		</a>
		</#if>
	</#list>
</div>