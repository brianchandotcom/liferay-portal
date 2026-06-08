/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

import Item from './Item';
import ItemField from './ItemField';
import ItemText from './ItemText';
import ItemTitle from './ItemTitle';

const listGroupPropTypes = {
	noBorder: PropTypes.bool,
	showQuickActionsOnHover: PropTypes.bool,
};

export default class ListGroup extends React.Component {
	static defaultProps = {
		noBorder: false,
		showQuickActionsOnHover: true,
	};

	static propTypes = listGroupPropTypes;

	render() {
		const {
			children,
			className,
			noBorder,
			showQuickActionsOnHover,
			...otherProps
		} = this.props;

		const classes = getCN('list-group', 'list-group-root', className, {
			'no-border': noBorder,
			'show-quick-actions-on-hover': showQuickActionsOnHover,
		});

		return (
			<ul
				{...omitDefinedProps(otherProps, listGroupPropTypes)}
				className={classes}
			>
				{children}
			</ul>
		);
	}
}

ListGroup.Item = Item;
ListGroup.ItemField = ItemField;
ListGroup.ItemText = ItemText;
ListGroup.ItemTitle = ItemTitle;
