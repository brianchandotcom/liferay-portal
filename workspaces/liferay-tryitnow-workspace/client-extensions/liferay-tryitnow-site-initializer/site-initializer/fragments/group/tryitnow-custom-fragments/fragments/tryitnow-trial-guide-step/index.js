/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {displayToasts, stepNumber} = configuration;

function findDropArea(root) {
	const container = root.querySelector('.trial-step__body-inner') || root;

	const dropzoneTag = container.querySelector('lfr-drop-zone');
	const editorEmpty = container.querySelector(
		'.page-editor__no-fragments-state, .-page-editor__no-fragments-state'
	);
	const editorZone = container.querySelector(
		'[data-lfr-drop-zone-id], .page-editor__drop-zone'
	);

	return {container, dropzoneTag, editorEmpty, editorZone};
}

function isDropAreaEmpty(root) {
	const {container, dropzoneTag, editorEmpty, editorZone} =
		findDropArea(root);

	if (layoutMode === 'edit') {
		if (editorEmpty) {
			return true;
		}

		if (editorZone) {
			const children = Array.from(editorZone.children).filter(
				(targetElement) =>
					!targetElement.classList.contains(
						'page-editor__no-fragments-state'
					) &&
					!targetElement.classList.contains(
						'-page-editor__no-fragments-state'
					)
			);

			return !children.length;
		}

		const hasRealContent = Array.from(container.children).some(
			(targetElement) =>
				targetElement.nodeType === 1 &&
				!targetElement.classList.contains('trial-step__callToAction')
		);

		return !hasRealContent;
	}

	if (dropzoneTag) {
		return true;
	}

	const hasRealContent = Array.from(container.children).some(
		(targetElement) =>
			targetElement.nodeType === 1 &&
			!targetElement.classList.contains('trial-step__callToAction')
	);

	return !hasRealContent;
}

function watchDropArea(root) {
	const {container} = findDropArea(root);

	if (!container) {
		return;
	}

	const apply = () =>
		root.classList.toggle('is-empty', isDropAreaEmpty(root));

	apply();

	const mutationObserver = new MutationObserver(apply);

	mutationObserver.observe(container, {
		attributes: true,
		characterData: true,
		childList: true,
		subtree: true,
	});
}

function toast({error, message, type = 'success'}) {
	if (configuration.displayToasts && Liferay?.Util?.openToast) {
		Liferay.Util.openToast({
			message,
			toastProps: {autohide: true},
			type,
		});
	}
}

async function fetchJSON(url, options) {
	const response = await Liferay.Util.fetch(url, {
		headers: {
			'Content-Type': 'application/json',
			'Accept': 'application/json',
		},
		...options,
	});

	if (!response.ok) {
		throw new Error(`HTTP ${res.status}`);
	}

	return response.json();
}

function setLoading(targetElement, isLoading) {
	targetElement?.classList.toggle('is-loading', !!isLoading);
	const spinner =
		targetElement?.querySelector('[data-role^="spinner"]') ||
		targetElement?.querySelector('.trial-step__spinner');

	spinner?.classList.toggle('is-hidden', !isLoading);
}

function hide(targetElement) {
	targetElement?.classList.add('is-hidden');
}

function show(targetElement) {
	targetElement?.classList.remove('is-hidden');
}

function setField(root, key, value) {
	const targetElement = root.querySelector(
		`.trial-step__field[data-field="${key}"]`
	);
	if (!targetElement) {
		return;
	}
	targetElement.textContent = value ?? '';
}

function hydrateFromModel(root, model) {
	setField(root, 'stepNumber', model.stepNumber);
	setField(root, 'title', model.title);
	setField(
		root,
		'timeToComplete',
		model.timeToComplete ?? model.timeToRead ?? ''
	);
	setField(root, 'callToActionText', model.callToActionText ?? '');

	const callToAction = root.querySelector('.trial-step__callToAction');

	if (callToAction) {
		if (model.callToActionHref) {
			callToAction.setAttribute('href', model.callToActionHref);
		}

		if (model.callToActionTarget) {
			callToAction.setAttribute('target', `_${model.callToActionTarget}`);
		}
	}
}

function waitForSlideEnd(targetElement, callback) {
	const handler = (event) => {
		if (event.propertyName !== 'grid-template-rows') {
			return;
		}
		targetElement.removeEventListener('transitionend', handler);
		callback?.();
	};

	targetElement.addEventListener('transitionend', handler);
}

function initTrialStep(targetElement, item) {
	const root = targetElement.firstElementChild;
	let isAnimating = false;

	const elementReferences = {
		arrow: root.querySelector('.trial-step__arrow'),
		body: root.querySelector('.trial-step__body'),
		btnComplete: root.querySelector('[data-action="mark-complete"]'),
		btnIncomplete: root.querySelector('[data-action="mark-incomplete"]'),
		stepComplete: root.querySelector('.trial-step__step--complete'),
		stepIncomplete: root.querySelector('.trial-step__step--incomplete'),
		toggle: root.querySelector('[data-action="toggle"]'),
	};

	const model = {
		callToActionHref: item?.callToActionLink ?? null,
		callToActionTarget: item?.callToActionTarget
			? item?.callToActionTarget.key
			: null,
		callToActionText: item?.callToActionText ?? null,
		done: !!item?.done,
		id: item?.id,
		stepNumber: item?.stepNumber,
		timeToComplete: item?.timeToComplete ?? '',
		title: item?.title ?? '',
	};

	watchDropArea(root);

	hydrateFromModel(root, model);

	function render() {
		if (model.done) {
			show(elementReferences.stepComplete);
			hide(elementReferences.stepIncomplete);
			show(elementReferences.btnIncomplete);
			hide(elementReferences.btnComplete);
		}
		else {
			show(elementReferences.stepIncomplete);
			hide(elementReferences.stepComplete);
			show(elementReferences.btnComplete);
			hide(elementReferences.btnIncomplete);
		}
	}

	async function update(done) {
		const button = done
			? elementReferences.btnComplete
			: elementReferences.btnIncomplete;

		setLoading(button, true);
		try {
			await fetchJSON(`/o/c/m2h8progresstrackers/${model.id}`, {
				method: 'PATCH',
				body: JSON.stringify({
					id: model.id,
					done,
					stepNumber: model.stepNumber,
				}),
			});
			model.done = done;
			render();
			toast({
				message: done ? 'Marked as complete.' : 'Marked as incomplete.',
			});
		}
		catch (error) {
			toast({
				message: 'Something went wrong, please try again later.',
				type: 'danger',
				error,
			});
		}
		finally {
			setLoading(button, false);
		}
	}

	function toggleHandler() {
		if (isAnimating) {
			return;
		}

		const isExpanded = root.classList.contains('trial-step--expanded');

		if (isExpanded) {
			isAnimating = true;
			root.classList.add('is-collapsing');
			root.classList.remove('trial-step--expanded');
			root.classList.add('trial-step--collapsed');

			elementReferences.arrow.classList.remove(
				'trial-step__arrow--expand'
			);
			elementReferences.arrow.classList.add(
				'trial-step__arrow--collapse'
			);
			elementReferences.toggle.setAttribute('aria-expanded', 'false');
			waitForSlideEnd(elementReferences.body, () => {
				root.classList.remove('is-collapsing');
				isAnimating = false;
			});
		}
		else {
			isAnimating = true;
			root.classList.add('is-expanding');
			root.classList.remove('trial-step--collapsed');
			root.classList.add('trial-step--expanded');

			elementReferences.arrow.classList.add('trial-step__arrow--expand');
			elementReferences.arrow.classList.remove(
				'trial-step__arrow--collapse'
			);
			elementReferences.toggle.setAttribute('aria-expanded', 'true');
			waitForSlideEnd(elementReferences.body, () => {
				root.classList.remove('is-expanding');
				isAnimating = false;
			});
		}
	}

	elementReferences.btnComplete?.addEventListener('click', () =>
		update(true)
	);
	elementReferences.btnIncomplete?.addEventListener('click', () =>
		update(false)
	);
	elementReferences.toggle?.addEventListener('click', toggleHandler);

	root.classList.add('trial-step--collapsed');
	root.classList.remove('trial-step--expanded');

	render();
}

async function init() {
	let response;
	try {
		const fields =
			'?fields=id,stepNumber,done,title,timeToComplete,callToActionText,callToActionTarget,callToActionLink';
		const filter = configuration.stepNumber
			? '&filter=' +
				encodeURIComponent(`stepNumber eq ${configuration.stepNumber}`)
			: '';
		response = await fetchJSON(
			`/o/c/m2h8progresstrackers${fields}${filter}`
		);
	}
	catch (error) {
		toast({message: 'Could not load data.', type: 'danger', error});

		return;
	}

	const stepModel = response?.items?.[0];
	if (!stepModel) {
		return;
	}

	initTrialStep(fragmentElement, stepModel);
}

Liferay.on('allPortletsReady', async () => init());
