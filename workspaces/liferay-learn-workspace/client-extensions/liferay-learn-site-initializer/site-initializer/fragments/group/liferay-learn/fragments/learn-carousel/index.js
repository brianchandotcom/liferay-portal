/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */
(function () {
	function addEventListeners(container, swiper) {
		container.addEventListener('focusin', () => swiper.autoplay.stop());
		container.addEventListener('focusout', () => swiper.autoplay.start());

		container.addEventListener('keydown', (event) => {
			switch (event.key) {
				case 'ArrowLeft':
					event.preventDefault();
					swiper.slidePrev();
					break;
				case 'ArrowRight':
					event.preventDefault();
					swiper.slideNext();
					break;
				default:
					break;
			}
		});

		container.addEventListener('mouseenter', () => swiper.autoplay.stop());
		container.addEventListener('mouseleave', () => swiper.autoplay.start());
	}

	function cloneSlides(slides, slideCount, wrapper) {
		const fragment = document.createDocumentFragment();
		let total = slideCount;

		while (total < 5) {
			slides.forEach((slide, index) => {
				const clone = slide.cloneNode(true);

				clone.classList.add('clone');
				clone.dataset.originalIndex = index;

				clone
					.querySelectorAll('[id]')
					.forEach((element) => element.removeAttribute('id'));

				fragment.appendChild(clone);
			});

			total += slideCount;
		}

		wrapper.appendChild(fragment);
	}

	function getPagination(slideCount) {
		return {
			clickable: true,
			el: '.carousel-nav-container-indicators',
			renderBullet(index, className) {
				if (index < slideCount) {
					return `<span aria-label="Go to slide ${index + 1}" class="${className}" role="button"></span>`;
				}

				return '';
			},
			type: 'bullets',
		};
	}

	function initializeSwiper(slides, slideCount) {
		const isLoop = slideCount > 1;
		const initialSlide = slides.length > 2 ? 1 : 0;

		return new globalJS.Swiper('.swiper', {
			allowTouchMove: true,
			autoplay: {
				delay: 6000,
				disableOnInteraction: false,
			},
			breakpoints: {
				0: {slidesPerView: 1},
				1024: {slidesPerView: 1.15},
				1440: {slidesPerView: 1.15},
			},
			centeredSlides: true,
			initialSlide,
			loop: isLoop,
			mousewheel: {
				invert: true,
			},
			navigation: {
				nextEl: '.carousel-nav-button-next',
				prevEl: '.carousel-nav-button-prev',
			},
			pagination: getPagination(slideCount),
			spaceBetween: 16,
		});
	}

	function prepareSlides(container, wrapper) {
		const slides = Array.from(container.querySelectorAll('.swiper-slide'));

		const slideCount = slides.length;

		if (slideCount > 1 && slideCount < 5) {
			cloneSlides(slides, slideCount, wrapper);

			return Array.from(container.querySelectorAll('.swiper-slide'));
		}

		slides.forEach((slide, index) => {
			slide.dataset.originalIndex = index;
		});

		return slides;
	}

	function setupCarousel() {
		const carouselMainContainer = document.querySelector(
			'.carousel-main-container'
		);

		if (!carouselMainContainer) {
			return;
		}

		const initialSlides = Array.from(
			carouselMainContainer.querySelectorAll('.swiper-slide')
		);
		const swiperWrapper =
			carouselMainContainer.querySelector('.swiper-wrapper');

		const slides = prepareSlides(carouselMainContainer, swiperWrapper);

		const swiper = initializeSwiper(slides, initialSlides.length);

		updateSlides(initialSlides, slides, swiper);

		swiper.on('slideChange', () => {
			updateSlides(initialSlides, slides, swiper);
		});

		addEventListeners(carouselMainContainer, swiper);
	}

	function updateSlides(initialSlides, slides, swiper) {
		const bullets = document.querySelectorAll(
			'.carousel-nav-container-indicators .swiper-pagination-bullet'
		);

		bullets.forEach((bullet) =>
			bullet.classList.remove('swiper-pagination-bullet-active')
		);

		const activeIndex = swiper.realIndex % initialSlides.length;

		if (bullets[activeIndex]) {
			bullets[activeIndex].classList.add(
				'swiper-pagination-bullet-active'
			);
			bullets[activeIndex].setAttribute('aria-current', 'true');
		}

		const realIndex = (swiper.realIndex % initialSlides.length) + 1;

		const carouselLiveRegion = document.querySelector(
			'.carousel-live-region'
		);

		if (carouselLiveRegion) {
			carouselLiveRegion.textContent = `Slide ${realIndex} of ${initialSlides.length}.`;
		}

		slides.forEach((slide) => {
			slide.setAttribute('aria-roledescription', 'slide');
			slide.setAttribute('role', 'group');

			const originalIndex = slide.dataset.originalIndex
				? Number.parseInt(slide.dataset.originalIndex, 10) + 1
				: realIndex;

			slide.setAttribute(
				'aria-label',
				`Slide ${originalIndex} of ${initialSlides.length}`
			);
		});
	}

	Liferay.on('allPortletsReady', setupCarousel);
})();
