import ClayButton from '@clayui/button';

import arrowDown from '../../assets/icons/arrow_down_icon.svg';
import documentIcon from '../../assets/icons/document_icon.svg';
import folderIcon from '../../assets/icons/folder_fill_icon.svg';
import unitedStatesIcon from '../../assets/icons/united_states_icon.svg';
import {CardLink} from '../../components/Card/CardLink';
import {CardTags} from '../../components/Card/CardTags';
import {CardView} from '../../components/Card/CardView';
import {RequiredMask} from '../../components/FieldBase';
import {UploadedFile} from '../../components/FileList/FileList';
import {LicensePriceChildren} from '../../components/LicensePriceCard/LicensePriceChildren';
import {Tag} from '../../components/Tag/Tag';
import {Tooltip} from '../../components/Tooltip/Tooltip';
import {useAppContext} from '../../manage-app-state/AppManageState';

import './CardSection.scss';

interface CardSectionProps {
	build?: boolean;
	buildZIPTitles?: string[];
	cardDescription?: string;
	cardInfos?: {icon: string; link: string; title: string}[];
	cardLink?: boolean;
	cardTags?: {icon: string; tags: string[]; title: string}[];
	cardTitle?: string;
	cardView?: boolean;
	description?: string;
	enableEdit?: boolean;
	files?: UploadedFile[];
	icon?: string;
	localized?: boolean;
	paragraph?: string;
	price?: string;
	required?: boolean;
	sectionName?: string;
	storefront?: boolean;
	tags?: string[];
	title?: string;
	version?: string | null;
}

export function CardSection({
	build,
	buildZIPTitles,
	cardDescription,
	cardInfos,
	cardLink,
	cardTags,
	cardTitle,
	cardView,
	description,
	enableEdit = true,
	files,
	icon,
	localized,
	paragraph,
	price,
	required,
	sectionName,
	storefront,
	tags,
	title,
	version,
}: CardSectionProps) {
	const priceData = {
		currency: {
			icon: unitedStatesIcon,
			name: 'USA',
		},
		quantity: {
			from: '1',
			to: '1',
		},
	};
	const [{priceModel}] = useAppContext();

	return (
		<div className="card-section-body-section">
			<div className="card-section-body-section-header">
				<span className="card-section-body-section-header-title">
					{sectionName}

					{required && <RequiredMask />}
				</span>

				<div className="card-section-body-section-header-actions">
					{localized && (
						<div className="field-base-localized-field">
							<ClayButton displayType={null}>
								English (US)
								<img
									className="arrow-down-icon"
									src={arrowDown}
								/>
							</ClayButton>

							<>
								&nbsp;
								<Tooltip tooltip="choose a language" />
							</>
						</div>
					)}

					{enableEdit && (
						<ClayButton className="edit-button" displayType={null}>
							Edit
						</ClayButton>
					)}
				</div>
			</div>

			{tags && (
				<div className="card-section-body-section-tags">
					{tags.map((tag, index) => {
						return <Tag key={index} label={tag}></Tag>;
					})}
				</div>
			)}

			{paragraph && (
				<p className="card-section-body-section-paragraph">
					{paragraph}
				</p>
			)}

			{build &&
				buildZIPTitles?.map((buildZIPTitle: string, index) => (
					<div className="card-section-body-section-file" key={index}>
						<div className="card-section-body-section-file-container">
							<img
								alt="Folder Icon"
								className="card-section-body-section-file-container-icon"
								src={folderIcon}
							/>
						</div>

						<img
							alt="Document Icon"
							className="card-section-body-section-file-icon"
							src={documentIcon}
						/>

						<span className="card-section-body-section-file-name">
							{buildZIPTitle}
						</span>
					</div>
				))}

			{cardView && (
				<CardView
					description={cardDescription as string}
					icon={icon}
					title={cardTitle as string}
				>
					{sectionName === 'Licensing' &&
					priceModel.value !== 'Free' ? (
						<LicensePriceChildren
							currency={priceData.currency}
							quantity={priceData.quantity}
							value={price as string}
						/>
					) : (
						''
					)}
				</CardView>
			)}

			{storefront && (
				<div>
					{files?.map(({fileName, id, preview}) => {
						return (
							<div
								className="card-section-body-section-files"
								key={id}
							>
								<div className="card-section-body-section-files-container">
									<img
										className="preview-image"
										style={{
											backgroundImage: `url(${preview})`,
										}}
									/>
								</div>

								<div className="card-section-body-section-files-data">
									<img
										alt={fileName}
										className="card-section-body-section-files-data-icon"
										src={documentIcon}
									/>

									<span className="card-section-body-section-files-data-name">
										{fileName}
									</span>

									<span className="card-section-body-section-files-data-description"></span>
								</div>
							</div>
						);
					})}

					<div className="card-section-body-section-files-info">
						Important: Images will be displayed following the
						numerical order above
					</div>
				</div>
			)}

			{version && (
				<div className="card-section-body-section-version">
					<div className="card-section-body-section-version-container">
						<div className="card-section-body-section-version-container-icon">
							{version}
						</div>
					</div>

					<div className="card-section-body-section-version-data">
						<span className="card-section-body-section-version-data-name">
							{title}
						</span>

						<span className="card-section-body-section-version-data-description">
							{description}
						</span>
					</div>
				</div>
			)}

			{cardLink &&
				cardInfos?.map(({icon, link, title}) => {
					return (
						<CardLink
							description={link as string}
							icon={icon}
							key={title}
							title={title as string}
						/>
					);
				})}

			{cardTags &&
				cardTags?.map(({icon, tags, title}) => {
					return (
						<CardTags
							icon={icon}
							key={title}
							tags={tags}
							title={title as string}
						/>
					);
				})}
		</div>
	);
}
