/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSelector} from '@xstate/store/react';
import React, {useMemo} from 'react';
import {useLocation} from 'react-router-dom';

import i18n, {Word} from '../../i18n';
import {Liferay} from '../../liferay/liferay';
import {breadcrumbStore} from './BreadcrumbStore';
import Item from './Item';

import './Breadcrumb.scss';

type BreadcrumbsProps = {
	basePath?: string;
	hiddenPaths?: string[];
};

export function Breadcrumbs({
	basePath = Liferay.ThemeDisplay.getLayoutRelativeURL(),
	hiddenPaths = [],
}: BreadcrumbsProps) {
	const {pathname} = useLocation();

	const replacements = useSelector(
		breadcrumbStore,
		({context: {replacements}}) => replacements
	);

	const items = useMemo(() => {
		const rawPaths = [
			...basePath.split('/'),
			...pathname.split('/'),
		].filter(Boolean);

		return [
			{
				active: false,
				href: '/web/marketplace',
				label: 'Marketplace',
			},
			...rawPaths
				.slice(2)
				.map((path, index, array) => ({
					active: index === array.length - 1,
					href:
						'/' +
						rawPaths
							.map((segment) =>
								segment.endsWith('-dashboard')
									? segment + '#'
									: segment
							)
							.slice(0, index + 3)
							.join('/'),
					label: replacements[path] || i18n.translate(path as Word),
				}))
				.filter(
					(item) =>
						!hiddenPaths.some((hiddenPath) =>
							item.href.endsWith(hiddenPath)
						)
				),
		];
	}, [basePath, hiddenPaths, pathname, replacements]);

	return (
		<nav aria-label="Breadcrumb" className="breadcrumb-bar">
			<ol className="breadcrumb">
				{items.map((item: React.ComponentProps<typeof Item>, index) => (
					<Item
						active={item.active}
						href={item.href}
						key={index}
						label={item.label}
						onClick={item.onClick}
					/>
				))}
			</ol>
		</nav>
	);
}
