/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import getCN from 'classnames';
import {every, noop} from 'lodash';
import React, {useRef, useState} from 'react';
import ToggleSwitch from '~/shared/components/ToggleSwitch';
import Form from '~/shared/components/form';
import Modal from '~/shared/components/modal';

interface IToggleSwitchModalProps {
	className?: string;
	items: string[];
	message?: string;
	onClose: () => void;
	onSubmit: (values: {[key: string]: any}) => void;
	title?: string;
	toggleAllMessage?: string;
}

const ToggleSwitchModal: React.FC<IToggleSwitchModalProps> = ({
	className,
	items,
	message,
	onClose = noop,
	onSubmit = noop,
	title,
	toggleAllMessage,
	...otherProps
}) => {
	const _formRef = useRef<any>(null);

	const [checkAll, setCheckAll] = useState(false);

	const handleFormChange = (event: React.FormEvent<HTMLInputElement>) => {
		const {checked, name} = event.target as HTMLInputElement;

		const {values} = _formRef.current;

		setCheckAll(every({...values, [name]: checked}, Boolean));
	};

	const handleSelectAllChange = (
		event: React.FormEvent<HTMLInputElement>
	) => {
		const {checked} = event.target as HTMLInputElement;

		const {setFieldValue, values} = _formRef.current;

		Object.keys(values).map((key) => setFieldValue(key, checked));

		setCheckAll(checked);
	};

	return (
		<Modal
			{...otherProps}
			className={getCN('toggle-switch-modal-root', className)}
		>
			{title && <Modal.Header onClose={onClose} title={title} />}

			<Modal.Body>{message}</Modal.Body>

			{toggleAllMessage && (
				<div className="toggle-all">
					<ToggleSwitch
						checked={checkAll}
						label={toggleAllMessage}
						name="toggleAll"
						onChange={handleSelectAllChange}
					/>
				</div>
			)}

			<Form
				initialValues={items.reduce<{[key: string]: boolean}>(
					(acc, item) => {
						acc[item] = false;

						return acc;
					},
					{}
				)}
				innerRef={_formRef as any}
				onSubmit={onSubmit}
			>
				{({handleSubmit}) => (
					<Form.Form
						onChange={handleFormChange}
						onSubmit={handleSubmit}
					>
						{items.map((item) => (
							<Form.Group key={item}>
								<Form.ToggleSwitch label={item} name={item} />
							</Form.Group>
						))}

						<Modal.Footer>
							<ClayButton
								className="button-root"
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								className="button-root"
								displayType="primary"
								type="submit"
							>
								{Liferay.Language.get('done')}
							</ClayButton>
						</Modal.Footer>
					</Form.Form>
				)}
			</Form>
		</Modal>
	);
};

export default ToggleSwitchModal;
