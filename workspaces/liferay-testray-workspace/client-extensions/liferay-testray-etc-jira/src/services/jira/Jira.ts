import Cache from '../../lib/Cache';
import JiraAuth from './JiraAuth';
import logger from '../../lib/Logger';

const {
  JIRA_API_BASE_URL,
  JIRA_APP_NAME,
  JIRA_AUTH_BASE_URL,
  JIRA_AUTH_CLIENT_ID,
  JIRA_AUTH_CLIENT_SECRET,
  JIRA_AUTH_GRANT_TYPE_REFRESH_TOKEN,
  JIRA_AUTH_GRANT_TYPE,
  JIRA_AUTH_REDIRECT_URI,
  JIRA_AUTH_SCOPES,
  JIRA_AUTH_STATE_PREFIX,
} = Bun.env;

const cacheInstance = Cache.getInstance();

logger.debug({
  cacheInstance,
  JIRA_API_BASE_URL,
  JIRA_APP_NAME,
  JIRA_AUTH_BASE_URL,
  JIRA_AUTH_CLIENT_ID,
  JIRA_AUTH_CLIENT_SECRET,
  JIRA_AUTH_GRANT_TYPE_REFRESH_TOKEN,
  JIRA_AUTH_GRANT_TYPE,
  JIRA_AUTH_REDIRECT_URI,
  JIRA_AUTH_SCOPES,
  JIRA_AUTH_STATE_PREFIX,
});

class Jira extends JiraAuth {
  public async getIssue(ticket: string, userId: string) {
    const { cloudId, token } = await this.getTokenAndCloudId(userId);

    const response = await fetch(
      `${JIRA_API_BASE_URL}/ex/jira/${cloudId}/rest/api/latest/issue/${ticket}`,
      {
        headers: {
          'Content-Type': 'application/json',
          Authorization: 'Bearer ' + token,
        },
      }
    );

    return response.json();
  }

  public async updateIssue(ticket: string, body: unknown, userId: string) {
    const { cloudId, token } = await this.getTokenAndCloudId(userId);

    const response = await fetch(
      `${JIRA_API_BASE_URL}/ex/jira/${cloudId}/rest/api/latest/issue/${ticket}`,
      {
        body: JSON.stringify(body),
        headers: {
          'Content-Type': 'application/json',
          Authorization: 'Bearer ' + token,
        },
        method: 'PUT',
      }
    );

    return response.json();
  }
}

export default Jira;
