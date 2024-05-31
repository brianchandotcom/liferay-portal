/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useEffect, useRef} from 'react';

import AutofillBuilds from './AutofillBuilds';

type AutofillBuildsPopoverProps = {
	expanded?: boolean;
	setType: (state: 'autofill' | 'compareRuns') => void;
	setVisible: (state: boolean) => void;
	triggedRef: React.RefObject<HTMLDivElement>;
	visible: boolean;
};

const AutofillBuildsPopover: React.FC<AutofillBuildsPopoverProps> = ({
	expanded = false,
	setType,
	setVisible,
	triggedRef,
	visible,
}) => {
	const ref = useRef<HTMLDivElement>(null);

	useEffect(() => {
		const handleClickOutside = (event: any) => {
			if (
				ref.current &&
				!ref.current.contains(event.target) &&
				!triggedRef.current?.contains(event.target)
			) {
				setVisible(false);
			}
		};

		document.addEventListener('mousedown', handleClickOutside);

		return () =>
			document.removeEventListener('mousedown', handleClickOutside);
	}, [setVisible, triggedRef]);

	return (
		<div
			className={classNames('tr-autofill-builds-popover', {
				'hidden': !visible && !expanded,
				'hidden--expanded': !visible && expanded,
				'visible': visible && !expanded,
				'visible--expanded': visible && expanded,
			})}
			onBlur={() => setVisible(false)}
			ref={ref}
		>
			<AutofillBuilds setType={setType} setVisible={setVisible} />
		</div>
	);
};

export default AutofillBuildsPopover;
