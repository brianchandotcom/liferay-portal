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

import React from 'react';

function Comic({oAuth2Client}) {
	const [comicData, setComicData] = React.useState(null);

	React.useEffect(() => {
		const request = oAuth2Client.fetch('/comic').then((comic) => {
			setComicData({
				alt: comic.alt,
				img: comic.img,
				title: comic.safe_title,
			});
		});

		return () => request.cancel();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return !comicData ? (
		<div>Loading...</div>
	) : (
		<div>
			<h2>{comicData.title}</h2>

			<p>
				<img alt={comicData.alt} src={comicData.img} />
			</p>
		</div>
	);
}

export default Comic;
