<style>
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

	.carousel-inner {
		border-radius: 10px;
		height: 353px;
		overflow: hidden;
		width: 682.99px;
	}

	.carousel-control-next, .carousel-control-prev {
		border-radius: 10px;
		transition: all .3s ease-in-out;
	}

	.carousel-control-next:hover, .carousel-control-prev:hover {
		background: rgb(0, 0, 0);
	}

	.carousel-control-prev:hover {
		background: linear-gradient(90deg, rgba(0, 0, 0, 0.3) 0%, rgba(255, 255, 255, 0) 90%);
		border-radius: 10px;
		transition: all .3s ease-in-out;
	}

	.carousel-control-next:hover {
		background: linear-gradient(-90deg, rgba(0, 0, 0, 0.3) 0%, rgba(255, 255, 255, 0) 90%);
		border-radius: 10px;
		transition: all .3s ease-in-out;
	}

	.carousel-indicators li {
		border-radius: 50%;
		border: solid 1px rgba(0, 0, 0, 0.5) !important;
		height: 10px !important;
		width: 10px !important;
	}
</style>

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
/>

<#if product.productSpecifications?has_content>
	<#assign
		productSpecifications = product.productSpecifications

		blocksSpecification = productSpecifications?filter(specification -> specification.specificationKey == "solution-details-blocks")
	/>

	<#if blocksSpecification?has_content>
		<#assign blocks = blocksSpecification[0].value?eval />
	</#if>
</#if>

<#macro blockHeader
	description
	title
>
	<div>
		<h2>
			${title}
		</h2>

		<p>
			${description}
		</p>
	</div>
</#macro>

<#macro videoPreview
	videoUrl
>
	<#if videoUrl?has_content>
		<#assign videoUrlSplited = videoUrl?split("v=")[1] />

		<#if videoUrlSplited?has_content>
			<#assign youtubeVideoId = videoUrlSplited?split("&")[0] />

			<a href="${videoUrl}" target="_blank">
				<div class="align-items-center d-flex justify-content-center position-relative video-preview">
					<img
						aria-label="video-thumbnail"
						class="video-thumbnail"
						src="https://img.youtube.com/vi/${youtubeVideoId}/0.jpg"
					/>

					<div class="position-absolute video-thumbnail-play-symbol">
						<@clay["icon"] symbol="video" />
					</div>
				</div>
			</a>
		</#if>
	</#if>
</#macro>

<#macro productCarrousel
	carouselId
	files
>
	<#if files?has_content>
		<div id="${carouselId}" class="carousel slide" data-ride="carousel">
			<ol class="carousel-indicators">
				<#list files as fileImage>
					<li data-target="#carouselExampleIndicators" data-slide-to="${fileImage?index}" class="<#if fileImage?index == 0>active</#if> border mx-1"></li>
				</#list>
			</ol>

			<div class="align-items-center carousel-inner d-flex">
				<#list files as fileImage>
					<#assign fileImageSourceUrlSplited = fileImage.src?split("/o") />

					<#if fileImageSourceUrlSplited?has_content>
						<#assign productThumbnail = "/o/${fileImageSourceUrlSplited[1]}" />

						<div class="carousel-item<#if fileImage?index == 0> active</#if>">
							<img alt="Slide ${fileImage?index}" class="d-block w-100" src="${productThumbnail}">
						</div>
					</#if>
				</#list>
			</div>

			<a class="carousel-control-prev" href="#${carouselId}" role="button" data-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a>

			<a class="carousel-control-next" href="#${carouselId}" role="button" data-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
	</#if>
</#macro>

<#if blocks?has_content>
	<#list blocks as block>
		<div class="block-container">
			<div class="block-content">
				<div class="align-items-center container d-flex flex-column">
					<#if block.type == "text-block">
						<@blockHeader
							description = block.content.description
							title = block.content.title
						/>
					</#if>

					<#if block.type == "text-images-block">
						<div class="d-flex">
							<div class="mr-5">
								<@blockHeader
									description = block.content.description
									title = block.content.title
								/>
							</div>

							<#if productImage?has_content && block.content.files?has_content>
								<#assign
									blockFilesImageId = block.content.files
									allFiles = productImage?filter(file ->blockFilesImageId?seq_contains(file.externalReferenceCode))
								/>

								<@productCarrousel
									carouselId = "${block.type}${block?index}"
									files = allFiles
								/>
							</#if>
						</div>
					</#if>

					<#if block.type == "text-video-block">
						<div class="d-flex">
							<div class="mr-5">
								<@blockHeader
									description = block.content.description
									title = block.content.title
								/>
							</div>

							<@videoPreview videoUrl = block.content.videoUrl />
						</div>
					</#if>
				</div>
			</div>
		</div>
	</#list>
</#if>