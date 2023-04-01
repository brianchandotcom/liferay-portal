# Testray LXC Jira Integration

### Description

This App is part of Client Extension of **Testray** and responsible to do an Integration between Testray and Jira, written in TypeScript, using **Bun** and **Elysia**.

Q: Why [Elysia?](https://elysiajs.com) 
A: **Elysia** is a rest framework, similar to **Express**, **Fastify** with the advantage of being faster than both and typesafe, along with many other features, is optimized to run with **Bun**, but can be used with Node.js as well.

Q: Why [Bun?](https://bun.sh)
A: Bun is a Javascript runtime, is faster than **Node.js** and **Deno** in many aspects and run TypeScript by default, specially chosen to be used with **Elysia**. 

### Getting Started
To get started, you must have intalled in your machine **Docker** or **Bun** 

Create a copy of `.env.example` and rename to `.env` to run local, it's necessary to replace the following credentials from Jira Cloud and Liferay as commented in env file.

Replace the keys `JIRA_AUTH_CLIENT_ID`,`JIRA_AUTH_CLIENT_SECRET`, `JIRA_AUTH_CLOUD_ID` using Jira Cloud App Credentials

### Development
To start the development server run:
```bash
bun run dev
```

Open http://localhost:3333 with your browser to see the result.

### Docker

```bash
docker build -t "testray-lxc-jira" . 

docker run --name="testray-lxc-jira-integration" -p 3333:3333 testray-lxc-jira
```

### Liferay Cloud

First, install [LCP](https://learn.liferay.com/w/liferay-cloud/reference/command-line-tool)

Run:

```
lcp deploy
```