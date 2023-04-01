import Jira from './Jira';
import Testray from '../liferay/Testray';

const jira = new Jira();
const testray = new Testray();

const { JIRA_APP_FIELD_ID_QA_TEST_NAME, JIRA_APP_FIELD_ID_QA_TEST_SCORE } =
  Bun.env;

class JiraEngine {
  public async updateIssues(issues: string[], userId: string) {
    const testrayIssues = await testray.getIssues(issues);

    for (const testrayIssue of testrayIssues) {
      let totalPriority = 0;

      const testrayCaseNames = new Set<string>();

      for (const {
        r_caseResultToCaseResultsIssues_c_caseResult: caseResult,
      } of testrayIssue.issueToCaseResultsIssues) {
        const { r_caseToCaseResult_c_case: testrayCase } = caseResult;

        const caseName = testrayCase.name.replace(' ', '_');

        if (testrayCaseNames.has(caseName)) {
          continue;
        }

        totalPriority += testrayCase.priority;

        testrayCaseNames.add(caseName);
      }

      const payload = {
        fields: {
          [JIRA_APP_FIELD_ID_QA_TEST_NAME as string]: [...testrayCaseNames],
          [JIRA_APP_FIELD_ID_QA_TEST_SCORE as string]: totalPriority,
        },
      };

      await jira.updateIssue(testrayIssue.name, payload, userId);
    }
  }
}

export default JiraEngine;
