
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import React from 'react';
import {createRoot} from 'react-dom/client';

import CertificationList from './components/Certification/CertificationList';
import CoursesList from './components/Course/CoursesList';
import LandingPageView from './components/Global/LandingPageView';
import LearningPathsList from './components/LearningPath/LearningPathList';

import './index.scss';

const ELEMENT_ID_CERTIFICATION_LIST = 'liferay-lms-certification-list';
const ELEMENT_ID_COURSES_LIST = 'liferay-lms-courses-list';
const ELEMENT_ID_LANDINGPAGE_VIEW = 'liferay-lms-landingpage-view';
const ELEMENT_ID_LEARN_PATHS_LIST = 'liferay-lms-learn-paths-list';

class CertificationListComponent extends HTMLElement {
	constructor() {
		super();
		this.root = null;
	}

	connectedCallback() {
		if (!this.root) {
			this.root = createRoot(this);
		}
		this.renderComponent();
	}

	renderComponent(
		page = this.getAttribute('page') || 1,
		pageSize = this.getAttribute('page-size') || 3,
		paging = this.getAttribute('paging') || false,
		durationFormat = this.getAttribute('duration-format')
	) {
		if (this.root) {
			this.root.render(
				<ClayIconSpriteContext.Provider value={Liferay.Icons.spritemap}>
					<CertificationList
						durationFormat={durationFormat}
						page={page}
						pageSize={pageSize}
						paging={paging}
					></CertificationList>
				</ClayIconSpriteContext.Provider>
			);
		}
	}
}
class CoursesListComponent extends HTMLElement {
	constructor() {
		super();
		this.root = null;
	}

	connectedCallback() {
		if (!this.root) {
			this.root = createRoot(this);
		}
		this.renderComponent();
	}

	renderComponent(
		page = this.getAttribute('page') || 1,
		pageSize = this.getAttribute('page-size') || 3,
		paging = this.getAttribute('paging') || false,
		durationFormat = this.getAttribute('duration-format')
	) {
		if (this.root) {
			this.root.render(
				<ClayIconSpriteContext.Provider value={Liferay.Icons.spritemap}>
					<CoursesList
						durationFormat={durationFormat}
						page={page}
						pageSize={pageSize}
						paging={paging}
					></CoursesList>
				</ClayIconSpriteContext.Provider>
			);
		}
	}

	disconnectedCallback() {
		if (this.root) {
			this.root.unmount();
			this.root = null;
		}
	}
}

class LandingPageViewComponent extends HTMLElement {
	constructor() {
		super();
		this.root = null;
	}
	connectedCallback() {
		if (!this.root) {
			this.root = createRoot(this);
		}
		this.renderComponent();
	}
	renderComponent(type = this.getAttribute('type')) {
		if (this.root) {
			this.root.render(
				<ClayIconSpriteContext.Provider value={Liferay.Icons.spritemap}>
					<LandingPageView type={type} />
				</ClayIconSpriteContext.Provider>
			);
		}
	}
	disconnectedCallback() {
		if (this.root) {
			this.root.unmount();
			this.root = null;
		}
	}
}
class LearningPathsListComponent extends HTMLElement {
	constructor() {
		super();
		this.root = null;
	}

	connectedCallback() {
		if (!this.root) {
			this.root = createRoot(this);
		}
		this.renderComponent();
	}

	renderComponent(
		page = this.getAttribute('page') || 1,
		pageSize = this.getAttribute('page-size') || 3,
		paging = this.getAttribute('paging') || false,
		durationFormat = this.getAttribute('duration-format')
	) {
		if (this.root) {
			this.root.render(
				<ClayIconSpriteContext.Provider value={Liferay.Icons.spritemap}>
					<LearningPathsList
						durationFormat={durationFormat}
						page={page}
						pageSize={pageSize}
						paging={paging}
					></LearningPathsList>
				</ClayIconSpriteContext.Provider>
			);
		}
	}

	disconnectedCallback() {
		if (this.root) {
			this.root.unmount();
			this.root = null;
		}
	}
}

if (!customElements.get(ELEMENT_ID_CERTIFICATION_LIST)) {
	customElements.define(
		ELEMENT_ID_CERTIFICATION_LIST,
		CertificationListComponent
	);
}

if (!customElements.get(ELEMENT_ID_COURSES_LIST)) {
	customElements.define(ELEMENT_ID_COURSES_LIST, CoursesListComponent);
}

if (!customElements.get(ELEMENT_ID_LANDINGPAGE_VIEW)) {
	customElements.define(
		ELEMENT_ID_LANDINGPAGE_VIEW,
		LandingPageViewComponent
	);
}

if (!customElements.get(ELEMENT_ID_LEARN_PATHS_LIST)) {
	customElements.define(
		ELEMENT_ID_LEARN_PATHS_LIST,
		LearningPathsListComponent
	);
}