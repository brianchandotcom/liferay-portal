<style>
	.catalog-icon {
		height: 90px;
		width: 90px;
	}

	.company-description-container {
		width: 846px;
	}

	.company-description-icons-container {
		border-radius: 10px;
		border: solid 1.5px #E2E2E4;
		width: 846px;
	}

	.company-icon {
		height: 48px;
		width: 48px;
	}
</style>

<#if themeDisplay?has_content>
	<#assign signedIn = themeDisplay.isSignedIn() />
</#if>

<#if (CPDefinition_cProductId.getData())??>
	<#assign productId = CPDefinition_cProductId.getData() />
</#if>

<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<#assign channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${scopeGroupId}'") />

<#if channel?has_content>
	<#assign channelId = channel.items[0].id />
</#if>

<#assign
	productId = CPDefinition_cProductId.getData()

	product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&images.accountId=-1&nestedFields=categories,images,productSpecifications")

	catalogName = product.catalogName
	productImage = product.images![]

	solutionHeaderImages = productImage?filter(image -> image.tags?seq_contains("solution-profile-app-icon"))
/>

<#if product.productSpecifications?has_content>
	<#assign
		productSpecifications = product.productSpecifications

		companyDescriptionSpecification = productSpecifications?filter(specification -> specification.specificationKey == "solution-company-description")
		companyEmailSpecification = productSpecifications?filter(specification -> specification.specificationKey == "solution-company-email")
		companyPhoneSpecification = productSpecifications?filter(specification -> specification.specificationKey == "solution-company-phone")
		companyWebsiteSpecification = productSpecifications?filter(specification -> specification.specificationKey == "solution-company-website")
	/>

	<#if companyDescriptionSpecification?has_content>
		<#assign
			companyDescription = companyDescriptionSpecification[0].value
			companyEmail = companyEmailSpecification[0].value
			companyPhone = companyPhoneSpecification[0].value
			companyWebsite = companyWebsiteSpecification[0].value
		/>
	</#if>

	<#if hasVideo?has_content>
		<#assign headerVideoUrl = hasVideo[0].value />
	</#if>
</#if>

<#if catalogName?has_content && companyDescription?has_content>
	<div class="block-container">
		<div class="align-items-center container d-flex flex-column">
			<#if solutionHeaderImages?has_content>
				<#list solutionHeaderImages as image>
					<#assign imageSourceSplitedUrl = image.src?split("/o") />

					<#if imageSourceSplitedUrl?has_content>
						<#assign productThumbnail = "/o/${imageSourceSplitedUrl[1]}" />

						<img alt="Slide ${image?index}" class="catalog-icon mb-8" src="${productThumbnail}">
					</#if>
				</#list>
			</#if>

			<h1 class="mb-6">
				${catalogName}
			</h1>

			<div class="company-description-container mb-6">
				${companyDescription}
			</div>

			<div class="bg-white company-description-icons-container d-flex px-8 py-7<#if signedIn?string("yes", "no") == "no"> justify-content-center</#if><#if signedIn?string("yes", "no") == "yes"> justify-content-between</#if>">
				<#if companyWebsite?has_content>
					<div class="d-flex flex-row">
						<div class="align-items-center d-flex mr-2">
							<img class="company-icon" aria-label="video-thumbnail" src="/documents/d/marketplace/lr_icon_063-png"></img>
						</div>

						<div class="d-flex flex-column">
							<h2 class="m-0">Website</h2>

							<a class="font-weight-bold" href="https://${companyWebsite}" target="_blank">
								${companyWebsite}
							</a>
						</div>
					</div>
				</#if>

				<#if signedIn?string("yes", "no") == "yes">
					<#if companyEmail?has_content>
						<div class="d-flex flex-row">
							<div class="align-items-center d-flex mr-2">
								<img class="company-icon" aria-label="video-thumbnail" src="/documents/d/marketplace/lr_icon_184-png"></img>
							</div>

							<div class="d-flex flex-column">
								<h2 class="m-0">Email</h2>

								<a class="font-weight-bold" href="mailto:${companyEmail}" target="_blank">
									${companyEmail}
								</a>
							</div>
						</div>
					</#if>

					<#if companyPhone?has_content>
						<div class="d-flex flex-row">
							<div class="align-items-center d-flex mr-2">
								<img class="company-icon" aria-label="video-thumbnail" src="/documents/d/marketplace/lr_icon_185-png"></img>
							</div>

							<div class="d-flex flex-column">
								<h2 class="m-0">Phone</h2>

								<a class="font-weight-bold" href="tel:${companyPhone}" target="_blank">
									${companyPhone}
								</a>
							</div>
						</div>
					</#if>
				</#if>
			</div>
		</div>
	</div>
</#if>