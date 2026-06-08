/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import getCN from 'classnames';
import {pickBy} from 'lodash';
import React, {useState} from 'react';
import Breadcrumbs from '~/shared/components/Breadcrumbs';
import TextTruncate from '~/shared/components/TextTruncate';
import {IBreadcrumbArgs} from '~/shared/util/breadcrumbs';
import {
	getMatchedRoute,
	setUriQueryValues,
	toRoute,
} from '~/shared/util/router';

import NotificationAlertList, {
	useNotificationsAPI,
} from '../NotificationAlertList';
import Row from './Row';

type NavBarItem = {
	deprecated?: boolean;
	exact: boolean;
	label: string;
	route: string;
};

interface INavBarProps extends React.HTMLAttributes<HTMLDivElement> {
	items: NavBarItem[];
	routeParams?: object;
	routeQueries?: object;
}

const NavBar: React.FC<INavBarProps> = ({
	items,
	routeParams = {},
	routeQueries = {},
}) => {
	const matchedRoute = getMatchedRoute(items);

	const initialItem =
		items.find((item) => item.route === matchedRoute) ?? items[0];

	const [activeLabel, setActiveLabel] = useState(initialItem.label);

	return (
		<div className="row">
			<ClayNavigationBar triggerLabel={activeLabel}>
				{items.map(({deprecated, label, route}) => (
					<ClayNavigationBar.Item
						active={matchedRoute === route}
						key={label}
					>
						<ClayLink
							href={setUriQueryValues(
								pickBy(routeQueries),
								toRoute(route, routeParams)
							)}
							onClick={() => setActiveLabel(label)}
						>
							{label}

							{deprecated && (
								<ClayBadge
									className="ml-1"
									displayType="warning"
									label={Liferay.Language.get(
										'deprecated'
									).toUpperCase()}
									translucent
								/>
							)}
						</ClayLink>
					</ClayNavigationBar.Item>
				))}
			</ClayNavigationBar>
		</div>
	);
};

interface Action extends React.HTMLAttributes<HTMLElement> {
	deprecated?: boolean;
	disabled: boolean;
	external?: boolean;
	href: string;
	icon?: {
		symbol: string;
	};
	label: string;
}

interface IPageActionsProps {
	actions?: Action[];
	actionsDisplayLimit?: number;
	disabled?: boolean;
	label?: string;
}

const PageActions: React.FC<IPageActionsProps> = ({
	actions = [],
	actionsDisplayLimit = 1,
	disabled = false,
	label = '',
}) => (
	<>
		{actions.length <= actionsDisplayLimit &&
			actions.map(({icon, label, ...props}) => {
				const Button = props.href ? ClayLink : ClayButton;

				return (
					<Button
						button
						className={getCN(
							getCN('button-root', {
								disabled: props.disabled,
							})
						)}
						displayType="secondary"
						key={label}
						{...props}
					>
						{icon && (
							<ClayIcon className="mr-2" symbol={icon.symbol} />
						)}

						{label}
					</Button>
				);
			})}

		{actions.length > actionsDisplayLimit && (
			<ClayDropDown
				alignmentPosition={Align.BottomRight}
				trigger={
					<ClayButton
						aria-label={label && Liferay.Language.get('menu')}
						disabled={disabled}
						displayType={label.length ? 'primary' : 'unstyled'}
					>
						{label ? (
							<>
								<span>{label}</span>

								<ClayIcon
									className="icon-root ml-2"
									symbol="caret-bottom"
								/>
							</>
						) : (
							<ClayIcon
								className="icon-root"
								symbol="ellipsis-v"
							/>
						)}
					</ClayButton>
				}
			>
				{actions.map(({deprecated, label, ...props}) => (
					<ClayDropDown.Item key={label} {...props}>
						{label}

						{deprecated && (
							<ClayBadge
								className="ml-1"
								displayType="warning"
								label={Liferay.Language.get(
									'deprecated'
								).toUpperCase()}
								translucent
							/>
						)}
					</ClayDropDown.Item>
				))}
			</ClayDropDown>
		)}
	</>
);

const Section: React.FC<React.HTMLAttributes<HTMLDivElement>> = ({
	children,
	className,
}) => <div className={getCN('header-section', className)}>{children}</div>;

interface ITitleSectionProps extends React.HTMLAttributes<HTMLDivElement> {
	label?: boolean;
	subtitle?: React.ReactNode | string;
	title?: string;
}

export interface IActionProps extends React.HTMLAttributes<HTMLDivElement> {
	displayType: string;
	label: string;
	onClick?: () => void;
	redirectURL?: string;
}

interface IActionsProps extends React.HTMLAttributes<HTMLDivElement> {
	actions: IActionProps[];
}

const TitleSection: React.FC<ITitleSectionProps> = ({
	children,
	className,
	label = false,
	subtitle,
	title,
}) => (
	<Section className={getCN('title-section', className, {subtitle})}>
		<span className="align-items-center d-flex">
			<h1 className="text-truncate title">
				<TextTruncate title={title} />
			</h1>

			{children}
		</span>

		{subtitle &&
			(label ? (
				<ClayLabel className="mb-4" displayType="info">
					{subtitle}
				</ClayLabel>
			) : (
				<div className="subtitle">{subtitle}</div>
			))}
	</Section>
);

const Actions: React.FC<IActionsProps> = ({actions = []}) => (
	<div className="header-actions">
		{actions.map(({displayType, label, onClick, redirectURL}, index) =>
			redirectURL ? (
				<ClayLink
					className={getCN(`btn btn-${displayType}`, 'ml-2')}
					href={redirectURL}
					key={index}
					target="_blank"
				>
					<ClayIcon className="mr-2" symbol="shortcut" />

					{label}
				</ClayLink>
			) : (
				<ClayButton
					className="ml-2"
					displayType={displayType as any}
					key={index}
					onClick={onClick}
				>
					{label}
				</ClayButton>
			)
		)}
	</div>
);

interface IHeaderProps extends React.HTMLAttributes<HTMLDivElement> {
	breadcrumbs: IBreadcrumbArgs[];
	fluid?: boolean;
	groupId: string;
}

const Header: React.FC<IHeaderProps> & {
	Actions: typeof Actions;
	NavBar: typeof NavBar;
	PageActions: typeof PageActions;
	Section: typeof Section;
	TitleSection: typeof TitleSection;
} = ({breadcrumbs, children, fluid, groupId}) => {
	const notificationResponse = useNotificationsAPI(groupId);

	if (fluid) {
		return (
			<header className="header-root">
				<div className="mx-5">
					{breadcrumbs && (
						<Row>
							<Breadcrumbs items={breadcrumbs} />
						</Row>
					)}

					{children}
				</div>

				<NotificationAlertList
					{...notificationResponse}
					groupId={groupId}
					stripe
				/>
			</header>
		);
	}

	return (
		<header className="header-root">
			<div className="header-container">
				{breadcrumbs && (
					<Row>
						<Breadcrumbs items={breadcrumbs} />
					</Row>
				)}

				{children}
			</div>

			<NotificationAlertList
				{...notificationResponse}
				groupId={groupId}
				stripe
			/>
		</header>
	);
};

Header.Actions = Actions;
Header.NavBar = NavBar;
Header.PageActions = PageActions;
Header.Section = Section;
Header.TitleSection = TitleSection;

export default Header;

export {NavBar, PageActions, Section, TitleSection};
