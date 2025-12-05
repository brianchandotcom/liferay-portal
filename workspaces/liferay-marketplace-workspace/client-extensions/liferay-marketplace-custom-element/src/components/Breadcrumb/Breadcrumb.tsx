/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLocation} from 'react-router-dom';
import {useSelector} from '@xstate/store/react';
import classNames from 'classnames';
import React, {useEffect, useMemo, useState} from 'react';

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
	const [loaded, setLoaded] = useState(false);
	const {pathname} = useLocation();

	useEffect(() => {
		setTimeout(() => setLoaded(true), 25);
	}, []);

	const {replacements, visible} = useSelector(
		breadcrumbStore,
		({context}) => context
	);

	const items = useMemo(() => {
		const rawPaths = [
			...basePath.split('/'),
			...pathname.split('/'),
		].filter(Boolean);

		const items = [];

		if (!basePath.startsWith('/web/marketplace')) {
			items.push({
				href: '/web/marketplace',
				label: 'Marketplace',
			});
		}

		return [
			...items,
			...rawPaths
				.map((path, index) => ({
					active: index === rawPaths.length - 1,
					href:
						'/' +
						rawPaths
							.map((segment) =>
								segment.endsWith('-dashboard')
									? segment + '#'
									: segment
							)
							.slice(0, index + 1)
							.join('/'),
					label: replacements[path] || i18n.translate(path as Word),
				}))
				.filter(
					(item) =>
						!['web', ...hiddenPaths].some((hiddenPath) =>
							item.href.endsWith(hiddenPath)
						)
				),
		];
	}, [basePath, hiddenPaths, loaded, pathname, replacements]);

	if (!visible) {
		return null;
	}

	return (
		<nav
			aria-label="Breadcrumb"
			className={classNames('breadcrumb-bar', {
				invisible: !loaded,
			})}
		>
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
