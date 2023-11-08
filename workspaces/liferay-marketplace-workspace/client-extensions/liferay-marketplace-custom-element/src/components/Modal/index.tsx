/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayModal from '@clayui/modal';
import {Observer, Size} from '@clayui/modal/lib/types';
import {ReactNode} from 'react';

import FooterButtons, {ButtonProps} from '../FooterButtons';

type ButtonInfoProps = {
	cancelButton: ButtonProps;
	customizedButton: ButtonProps;
	nextButton: ButtonProps;
};

type ModalProps = {
	buttonsInfo: ButtonInfoProps;
	children: ReactNode;
	observer: Observer;
	size: Size;
};

const Modal = ({buttonsInfo, children, observer, size}: ModalProps) => {
	return (
		<ClayModal center observer={observer} size={size} status="info">
			<div>
				<ClayButtonWithIcon
					aria-label="Close"
					className="float-right mr-2 mt-2"
					displayType="unstyled"
					onClick={buttonsInfo.cancelButton.onClick}
					size="sm"
					symbol="times"
					title="Close"
				/>

				<div className="pt-4 px-4">
					{children}

					<div className="mb-5">
						<FooterButtons
							className="d-flex justify-content-between mt-6"
							dataButtons={buttonsInfo}
						/>
					</div>
				</div>
			</div>
		</ClayModal>
	);
};

export default Modal;
