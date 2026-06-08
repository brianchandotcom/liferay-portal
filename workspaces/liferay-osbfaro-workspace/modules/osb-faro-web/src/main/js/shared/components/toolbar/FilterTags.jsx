/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import {PropTypes} from 'prop-types';
import React from 'react';
import Label from '~/shared/components/Label';
import SubnavTbar from '~/shared/components/SubnavTbar';

class FilterTagItem extends React.Component {
	static propTypes = {
		field: PropTypes.string.isRequired,
		onRemove: PropTypes.func.isRequired,
		value: PropTypes.string.isRequired,
	};

	@autobind
	handleRemove() {
		const {field, onRemove, value} = this.props;

		onRemove(field, value);
	}

	render() {
		const {label} = this.props;

		return (
			<Label
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
				display="info"
				onRemove={this.handleRemove}
				size="lg"
			>
				{label}
			</Label>
		);
	}
}

export default class FilterTags extends React.Component {
	static defaultProps = {
		tags: [],
	};

	static propTypes = {
		tags: PropTypes.array,
	};

	render() {
		const {tags, ...otherProps} = this.props;

		return (
			<>
				{tags.map(({field, label, value}, i) => (
					<SubnavTbar.Item expand={i === tags.length - 1} key={value}>
						<FilterTagItem
							{...otherProps}
							field={field}
							label={label}
							value={value}
						/>
					</SubnavTbar.Item>
				))}
			</>
		);
	}
}
