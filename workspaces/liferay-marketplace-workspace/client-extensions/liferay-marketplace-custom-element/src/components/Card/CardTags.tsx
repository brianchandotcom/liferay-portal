/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './CardTags.scss';
import {Tag} from '../../components/Tag/Tag';

interface CardTagsProps {
	icon?: string;
	tags: string[];
	title: string;
}

export function CardTags({icon, tags, title}: CardTagsProps) {
	return (
		<div className="card-tags-container">
			<div className="card-tags-main-info">
				<div className="card-tags-icon">
					<img
						alt="Icon"
						className="card-tags-icon-image"
						src={icon}
					/>
				</div>

				<div className="card-tags-info">
					<span className="card-tags-info-text">{title}</span>

					<div className="card-tags-info-tags">
						{tags.map((tag, index) => {
							return <Tag key={index} label={tag}></Tag>;
						})}
					</div>
				</div>
			</div>
		</div>
	);
}
