/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown, {Align} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {useModal} from '@clayui/modal';
import {useEffect, useRef, useState} from 'react';
import {UseFormReturn} from 'react-hook-form';

import useSiteDocuments from '../../hooks/useSiteDocuments';
import {Schema} from '../AIWizard';
import ChatFileModal from './ChatFileModal';

type Props = {
	form: UseFormReturn<Schema>;
	onSubmit: (data: Schema) => void;
	placeholder?: string;
};

export default function ChatInput(props: Props) {
	const [selectedTree, setSelectedTree] = useState<any>();
	const {formState, handleSubmit, register, setValue, watch} = props.form;
	const formRef = useRef<HTMLFormElement>(null);
	const [image, setImage] = useState('');
	const modal = useModal();
	const files = watch('files');
	const inputRef = useRef<HTMLInputElement>(null);
	const text = watch('input');
	const {data: response} = useSiteDocuments();

	useEffect(() => {
		const modalContainer = document.querySelector('.ai-parent-modal div');

		if (!modal.open && modalContainer) {
			modalContainer.removeAttribute('inert');
		}
	}, [modal.open]);

	const handleKeyDown = (event: any) => {
		if (event.key === 'Enter') {
			if (!event.shiftKey && text.trim() !== '') {
				event.preventDefault();
				formRef.current?.requestSubmit();
			}
		}
	};

	const onChoose = () => {
		setValue('files', [
			...files,
			{type: 'fileEntryId', value: selectedTree.image},
		]);

		modal.onClose();
	};

	const resetInputs = () => {
		setImage('');

		if (inputRef.current) {
			inputRef.current.value = '';
		}
	};

	return (
		<ClayForm
			className="d-flex w-100"
			onSubmit={(event) =>
				handleSubmit(props.onSubmit)(event).then(resetInputs)
			}
			ref={formRef}
		>
			{modal.open && (
				<ChatFileModal
					items={response?.data?.documents?.items ?? []}
					modal={modal}
					onChoose={onChoose}
					selectedTree={selectedTree}
					setSelectedTree={setSelectedTree}
				/>
			)}

			<ClayLayout.ContentRow className="w-100">
				<ClayLayout.ContentCol expand>
					<ClayInput
						{...register('input')}
						component="textarea"
						disabled={formState.isSubmitting || formState.isLoading}
						onKeyDown={handleKeyDown}
						placeholder={
							props.placeholder ||
							'Ask the Assistant to create a Liferay Asset'
						}
						value={text}
					/>

					<ClayInput
						{...register('image')}
						id="wizard-content-image"
						type="hidden"
						value={image}
					/>
					<ClayInput
						className="d-none"
						onChange={(event) => {
							if (event.target.files?.[0]) {
								const reader = new FileReader();
								reader.readAsDataURL(event.target.files[0]);
								reader.onload = () => {
									setImage(reader.result as string);
								};
							}
						}}
						ref={inputRef}
						type="file"
					/>

					<div className="d-flex my-2">
						{image ? <img src={image} width={150} /> : null}
					</div>

					{!!files.length && (
						<div className="d-flex my-2">
							{files.map((file, index) => (
								<img
									className="border mr-1 p-1 rounded"
									draggable={false}
									height={60}
									key={index}
									src={(file as any).value}
									width={60}
								/>
							))}
						</div>
					)}
				</ClayLayout.ContentCol>
				<ClayLayout.ContentCol>
					<ClayLayout.ContentSection>
						<ClayButton.Group>
							<DropDown
								alignmentPosition={Align.BottomLeft}
								trigger={
									<ClayButton
										aria-label="Submit Prompt button"
										borderless
										disabled={
											formState.isSubmitting ||
											formState.isLoading
										}
									>
										<ClayIcon
											aria-label="Submit Prompt"
											color="gray"
											symbol="upload-multiple"
										/>
									</ClayButton>
								}
							>
								<DropDown.ItemList>
									<DropDown.Item
										onClick={() => {
											modal.onOpenChange(true);
										}}
									>
										<ClayIcon
											aria-label="Picture Icon"
											className="mr-2"
											symbol="picture"
										/>
										Choose from Docs & Media
									</DropDown.Item>
									<DropDown.Item
										onClick={() => {
											inputRef.current?.click();
										}}
									>
										<ClayIcon
											className="mr-2"
											symbol="display"
										/>
										Upload from your computer
									</DropDown.Item>
								</DropDown.ItemList>
							</DropDown>

							<ClayButton
								aria-label="Submit button"
								disabled={
									formState.isSubmitting ||
									formState.isLoading ||
									!text?.trim()?.length
								}
								displayType="primary"
								type="submit"
							>
								<ClayIcon
									aria-label="Submit Prompt"
									symbol="order-arrow-right"
								/>
							</ClayButton>
						</ClayButton.Group>
					</ClayLayout.ContentSection>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</ClayForm>
	);
}
