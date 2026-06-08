/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React, {useRef, useState} from 'react';
import Loading, {Align} from '~/shared/components/Loading';
import Form from '~/shared/components/form';
import Label from '~/shared/components/form/Label';

interface IInputWithEditToggleProps {
	className?: string;
	editable: boolean;
	inputWidth?: number;
	label?: string;
	name?: string;
	onSubmit: (value: any, name: string) => Promise<any>;
	required: boolean;
	validate: (value: any) => Promise<any>;
	value: string;
}

const InputWithEditToggle: React.FC<IInputWithEditToggleProps> = ({
	className,
	editable = true,
	inputWidth,
	label,
	name = 'name',
	onSubmit,
	required = false,
	validate,
	value,
}) => {
	const [editing, setEditing] = useState(false);

	const _formRef = useRef<any>(null);

	const handleSubmit = (values: any) => {
		const {resetForm, setSubmitting} = _formRef.current;

		if (onSubmit) {
			onSubmit(values[name], name)
				.then(() => {
					setSubmitting(false);

					resetForm();

					setEditing(false);
				})
				.catch((error) => {
					if (!error.IS_CANCELLATION_ERROR) {
						setSubmitting(false);
					}
				});
		}
	};

	return (
		<div
			className={getCN(
				'input-with-edit-toggle-root',
				'definition-item-root',
				className
			)}
		>
			{!editing && (
				<div className="d-inline-flex">
					<Text size={8} weight="bold">
						{value}
					</Text>

					<ClayButton
						aria-label={Liferay.Language.get('edit')}
						borderless
						className="button-root"
						data-testid="edit"
						disabled={!editable}
						displayType="secondary"
						onClick={() => setEditing(true)}
						size="sm"
					>
						<ClayIcon className="icon-root" symbol="pencil" />
					</ClayButton>
				</div>
			)}

			{editing && (
				<Form
					initialValues={{[name]: value}}
					innerRef={_formRef as any}
					onSubmit={handleSubmit}
				>
					{({handleSubmit, isSubmitting, isValid, resetForm}) => (
						<Form.Form
							className="input-with-edit-toggle-editor"
							onSubmit={handleSubmit}
						>
							{label && (
								<Label required={required}>{label}</Label>
							)}

							<Form.Group autoFit className="align-items-center">
								<Form.Input
									contentAfter={
										<>
											<ClayButton
												aria-label={Liferay.Language.get(
													'cancel'
												)}
												className="button-root"
												displayType="secondary"
												onClick={() => {
													setEditing(false);

													resetForm();
												}}
												size="sm"
											>
												<ClayIcon
													className="icon-root"
													symbol="times"
												/>
											</ClayButton>

											<ClayButton
												aria-label={Liferay.Language.get(
													'submit'
												)}
												className="button-root"
												disabled={!isValid}
												displayType="primary"
												size="sm"
												type="submit"
											>
												{isSubmitting && (
													<Loading
														align={Align.Left}
													/>
												)}

												<ClayIcon
													className="icon-root"
													symbol="check"
												/>
											</ClayButton>
										</>
									}
									disabled={
										!editing || !editable || isSubmitting
									}
									name={name}
									validate={validate}
									width={inputWidth}
								/>
							</Form.Group>
						</Form.Form>
					)}
				</Form>
			)}
		</div>
	);
};

export default InputWithEditToggle;
