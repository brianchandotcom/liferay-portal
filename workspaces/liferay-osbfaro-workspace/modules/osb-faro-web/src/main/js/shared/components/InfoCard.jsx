/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PropTypes} from 'prop-types';
import React from 'react';
import Card from '~/shared/components/Card';
import {DETAILS_LABEL_MAP} from '~/shared/util/lang';
import {getSafeDisplayValue} from '~/shared/util/util';

const TermList = ({children}) => <dl className="term-list">{children}</dl>;

const Term = ({name, value}) => (
	<div className="term">
		<dt>{DETAILS_LABEL_MAP[name]}</dt>

		<dd>{getSafeDisplayValue(value)}</dd>
	</div>
);

export default class InfoCard extends React.Component {
	static propTypes = {
		header: PropTypes.string,
		image: PropTypes.string,
		items: PropTypes.arrayOf(
			PropTypes.shape({
				name: PropTypes.string,
				value: PropTypes.oneOfType([
					PropTypes.string,
					PropTypes.array,
					PropTypes.number,
					PropTypes.func,
				]),
			})
		).isRequired,
	};

	render() {
		const {header, image, items} = this.props;

		const imageStyles = image
			? {
					backgroundImage: `url(${image})`,
				}
			: undefined;

		return (
			<Card
				className={`info-card-root${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				<Card.Header>
					<Card.Title>{header}</Card.Title>
				</Card.Header>

				<Card.Body>
					{!!image && (
						<div className="image-container" style={imageStyles} />
					)}

					<TermList>
						{items.map((params, i) => (
							<Term {...params} key={i} />
						))}
					</TermList>
				</Card.Body>
			</Card>
		);
	}
}
