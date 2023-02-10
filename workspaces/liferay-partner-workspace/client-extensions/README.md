## steps

* deploy this client extension
* login to DXP
* open the console session in your browser and paste the following:

```javascript
window.Liferay.OAuth2Client.FromUserAgentApplication('salesforce-sync-api-user-agent').fetch(
  '/salesforce/trigger'
).then(
  r => r.text()
).then(
  r => console.log('success:', r)
).catch(
  e => console.log('error:', e)
);
```

You should see the result `success: <empty string>`