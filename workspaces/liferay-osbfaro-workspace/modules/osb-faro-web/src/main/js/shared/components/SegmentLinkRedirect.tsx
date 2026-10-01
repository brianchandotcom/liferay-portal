import * as API from 'shared/api';
import ErrorPage from 'shared/pages/ErrorPage';
import Loading from 'shared/components/Loading';
import React, {useEffect, useState} from 'react';
import {Routes, toRoute} from 'shared/util/router';
import {useNavigate, useParams} from 'react-router-dom';

interface ISegmentLinkRedirectProps {
	children: React.ReactNode;
}

/**
 * Segment links that leave out the channel still resolve: the segment is
 * fetched to learn its channel, and the URL is replaced with the one that
 * names it. A segment without a channel has no page to land on.
 */
const SegmentLinkRedirect: React.FC<ISegmentLinkRedirectProps> = ({
	children,
}) => {
	const {
		'*': subpath,
		channelId,
		groupId = '',
		id = '',
	} = useParams<{
		'*': string;
		'channelId'?: string;
		'groupId': string;
		'id': string;
	}>();

	const navigate = useNavigate();

	const [error, setError] = useState(false);

	const redirect = !channelId && !subpath;

	useEffect(() => {
		if (!redirect) {
			return;
		}

		let ignore = false;

		API.individualSegment
			.fetch({groupId, segmentId: id})
			.then((segment: {channelId: string | null; id: string}) => {
				if (ignore) {
					return;
				}

				if (!segment.channelId) {
					setError(true);

					return;
				}

				navigate(
					toRoute(Routes.CONTACTS_SEGMENT, {
						channelId: segment.channelId,
						groupId,
						id: segment.id,
					}),
					{replace: true}
				);
			})
			.catch(() => {
				if (!ignore) {
					setError(true);
				}
			});

		return () => {
			ignore = true;
		};
	}, [groupId, id, navigate, redirect]);

	if (!redirect) {
		return <>{children}</>;
	}

	if (error) {
		return <ErrorPage />;
	}

	return <Loading />;
};

export default SegmentLinkRedirect;
