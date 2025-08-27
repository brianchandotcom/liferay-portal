<#assign
	channel = restClient.get(
		"/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${themeDisplay.getScopeGroupId()}'"
	)
	productImagesResponse = restClient.get(
		"/headless-commerce-delivery-catalog/v1.0/channels/" + channel.items[0].id +
		"/products/" + CPDefinition_cProductId.getData() + "/images?accountId=-1"
	)
	productImages = productImagesResponse.items![]
	totalCount = productImagesResponse.totalCount
>

<div class = "carousel-container">
	<div class = "main-image-wrapper">
		<button class = "nav-button prev" aria-label = "Previous Image">
			<span class = "lexicon-icon-overwide"> <@clay["icon"] symbol = "angle-left" />
			</span>
		</button>

		<img id = "main-image" src = "${(productImages[0].src?replace("https://", "http://"))}" alt = "${productImages[0].title?html}" />

		<button class="nav-button next" aria-label="Next Image">
			<span class="lexicon-icon-overwide"> <@clay["icon"] symbol="angle-right" />
			</span>
		</button>
	</div>

	<div class="thumbnails-wrapper">
		<div class="thumbnails">
			<#list productImages as image>
				<#assign imgSrc = (image.src?replace("https://", "http://")) />

				<img class="thumbnail" src="${imgSrc}" alt="${image.title?html}" data-index="${image?index}" />
			</#list>
		</div>

		<button class="view-full-gallery">
			<span class="title">${languageUtil.get(locale, "full-gallery", "Full Gallery")}</span>
			<span class="subtitle">${totalCount} ${languageUtil.get(locale, "photos", "Photos")}</span>
		</button>
	</div>
</div>

<template id="modal-gallery">
<div class="modal-gallery-content">
	<button class="modal-prev" data-role="modal-prev" style="position: absolute; left: 0; top: 50%; transform: translateY(-50%);
		   background: rgba(0,0,0,0.4); border:none; color:white; font-size: 1.6rem;
		   padding: 14px; cursor: pointer; border-radius: 50%; display: flex; align-items: center; justify-content: center;"
>
	  <@clay["icon"] symbol="angle-left" />
	</button>

	<img class="modal-image" data-role="modal-image" style="max-width: 100vh; border-radius: 8px;" />

	<button class="modal-next" data-role="modal-next" style="position: absolute; right: 0; top: 50%; transform: translateY(-50%);
		   background: rgba(0,0,0,0.4); border:none; color:white; font-size: 1.6rem;
		   padding: 14px; cursor: pointer; border-radius: 50%; display: flex; align-items: center; justify-content: center;"
>
	  <@clay["icon"] symbol="angle-right" />
	</button>
</div>
</template>

<script>
	let currentIndex = 0;
	let images = [];

	const carrouselMainImage = document.getElementById('main-image');
	const carouselNextBtn = document.querySelector('.nav-button.next');
	const carouselPrevBtn = document.querySelector('.nav-button.prev');
	const carouselThumbnails = [...document.querySelectorAll('.thumbnail')];
	const fullGalleryBtn = document.querySelector('.view-full-gallery');

	function loadImages() {
		images = [
			<#list productImages as image>
			{
				src: "${(image.src?replace('https://', 'http://'))?js_string}",
				alt: "${image.title?html?js_string}"
			}<#if image_has_next>,</#if>
			</#list>
		];
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
			headerHTML: '<h2 id="modal-header-title">${languageUtil.get(locale, "Image")} <span id="modal-index-display"></span></h2>',
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

	function updateMainImage(index) {
		currentIndex = index;
		carrouselMainImage.src = images[index].src;
		carrouselMainImage.alt = images[index].alt;

		carouselThumbnails.forEach(img => img.classList.remove('selected'));
		if (carouselThumbnails[index]) {
			carouselThumbnails[index].classList.add('selected');
		}

		carouselPrevBtn.disabled = index === 0;
		carouselNextBtn.disabled = index === images.length - 1;
	}

	function setupNavigationButtons() {
		carouselPrevBtn.addEventListener('click', () => {
			if (!carouselPrevBtn.disabled) {
				updateMainImage(currentIndex - 1);
			}
		});

		carouselNextBtn.addEventListener('click', () => {
			if (!carouselNextBtn.disabled) {
				updateMainImage(currentIndex + 1);
			}
		});
	}

	function setupThumbnailClickListeners() {
		carouselThumbnails.forEach((thumb, index) => {
			thumb.addEventListener('click', () => updateMainImage(index));
		});
	}

	function setupModalTriggers() {
		carrouselMainImage.addEventListener('click', () => openModalGallery(currentIndex));
		fullGalleryBtn.addEventListener('click', () => openModalGallery(currentIndex));
	}

	function main() {
		loadImages();
		setupThumbnailClickListeners();
		setupNavigationButtons();
		setupModalTriggers();
		updateMainImage(0);
	}

	main();
</script>

<style>

.carousel-container {
	max-width: 808px;
}

.carousel-container img {
	cursor: pointer;
}

.custom-gallery-modal button:disabled {
	cursor: default;
	opacity: 0.4;
	pointer-events: none;
}

.custom-gallery-modal {
	background-color: #282934 !important;
	border-bottom: none;
	color: white !important;

}

.custom-gallery-modal .liferay-modal-body {
	align-items: center;
	display: flex;
	justify-content: center;
	position: relative;
}

.custom-gallery-modal .close{
	color: white !important;
}

.lexicon-icon-overwide .lexicon-icon{
	height: 2em;
	margin: 0px;
}

.main-image-wrapper {
	position: relative;
}

.main-image-wrapper img {
	border-radius: 8px;
	max-height: 454px;
	width: 808px;
}

.modal-image {
  max-width: 100vh;
  border-radius: 8px;
}

.modal-prev,
.modal-next {
  align-items: center;
  background: rgba(0, 0, 0, 0.4);
  border: none;
  border-radius: 50%;
  color: white;
  cursor: pointer;
  display: flex;
  font-size: 1.6rem;
  justify-content: center;
  padding: 14px;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}

.modal-prev {
  left: 0;
}

.modal-next {
  right: 0;
}


.nav-button {
	background: rgba(0,0,0,0.4);
	border: none;
	border-radius: 50%;
	color: white;
	cursor: pointer;
	font-size: 1.3rem;
	opacity: 0;
	position: absolute;
	padding: 0 10px;
	top: 50%;
	transform: translateY(-50%);
	transition: opacity 0.3s ease;
	user-select: none;
}

.main-image-wrapper:hover .nav-button {
	opacity: 1;
	pointer-events: auto;
}

.main-image-wrapper:hover .nav-button:disabled{
	cursor: default;
	opacity: 0.4;
}

.nav-button.prev {
	left: 10px;
}

.nav-button.next {
	right: 10px;
}

.thumbnails-wrapper {
	align-items: center;
	display: flex;
	justify-content: flex-start;
	margin-top: 12px;
	max-height: 86px;
	max-width: 808px;
}

.view-full-gallery {
	margin-left: 8px;
}

.thumbnails {
	display: flex;
	gap: 8px;
	overflow-x: auto;
}

.thumbnail {
	border: 2px solid transparent;
	border-radius: 12px;
	cursor: pointer;
	height: 86px;
	object-fit: cover;
	opacity: 0.6;
	transition: opacity 0.3s ease;
	width: 152px;
}

.thumbnail.selected {
	border-color: #8FB5FF;
	opacity: 1;
}

.view-full-gallery {
	background-color: white;
	border: 1px solid #E2E2E4;
	border-radius: 12px;
	color: #2563eb;
	cursor: pointer;
	display: flex;
	flex-direction: column;
	height: 86px;
	justify-content: center;
	min-width: 152px;
	transition: background-color 0.3s ease, box-shadow 0.3s ease;
}

.view-full-gallery:hover {
	background-color: #f3f4f6;
	box-shadow: 0 2px 4px rgb(0 0 0 / 0.1);
}

.view-full-gallery .title {
	font-weight: 600;
	font-size: 16px;
	line-height: 1;
	margin-bottom: 4px;
}

.view-full-gallery .subtitle {
	color: #6b7280;
	font-weight: 400;
	font-size: 12px;
	line-height: 1;
}
</style>