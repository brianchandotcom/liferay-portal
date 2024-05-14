/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import VideoThumbnail from '../../../../../../components/VideoThumbnail';

interface ContentReviewVideoProps {
	className?: string;
	videoDescription?: string | undefined;
	videoUrl: string;
}

export function ContentReviewVideo({
	className,
	videoDescription,
	videoUrl,
}: ContentReviewVideoProps) {
	return (
		<div className={classNames('d-flex', className)}>
			<VideoThumbnail videoURL={videoUrl} />
			<div className="d-flex flex-column ml-3 pt-1">
				<ClayIcon
					className="mb-3"
					color="#0B5FFF"
					fontSize={16}
					symbol="document-multimedia"
				/>
				<p className="font-weight-bold mb-1">{videoUrl}</p>
				{videoDescription && <p>{videoDescription}</p>}
			</div>
		</div>
	);
}
