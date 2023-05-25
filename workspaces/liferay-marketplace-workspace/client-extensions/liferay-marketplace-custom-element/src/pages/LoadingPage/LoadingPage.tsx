/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';

import './LoadingPage.scss';

interface LoadingPageProps {
	appTitle: string;
	appVersion: string;
}

export function LoadingPage({appTitle, appVersion}: LoadingPageProps) {
	return (
		<div className="loading-page-container">
			<ClayLoadingIndicator
				displayType="primary"
				shape="squares"
				size="lg"
			/>

			<div className="loading-page-text-container">
				<span className="loading-page-text">
					Hang tight, the submission of <strong>{appTitle}</strong>
				</span>

				<span className="loading-page-text">
					<strong>{appVersion}</strong> is being sent to{' '}
					<strong>Liferay</strong>
				</span>
			</div>
		</div>
	);
}
