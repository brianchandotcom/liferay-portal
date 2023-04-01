import { Elysia, t } from 'elysia';
import Cache from './lib/Cache';
import Jira from './services/jira/Jira';
import JiraEngine from './services/jira/JiraEngine';
import logger from './lib/Logger';
import Testray from './services/liferay/Testray';

const { APP_DEBUG_CACHE_ROUTER = '/cache', PORT } = Bun.env;

const jira = new Jira();
const jiraEngine = new JiraEngine();
const testray = new Testray();

const getUserId = (request: Request) =>
  request.headers.get('liferay-user-id') ?? '20123';

const cacheInstance = Cache.getInstance();

new Elysia()
  .get('/', () => 'Testray LXC Jira Integration')
  .get(APP_DEBUG_CACHE_ROUTER, () => {
    logger.debug(cacheInstance);

    return cacheInstance;
  })
  .get('/jira/authorize', async ({ set, request }) =>
    jira.authorize(getUserId(request), set)
  )
  .get('/jira/authorize/callback', async ({ query: { code, state } }) => {
    const response = await jira.exchangeAuthorizationCode({
      code: code as string,
      state: state as string,
    });

    await testray.setTestrayOAuthJiraCode(response, state as string);

    return response;
  })
  .put(
    '/jira/ticket',
    ({ request, body }) =>
      jiraEngine.updateIssues(body.tickets, getUserId(request)),
    {
      schema: {
        body: t.Object({
          tickets: t.Array(t.String()),
        }),
      },
    }
  )
  .get('/jira/ticket/:ticket', ({ request, params: { ticket } }) =>
    jira.getIssue(ticket, getUserId(request))
  )
  .listen(Number(PORT), ({ hostname, port }) =>
    logger.info(
      `🦊 Testray LXC Integration with Elysia is running at ${hostname}:${port}`
    )
  );
