# REBRAND_PLAN.md

## Document status

Status: Execution plan  
Implementation mode: Incremental

---

## Objective

Transform the existing Break Bad Habits codebase into a polished new product without breaking existing core behavior or accidentally changing production identity.

This plan separates:

1. Product redefinition
2. Brand identity
3. UX redesign
4. Design-system implementation
5. Screen migration
6. Technical identity changes
7. Release preparation

---

## Phase 0 — Guardrails and baseline

### Goals
- establish Codex project instructions
- document current architecture
- create a clean baseline
- verify build

### Tasks
- add `AGENTS.md`
- add `docs/`
- run current Android debug build
- document any pre-existing build failures
- create a dedicated rebrand branch

### Exit criteria
- Codex instructions available
- baseline build status known
- no production identity changed

---

## Phase 1 — Product audit

### Goals
Understand what should be preserved and what should change.

### Tasks
- inventory screens
- inventory navigation
- inventory UI strings
- inventory brand assets
- inventory analytics/Firebase touchpoints
- map persistence
- map major product states
- document UX issues

### Deliverable
Update `PRODUCT.md`, `ARCHITECTURE.md`, and this plan.

### Exit criteria
No major current user flow is unknown.

---

## Phase 2 — Product positioning

Core strategic direction is approved:

- working name: Hablyra
- adults 18+
- non-medical habit / behavior reset tracker
- calm, direct, non-judgmental, minimal, premium
- new Google Play listing
- Android-first
- light + dark mode
- free with advertising
- AdMob as initial ad platform
- Firebase Analytics + Crashlytics in a new application identity
- no account/backend requirement for V1

Remaining blocker:
- formal trademark/domain clearance for "Hablyra"

### Exit criteria
Naming risk is accepted or a replacement public brand name is chosen.

---

## Phase 3 — Brand direction

### Deliverables
- approved name
- logo direction
- app icon direction
- palette
- typography direction
- voice/tone
- initial store positioning

### Exit criteria
Enough approved material exists to implement a coherent design system.

---

## Phase 4 — UX architecture

### Goals
Improve flow before visual polish.

Review:

#### Dashboard
- immediate progress comprehension
- quick logging
- add-habit action
- empty state

#### Habit details
- metric hierarchy
- event logging CTA
- history/analytics hierarchy
- editing affordance

#### Event logging
- fastest common path
- time/date UX
- optional comment
- validation
- edit/delete behavior

#### History
- calendar usefulness
- record discoverability
- no-data states

### Exit criteria
Core flow and screen hierarchy approved.

---

## Phase 5 — Design system foundation

### Goal
Remove brand styling from individual screens.

### Initial technical target
Extract current hard-coded root theme into dedicated design-system code.

### Implement
- theme
- colors
- typography
- shapes
- spacing strategy
- component conventions
- preview/test approach where practical

### Important
Keep this phase primarily structural. Avoid changing every screen simultaneously.

### Exit criteria
A screen can be redesigned using shared tokens without adding random brand values locally.

---

## Phase 6 — First vertical slice

Recommended pilot:

Dashboard
→ Habit Details
→ Add Event
→ Save
→ Updated Progress

Why this slice:
- represents daily core value
- touches navigation
- touches persistence
- exercises main brand components
- validates event logging
- validates progress feedback

### Implement
- redesigned dashboard
- redesigned habit details header/core hierarchy
- redesigned add-event flow
- shared components required by the flow
- empty/error/validation states
- accessibility pass

### Exit criteria
The slice is visually coherent, functionally stable and establishes reusable patterns.

Do not redesign the rest of the app until lessons from this slice are incorporated.

---

## Phase 7 — Remaining screen migration

Migrate:
- habit creation/editing
- event history
- event editing
- statistics
- histogram/chart containers
- calendar presentation
- deletion dialogs
- secondary/empty states

### Exit criteria
No user-facing screen still visually belongs to the old brand.

---

## Phase 8 — Product copy and localization

### Tasks
- apply approved terminology
- audit all user-facing strings
- fix inconsistent labels
- update accessibility copy
- verify supported locales
- test long strings

Avoid changing technical identifiers during copy cleanup.

---

## Phase 9 — Technical identity rebrand

Only after store strategy is decided.

Create an explicit migration table for:

- application label
- applicationId
- namespace
- Kotlin packages
- repository name
- Firebase app/project
- launcher assets
- iOS bundle/display identity
- signing
- analytics
- store listing

Each item must be marked:
- change now
- preserve
- migrate later
- not applicable

### Critical rule
A new brand name does not automatically imply a new applicationId.

---

## Phase 10 — Asset and store release preparation

### Android
- adaptive icon
- monochrome icon
- launcher icon verification
- screenshots
- feature graphic if needed
- app title
- short description
- full description
- privacy disclosure
- release notes

### iOS if launching
- app icon
- display name
- screenshots
- privacy/store metadata
- bundle/signing validation

---

## Phase 11 — Release QA

### Functional
- create/edit/delete habit
- add/edit/delete record
- abstinence/progress update
- statistics
- calendar/history
- app restart persistence

### Migration
If upgrading an existing listing:
- install old version
- create realistic data
- upgrade to rebranded build
- verify all data survives

### UI
- small screen
- large screen
- long names
- large font
- empty data
- dense data
- dark/light themes as supported

### Configuration
- release build
- Firebase intended environment
- no accidental debug config
- signing strategy correct
- analytics behavior approved

---

## Codex task sizing

Prefer tasks that can be reviewed as one coherent change.

Good examples:
- "Extract app theme into design system without visual changes."
- "Redesign dashboard using approved tokens and preserve behavior."
- "Implement new event logging layout and keep SQL schema unchanged."

Bad examples:
- "Rebrand the whole app."
- "Modernize everything."
- "Rename all BreakBadHabits references."
- "Refactor architecture while redesigning all screens."

---

## Standard Codex task template

For every substantial implementation task, provide:

### Goal
What user/product outcome is required?

### Constraints
What must not change?

### References
Which docs/screens/files are authoritative?

### Acceptance criteria
Observable completion conditions.

### Validation
Commands/tests/manual scenarios.

### Out of scope
Explicit exclusions.

---

## Immediate next task

Before visual redesign, Codex should perform a read-only audit and update documentation.

It must NOT:
- change applicationId
- change namespace
- change Firebase configuration
- change SQLDelight schema
- mass rename packages
- delete old assets
- implement final branding before brand decisions are approved

Output should identify the five product decisions that most strongly block implementation.


## Phase 8A — Monetization integration

Perform after the core redesigned vertical slice is stable.

### Tasks
- create new AdMob application
- implement consent bootstrap
- integrate test ads
- create reusable ad-slot UI
- add initial adaptive banner to approved surface(s)
- verify no sensitive product data is passed to ad/analytics systems
- validate no layout break when ads fail or are unavailable

### Initial formats
- adaptive banner: yes
- native: optional later
- interstitial: no for initial launch
- rewarded: no
- app-open: no

See `docs/MONETIZATION.md`.
