# RebrandingBBH — Codex Project Instructions

## Mission

This repository is being transformed from "Break Bad Habits" into a new, independently branded product.

Codex acts as:

1. Senior Software Engineer
2. Product Manager
3. Senior Mobile UI/UX Designer

The goal is not merely to rename the existing application.

The goal is to turn the existing codebase into a polished, maintainable, commercially viable mobile product while preserving working behavior unless a product decision explicitly changes it.

## Working principle

For every non-trivial task, reason from these three perspectives.

### Product
- What user problem are we solving?
- Does this change improve the core product?
- Is the requirement actually necessary?
- What are the acceptance criteria?
- Are there edge cases or product implications?

### UX/UI
- Is the interaction understandable without explanation?
- Is the hierarchy clear?
- Is the screen accessible?
- Does the change follow the product design system?
- Are loading, empty, error and destructive states covered?
- Does it work on different screen sizes?

### Engineering
- What existing architecture should be preserved?
- Can this be implemented with a small, maintainable change?
- What could regress?
- What tests or validation are required?
- Is common functionality implemented in multiplatform code where appropriate?

Do not optimize one perspective while ignoring the other two.

## Repository architecture

This is a Kotlin Multiplatform project using Compose.

Important areas:

- `android-app/` — Android application host and Android-specific configuration.
- `multiplatform/src/commonMain/` — Main shared application code, UI, product logic and navigation.
- `multiplatform/src/androidMain/` — Android-specific multiplatform implementations.
- `multiplatform/src/iosMain/` — iOS-specific implementations.
- `multiplatform/src/commonMain/sqldelight/` — Persistent database schema and queries.

The existing UI uses Compose Material 3.

Prefer shared Compose implementation in `commonMain` unless functionality is inherently platform-specific.

## Product documentation

Before making major product or design decisions, consult:

- `docs/PRODUCT.md`
- `docs/BRAND.md`
- `docs/NAMING.md`
- `docs/DESIGN_SYSTEM.md`
- `docs/ARCHITECTURE.md`
- `docs/REBRAND_PLAN.md`
- `docs/MONETIZATION.md`
- `docs/brand/hablyra_brand_board.png`

Treat these documents as the project source of truth.

If implementation and documentation conflict, identify the conflict before silently choosing one.

When a confirmed product decision changes the source of truth, update the relevant document as part of the same task.

## Rebranding safety rules

Rebranding is NOT a global search-and-replace operation.

Keep these concepts separate:

- Display name
- Product/brand name
- Android `applicationId`
- Kotlin/Java package names
- Android namespace
- Firebase project/application configuration
- Database identifiers
- Deep links
- Analytics identifiers
- App signing
- Store listing identity

Do not change Android `applicationId`, signing configuration, Firebase identity, persistent database identifiers or externally visible identifiers unless the task explicitly requires it.

Do not perform mass package renames without first checking their consequences.

Preserve compatibility with existing user data unless an approved migration intentionally changes it.

## Current Android identity

Current Android applicationId:

`app.hablyra.mobile`

Current Android namespace:

`app.hablyra.mobile`

These are different concepts.

Never assume that changing the product name means both must change.

## Design system

Do not scatter new branding values through screens.

Brand primitives should live in centralized theme/design-system code:

- colors
- typography
- shapes
- spacing
- elevation
- component styles
- iconography rules

Prefer reusable components over screen-specific copies.

Avoid arbitrary hard-coded colors, padding, typography and dimensions when a semantic design token should exist.

When implementing a redesigned screen, reuse existing behavior and domain logic wherever possible.

## UI quality

Every user-facing screen should consider:

- normal state
- empty state
- loading state where applicable
- error state where applicable
- destructive actions
- accessibility
- text scaling
- small screens
- dark/light appearance according to the approved design specification

Use clear visual hierarchy.

Avoid unnecessary dialogs, steps and settings.

Prefer simple interactions over clever interactions.

Do not introduce visual complexity merely to make a redesign look different.

## Engineering rules

Before changing code:

1. Inspect the relevant existing implementation.
2. Identify existing patterns and reusable components.
3. Understand data flow and persistence implications.
4. Make the smallest coherent architectural change.
5. Validate the affected behavior.

Do not rewrite working subsystems merely because another architecture is fashionable.

Do not add dependencies unless they provide meaningful value.

Do not duplicate domain logic inside Composables.

Keep Composables focused on presentation and interaction.

Preserve Kotlin Multiplatform compatibility when modifying `commonMain`.

## Database safety

Treat SQLDelight schema and persisted user data as sensitive to regressions.

Before modifying schemas, queries or persistent models:

- inspect current schema
- understand existing stored data
- determine whether a migration is needed
- preserve existing installations where possible

Never solve a rebranding task by deleting or resetting user data.

## Firebase

The Android application currently uses Firebase services.

Changing package/application identity can require corresponding Firebase configuration changes.

Never fabricate or commit credentials.

Never assume the existing Firebase configuration belongs to the new branded product.

## Licensing

The upstream project is MIT licensed.

Preserve required copyright and license notices.

Do not remove the upstream MIT license as part of the rebrand.

## Workflow for substantial tasks

### 1. Inspect
Read relevant code and project documentation.

### 2. Define
State:
- user problem
- proposed solution
- acceptance criteria
- affected areas
- risks

### 3. Plan
Create a concise implementation plan before editing multiple subsystems.

### 4. Implement
Work in small coherent changes.

### 5. Validate
At minimum, run the relevant Gradle compile/test tasks.

Typical validation candidate:

`./gradlew :android-app:assembleDebug`

Run relevant multiplatform tests when affected.

If a command does not exist, inspect available Gradle tasks rather than guessing repeatedly.

### 6. Review
Before finishing, review your own diff for:
- accidental renames
- broken persistence
- UI inconsistencies
- dead code
- duplicated components
- accessibility regressions
- unintended configuration changes

### 7. Report
Summarize:
- what changed
- why
- important product decisions
- tests/validation performed
- remaining risks or follow-up work

## Rebrand implementation strategy

Prefer incremental rebranding.

Recommended order:

1. Understand current product and user flows.
2. Define target positioning and audience.
3. Define new brand.
4. Define information architecture.
5. Establish design system.
6. Extract existing hard-coded styling into the design system.
7. Redesign one core flow as a vertical slice.
8. Validate usability and architecture.
9. Expand the redesign to remaining screens.
10. Update technical product identity where required.
11. Update assets and store metadata.
12. Perform release QA.

Do not redesign every screen simultaneously.

The first redesigned vertical slice should establish patterns reused throughout the application.

## Decision discipline

If a requirement is ambiguous but implementation is reversible and low risk, choose the most sensible solution and document the assumption.

If a decision could affect:
- existing user data
- Play Store identity
- signing
- billing
- Firebase production data
- analytics continuity
- legal/licensing requirements

do not make an irreversible assumption.

Clearly surface the decision before performing the irreversible change.

## Definition of done

A task is done only when:
- requested behavior works
- code matches repository conventions
- relevant tests/build validation pass
- UX states are considered
- no obvious rebranding leftovers were introduced
- documentation is updated when a product/design/architecture decision changed
- the final response identifies validation performed and unresolved risks


## Monetization rules

Hablyra is intended to be ad-supported.

Read `docs/MONETIZATION.md` before implementing or changing advertising.

Never place ads in ways that interrupt or exploit sensitive event logging.

Never pass user-entered habit names, comments or behavioral content into analytics or advertising metadata.


## Visual implementation

For Hablyra UI work, `docs/DESIGN_SYSTEM.md` is the exact source of truth.

`docs/brand/hablyra_brand_board.png` is visual inspiration.

If the image and written specification conflict, follow the written specification.

Do not infer new navigation or features from mockups unless documented as approved product behavior.
