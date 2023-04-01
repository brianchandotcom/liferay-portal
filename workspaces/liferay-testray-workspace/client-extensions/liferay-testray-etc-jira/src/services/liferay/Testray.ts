import {
  APIResponse,
  JiraAuthorizePayload,
  TestrayIssue,
  TestrayJiraOAuth,
} from '../../lib/Types';
import SearchBuilder from '../../lib/SearchBuilder';
import LiferayAuth from './LiferayAuth';
import { getSearchParams } from '../../lib/SearchParams';

const { JIRA_AUTH_STATE_PREFIX } = Bun.env;

class Testray extends LiferayAuth {
  public async getOAuthJira(userId: string) {
    const searchParams = getSearchParams({
      filter: SearchBuilder.eq('r_testrayJiraOAuth_userId', userId),
    });

    const response: APIResponse<TestrayJiraOAuth> = await this.fetcher(
      `/o/c/testrayjiraoauths?${searchParams}`
    );

    if (response.totalCount) {
      return response.items[0];
    }

    throw new Error(`No Jira accessToken for ${userId}`);
  }

  public async setTestrayOAuthJiraCode(
    { access_token, expires_in, refresh_token }: JiraAuthorizePayload,
    state: string
  ) {
    const [, userId] = state.split(JIRA_AUTH_STATE_PREFIX as string);

    try {
      const response = await this.getOAuthJira(userId);

      await this.fetcher(`/o/c/testrayjiraoauths/${response.id}`, {
        body: JSON.stringify({
          expiresIn: expires_in,
          accessToken: access_token,
          r_testrayJiraOAuth_userId: userId,
        }),
        method: 'PUT',
      });
    } catch {
      await this.fetcher('/o/c/testrayjiraoauths', {
        body: JSON.stringify({
          expiresIn: expires_in,
          accessToken: access_token,
          r_testrayJiraOAuth_userId: userId,
          refreshToken: refresh_token,
        }),
        method: 'POST',
      });
    }
  }

  public async getIssues(issues: string[]) {
    const searchParams = getSearchParams({
      fields:
        'id,name,issueToCaseResultsIssues.r_caseResultToCaseResultsIssues_c_caseResult.r_caseToCaseResult_c_case.name,issueToCaseResultsIssues.r_caseResultToCaseResultsIssues_c_caseResult.r_caseToCaseResult_c_case.priority',
      filter: SearchBuilder.in('name', issues),
      nestedFields:
        'issueToCaseResultsIssues,r_caseResultToCaseResultsIssues_c_caseResult',
      nestedFieldsDepth: 3,
      pageSize: 100,
    });

    const response: APIResponse<TestrayIssue> = await this.fetcher(
      decodeURIComponent(`/o/c/issues?${searchParams}`)
    );

    return response.items;
  }
}

export default Testray;
