/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {Link} from 'react-router-dom';

import i18n from '../../i18n';
import TestrayIcons from '../Icons/TestrayIcon';
import Tooltip from '../Tooltip';

type SidebarItemProps = {
	active?: boolean;
	className?: string;
	expanded?: boolean;
	icon: string;
	label: string;
	path?: string;
};

const SidebarItem: React.FC<SidebarItemProps> = ({
	active,
	expanded,
	icon,
	label,
	path,
}) => {
	const LinkComponent = path
		? Link
		: ({children, ...props}: any) => <div {...props}>{children}</div>;

	return (
		<LinkComponent
			className={classNames('tr-sidebar__content__list__item', {
				'tr-sidebar__content__list__item--active': active,
			})}
			to={path as string}
		>
			<Tooltip
				className="align-items-center d-flex"
				position="right"
				title={expanded ? undefined : i18n.translate(label)}
			>
				<TestrayIcons
					className="tr-sidebar__content__list__item__icon"
					fill={active ? 'white' : '#8b8db2'}
					size={35}
					symbol={icon}
				/>

				<span
					className={classNames(
						'tr-sidebar__content__list__item__text',
						{
							'tr-sidebar__content__list__item__text--expanded': expanded,
						}
					)}
				>
					{label}
				</span>
			</Tooltip>
		</LinkComponent>
	);
};

export default SidebarItem;
