/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

import {ContentReviewBlock} from './ContentReviewBlock';
import {ContentReviewHeader} from './ContentReviewHeader';
import {ContentReviewImageInfo} from './ContentReviewImageInfo';
import {ContentReviewParagraph} from './ContentReviewParagraph';
import {ContentReviewSection} from './ContentReviewSection';
import {ContentReviewSeparator} from './ContentReviewSeparator';
import {ContentReviewSupportLink} from './ContentReviewSupportLink';
import {ContentReviewVideo} from './ContentReviewVideo';

type ContentReviewProps = {
	children: ReactNode;
};

type ContentReviewChildrens = {
	Block: typeof ContentReviewBlock;
	Header: typeof ContentReviewHeader;
	ImageInfo: typeof ContentReviewImageInfo;
	Paragraph: typeof ContentReviewParagraph;
	Section: typeof ContentReviewSection;
	Separator: typeof ContentReviewSeparator;
	SupportLink: typeof ContentReviewSupportLink;
	Video: typeof ContentReviewVideo;
};

const ContentReview: React.FC<ContentReviewProps> & ContentReviewChildrens = ({
	children,
}) => <div className="border p-5 rounded-lg">{children}</div>;

ContentReview.Block = ContentReviewBlock;
ContentReview.Header = ContentReviewHeader;
ContentReview.ImageInfo = ContentReviewImageInfo;
ContentReview.Paragraph = ContentReviewParagraph;
ContentReview.Section = ContentReviewSection;
ContentReview.Separator = ContentReviewSeparator;
ContentReview.SupportLink = ContentReviewSupportLink;
ContentReview.Video = ContentReviewVideo;

export {ContentReview};
