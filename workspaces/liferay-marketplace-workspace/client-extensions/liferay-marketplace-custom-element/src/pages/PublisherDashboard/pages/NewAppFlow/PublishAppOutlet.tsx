/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import {useMemo} from 'react';
import {Link, Outlet} from 'react-router-dom';

import AppPublish from '../../../../components/AppPublish';
import Modal from '../../../../components/Modal';
import {useNewAppContext} from '../../../../context/NewAppContext';
import {ProductWorkflowStatusCode} from '../../../../enums/Product';
import {useAccount} from '../../../../hooks/data/useAccounts';
import i18n from '../../../../i18n';
import usePublishAppSubmission from '../../hooks/usePublishAppSubmission';
import usePublishHeader from '../../hooks/usePublishHeader';
import usePublishNavigation from '../../hooks/usePublishNavigation';
import {APP_FLOW_ITEMS} from './constants';

import './PublishAppOutlet.scss';

const isDraft = (status: number) => status === ProductWorkflowStatusCode.DRAFT;

const PublishAppOutlet = () => {
	usePublishHeader();

	const {data: account} = useAccount();
	const [context, dispatch] = useNewAppContext();

	const isDraft = (status: number) =>
		status === ProductWorkflowStatusCode.DRAFT;

	const isSaveAsDraft = isDraft(
		context._product ? context._product.productStatus : 0
	);

	const getFlowItems = () => {
		return APP_FLOW_ITEMS.filter((item) => item.visible(context));
	};

	const {
		activeIndex,
		activeRoute,
		isLastStep,
		onClickContinue,
		onClickPrevious,
		onExit,
		steps,
	} = usePublishNavigation({
		exitLink: '/',
		flowItems: getFlowItems(),
	});

	const {onSave, onSaveAsDraft} = usePublishAppSubmission(context, dispatch);

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

	if (context.loading) {
		return null;
	}

	return (
		<>
			{!context.loading && (
				<AppPublish>
					<AppPublish.Navbar
						accountImage={account?.logoURL}
						accountName={account?.name as string}
						appImage={context.profile.file?.preview}
						appName={context.profile.name}
						appStatus={context._product?.productStatus}
						display={{
							preview: true,
							saveAsDraft: !isSaveAsDraft,
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
							disabled: isDisabled || isSaveAsDraft,
							onClick: onSaveAsDraft,
						}}
						submitProps={{
							onClick: onSave,
						}}
					/>

					<AppPublish.Body>
						<AppPublish.Sidebar
							activeIndex={activeIndex}
							items={steps}
						/>

						<AppPublish.Content>
							<h1 className="header-title mb-4">
								{activeRoute.title}
							</h1>

							<p>{activeRoute.description}</p>

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
									onClick={() => {
										if (isLastStep) {
											return onSave().then(onExit);
										}

										onClickContinue();
									}}
								>
									{i18n.translate(
										isLastStep ? 'submit' : 'continue'
									)}
								</ClayButton>
							</div>
						</AppPublish.Content>
					</AppPublish.Body>

					<Modal
						last={
							<>
								<ClayButton
									displayType="secondary"
									disabled={isDisabled || isSaveAsDraft}
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
								<ClayButton
									className="btn btn-primary ml-2"
									displayType="primary"
									onClick={onExit}
								>
									{i18n.translate('exit')}
								</ClayButton>
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
				</AppPublish>
			)}
		</>
	);
};

export default PublishAppOutlet;
