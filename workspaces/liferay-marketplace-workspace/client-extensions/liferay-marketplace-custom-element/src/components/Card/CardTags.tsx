/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
