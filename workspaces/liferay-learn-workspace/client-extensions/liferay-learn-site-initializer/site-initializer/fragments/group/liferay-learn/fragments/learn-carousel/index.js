/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */
(function () {
	const MIN_SLIDES_FOR_LOOP = 5;

	function cloneSlidesForLoop(
		swiperWrapper,
		originalSlides,
		qtyOriginalSlides
	) {
		const fragment = document.createDocumentFragment();
		let currentCount = qtyOriginalSlides;

		while (currentCount < MIN_SLIDES_FOR_LOOP) {
			originalSlides.forEach((slide, index) => {
				const clone = slide.cloneNode(true);

				clone.classList.add('is-manual-clone');
				clone.dataset.originalIndex = index;

				clone
					.querySelectorAll('[id]')
					.forEach((element) => element.removeAttribute('id'));

				fragment.appendChild(clone);
			});
			currentCount += qtyOriginalSlides;
		}
		swiperWrapper.appendChild(fragment);
	}

	function prepareSlides(carouselContainer, swiperWrapper) {
		let slides = Array.from(
			carouselContainer.querySelectorAll('.swiper-slide')
		);
		const qtyOriginalSlides = slides.length;
		const isLoopCandidate =
			qtyOriginalSlides > 1 && qtyOriginalSlides < MIN_SLIDES_FOR_LOOP;

		if (isLoopCandidate) {
			cloneSlidesForLoop(swiperWrapper, slides, qtyOriginalSlides);

			slides = Array.from(
				carouselContainer.querySelectorAll('.swiper-slide')
			);
		}
		else {
			slides.forEach((slide, index) => {
				slide.dataset.originalIndex = index;
			});
		}

		return slides;
	}

	function getPaginationConfig(qtyOriginalSlides) {
		return {
			clickable: true,
			el: '.carousel-nav-container-indicators',
			renderBullet(index, className) {
				if (index < qtyOriginalSlides) {
					return `<span class="${className}" role="button" aria-label="Go to slide ${
						index + 1
					}"></span>`;
				}

				return '';
			},
			type: 'bullets',
		};
	}

	function initializeSwiper(qtyOriginalSlides, slides) {
		const isLoop = qtyOriginalSlides > 1;
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
			pagination: getPaginationConfig(qtyOriginalSlides),
			spaceBetween: 16,
		});
	}

	function attachEventListeners(carouselContainer, swiper) {
		carouselContainer.addEventListener('keydown', (event) => {
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

		carouselContainer.addEventListener('mouseenter', () =>
			swiper.autoplay.stop()
		);
		carouselContainer.addEventListener('mouseleave', () =>
			swiper.autoplay.start()
		);
		carouselContainer.addEventListener('focusin', () =>
			swiper.autoplay.stop()
		);
		carouselContainer.addEventListener('focusout', () =>
			swiper.autoplay.start()
		);
	}

	function adjustUIForSingleSlide(
		qtyOriginalSlides,
		nextButton,
		prevButton,
		swiper
	) {
		if (qtyOriginalSlides > 1) {
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

	function setupCarousel() {
		const carouselContainer = document.querySelector(
			'.carousel-main-container'
		);

		if (!carouselContainer) {
			return;
		}

		const swiperWrapper =
			carouselContainer.querySelector('.swiper-wrapper');
		const nextButton = document.querySelector('.carousel-nav-button-next');
		const prevButton = document.querySelector('.carousel-nav-button-prev');
		const liveRegion = document.querySelector('.carousel-live-region');

		const initialSlides = Array.from(
			carouselContainer.querySelectorAll('.swiper-slide')
		);
		const qtyOriginalSlides = initialSlides.length;

		const slides = prepareSlides(carouselContainer, swiperWrapper);
		const swiper = initializeSwiper(qtyOriginalSlides, slides);

		function updateActiveBullet() {
			const bullets = document.querySelectorAll(
				'.carousel-nav-container-indicators .swiper-pagination-bullet'
			);

			bullets.forEach((bullet) =>
				bullet.classList.remove('swiper-pagination-bullet-active')
			);

			const activeIndex = swiper.realIndex % qtyOriginalSlides;

			if (bullets[activeIndex]) {
				bullets[activeIndex].classList.add(
					'swiper-pagination-bullet-active'
				);
				bullets[activeIndex].setAttribute('aria-current', 'true');
			}
		}

		function updateSlideARIA() {
			const realIndex = (swiper.realIndex % qtyOriginalSlides) + 1;

			if (liveRegion) {
				liveRegion.textContent = `Slide ${realIndex} of ${qtyOriginalSlides}.`;
			}

			slides.forEach((slide) => {
				slide.setAttribute('role', 'group');
				slide.setAttribute('aria-roledescription', 'slide');

				const originalIndex = slide.dataset.originalIndex
					? Number.parseInt(slide.dataset.originalIndex, 10) + 1
					: realIndex;

				slide.setAttribute(
					'aria-label',
					`Slide ${originalIndex} of ${qtyOriginalSlides}`
				);
			});
		}

		updateSlideARIA();
		updateActiveBullet();

		swiper.on('slideChange', () => {
			updateSlideARIA();
			updateActiveBullet();
		});

		attachEventListeners(carouselContainer, swiper);
		adjustUIForSingleSlide(
			qtyOriginalSlides,
			nextButton,
			prevButton,
			swiper
		);
	}

	Liferay.on('allPortletsReady', setupCarousel);
})();
