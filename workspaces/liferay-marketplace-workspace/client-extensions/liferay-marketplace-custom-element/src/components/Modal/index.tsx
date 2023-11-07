/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import {ReactNode} from 'react';
import {Observer, Size} from '@clayui/modal/lib/types';
import FooterButtons, {ButtonProps} from '../FooterButtons';
import {ClayButtonWithIcon} from '@clayui/button';

type ButtonInfoProps = {
	cancelButton: ButtonProps;
	customizedButton: ButtonProps;
	nextButton: ButtonProps;
};

type ModalProps = {
	buttonsInfo: ButtonInfoProps;
	children: ReactNode;
	observer: Observer;
	onClose: () => void;
	size: Size;
};

const Modal = ({
	children,
	observer,
	onClose,
	buttonsInfo,
	size,
}: ModalProps) => {
	return (
		<ClayModal center observer={observer} size={size} status="info">
			<div>
				<ClayButtonWithIcon
					className="float-right mt-2 mr-2"
					aria-label="Close"
					displayType="unstyled"
					symbol="times"
					title="Close"
					onClick={onClose}
					size="sm"
				/>

				<div className="pt-4 px-4">
					{children}

					<div className="mb-5">
						<FooterButtons
							className="d-flex justify-content-between mt-6"
							dataButtons={buttonsInfo}
							onClickCancel={onClose}
						/>
					</div>
				</div>
			</div>
		</ClayModal>
	);
};

export default Modal;
