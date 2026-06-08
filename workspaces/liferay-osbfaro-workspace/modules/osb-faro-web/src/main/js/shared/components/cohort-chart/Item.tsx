/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useRef, useState} from 'react';
import ReactDOM from 'react-dom';
import Popover from '~/shared/components/Popover';
import {sub} from '~/shared/util/lang';

import {CohortHeatMapType} from './index';

const Item: React.FC<CohortHeatMapType> = ({
	colorHex,
	date,
	dateLabelFn,
	periodLabel,
	retention,
	value,
}) => {

	// eslint-disable-next-line no-undef
	const _refRef = useRef<HTMLTableDataCellElement>(null);

	const [visible, setVisible] = useState(false);

	const handleMouseOut = () => setVisible(false);
	const handleMouseOver = () => setVisible(true);

	const content = (
		<span className="cohort-item-popover d-flex justify-content-between">
			<span className="period">{periodLabel}</span>

			<span className="visitors">
				{sub(Liferay.Language.get('x-visitors'), [
					value.toLocaleString(),
				])}
			</span>
		</span>
	);

	return (
		<td
			className="cohort-item-root table-column-text-center"
			onBlur={handleMouseOver}
			onFocus={handleMouseOut}
			onMouseOut={handleMouseOut}
			onMouseOver={handleMouseOver}
			ref={_refRef}
			style={{background: colorHex || undefined}}
		>
			<span className="retention">{`${retention.toFixed(2)}%`}</span>

			{ReactDOM.createPortal(
				<Popover
					alignElement={_refRef.current!}
					content={content}
					title={sub(Liferay.Language.get('x-cohort'), [
						dateLabelFn(date, true),
					])}
					visible={visible}
				/>,
				document.querySelector('body.dxp') || document.body
			)}
		</td>
	);
};

export default Item;
