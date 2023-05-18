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
