/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Component, ErrorInfo, ReactNode} from 'react';

type Props = {
	children: ReactNode;
	fallback?: ReactNode;
	resetKeys?: unknown[];
};

type State = {
	hasError: boolean;
};

export default class ErrorBoundary extends Component<Props, State> {
	constructor(props: Props) {
		super(props);
		this.state = {hasError: false};
	}

	static getDerivedStateFromError(): State {
		return {hasError: true};
	}

	componentDidCatch(error: Error, info: ErrorInfo) {
		console.error(error, info.componentStack);
	}

	componentDidUpdate(prevProps: Props) {
		if (
			this.state.hasError &&
			this.props.resetKeys?.some(
				(key, i) => key !== prevProps.resetKeys?.[i]
			)
		) {
			this.setState({hasError: false});
		}
	}

	render() {
		if (this.state.hasError) {
			return this.props.fallback ?? null;
		}

		return this.props.children;
	}
}
