# FIRST_CODEX_TASK.md

You are beginning the RebrandingBBH project.

Read `AGENTS.md` and all files in `docs/` before acting.

Do not modify production code yet.

Perform a complete repository and product audit.

Act simultaneously as:
- Senior Software Engineer
- Product Manager
- Senior Mobile UI/UX Designer

## Produce

### 1. Current product summary
- core user problem
- primary user flows
- main features
- important domain concepts

### 2. Screen inventory
- every major screen
- navigation relationships
- important dialogs/states/actions

### 3. Technical architecture
- modules
- shared vs platform-specific code
- navigation
- persistence/database
- Firebase/analytics
- advertising/consent readiness
- theming
- localization
- major dependencies

### 4. Rebranding surface inventory
Identify every category that can contain old branding:
- app/display name
- UI copy
- colors
- typography
- icons
- launcher assets
- package/namespace/applicationId
- Firebase
- database identifiers
- analytics
- Android resources
- iOS resources
- filenames/classes where relevant
- store-related references
- documentation

### 5. UX audit
For the main flows identify:
- usability problems
- visual hierarchy problems
- confusing interactions
- accessibility issues
- missing empty/error states
- opportunities for simplification

### 6. Engineering risks
Especially identify anything where rebranding could break:
- persisted user data
- database compatibility
- application identity
- Firebase
- navigation
- Android/iOS builds

### 7. Proposed rebranding architecture
Recommend where the new:
- design system
- theme
- brand assets
- product copy
- localization
should live.

### 8. Recommended implementation phases
Break the work into small phases and evaluate whether the vertical slice proposed in `docs/REBRAND_PLAN.md` is the best first slice.

### 9. Documentation updates
Update these documents only where the repository audit provides verified facts:
- `docs/PRODUCT.md`
- `docs/ARCHITECTURE.md`
- `docs/REBRAND_PLAN.md`

Do not convert TBD product-owner decisions into invented answers.

## Hard constraints

Do not perform a mass rename.

Do not change:
- applicationId
- namespace
- Firebase configuration
- database schema
- signing configuration
- persistent identifiers

Do not redesign or implement screens yet.

## Final response

End with:
1. the remaining decisions required before implementation (do not repeat decisions already approved in docs),
2. the 5 highest technical/release risks,
3. the recommended first implementation task after those decisions are made.
