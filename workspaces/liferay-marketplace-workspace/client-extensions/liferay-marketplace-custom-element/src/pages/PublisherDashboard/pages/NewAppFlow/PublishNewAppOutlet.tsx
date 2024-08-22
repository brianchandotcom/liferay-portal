/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Link, Outlet} from 'react-router-dom';

import 'react-quill/dist/quill.snow.css';
import {useModal} from '@clayui/modal';
import {useMemo} from 'react';
import { PRODUCT_WORKFLOW_STATUS_CODE } from '../../../../enums/Product';
import { useAccount } from '../../../../hooks/data/useAccounts';
import usePublishSolutionHeader from '../../hooks/usePublishSolutionHeader';
import { useNewAppContext } from '../../../../context/NewAppContext';
import AppToolbar from '../../../../components/AppToolBar/AppToolBar';
import PublishNav from '../Solutions/components/PublishNav';
import i18n from '../../../../i18n';
import Modal from '../../../../components/Modal';
import usePublishAppNavigation from '../../hooks/usePublishAppNavigation';
import usePublishAppSubmission from '../../hooks/usePublishAppSubmission';

import './PublishNewAppOutlet.scss';

const PublishNewAppOutlet = () => {
	usePublishSolutionHeader();

	const {data: account} = useAccount();
	const [context, dispatch] = useNewAppContext();

	const {
		activeIndex,
		activeRoute,
		isLastStep,
		onClickContinue,
		onClickPrevious,
		onExit,
		publishAppSteps,
	} = usePublishAppNavigation();

	const {onSave, onSaveAsDraft} = usePublishAppSubmission(
		context,
		dispatch
	);

	const {observer, onOpenChange, open} = useModal();
	const onExitModal = useModal();

	const parsedSchema = useMemo(() => {
		const parseSchema = activeRoute?.parseSchema;

		if (parseSchema) {
			return parseSchema(context);
		}

		return null;
	}, [activeRoute, context]);

	const isDisabled = parsedSchema ? !parsedSchema.success : false;

	const isDraft = (status?: number) =>
		status === PRODUCT_WORKFLOW_STATUS_CODE.DRAFT;

	const isSaveAsDraft =
		!context._product || isDraft(context._product.productStatus);

	return (
		<>
			<AppToolbar
				accountImage={account?.logoURL}
				accountName={account?.name as string}
				appImage={context.profile.file?.preview}
				appName={context.profile.name}
				display={{
					preview: true,
					saveAsDraft: isSaveAsDraft,
					submit:
						!!context._product &&
						!isDraft(context._product.productStatus),
				}}
				exitProps={{
					onClick: () => {
						isSaveAsDraft
							? onOpenChange(true)
							: onExitModal.onOpenChange(true);
					},
				}}
				previewProps={{
					disabled: false,
					onClick: () => alert('Preview...'),
				}}
				saveAsDraftProps={{
					disabled: isDisabled,
					onClick: onSaveAsDraft,
				}}
				submitProps={{
					onClick: onSave,
				}}
			/>

			<hr />

			<div className="d-flex justify-content-center mt-8">
				<PublishNav
					activeIndex={activeIndex}
					items={publishAppSteps}
				/>

				<div className="ml-8 new-app-body-container">
					<h1 className="header-title mb-4">{activeRoute.title}</h1>
					{activeRoute.description}

					<div className="mt-6 new-app-form">
						<Outlet />
					</div>
					<hr className="my-6" />
					<div className="d-flex justify-content-end">
						{activeIndex !== 0 && (
							<ClayButton
								className="mr-4"
								displayType="secondary"
								onClick={onClickPrevious}
							>
								{i18n.translate('back')}
							</ClayButton>
						)}

						<ClayButton
							disabled={isDisabled}
							displayType="primary"
							onClick={async () => {
								if (isLastStep) {
									return onSave().then(onExit);
								}

								onClickContinue();
							}}
						>
								{isLastStep ? 'Submit App' : 'Continue'}
							
						</ClayButton>
					</div>
				</div>
			</div>

			<Modal
				last={
					<>
						<ClayButton
							displayType="secondary"
							onClick={() => onSaveAsDraft().then(onExit)}
						>
							{i18n.translate('save-as-a-draft-exit')}
						</ClayButton>

						<Link className="btn btn-primary ml-2" to="/">
							{i18n.translate('exit')}
						</Link>
					</>
				}
				observer={observer}
				size={'md' as any}
				title="Exit from creating an app"
				visible={open}
			>
				<p>
					{i18n.translate(
						'all-progress-and-information-related-to-the-creation-of-the-app-will-be-lost-unless-you-save-the-app-as-a-draft-do-you-still-want-to-exit'
					)}
				</p>
			</Modal>

			{onExitModal.open && (
				<Modal
					last={
						<>
							<ClayButton
								className="btn btn-primary ml-2"
								displayType="primary"
								onClick={onExit}
							>
								{i18n.translate('exit')}
							</ClayButton>
						</>
					}
					observer={onExitModal.observer}
					size={'md' as any}
					title="Exit from creating an App"
					visible={onExitModal.open}
				>
					<p>
						{i18n.translate(
							'all-progress-and-information-related-to-the-creation-of-the-app-will-be-lost-do-you-still-want-to-exit'
						)}
					</p>
				</Modal>
			)}
		</>
	);
};

export default PublishNewAppOutlet;
