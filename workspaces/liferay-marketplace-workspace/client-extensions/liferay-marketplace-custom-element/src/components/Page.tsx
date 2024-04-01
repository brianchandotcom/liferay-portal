/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentProps, ReactElement, ReactNode} from 'react';

import {Header} from './Header/Header';
import FetcherError from '../services/fetcher/FetcherError';
import EmptyState from './EmptyState';
import Loading from './Loading';

export type PageRendererProps = {
	children: ReactElement;
	error?: FetcherError;
	isLoading: boolean;
};

const PageRenderer: React.FC<PageRendererProps> = ({
	children,
	error,
	isLoading,
}) => {
	if (isLoading) {
		return <Loading className="mt-4" />;
	}

	if (error) {
		return (
			<EmptyState
				description={error?.info?.title}
				title={error.message}
				type="EMPTY_SEARCH"
			/>
		);
	}

	return children;
};

type PageProps = {
	children: any;
	description: string;
	title: string;
	rightButton?: ReactNode;
	pageRendererProps?: Omit<ComponentProps<typeof PageRenderer>, 'children'>;
};

const Page: React.FC<PageProps> = ({
	children,
	description,
	rightButton,
	title,
	pageRendererProps,
}) => (
	<div className="w-100">
		<div className="align-items-center d-flex justify-content-between">
			<Header description={description} title={title} />

			{rightButton}
		</div>

		{pageRendererProps ? (
			<PageRenderer {...pageRendererProps}>
				{children as ReactElement}
			</PageRenderer>
		) : (
			children
		)}
	</div>
);

export {PageRenderer};

export default Page;
