/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PropTypes} from 'prop-types';
import React from 'react';
import {BrowserRouter as Router} from 'react-router-dom';
import {hasChanges} from '~/shared/util/react';

class DocumentTitle extends React.Component {
	static propTypes = {
		title: PropTypes.string,
	};

	constructor(props) {
		super(props);

		this._router = Router;
	}

	componentDidMount() {
		this.setTitle();
	}

	componentDidUpdate(prevProps) {
		if (hasChanges(prevProps, this.props, 'title')) {
			this.setTitle();
		}
	}

	setTitle() {
		const {title} = this.props;

		const defaultTitle = Liferay.Language.get('analytics-cloud');

		const newTitle = title ? `${title} - ${defaultTitle}` : defaultTitle;

		document.title = newTitle;
	}

	render() {
		return null;
	}
}

export default DocumentTitle;
