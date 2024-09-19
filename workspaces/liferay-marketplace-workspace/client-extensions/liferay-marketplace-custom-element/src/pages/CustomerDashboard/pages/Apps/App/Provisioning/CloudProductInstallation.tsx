/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useEffect, useMemo} from 'react';
import {useForm} from 'react-hook-form';
import {useNavigate, useOutletContext} from 'react-router-dom';
import {z} from 'zod';

import FooterButtons from '../../../../../../components/FooterButtons';
import Loading from '../../../../../../components/Loading';
import useMarketplaceSpringBootOAuth2 from '../../../../../../hooks/useMarketplaceSpringBootOAuth2';
import i18n from '../../../../../../i18n';
import {Liferay} from '../../../../../../liferay/liferay';
import zodSchema, {zodResolver} from '../../../../../../schema/zod';
import ProductCard from '../../../../../GetApp/components/ProductCard/ProductCard';
import {convertMegabyteToGigabyte} from '../../../../../GetApp/hooks/useGetResourceInfo';
import {InstallCloudAppOutlet} from './InstallCloudAppOutlet';
import {ExtendBannerProps, StepCloudInstallation} from './Types';
import EnvironmentRadio from './components/EnvironmentRadio';
import ProjectRadio from './components/ProjectRadio';

type UserForm = z.infer<typeof zodSchema.installProductSchema>;

const ExtendBanner: React.FC<ExtendBannerProps> = ({project}) => (
	<div className="align-items-center d-flex justify-content-between">
		<small className="font-weight-bold">
			{i18n.translate('selected-project')}
		</small>

		<div>
			<span className="align-items-end d-flex flex-column">
				<small className="font-weight-bold m-0">
					{project?.rootProjectId.toUpperCase()}
				</small>
				<small className="subscription-banner-text text-nowrap">
					{`${project?.environments.length} environments, ${project?.rootProjectPlanUsage.cpu.free} CPUs, ${convertMegabyteToGigabyte(
						{
							inverseOperation: true,
							value: project?.rootProjectPlanUsage.memory.free,
						}
					)} GB RAM`}
				</small>
			</span>
		</div>
	</div>
);

const CloudProductInstallation = () => {
	const navigate = useNavigate();
	const context = useOutletContext<InstallCloudAppOutlet>();

	const orderInfo = context?.orderInfo.data;
	const placedOrder = orderInfo?.placedOrder;

	const resourceRequest = context?.resourceResponse;
	const productRequirements = context?.productRequirements;

	const verifyAvailabilityToInstall = (
		userProject: any,
		productRequirements: any
	) => {
		const availableCPU = userProject?.rootProjectPlanUsage?.cpu?.free;
		const availableRAM = userProject?.rootProjectPlanUsage?.memory?.free;

		return (
			availableCPU >= productRequirements?.cpu &&
			availableRAM >=
				convertMegabyteToGigabyte({value: productRequirements?.ram})
		);
	};

	const userProjects = useMemo(
		() =>
			resourceRequest?.resourceRequest?.userProjects.map(
				(userProject) => {
					return {
						...userProject,
						availabilityToProduct: verifyAvailabilityToInstall(
							userProject,
							productRequirements
						),
					};
				}
			) ?? [],
		[resourceRequest?.resourceRequest?.userProjects, productRequirements]
	);

	const {setValue, watch} = useForm<UserForm>({
		defaultValues: {
			environment: undefined,
			orderItemId: undefined,
			project: undefined,
			step: StepCloudInstallation.PROJECT,
		},
		resolver: zodResolver(zodSchema.installProductSchema),
	});

	const {environment, orderItemId, project, step} = watch();

	const marketplaceSpringBootOAuth2 = useMarketplaceSpringBootOAuth2();

	useEffect(() => {
		setValue('orderItemId', placedOrder.placedOrderItems[0].id);

		if (userProjects.length === 1) {
			setValue('project', userProjects[0]);
			setValue('step', StepCloudInstallation.ENVIRONMENT);
		}
	}, [userProjects, placedOrder.placedOrderItems, setValue]);

	const stepsInformation: any = {
		[StepCloudInstallation.ENVIRONMENT]: {
			backStep: StepCloudInstallation.PROJECT,
			cardContent: (
				<EnvironmentRadio
					selectedEnvironment={environment}
					selectedProject={project}
					setValue={setValue}
				/>
			),
			cardTitle: i18n.translate('environment-selection'),
			footerHelper: '',
			nextStep: StepCloudInstallation.ENVIRONMENT,
			subTitle: (
				<>
					{`Environments available for `}
					<strong>{project?.rootProjectId?.toUpperCase()}</strong>
				</>
			),
		},
		[StepCloudInstallation.INSTALLATION]: {
			backStep: StepCloudInstallation.PROJECT,
			cardContent: '',
			cardTitle: i18n.translate('installation-in-progress'),
			footerHelper: '',
			nextStep: StepCloudInstallation.ENVIRONMENT,
			subTitle: (
				<div className="align-items-center d-flex flex-column mt-5">
					<div className="mb-4">
						<Loading
							displayType="primary"
							shape="squares"
							size="lg"
						/>
					</div>

					<div className="col-7 mt-8 text-center">
						<p>
							{i18n.translate(
								'the-installation-process-is-underway-and-should-be-completed-shortly'
							)}
						</p>
					</div>
				</div>
			),
		},
		[StepCloudInstallation.PROJECT]: {
			backStep: StepCloudInstallation.PROJECT,
			cardContent: (
				<ProjectRadio
					productRequirements={productRequirements}
					projects={userProjects}
					selectedProject={project}
					setValue={setValue}
				/>
			),
			cardTitle: i18n.translate('project-selection'),
			footerHelper: (
				<p className="secondary-text">
					{`${i18n.translate('not-seeing-a-specific-project')} `}
					<a
						className="font-weight-bold project-selection-page-link"
						href=""
						target="_blank"
					>
						{i18n.translate('contact-support')}
					</a>
				</p>
			),
			nextStep: StepCloudInstallation.ENVIRONMENT,
			subTitle: (
				<>
					{`Projects available for `}
					<strong>
						{Liferay.ThemeDisplay.getUserEmailAddress()}
					</strong>
					<span>{` (you)`}</span>
				</>
			),
		},
		[StepCloudInstallation.SUCCESS]: {
			backStep: StepCloudInstallation.INSTALLATION,
			cardContent: (
				<div className="align-items-center d-flex flex-column mt-5">
					<div className="mb-6">
						<ClayIcon
							color="#4AAB3B"
							fontSize="4rem"
							symbol="check-circle-full"
						/>
					</div>

					<div className="col-7 text-center">
						<p>
							{i18n.translate(
								'you-can-view-your-app-in-cloud-console-or-go-back-to-my-apps'
							)}
						</p>
					</div>
				</div>
			),
			cardTitle: i18n.translate('installation-success'),
			footerHelper: '',
			nextStep: StepCloudInstallation.ENVIRONMENT,
			subTitle: '',
		},
	};

	const buttonsInfo = useMemo(() => {
		const isFinalStep = step === StepCloudInstallation.INSTALLATION;
		const _onSubmit = async () => {
			await marketplaceSpringBootOAuth2.provisioningCloudApp(
				placedOrder.id,
				{
					orderItemId,
					projectId: environment,
				}
			);
		};

		const handleNextStep = () => {
			switch (step) {
				case StepCloudInstallation.PROJECT:
					setValue('step', StepCloudInstallation.ENVIRONMENT);
					break;
				case StepCloudInstallation.ENVIRONMENT:
					_onSubmit();

					setValue('step', StepCloudInstallation.INSTALLATION);

					setTimeout(() => {
						setValue('step', StepCloudInstallation.SUCCESS);
					}, 5000);
					break;
				case StepCloudInstallation.SUCCESS:
					window.open(
						`https://console.marketplace.liferay.sh/projects/${environment}/services`
					);

					break;
				default:
					break;
			}
		};

		return {
			cancelButton: {
				displayType: 'unstyled',
				onClick: () => navigate('..'),
				show:
					step !== StepCloudInstallation.INSTALLATION &&
					step !== StepCloudInstallation.SUCCESS,
			},
			customizedButton: {
				displayType: 'secondary',
				onClick: () =>
					step === StepCloudInstallation.ENVIRONMENT
						? setValue('step', StepCloudInstallation.PROJECT)
						: navigate('..'),
				show:
					step === StepCloudInstallation.ENVIRONMENT ||
					step === StepCloudInstallation.SUCCESS,
				text:
					step === StepCloudInstallation.ENVIRONMENT
						? 'Back'
						: 'Go to My Apps',
			},
			nextButton: {
				className: 'ml-6',
				disabled:
					step === StepCloudInstallation.PROJECT
						? !project?.availabilityToProduct
						: step === StepCloudInstallation.ENVIRONMENT &&
							!environment,
				displayType: 'primary',
				onClick: handleNextStep,
				show: !isFinalStep,
				text:
					step === StepCloudInstallation.ENVIRONMENT
						? 'Install'
						: 'View app in Cloud',
			},
		};
	}, [
		environment,
		marketplaceSpringBootOAuth2,
		navigate,
		orderItemId,
		placedOrder.id,
		project,
		setValue,
		step,
	]);

	return (
		<div className="align-items-center d-flex flex-column mb-6 mkt-create-license mt-6">
			<div className="mt-6 product-card-content">
				<ProductCard
					ExtendBanner={<ExtendBanner project={project} />}
					RightSideBanner={
						<div className="d-flex flex-column">
							<div>Standard License</div>
							<div className="d-flex justify-content-end">
								{`${productRequirements.cpu} CPUs, ${productRequirements.ram} GB RAM`}
							</div>
						</div>
					}
					creatorAccountName={orderInfo?.product.catalogName}
					product={orderInfo?.product as DeliveryProduct}
					showExtendBanner={!!project}
				/>
			</div>

			<div className="d-flex flex-column generate-license-content justify-content-center mb-7 mt-7 p-6">
				<div className="align-self-center h1">
					{stepsInformation[step].cardTitle}
				</div>

				<div className="my-4 secondary-text">
					{stepsInformation[step].subTitle}
				</div>

				<div>{stepsInformation[step].cardContent}</div>

				<div>{stepsInformation[step].footerHelper}</div>

				<FooterButtons
					className="d-flex justify-content-between mt-6"
					dataButtons={buttonsInfo}
				/>
			</div>
		</div>
	);
};

export default CloudProductInstallation;
