# UI — Site Initializer & Custom Element

One site-initializer (`liferay-one-site-initializer`) containing three page groups: Marketplace (public), Support (customer-authenticated), Admin (internal). One React custom element backs every dynamic page.

## Site-Initializer Structure

```
liferay-one-site-initializer/
└── site-initializer/
    ├── object-definitions/
    ├── list-type-definitions/
    ├── object-actions/
    ├── object-validations/
    ├── workflow-definitions/
    ├── roles/
    ├── oauth2-applications/
    ├── notification-templates/
    ├── fragments/
    │   └── group/liferay-one/
    │       ├── marketplace/
    │       ├── support/
    │       └── admin/
    ├── layout-page-templates/
    ├── journal-articles/
    ├── ddm-templates/
    ├── layouts/
    │   ├── marketplace/
    │   ├── support/
    │   └── admin/
    ├── documents/
    ├── navigation-menus.json
    ├── permissions/
    └── site.json
```

---

## Page Hierarchy (Skeleton)

```
portal.liferay.com
├─ GLOBAL ELEMENTS
│  ├─ Global Header (persistent)           COMPONENT
│  │  ├─ Liferay Sites Switcher            UI COMPONENT
│  │  ├─ Language Selector                 UI COMPONENT
│  │  ├─ Account Selector                  UI COMPONENT
│  │  └─ User Profile Menu                 UI COMPONENT
│  ├─ Global Footer                        COMPONENT
│  └─ Sign In / SSO                        AUTH PAGE
├─ HOME
│  ├─ Home Page                            LANDING PAGE
│  ├─ Marketplace Landing Page             LANDING PAGE
│  ├─ Support Landing Page                 LANDING PAGE
│  ├─ My Account Landing Page              LANDING PAGE
│  └─ Admin Landing Page                   LANDING PAGE
├─ MY ACCOUNT
│  ├─ My Account Landing Page              LANDING PAGE
│  ├─ Subscriptions                        PAGE
│  │  ├─ Subscription Summary              COMPONENT
│  │  ├─ Subscription Selector             UI COMPONENT
│  │  ├─ Subscription Detail               SUB-PAGE
│  │  │  ├─ Products in Subscription       COMPONENT
│  │  │  ├─ Activation Keys                PAGE
│  │  │  │  ├─ Generate Key Form           FORM
│  │  │  │  └─ Expiration Notification Settings  SETTINGS PAGE
│  │  │  ├─ EULA Management                SUB-PAGE
│  │  │  ├─ Purchases / Licenses           SUB-PAGE
│  │  │  └─ Auto-Renewal Toggle            UI COMPONENT
│  │  └─ Publisher Onboarding              WORKFLOW
│  │     └─ Account Verification           SUB-PAGE
│  ├─ Orders                               PAGE
│  │  └─ Order History                     SUB-PAGE
│  ├─ Billing & Usage                      PAGE
│  │  ├─ Usage this Period                 COMPONENT
│  │  ├─ Spend Management                  DASHBOARD
│  │  │  ├─ Workspace Usage Metrics        SUB-PAGE
│  │  │  ├─ Consumption-Based Pricing Dashboard  SUB-PAGE
│  │  │  └─ Invoice History                SUB-PAGE
│  │  ├─ Payment Methods & Billing Contact COMPONENT
│  │  └─ Checkout Flow                     WORKFLOW
│  │     ├─ Purchase Approval Workflow      WORKFLOW
│  │     ├─ Customer Business Verification  FORM
│  │     └─ VAT & Tax Localisation         COMPONENT
│  ├─ Account Details                      PAGE
│  │  ├─ Organisation                      COMPONENT
│  │  ├─ Primary Contact                   COMPONENT
│  │  └─ Security & Access                 COMPONENT
│  └─ Team Members                         PAGE
│     ├─ Invite / Edit Member              MODAL / FORM
│     ├─ Manage Roles                      MODAL / FORM
│     ├─ Incident Contacts                 MODAL / FORM
│     └─ Support Seat Management           SUB-PAGE
├─ MARKETPLACE
│  ├─ Marketplace Home                     LANDING PAGE
│  ├─ Applications Catalog                 LISTING PAGE
│  │  └─ Application Detail Page (PDP)     DETAIL PAGE
│  ├─ Products Catalog                     LISTING PAGE
│  │  └─ Products Detail Page (PDP)        DETAIL PAGE
│  ├─ Solutions Catalog                    LISTING PAGE
│  │  └─ Solution Detail Page              DETAIL PAGE
│  ├─ Publisher Profile Page               DETAIL PAGE
│  │  ├─ Publisher's Applications          LISTING (filtered)
│  │  ├─ Publisher's Products              LISTING (filtered)
│  │  └─ Publisher's Solutions             LISTING (filtered)
│  └─ Global Product Search                UI COMPONENT
├─ SUPPORT
│  ├─ Support Home                         LANDING PAGE
│  │  ├─ My Tickets (JSM)                  EXTERNAL LINK
│  │  ├─ Open a Ticket (JSM)               EXTERNAL LINK
│  │  ├─ Escalate a Ticket                 PAGE
│  │  ├─ Callback Requests                 PAGE
│  │  ├─ Business Events                   PAGE
│  │  └─ Ticket Attachments                PAGE
│  ├─ Knowledge Base Hub                   LANDING PAGE
│  │  ├─ Getting Started Guide             PAGE
│  │  ├─ Customer Portal Help Articles     PAGE
│  │  ├─ Release Notes                     PAGE        [TBD → learn.liferay?]
│  │  ├─ Security Vulnerabilities          PAGE
│  │  ├─ Support Coverage                  PAGE        [TBD → learn.liferay?]
│  │  ├─ Services & Compatibility          PAGE        [TBD → learn.liferay?]
│  │  └─ Customer Events                   PAGE
│  └─ Announcements Feed                   COMPONENT   [TBD]
└─ ADMIN
   ├─ Internal Admin Landing Page          LANDING PAGE
   ├─ Account Details                      SUB-PAGE
   ├─ Account Notes                        SUB-PAGE
   ├─ Liferay Workers View                 SUB-PAGE
   ├─ Partner Team Visibility              SUB-PAGE
   ├─ Roles & Permissions                  PAGE
   │  └─ Subscription-Level Toggles (Admin) ADMIN COMPONENT
   ├─ Admin Control Menu                   FUNCTIONAL AREA
   ├─ Administrator Dashboard              DASHBOARD
   ├─ Publisher Dashboard                  DASHBOARD
   ├─ Finance Dashboard (Admin)            DASHBOARD
   │  ├─ SaaS Trials & SSA Provisioning   PAGE
   │  ├─ LPKG / Luffa Wrapping (Publisher) WORKFLOW
   │  └─ Publish New App CTA              MODAL / FLOW
   └─ SSA Dashboard                        DASHBOARD
```

---

## Proposed Site Map (portal.liferay.com)

Columns: **Page / Area** · **Type** · **Status** · **Origin** · **URL / Notes** · **User Role** · **User Story** · **Acceptance Criteria** · **Notes**

### Global Elements

| Page / Area | Type | Status | Origin | URL / Notes | User Role | User Story | Acceptance Criteria | Notes |
|---|---|---|---|---|---|---|---|---|
| Global Header (persistent) | Component | REUSE | Marketplace / CP | Sites Switcher, Language Selector, Account Selector, User Profile Menu | Any User | As any user, I want a persistent header so I can navigate anywhere from any page. | Header renders on every page. Switcher links to all Liferay properties. | |
| Liferay Sites Switcher | UI Component | REUSE | Marketplace / CP | Links to all Liferay sister properties | | | | |
| Language Selector | UI Component | REUSE | Marketplace / CP | EN / JA / PT-BR / ES | Any User | As a non-English speaker, I want to switch my language so I can use the portal comfortably. | Language selector supports EN, JA, PT-BR, ES. Persists across sessions. | |
| Account Selector | UI Component | MERGE | Marketplace | Dropdown — paginated account list, search, new account CTA | Customer | As a customer with multiple accounts, I want to switch account context from the header so I always know which account I'm acting on. | Account switcher shows list of accounts, search, and new account CTA. Selection persists in session. Must span across all sites. | Do we need a subscription selector? |
| User Profile Menu | UI Component | REUSE | Both | Account Settings, Sign Out | | | | |
| Global Footer | Component | REUSE | CP | Copyright, Cookie Policy, Cookie Consent Banner | | | | |
| Sign In / SSO | Auth Page | REUSE | Both | Unified Okta SAML SSO across all portal areas | Any User | As any user, I want a single sign-on so I don't need separate logins for different Liferay portals. | One Okta SSO login grants access to all portal areas based on role. No separate credentials required. | |

### Home

| Page / Area | Type | Status | Origin | URL / Notes | User Role | User Story | Acceptance Criteria | Notes |
|---|---|---|---|---|---|---|---|---|
| Home Page | Landing Page | NEW BUILD | Unified Portal | Unauthenticated. | Any User | As any user, I want to navigate to the home page and access the Marketplace Catalogue and Support landing page. | From the home page, a user can navigate to the Marketplace Catalogue and view Support and Account information. Support and Account are intended for customers only. | |
| Marketplace Landing Page | Landing Page | NEW BUILD | Unified Portal | Unauthenticated. Showcases the current Marketplace catalogue for prospects and visitors. | Prospect / Any User | As a visitor, I want to browse the Marketplace catalogue without signing in so I can explore available products. | Page is fully ungated. Displays current Marketplace catalogue. Sign-in prompt visible but not required. | |
| Support Landing Page | Landing Page | NEW BUILD | Unified Portal | Requires authentication. Customers can access support tickets and support-related items from here. | Customer | As a customer, I want a dedicated support landing page so I can quickly access my tickets and support resources after signing in. | Page requires authentication. Displays support tickets, escalation options, and support resources. Unauthenticated users prompted to sign in. | |
| My Account Landing Page | Landing Page | NEW BUILD | Unified Portal | Authenticated entry point to My Account (replaces separate Subscriptions and Account Management landing pages) | Customer | As a customer, I want to view all my subscriptions in one place so I can manage my Liferay products and licences easily. | Requires authentication. Lists all active subscriptions with renewal dates, licence status. | |
| Announcements Feed | Component | REUSE | CP | Portal updates and customer events | Customer | As a customer, I want to see the latest updates. | | |
| Internal Admin Landing Page | Landing Page | NEW BUILD | Unified Portal | Liferay employees only. Power access to internal tools and account management capabilities. | Internal Admin / Liferay Staff | As a Liferay employee, I want a dedicated internal admin landing page so I can access internal tools and manage customer accounts efficiently. | Restricted to Liferay staff roles only. Surfaces internal tools, account overrides, provisioning controls, and admin dashboards. Not visible to customers. | |

### My Account

| Page / Area | Type | Status | Origin | URL / Notes | User Role | User Story | Acceptance Criteria | Notes |
|---|---|---|---|---|---|---|---|---|
| My Account | Landing Page | NEW BUILD | Unified Portal | Authenticated entry point. 5 tabs: Subscriptions, Orders, Billing & Usage, Account Details, Team Members. | Customer | As a customer, I want a single My Account section so I can manage all aspects of my account from one place. | Requires authentication. Displays 5 tabs. Tab visibility based on account type. | |
| **— TAB 1: Subscriptions** | Page | NEW BUILD | Unified Portal | Subscription selector, per-subscription detail panel, products table. | Customer | As a customer, I want to see all my subscriptions and attached products so I can manage licences in one place. | Subscription selector at top. Per-subscription detail panel. Products table with status, renewal date, licence type. | |
| Subscription Selector | UI Component | NEW BUILD | Unified Portal | Dropdown to switch between subscriptions | Customer | | | |
| Subscription Detail | Sub-page | NEW BUILD | Unified Portal | Per-subscription detail panel showing attached products | Customer | | | |
| Products in Subscription | Component | NEW BUILD | Unified Portal | Table of products under the selected subscription | Customer | | | |
| Activation Keys | Page | NEW BUILD | CP + Marketplace + Koroneiki | List, generate, download keys across all types | Support Admin / Internal Admin | As a customer, I want to view all activation keys in one place and generate new ones without contacting Liferay. | Key list with type, status, expiry. Generate key form with type selector. Download key file. | |
| Generate Key Form | Modal / Form | NEW BUILD | CP | Short form for creating keys by type (standard, developer, virtual cluster, complimentary) | | | | |
| Expiration Notification Settings | Settings Page | NEW BUILD | CP | Alert preferences for expiring keys | Customer | As a customer, I want to set expiry notifications so I'm warned before my licence lapses. | Set alert X days before expiry. Email and in-portal notification. | |
| EULA Management | Sub-page | NEW BUILD | Marketplace | Centralised EULA acceptance per vendor — presented during checkout | Customer | As a customer, I want to view and manage my EULA acceptances so I know which licences I've agreed to. | EULA list per vendor. Acceptance date and version shown. Re-accept flow for updated EULAs. | |
| Purchases / Licenses | Sub-page | NEW BUILD | Marketplace + Koroneiki | View purchases and licence entitlements | | | | |
| Auto-Renewal Toggle | UI Component | NEW BUILD | Unified Portal | Per product / per app auto-renewal switch | Customer | As a customer, I want to toggle auto-renewal per product so I control which subscriptions renew automatically. | Toggle per product. Confirmation modal. Renewal date updated on change. | |
| Publisher Onboarding | Workflow | MERGE | Marketplace | Publisher verification workflow nested within Subscriptions tab | Publisher | As a Publisher, I want to complete the onboarding and verification workflow so I can list apps in the Marketplace. | Onboarding checklist. Business verification step. Status tracker. | |
| Account Verification | Sub-page | MERGE | Marketplace | Business verification for VAT / tax purposes | Support Admin / Internal Admin | As an Account Admin, I want to verify our business entity so we receive the correct tax treatment. | Verification form collects company registration data. Status badge shown on account. | |
| **— TAB 2: Orders** | Page | NEW BUILD | Marketplace | Order history table for non-subscription / one-off purchase customers. | Customer | As a customer with one-off purchases, I want to view my order history so I can track what I've bought. | Order history table. Visible only when non-subscription orders exist. | |
| Order History | Sub-page | NEW BUILD | Marketplace | All one-off purchase orders | Customer | As a customer, I want to view my complete order history so I can track what I've purchased and when. | Order list with date, products, amount, status. Filterable by date range. | |
| **— TAB 3: Billing & Usage** | Page | NEW BUILD | Unified Portal | Usage meters, spend management, payment methods, checkout flow. | Customer | As a customer, I want a billing and usage tab so I can track consumption, manage payment, and complete purchases. | Contains: Usage this Period, Spend Management, Payment Methods & Billing Contact, Checkout Flow. | |
| Usage this Period | Component | NEW BUILD | Unified Portal | Real-time usage meters for current billing cycle | Customer | | | |
| Spend Management | Dashboard | NEW BUILD | Unified Portal | Self-impose limits on consumption charges | Customer | As a customer, I want to self-impose spend limits so I can manage my own consumption independently. | Customer can set and update their own consumption spend limit across purchases. | |
| Workspace Usage Metrics | Sub-page | MERGE | CP | PaaS and SaaS usage and performance metrics | Customer | As a customer, I want to view usage metrics for my workspaces so I understand my consumption. | Charts per environment. Covers PaaS and SaaS tiers. | |
| Consumption-Based Pricing Dashboard | Sub-page | NEW BUILD | Unified Portal | Real-time and historical consumption; forecast spend, set alerts | Customer | As a customer, I want to see my real-time usage and forecast spend so I can avoid billing surprises. | Time-series chart. Historical vs current billing cycle. Alert threshold configuration. | |
| Invoice History | Sub-page | NEW BUILD | Unified Portal | View and download invoices from Microsoft NAV ERP | Customer | As a customer, I want to view and download my invoice history so I don't need to contact Liferay finance. | Invoice list with date, amount, status. PDF download per invoice. NAV ERP integration. | |
| Payment Methods & Billing Contact | Component | NEW BUILD | Unified Portal | Manage stored payment methods and billing contact details | Customer | | | |
| Checkout Flow | Workflow | REUSE | Marketplace | Cart → EULA → Payment → Confirmation | Customer | As a customer, I want to pay for subscriptions by credit card so I can purchase without involving procurement. | Stripe payment form. Stored card management. Pro-ration displayed at checkout. | |
| Purchase Approval Workflow | Workflow | NEW BUILD | Unified Portal | Customers designate internal order approvers within the portal | Customer | As a customer, I want to designate an internal approver so purchases go through our company approval process. | Approver email designation. Pending approval queue visible in portal. | |
| Customer Business Verification | Modal / Form | NEW BUILD | Unified Portal | Inline modal to verify business entity for VAT / tax handling within Checkout Flow | | | | |
| VAT & Tax Localisation | Component | MERGE | Marketplace | Automated tax engine — extended to all Liferay jurisdictions | | | | |
| **— TAB 4: Account Details** | Page | MERGE | CP + Koroneiki | Organisation, Primary Contact, Security & Access panels. | Support Admin / Internal Admin | As an Account Admin, I want to view and edit account details so our organisation info is always up to date. | Three panels: Organisation, Primary Contact, Security & Access. | |
| Organisation | Component | MERGE | CP | Company name, address, industry, account ID | Support Admin / Internal Admin | | | |
| Primary Contact | Component | MERGE | CP | Primary contact name, email, phone | Support Admin / Internal Admin | | | |
| Security & Access | Component | MERGE | CP | SSO configuration, 2FA settings, password policy | Support Admin / Internal Admin | | | |
| **— TAB 5: Team Members** | Page | MERGE | CP + Koroneiki | Member list, invite/manage roles, incident contacts, support seat management. | Customer | As a customer, I want to view all team members and manage their roles and support access. | Members table: name, role, support seat status. Invite and manage actions. | |
| Invite / Edit Member | Modal / Form | REUSE | CP | Add, edit or remove team members | Support Admin / Internal Admin | As a Support Admin, I want to invite team members and assign roles so I can control who has access. | Invite form sends email. Role dropdown. Changes reflected immediately. | |
| Manage Roles | Modal / Form | MERGE | CP | Inline role management — modal with dropdown | Support Admin / Internal Admin | As a Support Admin, I want to manage roles inline so I can update access without leaving the page. | Modal with role dropdown per member. Changes applied immediately. | |
| Incident Contacts | Sub-page | REUSE | CP | Designate ticket-eligible team members | Support Admin / Internal Admin | As a Support Admin, I want to designate incident contacts so the right people are notified during critical events. | Incident contact toggle per member. Max contacts configurable. Reflects in support ticket routing. | |
| Support Seat Management | Sub-page | MERGE | CP | Consolidated support seat management from CP | | | | |

### Marketplace

| Page / Area | Type | Status | Origin | URL / Notes | User Role | User Story | Acceptance Criteria | Notes |
|---|---|---|---|---|---|---|---|---|
| Marketplace Landing Page | Landing Page | NEW BUILD | Unified Portal | Unauthenticated. Showcases the current Marketplace catalogue for prospects and visitors. | Prospect / Any User | As a visitor, I want to browse the Marketplace catalogue without signing in so I can explore available products before committing. | Page is fully ungated. Displays current Marketplace catalogue. Sign-in prompt visible but not required. | |
| Marketplace Home | Landing Page | REUSE | Marketplace | `/home` — categories, What's New, Highlights, Most Popular, Publisher Spotlight | Customer / Prospect | As a customer, I want a Marketplace home that surfaces relevant apps and highlights so I can discover new products easily. | Carousels for What's New, Highlights, Most Popular, Publisher Spotlight. Category tile grid. All ungated. | |
| Applications Catalog | Listing Page | REUSE | Marketplace | `/applications` — filter by version, availability, area, category, price, type | Customer / Prospect | As a customer, I want to browse apps filtered by my Liferay version so I only see compatible options. | Filter panel with version, availability, area, category, price model, type. Results update without reload. | |
| Application Detail Page (PDP) | Detail Page | REUSE | Marketplace | `/p/{slug}` — overview, publisher link, download/install CTA | Customer | As a customer, I want a detailed app page with pricing, screenshots, and publisher info so I can make an informed decision. | PDP shows overview, pricing, screenshots, publisher link. Download/install CTA. Ratings section reserved for future. | |
| Products Catalog | Listing Page | REUSE | Marketplace | `/products` — DXP Free Tier, CMP, AI Hub | | | | |
| Products Detail Page (PDP) | Detail Page | REUSE | Marketplace | `/p/{slug}` — product detail | | | | |
| Solutions Catalog | Listing Page | REUSE | Marketplace | `/solutions-marketplace` — filter by category, tag | Customer / Prospect | As a customer, I want to browse partner solutions by category so I can find pre-built implementations for my industry. | Filter panel with category and tag. Solution cards show partner name, description, and CTA. | |
| Solution Detail Page | Detail Page | REUSE | Marketplace | `/p/{slug}` | | | | |
| Publisher Profile Page | Detail Page | REUSE | Marketplace | `/e/publisher-details/{id}` — apps list, company info | | | | |
| Publisher's Applications | Listing (filtered) | REUSE | Marketplace | Filtered to publisher | | | | |
| Publisher's Products | Listing (filtered) | REUSE | Marketplace | Filtered to publisher | | | | |
| Publisher's Solutions | Listing (filtered) | REUSE | Marketplace | Filtered to publisher | | | | |
| Global Product Search | UI Component | REUSE | Marketplace | Search input, category filter, recent searches, featured products | Any User | As any user, I want to search for products from anywhere in the portal so I can find things without navigating menus. | Global search bar in header. Autocomplete with recent searches. Category filter. Dedicated results page. | |

### Support

| Page / Area | Type | Status | Origin | URL / Notes | User Role | User Story | Acceptance Criteria | Notes |
|---|---|---|---|---|---|---|---|---|
| Support Landing Page | Landing Page | NEW BUILD | Unified Portal | Requires authentication. Customers can access support tickets and support-related items from here. | Customer | As a customer, I want a dedicated support landing page so I can quickly access my tickets and support resources after signing in. | Page requires authentication. Displays support tickets, escalation options, and support resources. | |
| Support Home | Landing Page | REUSE | CP | Mega menu — Tickets, Support Resources, Connect | | | | |
| My Tickets (JSM) | External Link | REUSE | CP | `liferay.atlassian.net` — review open requests | Customer | As a customer, I want to view my open support tickets from within the portal so I don't need to navigate to a separate system. | JSM link opens in new tab. Ticket count badge visible in portal nav (future consideration). | |
| Open a Ticket (JSM) | External Link | REUSE | CP | `liferay.atlassian.net` — create new support ticket | | | | |
| Escalate a Ticket | Page | REUSE | CP | Internal escalation form at `/support-ticket-escalation` | Customer | As a customer, I want to escalate a support ticket directly in the portal so I can flag urgent issues quickly. | Escalation form. Ticket ID required. Priority selector. Confirmation message on submit. | |
| Callback Requests | Page | REUSE | CP | `/callback-request` — contact support engineer | Customer | As a customer, I want to request a callback from a support engineer so I can discuss complex issues verbally. | Callback request form. Preferred time slots. Confirmation with estimated contact time. | |
| Business Events | Page | REUSE | CP | Subscription milestones, renewals, planned go-lives | Customer | As a customer, I want to report upcoming business events so support is prepared for my go-lives and renewals. | Business events form. Event type, date, description. Linked to project. Visible in support view. | |
| Ticket Attachments | Page | REUSE | CP | LFU attachments — minor review recommended | Customer | As a customer, I want to upload large ticket attachments in the portal so I can share logs and files with support. | File uploader with progress bar. Size limit displayed. Files listed on project attachments page. | |
| Knowledge Base Hub | Landing Page | REUSE | CP | Guides, Product Docs, Security, Updates groups | Customer | As a customer, I want to search knowledge base articles and release notes so I can self-serve before opening a ticket. | Full-text search within KB. Faceted filters (type, category). Results from articles, release notes, security. | |
| Getting Started Guide | Page | REUSE | CP | Role-based onboarding paths: Admins, New Users, Partners | | | | |
| Customer Portal Help Articles | Page | REUSE | CP | Administration, Overview, DXP Activation, Cloud Activation, Team Members | | | | |
| Release Notes | Page | REUSE | CP | DXP, Cloud Infrastructure, Analytics Cloud, Version Comparison Tool | | | | Still pending decision if some articles will move to learn.liferay |
| Security Vulnerabilities | Page | REUSE | CP | Searchable CVE list at `/security-vulnerabilities` | | | | |
| Support Coverage | Page | REUSE | CP | Cloud Native, DXP, PaaS, SaaS coverage hubs | | | | Still pending decision if some articles will move to learn.liferay |
| Services & Compatibility | Page | REUSE | CP | Compatibility Matrix, Patching, Search Compatibility, Service Levels, FAQ | Customer | As a customer, I want to view the compatibility matrix so I know which Liferay versions support my configuration. | Filterable compatibility table. Product, version, environment columns. PDF export option. | Still pending decision if some articles will move to learn.liferay |
| Customer Events | Page | REUSE | CP | `/announcements` — event listing with type/region/country filters | | | | |
| Announcements Feed | Component | REUSE | CP | Portal updates and customer events | | | | TBD |

### Admin (Internal Only)

| Page / Area | Type | Status | Origin | URL / Notes | User Role | User Story | Acceptance Criteria | Notes |
|---|---|---|---|---|---|---|---|---|
| Internal Admin Landing Page | Landing Page | NEW BUILD | Unified Portal | Liferay employees only. Power access to internal tools and account management capabilities. | Internal Admin / Liferay Staff | As a Liferay employee, I want a dedicated internal admin landing page so I can access internal tools and manage customer accounts efficiently. | Restricted to Liferay staff roles only. Surfaces internal tools, account overrides, provisioning controls, and admin dashboards. Not visible to customers. | |
| Account Details | Sub-page | MERGE | CP + Koroneiki | Company info, SF key mapping, account editing | Internal Admin | As an Internal Admin, I want to edit account details so our company data stays accurate. | Edit form with validation. Changes sync to Salesforce. Audit entry created on save. | |
| Account Notes | Sub-page | TBD | Koroneiki | Internal notes and instructions per account. Audit trail TBC. | Internal Admin | As an Internal Admin, I need to be able to keep track of the account's history and list out important notes. | | |
| Liferay Workers View | Sub-page | MERGE | Koroneiki | View Liferay staff assigned to account | Internal Admin | As an Internal Admin, I want to see which Liferay staff are assigned to my account so I know who to contact. | Workers view lists name, role, and email for assigned Liferay staff. Read-only. | |
| Partner Team Visibility | Sub-page | MERGE | Koroneiki | FLS partner team visibility | Internal Admin | As an Internal Admin, I want to see which FLS team is assigned to an account. | Team view shows members; add/remove controls; FLS visibility toggles. | |
| Roles & Permissions | Page | MERGE | CP + Koroneiki | Unified RBAC across all portal areas | Any User | As any authenticated user, I want to see my current role and permissions so I understand what I can do in the portal. | Profile page shows assigned role, permissions summary, and account membership. | |
| Subscription-Level Toggles (Admin) | Admin Component | NEW BUILD | CP | Permanent licences, self-provisioning, complimentary keys — replaces Koroneiki manual toggles | Internal Admin | As an internal admin, I want to manage subscription-level toggles. | Admin subscription view with toggle panel. Changes logged. | |
| Admin Control Menu | Functional Area | REUSE | Both | Edit page, Configure, Simulation, A/B Test, Page Audit, Applications Menu | | | | |
| Administrator Dashboard | Dashboard | REUSE | Marketplace | `/administrator-dashboard` — internal admin hub | | | | |
| Publisher Dashboard | Dashboard | REUSE | Marketplace | `/publisher-dashboard` — Apps, Solutions, Account | Publisher Admin | As a publisher I want to be able to manage my apps. | | |
| Finance Dashboard (Admin) | Dashboard | REUSE | Marketplace | `/finance-dashboard` — Orders, Payments sub-pages (internal) | Finance Admin | As a Finance Admin, I want to view all orders and payments in one dashboard so I can manage billing queries. | Finance dashboard with Orders and Payments sub-pages. Export to CSV. Filter by date and status. | |
| SaaS Trials & SSA Provisioning | Page | REUSE | Marketplace | 7-day self-serve SaaS trials; SSA SaaS cluster provisioning | Prospect / Customer | As a prospect, I want to start a 7-day free SaaS trial without contacting sales so I can evaluate the product independently. | Trial launch CTA on PDP. SSA provisioning automated. Confirmation email sent. Environment ready within session. | |
| LPKG / Luffa Wrapping (Publisher) | Workflow | MERGE | Marketplace | Licensed LPKGs and Luffas for Marketplace publishers; moved over from web.liferay | Publisher | As a Publisher, I want to create LPKG/Luffa packages for my apps so I can distribute licensed versions to customers. | LPKG/Luffa wrapping workflow in publisher dashboard. App selector. Licence config. Download output. | |
| Publish New App CTA | Modal / Flow | REUSE | Marketplace | Publish new app workflow from publisher dashboard | Publisher | As a Publisher, I want to submit a new app for review from my dashboard so I can list it in the Marketplace. | Publish new app modal/flow. App details, screenshots, pricing config. Submission triggers review workflow. | |
| SSA Dashboard | Dashboard | REUSE | Marketplace | `/ssa-dashboard` — SaaS Demos, Environments, Manage Users | | | | |

### Provisioning Hub (Backend)

| Page / Area | Type | Status | Origin | URL / Notes | User Role | User Story | Acceptance Criteria | Notes |
|---|---|---|---|---|---|---|---|---|
| Provisioning Hub Overview | Dashboard | NEW BUILD | Unified Portal | Central hub for all activation, provisioning, and deployment | Customer | As a customer, I want a single provisioning hub so I can manage all my environments and activations in one place. | Hub page with status overview for all environments, pending activations, and available add-ons. | |
| Cloud Workspace Activation | Wizard | MERGE | CP + Marketplace | Provision PaaS and SaaS — CIC, Cloud Native, AC workspaces | Customer | As a customer, I want to activate my cloud workspace from within the portal so I can go live. | Multi-step wizard. Environment type selector. Provisioning status tracker. Confirmation email on completion. Eventually replaced with Marketplace Checkout and new CE API. | |
| Keyless Activation | Component | NEW BUILD | Unified Portal | No UI — backend component of provisioning hub | Customer | | | |
| Embedded Product Catalog | Component | NEW BUILD | Unified Portal | Local catalog of add-ons within customer bundle; version-specific | Backend | As a customer, I want to discover and activate add-ons within my subscription context without navigating away. | Embedded catalog shows version-specific add-ons. Purchase or activate in 1 click. Filtered by active subscription. | |

### Authentication & System

| Page / Area | Type | Status | Origin | URL / Notes | Notes |
|---|---|---|---|---|---|
| Sign In | Auth Page | REUSE | Both | `/c/portal/login` → Okta SAML SSO | |
| OAuth2 Endpoints | System | REUSE | Both | `/o/oauth2/*` — unchanged | |
| Cookie Configuration | Overlay | REUSE | Both | Accept All / Decline All / Configurations | |
| Create an Account | Auth Page | REUSE | Marketplace | `login.liferay.com/signin/register` | Create account UX is not intuitive; needs improvement in a future phase |

### Embedded Product Experience (DXP)

Future-phase features exposing liferay-one data inside DXP via headless API.

| Page / Area | Type | Status | Origin | URL / Notes | User Role | User Story | Acceptance Criteria |
|---|---|---|---|---|---|---|---|
| In-App Subscription View | Component | NEW BUILD | Unified Portal | Current plan, tier, subscription inside DXP — live via headless API | DXP User | As a DXP user, I want to see my current subscription plan inside DXP so I know my entitlements without switching portals. | In-app widget shows plan name, tier, renewal date. Live data via headless API. Always current. |
| Entitlement & Licence Enforcement | Component | NEW BUILD | Unified Portal | Real-time consumed vs available entitlements; enforced at app level | DXP Admin | As a DXP admin, I want to see how many entitlements I've consumed so I can plan usage. | Entitlement gauge widget. Consumed / available / total. Refreshes on page load. Near-limit warning state. |
| In-App Product Discovery & Add-ons | Component | NEW BUILD | Unified Portal | Browse and purchase add-ons without leaving DXP | DXP User | As a DXP user, I want to browse and purchase add-ons from within DXP so I can extend my instance without leaving the product. | Add-on catalog widget filtered to current subscription version. 1-click purchase triggers checkout flow in unified portal. |
| Headless API Layer | Architecture | NEW BUILD | Unified Portal | All features exposed as headless APIs — DXP is first consumer | | | |

---

## Navigation

### Public (Marketplace)

Unauthenticated visitors see Marketplace nav + sign-in link. No Support or Admin links visible.

### Customer-Authenticated

Authenticated customers see:
- Marketplace (persistent)
- Customer Portal dropdown: My Tickets, Deployments, Business Events, Callback Request

### Internal (Admin)

Visible only to `Administrator` or `Liferay Staff` users. Persistent side nav.

---

## Page Permissions

| Page Tree | Default View Permission |
|---|---|
| `/` and Marketplace public pages | Guest + authenticated |
| `/publisher-onboarding`, `/contact-sales` | Guest + authenticated (public form) |
| Customer portal (`/home`, `/tickets`, `/projects`, …) | Any account-membered user |
| `/admin/*` | `Liferay Staff` user group + `Administrator` role |
| `/admin/entitlement-definitions` | `Administrator` only |
| `/admin/license-keys` (generate/revoke) | `Administrator` + `license.admin` scope |
| `/admin/debug` | `Administrator` only |

---

## Custom Element

One React + TypeScript custom element, `liferay-one-custom-element`. Feature modules under `src/features/`:

- **Marketplace features** — ported from `liferay-marketplace-workspace` (~349 TSX files): App catalog, App detail + purchase flow, Publisher dashboard, Trial-request flow, Feedback modals.
- **Support features** — ported from `liferay-customer-workspace`: Deployment list/detail, Ticket create/view, Attachment upload, BusinessEvent editor, Security vulnerabilities browser, Release notes browser.
- **Admin features** — new: Account browser, Entitlement-definition editor, Scheduled-task dashboard, OAuth2 app management, ExternalLink audit browser, Debug panel.

---

## Future Improvements (Out of Scope for Current Build)

### Product Discovery & Marketplace

| Feature | Status |
|---|---|
| AI Hub Marketplace — dedicated section for AI-powered apps (citizen developer & partner contributions) | Won't Have |
| Ratings & Reviews — community-driven star ratings and written reviews per product | Won't Have |
| Developer Badges & Certifications — verified Liferay certifications on publisher profiles | Won't Have |

### Provisioning Hub

| Feature | Status |
|---|---|
| Auto-Updater — customers configure automatic Liferay updates from within portal | Won't Have |

### Commerce & Billing

| Feature | Status |
|---|---|
| Payment Method Management — manage stored payment cards via Stripe | Won't Have |
| Automatic Invoice Generation (end-to-end) | Out of Scope |
| Subscription Bundling (strategic sales initiative) | Out of Scope |

### Authentication & System

| Feature | Status |
|---|---|
| Unified Login + Registration UX Redesign — Create an Account flow is not intuitive | Could Have |

### Embedded Product Experience (DXP)

| Feature | Status |
|---|---|
| In-App Subscription View — current plan inside DXP via headless API | Out of Scope |
| Entitlement & Licence Enforcement in DXP — real-time consumed vs available | Out of Scope |
| In-App Product Discovery & Add-ons — browse and purchase add-ons from within DXP | Out of Scope |

---

## Open Questions

1. **Marketplace-shared auth** — Confirm session-cookie + SSO behavior with existing Liferay auth infrastructure during phase 4.

1. **Account Selector vs Subscription Selector** — Do we need both? Single account selector spanning all sites.

1. **Release Notes / Support Coverage / Services & Compatibility** — Pending decision whether these articles move to `learn.liferay.com`.

1. **Single OAuth2 app vs separate per audience** — `one-custom-element` widens credential scope surface. Revisit if audit pressure requires tighter scoping.

1. **Feature flags** — Consider migrating `-testing` variant pages to a true feature-flag mechanism (GrowthBook) post-launch.