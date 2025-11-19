/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */
Liferay.on('allPortletsReady', () => {
	const carouselContainer = document.querySelector(
		'.carousel-main-container'
	);

	if (!carouselContainer) {
		return;
	}

	const swiperWrapper = carouselContainer.querySelector('.swiper-wrapper');
	const nextButton = document.querySelector('.carousel-nav-button-next');
	const prevButton = document.querySelector('.carousel-nav-button-prev');
	const liveRegion = document.querySelector('.carousel-live-region');
	let slides = Array.from(
		carouselContainer.querySelectorAll('.swiper-slide')
	);

	const qtyOriginalSlides = slides.length;
	const MIN_SLIDES_FOR_LOOP = 5;

	if (qtyOriginalSlides > 1 && qtyOriginalSlides < MIN_SLIDES_FOR_LOOP) {
		const frag = document.createDocumentFragment();

		let currentCount = qtyOriginalSlides;

		while (currentCount < MIN_SLIDES_FOR_LOOP) {
			slides.forEach((slide, index) => {
				const clone = slide.cloneNode(true);

				clone.classList.add('is-manual-clone');
				clone.dataset.originalIndex = index;

				clone
					.querySelectorAll('[id]')
					.forEach((element) => element.removeAttribute('id'));

				frag.appendChild(clone);
			});
			currentCount += qtyOriginalSlides;
		}
		swiperWrapper.appendChild(frag);

		slides = Array.from(
			carouselContainer.querySelectorAll('.swiper-slide')
		);
	}
	else {
		slides.forEach((slide, index) => {
			slide.dataset.originalIndex = index;
		});
	}

	const initialSlide = slides.length > 2 ? 1 : 0;
	const isLoop = qtyOriginalSlides > 1;

	const swiper = new globalJS.Swiper('.swiper', {
		allowTouchMove: true,
		autoplay: {
			delay: 6000,
			disableOnInteraction: false,
		},
		breakpoints: {
			0: {
				slidesPerView: 1,
			},
			1024: {
				slidesPerView: 1.15,
			},
			1440: {
				slidesPerView: 1.15,
			},
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
		pagination: {
			clickable: true,
			el: '.carousel-nav-container-indicators',
			renderBullet(index, className) {
				if (index < qtyOriginalSlides) {
					return (
						'<span class="' +
						className +
						'" role="button" aria-label="Go to slide ' +
						(index + 1) +
						'"></span>'
					);
				}

				return '';
			},
			type: 'bullets',
		},
		spaceBetween: 16,
	});

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

	if (qtyOriginalSlides <= 1) {
		swiper.autoplay.stop();

		if (nextButton) {
			nextButton.style.display = 'none';
		}
		if (prevButton) {
			prevButton.style.display = 'none';
		}

		const pagination = document.querySelector(
			'.carousel-nav-container-indicators'
		);
		if (pagination) {
			pagination.style.display = 'none';
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

	swiper.on('slideChange', () => {
		updateSlideARIA();
		updateActiveBullet();
	});

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
	carouselContainer.addEventListener('focusin', () => swiper.autoplay.stop());
	carouselContainer.addEventListener('focusout', () =>
		swiper.autoplay.start()
	);
});
