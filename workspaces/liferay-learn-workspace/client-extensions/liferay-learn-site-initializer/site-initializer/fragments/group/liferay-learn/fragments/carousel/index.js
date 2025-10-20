const nextButton = document.querySelector('.carousel-nav-button-next');
const prevButton = document.querySelector('.carousel-nav-button-prev');

const carouselContainer = document.querySelector('.carousel-main-container');
const liveRegion = document.querySelector('.carousel-live-region');
const slides = Array.from(document.querySelectorAll('.swiper-slide'));

let isAnimating = false;

const initialSlide = (slides.length > 2 ? 1 : 0);

const swiper = new Swiper('.swiper', {
	allowTouchMove: false,
	autoplay: {
		delay: 6000,
		disableOnInteraction: false,
	},
	breakpoints: {
		1440: {
			allowTouchMove: false,
			slidesPerView: 1.5,
		},
		1024: {
			allowTouchMove: true,
			slidesPerView: 1.25,
		},
		0: {
			allowTouchMove: true,
			slidesPerView: 1,
		}
	},
	centeredSlides: true,
	initialSlide: initialSlide,
	loop: false,
	mousewheel: {
		invert: true,
	},
	navigation: {
		nextEl: '.carousel-nav-button-next',
		prevEl: '.carousel-nav-button-prev',
	},
	pagination: {
		el: '.carousel-nav-container-indicators',
		clickable: true,
		type: 'bullets',
	},
	slidesPerView: 1.25,
	spaceBetween: 12,
});

function updateSlideARIA() {
	slides.forEach((slide, index) => {
		slide.setAttribute('role', 'group');
		slide.setAttribute('aria-roledescription', 'slide');
		slide.setAttribute('aria-label', `Slide ${index + 1} of ${slides.length}`);
	});
}
updateSlideARIA();

function announceSlide() {
	if (liveRegion) {
		liveRegion.textContent = `Slide ${swiper.activeIndex + 1} of ${slides.length}.`;
	}
}
swiper.on('slideChange', announceSlide);

carouselContainer.addEventListener('keydown', (e) => {
	switch (e.key) {
		case 'ArrowLeft':
			e.preventDefault();
			swiper.slidePrev();
			break;
		case 'ArrowRight':
			e.preventDefault();
			swiper.slideNext();
			break;
		case 'Home':
			e.preventDefault();
			swiper.slideTo(0);
			break;
		case 'End':
			e.preventDefault();
			swiper.slideTo(slides.length - 1);
			break;
	}
});

carouselContainer.addEventListener('mouseenter', () => swiper.autoplay.stop());
carouselContainer.addEventListener('mouseleave', () => swiper.autoplay.start());
carouselContainer.addEventListener('focusin', () => swiper.autoplay.stop());
carouselContainer.addEventListener('focusout', () => swiper.autoplay.start());

const moveNext = () => {
	if (isAnimating) return;
	isAnimating = true;
	swiper.slideNext();
};

const movePrev = () => {
	if (isAnimating) return;
	isAnimating = true;
	swiper.slidePrev();
};

if (nextButton) nextButton.addEventListener('click', moveNext);
if (prevButton) prevButton.addEventListener('click', movePrev);

swiper.on('slideChangeTransitionEnd', () => {
	isAnimating = false;
});

announceSlide();
