/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

  async function redirectToArticle() {
    const urlParams = new URLSearchParams(window.location.search);
    const externalReferenceCode = urlParams.get('erc');

    if (!externalReferenceCode) {
      console.error("Erc not found in URL parameters");
      window.location.href = `/not-found-404`;

      return;
    }

    try {
      const response = await fetch(
        `/o/c/p2s3knowledgearticles/by-external-reference-code/${externalReferenceCode}?fields=id`,
        {
          credentials: 'include',
          headers: {
            'Accept': 'application/json',
          },
          method: 'GET'
        }
      );

      if (!response.ok) {
        window.location.href = `/not-found-404`;

        return;
      }

      const data = await response.json();
      const knowledgeArticleId = data.id;

      if (!knowledgeArticleId) {
        console.error("Knowledge article ID not found in the response");
        window.location.href = `/not-found-404`;

        return;
      }

      let language = urlParams.get('lang') || 'en';

      if (language.toLowerCase() !== 'en' && language.toLowerCase() !== 'ja') {
        language = 'en-us';
      }

      window.location.href = `${Liferay.ThemeDisplay.getCDNBaseURL()}/${language}/l/${knowledgeArticleId}`;

    } catch (error) {
      console.error("Error: ", error);
      window.location.href = `/not-found-404`;
    }
  }

  redirectToArticle();