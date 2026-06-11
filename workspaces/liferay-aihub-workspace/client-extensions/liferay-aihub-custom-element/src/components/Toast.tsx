/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React, {useEffect, useRef} from 'react';

interface ToastProps {
	message: string;
	onDismiss: () => void;
}

export default function Toast({message, onDismiss}: ToastProps) {
	const onDismissRef = useRef(onDismiss);

	onDismissRef.current = onDismiss;

	useEffect(() => {
		const timer = setTimeout(() => onDismissRef.current(), 4000);

		return () => clearTimeout(timer);
	}, [message]);

	return (
		<div className="aihub-toast" role="status">
			<span className="aihub-toast-icon">
				<ClayIcon symbol="check-circle-full" />
			</span>

			<span>{message}</span>
		</div>
	);
}
