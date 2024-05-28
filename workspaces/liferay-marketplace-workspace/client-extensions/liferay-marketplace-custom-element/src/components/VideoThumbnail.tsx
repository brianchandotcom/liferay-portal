/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {ReactNode} from 'react';

import './VideoThumbnail.scss';

type VideoThumbnailProps = {
	videoURL: string;
};

const getThumbnail = (videoURL: string) => {
	if (!videoURL?.startsWith('https://')) {
		return '';
	}

	try {
		const url = new URL(videoURL);

		if (url && url.hostname.includes('youtube.com')) {
			const videoId = url.searchParams.get('v');

			return videoId ? `https://img.youtube.com/vi/${videoId}/0.jpg` : '';
		}
	}
	catch {}

	return '';
};

const Wrapper: React.FC<{children: ReactNode}> = ({children}) => (
	<div className="align-items-center d-flex justify-content-center position-relative video-player">
		{children}
	</div>
);

const VideoThumbnail: React.FC<VideoThumbnailProps> = ({videoURL}) => {
	const thumbnail = getThumbnail(videoURL);

	if (!thumbnail) {
		return (
			<Wrapper>
				<ClayIcon aria-label="video thumbnail empty" symbol="video" />
			</Wrapper>
		);
	}

	return (
		<Wrapper>
			<a
				className="align-items-center d-flex justify-content-center position-relative"
				href={videoURL}
				target="_blank"
			>
				<img
					aria-label="video-thumbnail"
					className="video-preview"
					src={getThumbnail(videoURL)}
				/>

				<ClayIcon
					aria-label="video thumbnail empty"
					className="video-thumbnail-play-symbol"
					symbol="video"
				/>
			</a>
		</Wrapper>
	);
};

export default VideoThumbnail;
