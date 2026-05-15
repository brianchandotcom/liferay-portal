<#assign
	channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=siteGroupId eq '${themeDisplay.getScopeGroupId()}'")

	productImagesResponse = restClient.get(
		"/headless-commerce-delivery-catalog/v1.0/channels/" + channel.items[0].id +
		"/products/" + CPDefinition_cProductId.getData() + "/images?accountId=-1"
	)

	productImages = productImagesResponse.items![]
	filteredProductImages = []
>

<#list productImages as image>
	<#if image.galleryEnabled>
		<#assign filteredProductImages += [image] />
	</#if>
</#list>

<#assign totalCount = filteredProductImages?size />

<div class = "carousel-container">
	<div class = "main-image-wrapper">
		<button class = "nav-button prev" aria-label = "Previous Image">
			<span class = "lexicon-icon-overwide"> <@clay["icon"] symbol = "angle-left" /></span>
		</button>

		<img alt = "${filteredProductImages[0].title?html}" id = "main-image" src = "${(filteredProductImages[0].src?replace("https://", "http://"))}" />

		<button class="nav-button next" aria-label="Next Image">
			<span class="lexicon-icon-overwide"> <@clay["icon"] symbol="angle-right" /></span>
		</button>
	</div>

	<div class="thumbnails-wrapper">
		<div class="align-items-center thumbnails"></div>

		<#assign count = (totalCount?default(0)?number) />

		<#if count gt 5>
			<button class="view-full-gallery">
				<span class="title">
					${languageUtil.get(locale, "full-gallery", "Full Gallery")}
				</span>
				<span class="subtitle">
					${count} ${languageUtil.get(locale, "photos", "Photos")}
				</span>
			</button>
		</#if>
		</div>
	</div>

<template id="modal-gallery">
	<div class="modal-gallery-content">
		<button class="modal-prev" data-role="modal-prev">
			<@clay["icon"] symbol="angle-left" />
		</button>

		<img class="modal-image" data-role="modal-image" />

		<button class="modal-next" data-role="modal-next">
			<@clay["icon"] symbol="angle-right" />
		</button>
	</div>
</template>

<script ${nonceAttribute}>
(function () {
	let currentIndex = 0;
	let images = [];

	const carouselNextBtn = document.querySelector('.nav-button.next');
	const carouselPrevBtn = document.querySelector('.nav-button.prev');
	const carouselMainImage = document.getElementById('main-image');
	const thumbnailsContainer = document.querySelector('.thumbnails');
	const viewFullGalleryBtn = document.querySelector('.view-full-gallery');

	function loadImages() {
		images = [
			<#list filteredProductImages as image>
			{
				src: "${(image.src?replace('https://', 'http://'))?js_string}",
				alt: "${image.title?html?js_string}"
			}<#if image_has_next>,</#if>
			</#list>
		]
	}

	function renderThumbnails() {
		const maxVisible = 5;
		let start = currentIndex - 2;

		if (start < 0) start = 0;
		if (start > images.length - maxVisible) start = Math.max(images.length - maxVisible, 0);

		const end = Math.min(images.length, start + maxVisible);

		thumbnailsContainer.innerHTML = '';

		for (let i = start; i < end; i++) {
			const img = document.createElement('img');
			img.className = 'thumbnail' + (i === currentIndex ? ' selected' : '');
			img.src = images[i].src;
			img.alt = images[i].alt;
			img.dataset.index = i;
			img.addEventListener('click', () => updateMainImage(i));
			thumbnailsContainer.appendChild(img);
		}
	}

	function updateMainImage(index) {
		currentIndex = index;
		carouselMainImage.src = images[index].src;
		carouselMainImage.alt = images[index].alt;

		carouselPrevBtn.disabled = index === 0;
		carouselNextBtn.disabled = index === images.length - 1;

		renderThumbnails();
	}

	function setupNavigationButtons() {
		carouselPrevBtn.addEventListener('click', () => {
			if (currentIndex > 0) updateMainImage(currentIndex - 1);
		});

		carouselNextBtn.addEventListener('click', () => {
			if (currentIndex < images.length - 1) updateMainImage(currentIndex + 1);
		});
	}

	function setupModalTriggers() {
		carouselMainImage.addEventListener('click', () => openModalGallery(currentIndex));
		if (viewFullGalleryBtn) {
				viewFullGalleryBtn.addEventListener('click', () => openModalGallery(currentIndex));
		}
	}

	function openModalGallery(startIndex) {
		let current = startIndex;

		const template = document.getElementById('modal-gallery');
		const clone = template.content.cloneNode(true);
		const container = document.createElement('div');
		container.appendChild(clone);

		Liferay.Util.openModal({
			bodyHTML: container.innerHTML,
			center: true,
			headerHTML: '<h2 class="modal-gallery-header" id="modal-header-title"><@clay["icon"] symbol="picture"/> ${languageUtil.get(locale, "Image")} <span id="modal-index-display"></span></h2>',
			size: "full-screen",
			onOpen: () => {
				const modalContainer = document.querySelector('.modal-content');
				if (modalContainer) {
					modalContainer.classList.add('custom-gallery-modal');
				}

				const modalImage = document.querySelector('[data-role="modal-image"]');
				const modalNext = document.querySelector('[data-role="modal-next"]');
				const modalPrev = document.querySelector('[data-role="modal-prev"]');
				const indexDisplay = document.getElementById('modal-index-display');

				function updateModalImage(index) {
					const img = images[index];
					modalImage.src = img.src;
					modalImage.alt = img.alt;

					modalNext.disabled = index === images.length - 1;
					modalPrev.disabled = index === 0;

					if (indexDisplay) {
						indexDisplay.textContent = (index + 1) + ' ${languageUtil.get(locale, "of")} ' + images.length;
					}
				}

				modalNext.addEventListener('click', () => {
					if (current < images.length - 1) {
						current++;
						updateModalImage(current);
					}
				});

				modalPrev.addEventListener('click', () => {
					if (current > 0) {
						current--;
						updateModalImage(current);
					}
				});

				updateModalImage(current);
			}
		});
	}

	function main() {
		loadImages();
		setupNavigationButtons();
		setupModalTriggers();
		updateMainImage(0);
	}

	main();
})();
</script>