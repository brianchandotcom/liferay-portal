/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */
(function () {
	function addEventListeners(container, swiper) {
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
		container.addEventListener('focusin', () => swiper.autoplay.stop());
		container.addEventListener('focusout', () => swiper.autoplay.start());
	}

	function adjustUIForSingleSlide(
		nextButton,
		prevButton,
		slideCount,
		swiper
	) {
		if (slideCount > 1) {
			return;
		}

		swiper.autoplay.stop();

		const elementsToHide = [
			nextButton,
			prevButton,
			document.querySelector('.carousel-nav-container-indicators'),
		];

		elementsToHide.forEach((element) => {
			if (element) {
				element.style.display = 'none';
			}
		});
	}

	function cloneSlidesForLoop(slides, slideCount, wrapper) {
		const docFragment = document.createDocumentFragment();
		let currentCount = slideCount;

		while (currentCount < 5) {
			slides.forEach((slide, index) => {
				const clone = slide.cloneNode(true);

				clone.classList.add('clone');
				clone.dataset.originalIndex = index;

				clone
					.querySelectorAll('[id]')
					.forEach((element) => element.removeAttribute('id'));

				docFragment.appendChild(clone);
			});

			currentCount += slideCount;
		}

		wrapper.appendChild(docFragment);
	}

	function getPaginationConfig(slideCount) {
		return {
			clickable: true,
			el: '.carousel-nav-container-indicators',
			renderBullet(index, className) {
				if (index < slideCount) {
					return `<span class="${className}" role="button" aria-label="Go to slide ${
						index + 1
					}"></span>`;
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
			pagination: getPaginationConfig(slideCount),
			spaceBetween: 16,
		});
	}

	function prepareSlides(container, wrapper) {
		let slides = Array.from(container.querySelectorAll('.swiper-slide'));

		const slideCount = slides.length;

		if (slideCount > 1 && slideCount < 5) {
			cloneSlidesForLoop(slides, slideCount, wrapper);

			slides = Array.from(container.querySelectorAll('.swiper-slide'));
		}
		else {
			slides.forEach((slide, index) => {
				slide.dataset.originalIndex = index;
			});
		}

		return slides;
	}

	function setupCarousel() {
		const carouselContainer = document.querySelector(
			'.carousel-main-container'
		);

		if (!carouselContainer) {
			return;
		}

		const initialSlides = Array.from(
			carouselContainer.querySelectorAll('.swiper-slide')
		);
		const liveRegion = document.querySelector('.carousel-live-region');
		const nextButton = document.querySelector('.carousel-nav-button-next');
		const prevButton = document.querySelector('.carousel-nav-button-prev');
		const swiperWrapper =
			carouselContainer.querySelector('.swiper-wrapper');

		const originalSlideCount = initialSlides.length;
		const slides = prepareSlides(carouselContainer, swiperWrapper);

		const swiper = initializeSwiper(slides, originalSlideCount);

		function updateActiveBullet() {
			const bullets = document.querySelectorAll(
				'.carousel-nav-container-indicators .swiper-pagination-bullet'
			);

			bullets.forEach((bullet) =>
				bullet.classList.remove('swiper-pagination-bullet-active')
			);

			const activeIndex = swiper.realIndex % originalSlideCount;

			if (bullets[activeIndex]) {
				bullets[activeIndex].classList.add(
					'swiper-pagination-bullet-active'
				);
				bullets[activeIndex].setAttribute('aria-current', 'true');
			}
		}

		function updateSlideARIA() {
			const realIndex = (swiper.realIndex % originalSlideCount) + 1;

			if (liveRegion) {
				liveRegion.textContent = `Slide ${realIndex} of ${originalSlideCount}.`;
			}

			slides.forEach((slide) => {
				slide.setAttribute('role', 'group');
				slide.setAttribute('aria-roledescription', 'slide');

				const originalIndex = slide.dataset.originalIndex
					? Number.parseInt(slide.dataset.originalIndex, 10) + 1
					: realIndex;

				slide.setAttribute(
					'aria-label',
					`Slide ${originalIndex} of ${originalSlideCount}`
				);
			});
		}

		updateSlideARIA();
		updateActiveBullet();

		swiper.on('slideChange', () => {
			updateSlideARIA();
			updateActiveBullet();
		});

		addEventListeners(carouselContainer, swiper);
		adjustUIForSingleSlide(
			nextButton,
			prevButton,
			originalSlideCount,
			swiper
		);
	}

	Liferay.on('allPortletsReady', setupCarousel);
})();
