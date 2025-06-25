/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useRef, useState} from 'react';
import {useNavigate} from 'react-router-dom';

import {generateFileMd5} from '../utils/generateFileMd5';

interface IParams {
	file: File;
	ticketId: string;
}

interface IProps {
	abortGenerateMd5: () => void;
	generateMd5: (params: IParams) => Promise<string | null>;
	loading: boolean;
	md5: string | null;
}

export default function useGenerateFileMd5(): IProps {
	const [loading, setLoading] = useState(false);
	const [md5, setMd5] = useState<string | null>(null);
	const abortControllerRef = useRef<AbortController | null>(null);
	const navigate = useNavigate();

	const generateMd5 = useCallback(
		async (params: IParams): Promise<string | null> => {
			if (abortControllerRef.current) {
				abortControllerRef.current.abort();
			}

			abortControllerRef.current = new AbortController();

			setLoading(true);
			setMd5(null);

			const {file, ticketId} = params;

			try {
				const hash = await generateFileMd5(file);

				if (abortControllerRef.current?.signal.aborted) {
					setMd5(null);

					return null;
				}

				setMd5(hash);

				return hash;
			}
			catch (generateError) {
				if (abortControllerRef.current?.signal.aborted) {
					setMd5(null);
				}
				else {
					navigate(`/${ticketId}/unexpected-error`, {
						state: {
							message: String(generateError),
						},
					});
				}

				return null;
			}
			finally {
				setLoading(false);

				abortControllerRef.current = null;
			}
		},
		[navigate]
	);

	const abortGenerateMd5 = useCallback(() => {
		if (abortControllerRef.current) {
			abortControllerRef.current.abort();
			setLoading(false);

			setMd5(null);
		}
	}, []);

	return {abortGenerateMd5, generateMd5, loading, md5};
}
