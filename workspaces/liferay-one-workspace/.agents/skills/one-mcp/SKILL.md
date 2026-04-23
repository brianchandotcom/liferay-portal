---

allowed-tools: [Bash, Read, Edit, Write]
argument-hint: "[email:password]"
description: Configure the Liferay MCP server for the liferay-one-workspace. Use when the user asks to set up or enable the Liferay MCP, or invokes /one-mcp.
name: one-mcp

---

# Configure Liferay MCP

Set up Claude Code to connect to the local Liferay instance as an MCP server. Run from `workspaces/liferay-one-workspace/`.

## 1. Resolve Credentials

Accept credentials from `${ARGUMENTS}` in the form `email:password`. If not supplied, ask the user for their local Liferay admin email and password.

## 2. Enable the Feature Flag

Read `configs/local/portal-env.properties`. If `feature.flag.LPD-63311=true` is not already present, append it. This enables the `/o/mcp` endpoint on the Liferay instance.

## 3. Compute the Auth Token

Run:

```bash
echo -n "email:password" | base64
```

## 4. Update `settings.local.json`

Read `.claude/settings.local.json`. Merge the `mcpServers` block below into it, preserving any existing keys. If a `liferay` entry already exists under `mcpServers`, replace it.

```json
{
	"mcpServers": {
		"liferay": {
			"args": [
				"-y",
				"mcp-remote",
				"http://localhost:8080/o/mcp",
				"--header",
				"Authorization: Basic <base64-token>"
			],
			"command": "npx"
		}
	}
}
```

## 5. Report Next Steps

Tell the user:

- If the feature flag was newly added: bounce Liferay (`docker compose restart liferay`) for the flag to take effect.
- Restart Claude Code (or run `/mcp` to reload servers) to pick up the new MCP server.
- When Liferay is running, `liferay` MCP tools will be available. When it is not running, the server will silently be unavailable — no other impact.