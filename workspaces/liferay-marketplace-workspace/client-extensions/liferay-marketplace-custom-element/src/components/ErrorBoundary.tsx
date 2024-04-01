/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Component, ErrorInfo} from 'react';

type ErrorBoundaryProps = {
	children: any;
	className?: string;
	errorMessage?: string;
};

type ErrorBoundaryState = {
	hasError: boolean;
};

export default class ErrorBoundary extends Component<
	ErrorBoundaryProps,
	ErrorBoundaryState
> {
	constructor(props: ErrorBoundaryProps) {
		super(props);
		this.state = {hasError: false};
	}

	componentDidCatch(error: Error, errorInfo: ErrorInfo) {
		console.error('Error caught by ErrorBoundary:', error, errorInfo);

		this.setState({hasError: true});
	}

	render() {
		if (this.state.hasError) {
			return (
				<div className={this.props.className}>
					{this.props.errorMessage || 'Something went wrong.'}
				</div>
			);
		}

		return this.props.children;
	}
}
