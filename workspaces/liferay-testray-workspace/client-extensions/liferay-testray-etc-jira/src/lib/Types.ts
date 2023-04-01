export interface APIResponse<T = any> {
  items: T[];
  totalCount: number;
}

export interface JiraAuthorizeCallback {
  code: string;
  expiresIn?: number;
  state: string;
}

export interface JiraAuthorizePayload {
  access_token: string;
  expires_in: number;
  refresh_token: string;
  scope: string;
}

export type JiraAccessibleResponse = {
  id: string;
  name: string;
  scopes: string[];
  url: string;
}[];

export interface TestrayJiraOAuth {
  expiresIn: number;
  id: number;
  accessToken: string;
  r_testrayJiraOAuth_userId: number;
  refreshToken: string;
}

export interface TestrayIssue {
  id: string;
  issueToCaseResultsIssues: {
    r_caseResultToCaseResultsIssues_c_caseResult: {
      r_caseToCaseResult_c_case: {
        priority: number;
        name: string;
      };
    };
  }[];
  name: string;
}
