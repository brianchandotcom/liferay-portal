<#assign
	channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${themeDisplay.getScopeGroupId()}'")

	product = restClient.get(
		"/headless-commerce-delivery-catalog/v1.0/channels/" + channel.items[0].id +
		"/products/" + CPDefinition_cProductId.getData() +
		"?accountId=-1&nestedFields=categories,productSpecifications,skus&skus.accountId=-1&skus.currencyCode=USD"
	)

	catalogName = product.catalogName!""
	categories = product.categories![]
	createDate = product.createDate!0
	productSpecifications = product.productSpecifications![]

	liferayVersions = productSpecifications?filter(item -> stringUtil.equals(item.specificationKey, "liferay-version"))
	platformOffering = categories?filter(item -> stringUtil.equals(item.vocabulary?replace(" ", "-"), "marketplace-liferay-platform-offering"))
>

<#assign
	publisherDetailsResponse = restClient.get("/c/publisherdetailses?filter=publisherName eq '${catalogName}'")
	redirectPath = "https://marketplace.liferay.com/e/publisher-details/29282497"
/>

<#if publisherDetailsResponse.items?has_content>
	<#assign
		publisherDetails = publisherDetailsResponse.items[0]
		profileImageURL = publisherDetails.publisherProfileImage?replace("https://", "http://")
	/>
</#if>

<#assign
	appDocumentationURL = getSpecificationValue("appdocumentationurl")
	appInstallationGuideURL = getSpecificationValue("appinstallationguideurl")
	appUsageTerms = getSpecificationValue("appusagetermsurl")
	cpuValue = getSpecificationValue("cpu")
	developerName = getSpecificationValue("developer-name", catalogName)
	publisherURL = (getSpecificationValue("publisherwebsiteurl")?trim?replace(" ", ""))!""
	ramValue = getSpecificationValue("ram")
	sourceCode = getSpecificationValue("source-code-url")
	supportEmail = getSpecificationValue("supportemailaddress")
	supportPhone = getSpecificationValue("supportphone")
	type = getSpecificationValue("type")?lower_case
>
<@section title = languageUtil.get(locale, "developer")>
	<#if publisherDetails?has_content>
		<a class = "bg-neutral-8" href = "${redirectPath}/${publisherDetails.id}">
			${developerName}
		</a>
	<#else>
		<a class = "bg-neutral-8" href = "/?developer-name=${developerName}">
			${developerName}
		</a>
	</#if>
</@section>

<@section title = languageUtil.get(locale, "publisher-date", "Publisher Date")>
	<#if createDate?has_content>
		<#assign parsedDate = createDate?datetime("yyyy-MM-dd'T'HH:mm:ss'Z'") />

		<p>${parsedDate?string("MMMM d, yyyy")}</p>
	</#if>
</@section>

<@section title = languageUtil.get(locale, "deployment-method", "Deployment Method")>
	<#list platformOffering as offering>
		<p>${offering.name}</p>
	</#list>
</@section>

<@section title = languageUtil.get(locale, "app-type", "App Type")>
	<#if type == 'client-extension'> Client Extension </#if>
	<#if type == 'cloud'> Cloud </#if>
	<#if type == 'composite-app'> Composite App </#if>
	<#if type == 'dxp'> DXP </#if>
	<#if type == 'low-code-configuration'> Low Code Configuration </#if>
</@section>

<@section title = languageUtil.get(locale, "version")>
	${getSpecificationValue("latest-version", "1.0.0")}
</@section>

<#if liferayVersions?has_content>
	<@section title = languageUtil.get(locale, "supported-versions", "Supported Versions")>
		<#list liferayVersions as version>
			${version.value}<#if version?has_next>, </#if>
		</#list>
	</@section>
</#if>

<#if cpuValue?has_content>
	<@section title = languageUtil.get(locale, "resource-requirements", "Resource Requirements")>
		<p>
			<#if cpuValue?has_content>
				${cpuValue}
				<#assign cpuNumber = cpuValue?number?default(0) />
				<#if cpuValue?eval gt 1>
					CPUS
				</#if>
				<#if cpuValue?eval lt 2>
					CPU
				</#if>
			</#if>, <#if ramValue?has_content>${ramValue} GB RAM</#if>
		</p>
	</@section>
</#if>

<@section title = languageUtil.get(locale, "standard-price", "Standard Price")>
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
						matched = true
						standardSku = sku
					/>

					<#break>
				</#if>
			</#list>
			<#if matched><#break></#if>
		</#list>

		<#if standardSku.price?? && standardSku.price.price?eval gt 0>
			<div class="bg-neutral-8">${standardSku.price.priceFormatted!""}</div>
		<#else>
			${languageUtil.get(locale, "free", "Free")}
		</#if>
	</div>
</@section>

<@section title = languageUtil.get(locale, "help-and-support", "Help and Support")>
	<div class="d-flex flex-column mt-4">
		<#if (appDocumentationURL?has_content || appInstallationGuideURL?has_content)>
			<div class="d-flex mb-4">
				<span class="support-modal-icon" id="app-documentation">
					<@clay["icon"] symbol="document" />
				</span>

				<a class="d-flex support-modal justify-content-between w-100" href="javascript:void(0)" onClick="openInstallationDocsModal();">
					<span class="ml-1 ">
						${languageUtil.get(locale, "app-documentation", "App Documentation")}
					</span>
				</a>
			</div>
		</#if>

		<div class="d-flex mb-4">
			<span class="support-modal-icon" id="publisher-support">
				<@clay["icon"] symbol="envelope-closed" />
			</span>

			<a class="d-flex justify-content-between support-modal w-100" href="javascript:void(0)" onClick="openPublisherSupportModal();">
				<span class="ml-1 ">
					${languageUtil.get(locale, "publisher-support", "Publisher Support")}
				</span>
			</a>
		</div>

		<#if sourceCode?has_content>
			<div class="d-flex mb-4">
				<span class="support-link-icon">
					<@clay["icon"] symbol="code" />
				</span>

				<a
					class="d-flex justify-content-between support-link w-100"
					href="${sourceCode}"
					target="_blank"
				>
					<span class="ml-1">
						${languageUtil.get(locale, "source-code", "Source Code")}

						<span class="d-none ml-1 support-link-icon-arrow-container">
							<@clay["icon"]
								className="support-link-icon-arrow"
								symbol="tap-ahead"
							/>
						</span>
					</span>
				</a>
			</div>
		</#if>

		<div class="d-flex">
			<span class="support-link-icon">
				<@clay["icon"] symbol="check-square" />
			</span>

			<a
				class="d-flex justify-content-between support-link w-100"
				href="/documents/d/marketplace/end_user_license_agreement-pdf"
				target="_blank">

				<span class="ml-1">
					${languageUtil.get(locale, "eula", "EULA")}

					<span class="d-none support-link-icon-arrow-container ml-1">
						<@clay["icon"]
							className="support-link-icon-arrow"
							symbol="tap-ahead"
						/>
					</span>
				</span>
			</a>
		</div>
	</div>
</@section>

<@section
	showLine = false
	title = languageUtil.get(locale, "share-link")
>
	<a class="align-items-center d-flex font-weight-semi-bold ml-1 support-link text-primary" href="#copy-share-link" onclick="copyToClipboard(Liferay.ThemeDisplay.getCanonicalURL())">
		<span class="link-icon mr-1">
			<@clay["icon"] symbol="link" />
		</span>
		Copy & Share
	</a>
</@section>

<#function getSpecificationValue key default="">
	<#local spec = productSpecifications?filter(productSpecification ->
		stringUtil.equals(productSpecification.specificationKey, key)) />

	<#return (spec?first.value)!default />
</#function>

<#macro section
	title
	showLine=true
>
	<p>
		<strong>${title}</strong>
	</p>

	<div>
		<#nested>
	</div>

	<#if showLine>
		<hr />
	</#if>
</#macro>

<script ${nonceAttribute}>
	function installationDocsModalBody() {
		return `
			<#if appDocumentationURL?has_content>
				<div class="d-flex flex-row align-items-center mb-3">
					<span class="align-items-center d-flex justify-content-center modal-icon-background mr-3" style="background: #E2E2E4; border-radius:50%; height:40px; overflow:hidden; width:40px;">
						<@clay["icon"]
							style="fill:#6B6C7E;"
							symbol="document-code"
						/>
					</span>

					<div class="d-flex flex-column">
						<span class="text-black-50">${languageUtil.get(locale, "app-documentation-url", "App Documentation URL")}</span>

						<a class="font-weight-bold" href="${appDocumentationURL}" target="_blank">
							${appDocumentationURL}
						</a>
					</div>
				</div>
			</#if>

			<#if appInstallationGuideURL?has_content>
				<div class="d-flex flex-row align-items-center mb-4">
					<span class="align-items-center d-flex justify-content-center modal-icon-background mr-3" style="background: #E2E2E4; border-radius:50%; height:40px; overflow:hidden; width:40px;">
						<@clay["icon"]
							style="fill:#6B6C7E;"
							symbol="document"
						/>
					</span>

					<div class="d-flex flex-column">
						<span class="text-black-50">${languageUtil.get(locale, "app-installation-guide-url", "App Installation Guide URL")}</span>

						<a class="font-weight-bold" href="${appInstallationGuideURL}" target="_blank">
							${appInstallationGuideURL}
						</a>
					</div>
				</div>
			</#if>
		`;
	}

	function publisherSupportModalBody() {
		return `
			<div class="align-items-center d-flex flex-row mb-3">
				<span class="align-items-center d-flex justify-content-center modal-icon-background mr-3" style="background: #E2E2E4; border-radius:50%; height:40px; overflow:hidden; width:40px;">
					<#if profileImageURL?? && profileImageURL?length gt 0>
						<img src="${profileImageURL}" alt="Publisher Image" style="width: 100%; height: 100%; object-fit: cover; border-radius: 50%;" />
					<#else>
						<@clay["icon"]
							style="fill:#6B6C7E;"
							symbol="picture"
						/>
					</#if>
				</span>

				<div class="d-flex flex-column">
					<h3 class="font-weight-bold mb-0">
						${catalogName}
					</h3>
				</div>
			</div>

			<#if publisherURL?has_content>
				<div class="align-items-center d-flex flex-row mb-3">
				<span class="align-items-center d-flex justify-content-center modal-icon-background mr-3" style="background: #E2E2E4; border-radius:50%; height:40px; overflow:hidden; width:40px;">
						<@clay["icon"]
							style="fill:#6B6C7E;"
							symbol="globe"
						/>
					</span>

					<div class="d-flex flex-column">
						<span class="text-black-50">${languageUtil.get(locale, "publisher-website", "Publisher Website")}</span>

						<a href="${publisherURL}" target="_blank" class="font-weight-bold">
							${publisherURL}
						</a>
					</div>
				</div>
			</#if>

			<#if supportEmail?has_content>
				<div class="align-items-center d-flex flex-row mb-3">
					<span class="align-items-center d-flex justify-content-center modal-icon-background mr-3" style="background: #E2E2E4; border-radius:50%; height:40px; overflow:hidden; width:40px;">
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
					<span class="align-items-center d-flex justify-content-center modal-icon-background mr-3" style="background: #E2E2E4; border-radius:50%; height:40px; overflow:hidden; width:40px;">
						<@clay["icon"]
							style="fill:#6B6C7E;"
							symbol="phone"
						/>
					</span>

					<div class="d-flex flex-column">
						<span class="text-black-50">${languageUtil.get(locale, "phone")}</span>

						<a class="font-weight-bold" href="tel:${supportPhone}" target="_blank">
							${supportPhone}
						</a>
					</div>
				</div>
			</#if>
		`;
	}

	function openInstallationDocsModal() {
		Liferay.Util.openModal({
			bodyHTML: installationDocsModalBody(),
			center: true,
			headerHTML: "<h2>Installation Guide</h2>",
			size: "md"
		});
	}

	function openPublisherSupportModal() {
		Liferay.Util.openModal({
			bodyHTML: publisherSupportModalBody(),
			center: true,
			headerHTML: "<h2>Publisher Support Info</h2>",
			size: "md"
		});
	}
</script>

<script ${nonceAttribute}>
	function copyToClipboard(text) {
		if (navigator && navigator.clipboard && navigator.clipboard.writeText) {
			navigator.clipboard.writeText(text);

			Liferay.Util.openToast({ message: "Copied link to the clipboard" });
		}
	}
</script>

<style ${nonceAttribute}>
	.support-link,
	.support-modal {
		font-size: 16px;
	}

	.support-link-icon {
		color: #0B5FFF;
	}

	.support-link-icon-arrow {
		height: 12px !important;
		width: 12px !important;
	}

	.support-link:hover,
	.support-modal:hover {
		transform: translateY(-0.75px);
	}

	.support-link:hover .support-link-icon-arrow-container {
		display: inline-block !important;
		transform: rotate(90deg);
	}

	.support-modal-icon,
	.support-modal {
		color: #54555F;
	}

	.support-modal:hover {
		color: #272833;
	}

	.support-svg mask,
	.link-arrow mask {
		mask-type: alpha;
	}
</style>