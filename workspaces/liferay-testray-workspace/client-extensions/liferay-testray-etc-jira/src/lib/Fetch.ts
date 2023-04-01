import logger from './Logger';

export async function fetcher<T = any>(
  url: string | URL,
  options?: FetchRequestInit
): Promise<T> {
  const response = await fetch(url, options);

  if (!response.ok) {
    const cause = await response.text();

    logger.error(cause, JSON.stringify({ url, options }, null, 2));

    throw new Error(cause);
  }

  return response.json();
}

export const baseFetcher =
  <T = any>(baseURL: string | URL, baseOptions?: FetchRequestInit) =>
  (url: string | URL, options?: FetchRequestInit) =>
    fetcher<T>(`${baseURL}${url}`, {
      ...baseOptions,
      ...options,
    });
