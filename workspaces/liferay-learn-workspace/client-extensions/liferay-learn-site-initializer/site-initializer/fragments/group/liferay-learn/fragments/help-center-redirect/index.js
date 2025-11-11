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
    }

    try {
      const response = await fetch(`/o/c/p2s3knowledgearticles/by-external-reference-code/${externalReferenceCode}?fields=id`,
        {
          credentials: 'include',
          headers: {
            'Accept': 'application/json',
          },
          method: 'GET',
        }
      );
      
      if (!response.ok) {
        // eslint-disable-next-line no-console
        console.log(`Error: ${response.status} ${response.statusText}`);
        window.location.href = `/not-found-404`;
      }
      
      const data = await response.json();
      
      const knowledgeArticleId = data.id;

      if (knowledgeArticleId) {
        let language = urlParams.get('lang');

        if(!language) {
          language = 'en';
        }
				
				

        window.location.href = `${Liferay.ThemeDisplay.getCDNBaseURL()}/${language}/l/${knowledgeArticleId}`;
      }
      else {
        console.error("Knowledge article ID not found in the response");
      }
      
    }
    catch (error) {
      console.error("Error: ", error);
    }
  }

  redirectToArticle();
