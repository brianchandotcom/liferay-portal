/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Link, Outlet} from 'react-router-dom';

import AppToolbar from '../../../../../components/AppToolbar/AppToolbar';
import {useAccount} from '../../../../../hooks/data/useAccounts';

import './PublishSolutionOutlet.scss';

import 'react-quill/dist/quill.snow.css';
import {useModal} from '@clayui/modal';
import {useMemo} from 'react';

import Modal from '../../../../../components/Modal';
import {useSolutionContext} from '../../../../../context/SolutionContext';
import usePublishSolutionHeader from '../../../hooks/usePublishSolutionHeader';
import usePublishSolutionNavigation from '../../../hooks/usePublishSolutionNavigation';
import usePublishSolutionSubmission from '../../../hooks/usePublishSolutionSubmission';
import PublishNav from '../components/PublishNav';

const PublishSolutionOutlet = () => {
	usePublishSolutionHeader();

	const {data: account} = useAccount();
	const [context, dispatch] = useSolutionContext();

	const {
		activeIndex,
		activeRoute,
		onClickContinue,
		onClickPrevious,
		onExit,
		publishSolutionSteps,
	} = usePublishSolutionNavigation();

	const {onSaveAsDraft} = usePublishSolutionSubmission(context, dispatch);

	const {observer, onOpenChange, open} = useModal();

	const parsedSchema = useMemo(() => {
		const parseSchema = activeRoute?.parseSchema;

		if (parseSchema) {
			return parseSchema(context);
		}

		return null;
	}, [activeRoute, context]);

	return (
		<>
			<AppToolbar
				accountImage={account?.logoURL}
				accountName={account?.name as string}
				appImage={context.profile.file?.preview}
				appName={context.profile.name}
				display={{preview: true, saveAsDraft: true}}
				exitProps={{
					onClick: () => {
						onOpenChange(true);
					},
					to: undefined as any,
				}}
				previewProps={{
					disabled: true,
					onClick: () => alert('Preview...'),
				}}
				saveAsDraftProps={{
					disabled: activeIndex < 1,
					onClick: onSaveAsDraft,
				}}
			/>
			<details>
				<pre>{JSON.stringify(context._product, null, 2)}</pre>
			</details>

			<hr />

			<div className="d-flex justify-content-center mt-8">
				<PublishNav
					activeIndex={activeIndex}
					items={publishSolutionSteps}
				/>

				<div className="ml-8 solutions-body-container">
					<h1 className="header-title mb-4">{activeRoute.title}</h1>
					{activeRoute.description}

					<details>
						<pre>{JSON.stringify(context.profile, null, 2)}</pre>
					</details>

					<div className="mt-6 solutions-form">
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
								Back
							</ClayButton>
						)}

						<ClayButton
							disabled={
								parsedSchema ? !parsedSchema.success : false
							}
							displayType="primary"
							onClick={onClickContinue}
						>
							Continue
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
							Save as a draft & exit
						</ClayButton>

						<Link
							className="btn btn-primary ml-2"
							to="../solutions"
						>
							Exit
						</Link>
					</>
				}
				observer={observer}
				size={'md' as any}
				title="Exit from creating a solution"
				visible={open}
			>
				<p>
					All progress and information related to the creation of the
					solution will be lost unless you save the solution as a
					draft, Do you still want to exit?
				</p>
			</Modal>
		</>
	);
};

export default PublishSolutionOutlet;
