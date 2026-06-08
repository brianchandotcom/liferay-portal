/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import autobind from 'autobind-decorator';
import getCN from 'classnames';
import {Set} from 'immutable';
import {isNil, noop} from 'lodash';
import {PropTypes} from 'prop-types';
import React from 'react';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

import {toggle, toggleSingleton} from '../util/set';
import Checkbox from './Checkbox';
import RadioGroup from './RadioGroup';
import ListGroup from './list-group';

const quickActionPropTypes = {
	item: PropTypes.object,
	onClick: PropTypes.func,
	symbol: PropTypes.string.isRequired,
};

class QuickAction extends React.Component {
	static propTypes = quickActionPropTypes;

	@autobind
	handleClick() {
		const {item, onClick} = this.props;

		if (onClick) {
			onClick(item);
		}
	}

	render() {
		const {className, symbol, ...otherProps} = this.props;

		return (
			<ClayButton
				{...omitDefinedProps(otherProps, quickActionPropTypes)}
				className={getCN('button-root quick-action-item', className)}
				displayType="unstyled"
				onClick={this.handleClick}
			>
				<ClayIcon className="icon-root" symbol={symbol} />
			</ClayButton>
		);
	}
}

class Item extends React.Component {
	static defaultProps = {
		disabled: false,
	};

	static propTypes = {
		disabled: PropTypes.bool,
		item: PropTypes.object,
		itemRenderer: PropTypes.func,
		onClick: PropTypes.func,
		quickActions: PropTypes.arrayOf(PropTypes.object),
		selectMultiple: PropTypes.bool,

		selected: PropTypes.bool,
	};

	@autobind
	handleClick(event) {
		const {item, onClick} = this.props;

		event.preventDefault();

		onClick(item);
	}

	render() {
		const {
			className,
			disabled,
			item,
			itemRenderer: ItemInternalComponent,
			quickActions,
			selectMultiple,
			selected,
		} = this.props;

		return (
			<ListGroup.Item
				active={selected}
				className={className}
				flex
				onClick={this.handleClick}
			>
				{!isNil(selected) && (
					<ListGroup.ItemField>
						{selectMultiple ? (
							<Checkbox
								checked={selected}
								disabled={disabled}
								onChange={this.handleClick}
							/>
						) : (
							<RadioGroup.Option
								checked={selected}
								disabled={disabled}
								name="list"
								onClick={this.handleClick}
							/>
						)}
					</ListGroup.ItemField>
				)}

				<ItemInternalComponent item={item} />

				{quickActions && !!quickActions.length && (
					<ListGroup.ItemField>
						<div className="quick-action-menu">
							{quickActions.map((action) => (
								<QuickAction
									{...action}
									item={item}
									key={item.id}
								/>
							))}
						</div>
					</ListGroup.ItemField>
				)}
			</ListGroup.Item>
		);
	}
}

const listViewPropTypes = {
	disabledItemsISet: PropTypes.instanceOf(Set),
	itemRenderer: PropTypes.func.isRequired,
	items: PropTypes.array,
	onClick: PropTypes.func,
	onSelectItemsChange: PropTypes.func,
	quickActions: PropTypes.arrayOf(PropTypes.object),
	selectMultiple: PropTypes.bool,

	selectedItemsISet: PropTypes.instanceOf(Set),
};

class ListView extends React.Component {
	static defaultProps = {
		disabledItemsISet: new Set(),
		onClick: noop,
		quickActions: [],
		selectMultiple: true,

		selectedItemsISet: new Set(),
	};

	static propTypes = listViewPropTypes;

	@autobind
	handleItemClick(item) {
		const {
			disabledItemsISet,
			onClick,
			onSelectItemsChange,
			selectMultiple,
			selectedItemsISet,
		} = this.props;

		if (onSelectItemsChange && !disabledItemsISet.has(item.id)) {
			onSelectItemsChange(
				selectMultiple
					? toggle(selectedItemsISet, item.id)
					: toggleSingleton(selectedItemsISet, item.id)
			);
		}

		onClick(item);
	}

	render() {
		const {
			children,
			disabledItemsISet,
			itemRenderer,
			items,
			onSelectItemsChange,
			quickActions,
			selectMultiple,
			selectedItemsISet,
			...otherProps
		} = this.props;

		return (
			<ListGroup {...omitDefinedProps(otherProps, listViewPropTypes)}>
				{children}
				{items &&
					items.map((item) => (
						<Item
							disabled={disabledItemsISet.has(item.id)}
							item={item}
							itemRenderer={itemRenderer}
							key={item.id}
							onClick={this.handleItemClick}
							quickActions={quickActions}
							selectMultiple={selectMultiple}
							selected={
								onSelectItemsChange
									? selectedItemsISet.has(item.id)
									: null
							}
						/>
					))}
			</ListGroup>
		);
	}
}

export default ListView;
