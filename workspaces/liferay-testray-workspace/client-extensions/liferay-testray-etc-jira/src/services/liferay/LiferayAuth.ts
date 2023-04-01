import { baseFetcher } from '../../lib/Fetch';

const {
  LIFERAY_AUTH_CLIENT_ID,
  LIFERAY_AUTH_CLIENT_SECRET,
  LIFERAY_AUTH_TOKEN,
  LIFERAY_BASE_URL,
} = Bun.env;

class LiferayAuth {
  protected fetcher = baseFetcher(LIFERAY_BASE_URL as string, {
    headers: {
      'Content-Type': 'application/json',
      Authorization: LIFERAY_AUTH_TOKEN as string,
    },
  });

  public async getAuthToken() {
    const response = await this.fetcher('/o/oauth2/token', {
      body: JSON.stringify({
        client_id: LIFERAY_AUTH_CLIENT_ID,
        client_secret: LIFERAY_AUTH_CLIENT_SECRET,
        grant_type: 'client_credentials',
      }),
    });

    return {
      access_token: response.access_token,
      expires_in: response.expires_in,
    };
  }
}

export default LiferayAuth;
