const { debugEnabled, displayToasts, stepNumber } = configuration;

const debug = (msg, ...args) => {
  if (debugEnabled) console.debug(`[Trial Step] ${msg}`, ...args);
};

debug('configuration', { debugEnabled, displayToasts, stepNumber });

function findDropArea(root) {
  const container = root.querySelector('.trial-step__body-inner') || root;

  const dzTag = container.querySelector('lfr-drop-zone');
  const editorEmpty = container.querySelector(
    '.page-editor__no-fragments-state, .-page-editor__no-fragments-state'
  );
  const editorZone = container.querySelector(
    '[data-lfr-drop-zone-id], .page-editor__drop-zone'
  );

  return { container, dzTag, editorEmpty, editorZone };
}

function isDropAreaEmpty(root) {
  const { container, dzTag, editorEmpty, editorZone } = findDropArea(root);

  if (layoutMode === 'edit') {
    if (editorEmpty) return true;
    if (editorZone) {
      const children = Array.from(editorZone.children).filter(
        (el) =>
          !el.classList.contains('page-editor__no-fragments-state') &&
          !el.classList.contains('-page-editor__no-fragments-state')
      );
      return children.length === 0;
    }
    const hasRealContent = Array.from(container.children).some(
      (el) => el.nodeType === 1 && !el.classList.contains('trial-step__cta')
    );
    return !hasRealContent;
  }

  if (dzTag) return true;
  const hasRealContent = Array.from(container.children).some(
    (el) => el.nodeType === 1 && !el.classList.contains('trial-step__cta')
  );
  return !hasRealContent;
}

function watchDropArea(root) {
  const { container } = findDropArea(root);
  const apply = () => root.classList.toggle('is-empty', isDropAreaEmpty(root));
  apply();

  if (!container) return;

  const mo = new MutationObserver(apply);
  mo.observe(container, {
    childList: true,
    subtree: true,
    characterData: true,
    attributes: true,
  });
}

function toast({ message, type = 'success', error }) {
  if (configuration.displayToasts && Liferay?.Util?.openToast) {
    Liferay.Util.openToast({ message, type, toastProps: { autohide: true } });
  } else {
    if (type === 'danger') {
      console.error(message, error);
      return;
    }
    debug(message, { type, error });
  }
}

async function fetchJSON(url, options) {
  const res = await Liferay.Util.fetch(url, {
    headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
    ...options,
  });
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

function setLoading(el, isLoading) {
  el?.classList.toggle('is-loading', !!isLoading);
  const spinner =
    el?.querySelector('[data-role^="spinner"]') ||
    el?.querySelector('.trial-step__spinner');
  spinner?.classList.toggle('is-hidden', !isLoading);
}

const show = (el) => el?.classList.remove('is-hidden');
const hide = (el) => el?.classList.add('is-hidden');

function setField(root, key, value) {
  const el = root.querySelector(`.trial-step__field[data-field="${key}"]`);
  if (!el) return;
  el.textContent = value ?? '';
}

function hydrateFromModel(root, model) {
  setField(root, 'stepNumber', model.stepNumber);
  setField(root, 'title', model.title);
  setField(
    root,
    'timeToComplete',
    model.timeToComplete ?? model.timeToRead ?? ''
  );
  setField(root, 'ctaText', model.ctaText ?? '');

  const cta = root.querySelector('.trial-step__cta');
  if (cta) {
    if (model.ctaHref) cta.setAttribute('href', model.ctaHref);
    if (model.ctaTarget) cta.setAttribute('target', `_${model.ctaTarget}`);
  }
}

function waitForSlideEnd(el, cb) {
  const handler = (e) => {
    if (e.propertyName !== 'grid-template-rows') return;
    el.removeEventListener('transitionend', handler);
    cb?.();
  };
  el.addEventListener('transitionend', handler);
}

function initTrialStep(hostEl, item) {
  const root = hostEl.firstElementChild;
  const els = {
    stepIncomplete: root.querySelector('.trial-step__step--incomplete'),
    stepComplete: root.querySelector('.trial-step__step--complete'),
    btnComplete: root.querySelector('[data-action="mark-complete"]'),
    btnIncomplete: root.querySelector('[data-action="mark-incomplete"]'),
    toggle: root.querySelector('[data-action="toggle"]'),
    arrow: root.querySelector('.trial-step__arrow'),
    body: root.querySelector('.trial-step__body'),
  };

  let model = {
    id: item?.id,
    stepNumber: item?.stepNumber,
    done: !!item?.done,
    title: item?.title ?? '',
    timeToComplete: item?.timeToComplete ?? '',
    ctaText: item?.cTAText ?? null,
    ctaHref: item?.cTALink ?? null,
    ctaTarget: item?.cTATarget ? item?.cTATarget.key : null,
  };

  let isAnimating = false;

  watchDropArea(root);
  hydrateFromModel(root, model);

  function render() {
    if (model.done) {
      show(els.stepComplete);
      hide(els.stepIncomplete);
      show(els.btnIncomplete);
      hide(els.btnComplete);
    } else {
      show(els.stepIncomplete);
      hide(els.stepComplete);
      show(els.btnComplete);
      hide(els.btnIncomplete);
    }
  }

  async function update(done) {
    const btn = done ? els.btnComplete : els.btnIncomplete;
    setLoading(btn, true);
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
    } catch (error) {
      toast({
        message: 'Something went wrong, please try again later.',
        type: 'danger',
        error,
      });
    } finally {
      setLoading(btn, false);
    }
  }

  function toggleHandler() {
    if (isAnimating) return;

    const isExpanded = root.classList.contains('trial-step--expanded');

    if (isExpanded) {
      isAnimating = true;
      root.classList.add('is-collapsing');

      root.classList.remove('trial-step--expanded');
      root.classList.add('trial-step--collapsed');

      els.arrow.classList.remove('trial-step__arrow--expand');
      els.arrow.classList.add('trial-step__arrow--collapse');
      els.toggle.setAttribute('aria-expanded', 'false');

      waitForSlideEnd(els.body, () => {
        root.classList.remove('is-collapsing');
        isAnimating = false;
        debug('Collapsed');
      });
    } else {
      isAnimating = true;
      root.classList.add('is-expanding');

      root.classList.remove('trial-step--collapsed');
      root.classList.add('trial-step--expanded');

      els.arrow.classList.add('trial-step__arrow--expand');
      els.arrow.classList.remove('trial-step__arrow--collapse');
      els.toggle.setAttribute('aria-expanded', 'true');

      waitForSlideEnd(els.body, () => {
        root.classList.remove('is-expanding');
        isAnimating = false;
        debug('Expanded');
      });
    }
  }

  els.btnComplete?.addEventListener('click', () => update(true));
  els.btnIncomplete?.addEventListener('click', () => update(false));
  els.toggle?.addEventListener('click', toggleHandler);

  root.classList.add('trial-step--collapsed');
  root.classList.remove('trial-step--expanded');

  render();
}

async function init() {
  let response;
  try {
    const fields =
      '?fields=id,stepNumber,done,title,timeToComplete,cTAText,cTATarget,cTALink';
    const filter = configuration.stepNumber
      ? '&filter=' +
        encodeURIComponent(`stepNumber eq ${configuration.stepNumber}`)
      : '';
    response = await fetchJSON(`/o/c/m2h8progresstrackers${fields}${filter}`);
  } catch (error) {
    toast({ message: 'Could not load data.', type: 'danger', error });
    return;
  }

  const stepModel = response?.items?.[0];
  if (!stepModel) {
    debug('No step model returned for this fragment/stepNumber');
    return;
  }

  initTrialStep(fragmentElement, stepModel);
}

Liferay.on('allPortletsReady', async () => init());
