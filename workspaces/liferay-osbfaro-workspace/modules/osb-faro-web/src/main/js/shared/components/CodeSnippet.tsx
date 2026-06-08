/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import CopyButton from '~/shared/components/CopyButton';

interface ICodeSnippet {
	codeLines: Array<string>;
}

const CodeSnippet: React.FC<ICodeSnippet> = ({codeLines}) => {
	const getDisplayedCode = ([...codeLines]: Array<string>): string => {
		const lastLineModifier = codeLines.length > 1 ? '\n' : '';
		const lastLine = codeLines.pop();

		return codeLines.join('\n\t').concat(`${lastLineModifier}${lastLine}`);
	};

	const displayedCode = getDisplayedCode(codeLines);

	return (
		<div className="code-snippet-root">
			<CopyButton
				buttonText={Liferay.Language.get('copy')}
				className="copy-button"
				displayType="secondary"
				text={displayedCode}
			/>

			<code className="code-container">{displayedCode}</code>
		</div>
	);
};

export default CodeSnippet;
