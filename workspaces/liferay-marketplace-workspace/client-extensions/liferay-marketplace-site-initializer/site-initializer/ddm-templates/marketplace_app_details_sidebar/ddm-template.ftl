<style>
	.help-and-support-link {
		color: inherit;
		text-decoration: none;
	}

	.help-and-support-link-arrow {
		fill: rgb(133, 140, 148);
	}

	.help-and-support-link:hover {
		color: inherit;
		text-decoration: none;
	}

	.help-and-support-link-icon {
		color: rgb(133, 140, 148);
	}

	.help-and-support-svg mask,
	.link-arrow mask {
		mask-type: alpha;
	}
</style>

<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<#assign
	channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${scopeGroupId}'")
>

<#assign
	channelId = channel.items[0].id
	productId = (CPDefinition_cProductId.getData())!"0"
/>

<#assign
	product = restClient.get(
		"/headless-commerce-delivery-catalog/v1.0/channels/" + channelId +
		"/products/" + productId +
		"?accountId=-1&nestedFields=categories,productSpecifications,skus&skus.accountId=-1&skus.currencyCode=USD"
	)
>

<#assign
	categories = product.categories![]
	catalogName = (product.catalogName)!""
	productImage = product.images![]
	productSpecifications = product.productSpecifications![]
/>

<#assign
	appTypeFiltered = productSpecifications?filter(item -> stringUtil.equals(item.specificationKey, "type"))
	cpuSpec = productSpecifications?filter(item -> stringUtil.equals(item.specificationKey, "cpu"))
	developerNames = productSpecifications?filter(item -> stringUtil.equals(item.specificationKey, "developer-name"))
	liferayVersions = productSpecifications?filter(item -> stringUtil.equals(item.specificationKey, "liferay-version"))
	platformOffering = categories?filter(item -> stringUtil.equals(item.vocabulary, "marketplace liferay platform offering"))
	publisherUrlFiltered = productSpecifications?filter(spec -> stringUtil.equals(spec.specificationKey, "publisherwebsiteurl"))
	ramSpec = productSpecifications?filter(item -> stringUtil.equals(item.specificationKey, "ram"))
	solutionHeaderImages = productImage?filter(image -> image.tags?seq_contains("app icon"))
	supportEmailFiltered = productSpecifications?filter(spec -> stringUtil.equals(spec.specificationKey, "supportemailaddress"))
	supportPhoneFiltered = productSpecifications?filter(spec -> stringUtil.equals(spec.specificationKey, "supportphone"))
>

<#assign
	appType = (appTypeFiltered[0].value)!""
	publisherUrl = (publisherUrlFiltered[0].value?trim?replace(" ", ""))!""
	sanitizedUrl = (publisherUrl?starts_with("http") || publisherUrl?starts_with("https"))?then(publisherUrl, "https://" + publisherUrl)
	supportEmail = (supportEmailFiltered[0].value)!""
	supportPhone = (supportPhoneFiltered[0].value)!""
>

<p><strong>${languageUtil.get(locale, "developer", "Developer")}</strong></p>
<div>
	<#if developerNames?has_content>
		<#list developerNames as developerName>
			<a class = "bg-neutral-8" href = "/?developer-name=${developerName.value}">
				${developerName.value}
			</a>
		</#list>
	</#if>
</div>

<hr />

<p><strong>${languageUtil.get(locale, "publisher-date", "Publisher Date")}</strong></p>

<#setting date_format="MMMM d, yyyy">

<#if CPDefinition_displayDate.getData()?has_content>
	<#assign parsedDate = CPDefinition_displayDate.getData()?date("MM/dd/yy, h:mm a") />

	<p>${parsedDate}</p>
</#if>

<hr />

<p><strong>${languageUtil.get(locale, "deployment-method", "Deployment Method")}</strong></p>
<#list platformOffering as offering>
	<p>${offering.name}</p>
</#list>

<hr />

<p><strong>${languageUtil.get(locale, "app-type", "App Type")}</strong></p>
<div>
	<div class="bg-neutral-8">
		${appType?upper_case}
	</div>
</div>

<hr />

<p><strong>${languageUtil.get(locale, "version", "Version")}</strong></p>
<#if (CPDefinition_version.getData())??>
	${CPDefinition_version.getData()}
</#if>

<hr />

<p><strong>${languageUtil.get(locale, "supported-versions", "Supported Versions")}</strong></p>
<div>
	<#if liferayVersions?has_content>
		<#list liferayVersions as version>
			${version.value}<#if version?has_next>, </#if>
		</#list>
	</#if>
</div>

<hr />

<#if cpuSpec?has_content>
	<p><strong>${languageUtil.get(locale, "resource-requirements", "Resource Requirements")}</strong></p>

	<div>
		<#assign cpuQuantity = (cpuSpec[0].value)!"", ramQuantity = (ramSpec[0].value)!"" />
		<#if cpuQuantity?has_content || ramQuantity?has_content>
			<p>
				<#if cpuQuantity?has_content>
					${cpuQuantity}
					<#assign cpuNumber = cpuQuantity?number?default(0) />
		  			<#if cpuQuantity?eval gt 1>
						CPUS
					</#if>
					<#if cpuQuantity?eval lt 2>
						CPU
					</#if>
				</#if>
				<#if cpuQuantity?has_content && ramQuantity?has_content>, </#if>
				<#if ramQuantity?has_content>${ramQuantity} GB RAM</#if>
			</p>
		</#if>
	</div>

	<hr />
</#if>

<p><strong>${languageUtil.get(locale, "standard-price", "Standard Price")}</strong></p>
<div>
	<#assign purchasableSkus = [] />
	<#list product.skus as sku>
		<#if sku.purchasable?? && sku.purchasable>
			<#assign purchasableSkus = purchasableSkus + [sku] />
		</#if>
	</#list>

	<#assign standardSku = {} />
	<#list purchasableSkus as sku>
		<#assign matched = false />
		<#list sku.skuOptions as opt>
			<#if stringUtil.equals(opt.skuOptionValueKey, "standard")>
				<#assign
					standardSku = sku
					matched = true
				/>

				<#break>
			</#if>
		</#list>
		<#if matched><#break></#if>
	</#list>

<#if standardSku.price.price?eval gt 0>
		<div class="bg-neutral-8">${standardSku.price.priceFormatted!""}</div>
	<#else>
		${languageUtil.get(locale, "free", "Free")?upper_case}
	</#if>
</div>

<hr />

<p><strong>${languageUtil.get(locale, "help-and-support", "Help and Support")}</strong></p>
<div class="d-flex flex-column mt-4">
	<div class="d-flex">
		<span class="help-and-support-link-icon">
			<@clay["icon"] symbol="document" />
		</span>

		<a class="d-flex w-100 justify-content-between help-and-support-link" href="https://www.liferay.com/en/legal/marketplace-terms-of-service" target="_blank">
			<span class="copy-text ml-1 help-and-support-link">
				${languageUtil.get(locale, "terms-and-conditions", "Terms & Conditions")}
			</span>

			<svg class="link-arrow help-and-support-link-arrow ml-auto" fill="none" height="16" width="16" xmlns="http://www.w3.org/2000/svg">
				<mask height="8" id="arrow" maskUnits="userSpaceOnUse" width="6" x="5" y="4">
					<path d="m6 10.584 2.587-2.587L6 5.41a.664.664 0 1 1 .94-.94L10 7.53c.26.26.26.68 0 .94l-3.06 3.06c-.26.26-.68.26-.94 0a.678.678 0 0 1 0-.946Z" fill="#000" />
				</mask>

				<g mask="url(#arrow)">
					<path fill="var(--neutral-5)" d="M0 0h16v16H0z" />
				</g>
			</svg>
		</a>
	</div>

	<div class="d-flex">
		<span class="help-and-support-link-icon">
			<@clay["icon"] symbol="document" />
		</span>

		<a class="d-flex w-100 justify-content-between help-and-support-link" href="javascript:void(0)" onClick="openModal()">
			<span class="copy-text ml-1 help-and-support-link">
				${languageUtil.get(locale, "publisher-contact-info", "Publisher Contact Info")}
			</span>

			<svg class="link-arrow help-and-support-link-arrow ml-auto" fill="none" height="16" width="16" xmlns="http://www.w3.org/2000/svg">
				<mask height="8" id="arrow" maskUnits="userSpaceOnUse" width="6" x="5" y="4">
					<path d="m6 10.584 2.587-2.587L6 5.41a.664.664 0 1 1 .94-.94L10 7.53c.26.26.26.68 0 .94l-3.06 3.06c-.26.26-.68.26-.94 0a.678.678 0 0 1 0-.946Z" fill="#000" />
				</mask>

				<g mask="url(#arrow)">
					<path fill="var(--neutral-5)" d="M0 0h16v16H0z" />
				</g>
			</svg>
		</a>
	</div>
</div>

<hr />

<p><strong>Share Link</strong></p>
<a class="align-items-center copy-text d-flex font-weight-bold ml-1 text-decoration-none text-primary" href="#copy-share-link" onclick="copyToClipboard(Liferay.ThemeDisplay.getCanonicalURL())">
	<span class="help-and-support-link-icon mr-1">
		<@clay["icon"] symbol="link" />
	</span>
	Copy & Share
</a>

<script>
	function modalBody() {
		return `
		<#if catalogName?has_content>
				<div class="align-items-center d-flex flex-row mb-3">
					<span class="align-items-center bg-light d-flex justify-content-center mr-3 overflow-hidden p-3 rounded-circle">
						<#if solutionHeaderImages?has_content>
							<#list solutionHeaderImages as image>
								<#assign imageSourceSplitedUrl = image.src?split("/o") />

								<#if imageSourceSplitedUrl?has_content>
									<#assign productThumbnail = "/o/${imageSourceSplitedUrl[1]}" />

									<img alt="Slide ${image?index}" class="catalog-icon" src="${productThumbnail}" style="height: 40px; object-fit: contain; width: 40px;">
								</#if>
							</#list>
						<#else>
							<@clay["icon"]
								style="fill:#6B6C7E;"
								symbol="picture"
							/>
						</#if>
					</span>

					<div class="d-flex flex-column">
						<#if catalogName?has_content>
							<h3 class="font-weight-bold mb-0">
								${catalogName}
							</h3>
						</#if>
					</div>
				</div>
		</#if>
		<#if sanitizedUrl?has_content && publisherUrl?has_content>
				<div class="align-items-center d-flex flex-row mb-3">
					<span class="align-items-center bg-light d-flex justify-content-center mr-3 p-3 rounded-circle">
						<@clay["icon"]
							style="fill:#6B6C7E;"
							symbol="globe"
						/>
					</span>

					<div class="d-flex flex-column">
						<span class="text-black-50">${languageUtil.get(locale, "publisher-website", "Publisher Website")}</span>

						<a href="${sanitizedUrl}" target="_blank" class="font-weight-bold">
							${publisherUrl}
						</a>
					</div>
				</div>
			</#if>
		<#if supportEmail?has_content>
				<div class="align-items-center d-flex flex-row mb-3">
					<span class="align-items-center bg-light d-flex justify-content-center mr-3 p-3 rounded-circle">
						<@clay["icon"] style="fill:#6B6C7E;"symbol="envelope-closed" />
					</span>

					<div class="d-flex flex-column">
						<span class="text-black-50">${languageUtil.get(locale, "support-email", "Support Email")}</span>

						<a class="font-weight-bold" href="mailto:${supportEmail}" target="_blank">
							${supportEmail}
						</a>
					</div>
				</div>
		</#if>
		<#if supportPhone?has_content>
				<div class="d-flex flex-row align-items-center mb-3">
					<span class="align-items-center bg-light d-flex justify-content-center mr-3 p-3 rounded-circle">
						<@clay["icon"]
							style="fill:#6B6C7E;"
							symbol="phone"
						/>
					</span>

					<div class="d-flex flex-column">
						<span class="text-black-50">${languageUtil.get(locale, "phone", "Phone")}</span>

						<a class="font-weight-bold" href="tel:${supportPhone}" target="_blank">
							${supportPhone}
						</a>
					</div>
				</div>
		</#if>
		`;
	}

	function openModal() {
		Liferay.Util.openModal({
			bodyHTML: modalBody(),
			center: true,
			headerHTML: "<h2>Publisher Support Contact Info</h2>",
			size: "md"
		});
	}
</script>

<script>
	function copyToClipboard(text) {
		if (navigator && navigator.clipboard && navigator.clipboard.writeText) {
			navigator.clipboard.writeText(text);
			Liferay.Util.openToast({ message: "Copied link to the clipboard" });
		}
	}
</script>