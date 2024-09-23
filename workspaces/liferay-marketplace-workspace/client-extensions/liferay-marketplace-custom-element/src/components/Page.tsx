/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentProps, ReactElement, ReactNode} from 'react';

import FetcherError from '../services/fetcher/FetcherError';
import EmptyState from './EmptyState';
import {Header} from './Header/Header';
import Loading from './Loading';

export type PageRendererProps = {
	children: any;
	className?: string;
	error?: FetcherError;
	isLoading: boolean;
};

const PageRenderer: React.FC<PageRendererProps> = ({
	children,
	className,
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

	return className ? <div className={className}>{children}</div> : children;
};

type PageProps = {
	children: any;
	description?: string;
	pageRendererProps?: Omit<ComponentProps<typeof PageRenderer>, 'children'>;
	rightButton?: ReactNode;
	title?: string;
};

const Page: React.FC<PageProps> = ({
	children,
	description,
	pageRendererProps,
	rightButton,
	title,
}) => (
	<div className="w-100">
		<div className="align-items-center d-flex justify-content-between">
			{(description || title) && (
				<Header description={description} title={title} />
			)}
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
