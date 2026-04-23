# UI

## Page Hierarchy (Skeleton)

```
one.liferay.com
├─ HOME                                    LANDING PAGE
├─ MY ACCOUNT                              SPA
│  ├─ Subscriptions                        ROUTE
│  │  ├─ Subscription Details              ROUTE
│  │  │  ├─ Activation Keys                ROUTE
│  │  │  ├─ EULA Management                ROUTE
│  │  │  └─ Purchases / Licenses           ROUTE
│  │  └─ Publisher Onboarding              ROUTE
│  │     └─ Account Verification           ROUTE
│  ├─ Orders                               ROUTE
│  │  └─ Order History                     ROUTE
│  ├─ Billing & Usage                      ROUTE
│  │  ├─ Spend Management                  ROUTE
│  │  │  ├─ Usage Metrics                  ROUTE
│  │  │  ├─ Consumption Metrics            ROUTE
│  │  │  └─ Invoice History                ROUTE
│  │  └─ Checkout Flow                     ROUTE
│  ├─ Account Details                      ROUTE
│  └─ Team Members                         ROUTE
├─ MARKETPLACE
│  ├─ Applications                         LANDING PAGE
│  │  └─ Application Details               PAGE
│  ├─ Products                             LANDING PAGE
│  │  └─ Products Details                  PAGE
│  ├─ Solutions                            LANDING PAGE
│  │  └─ Solution Details                  PAGE
│  └─ Publishers                           LANDING PAGE
│     └─ Publisher Profile                 PAGE
├─ SUPPORT                                 LANDING PAGE
│  ├─ Getting Started                      LANDING PAGE
│  ├─ Customer Portal Help                 LANDING PAGE
│  ├─ My Tickets (JSM)                     EXTERNAL LINK
│  ├─ Open a Ticket (JSM)                  EXTERNAL LINK
│  ├─ Escalate a Ticket                    EXTERNAL LINK
│  ├─ Callback Requests                    EXTERNAL LINK
│  ├─ Business Events                      EXTERNAL LINK
│  ├─ Ticket Attachments                   EXTERNAL LINK
│  ├─ Release Notes                        EXTERNAL LINK
│  ├─ Security Vulnerabilities             EXTERNAL LINK
│  ├─ Support Coverage                     EXTERNAL LINK
│  ├─ Services & Compatibility             EXTERNAL LINK
│  └─ Announcements                        LANDING PAGE
└─ ADMIN                                   SPA
   ├─ Apps                                 ROUTE
   ├─ Solutions                            ROUTE
   ├─ Trials                               ROUTE
   ├─ Environments                         ROUTE
   ├─ Publishers                           ROUTE
   ├─ Orders                               ROUTE
   └─ Payments                             ROUTE
```

---

## Global Elements

Persistent chrome rendered on every page — not routes in the site map.

**Header:** Liferay Sites Switcher, Language Selector (EN / JA / PT-BR / ES), Account Selector, User Profile Menu (Account Settings, Sign Out)

**Footer:** Copyright notice, Cookie Policy link, Cookie Consent Banner

---