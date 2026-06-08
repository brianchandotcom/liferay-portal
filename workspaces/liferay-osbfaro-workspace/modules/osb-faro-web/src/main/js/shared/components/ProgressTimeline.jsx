/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import {Link} from 'react-router-dom';

class ProgressTimeline extends React.Component {
	static propTypes = {
		activeIndex: PropTypes.number,
		className: PropTypes.string,
		items: PropTypes.array,
	};

	wrapWithLink(content, href, i) {
		return (
			<Link key={i} to={href}>
				{content}
			</Link>
		);
	}

	render() {
		const {activeIndex, className, items} = this.props;

		return (
			<div className={getCN('timeline-root', className)}>
				{items.map(({href, title}, i) => {
					const active = activeIndex === i;
					const previousStep = activeIndex > i;

					const step = i + 1;

					const content = (
						<div
							className={getCN('step', {
								active,
								'previous-step': previousStep,
							})}
							key={i}
						>
							<div className="title">{title}</div>

							<div className="circle">
								{!previousStep && step}

								{previousStep && (
									<ClayIcon
										className="icon-root"
										symbol="check"
									/>
								)}
							</div>
						</div>
					);

					return [
						href ? this.wrapWithLink(content, href, i) : content,
						items.length !== i + 1 && (
							<span
								className={getCN('bar', {filled: previousStep})}
								key={`bar-${i}`}
							/>
						),
					];
				})}
			</div>
		);
	}
}

export default ProgressTimeline;
