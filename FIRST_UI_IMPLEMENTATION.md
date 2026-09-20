# FIRST_UI_IMPLEMENTATION.md

## Goal

Implement the first Hablyra visual foundation and pilot user flow.

This is the first production UI task after the repository audit.

Read:
- `AGENTS.md`
- `APPROVED_DECISIONS.md`
- `docs/PRODUCT.md`
- `docs/BRAND.md`
- `docs/NAMING.md`
- `docs/DESIGN_SYSTEM.md`
- `docs/ARCHITECTURE.md`
- `docs/REBRAND_PLAN.md`
- `docs/MONETIZATION.md`

Use:
- `docs/brand/hablyra_brand_board.png`

as visual inspiration.

The written design-system specification takes precedence over the image when they conflict.

---

# Scope

Implement only:

1. Hablyra theme/design foundation
2. Dashboard visual redesign
3. Habit Details top/core section visual redesign
4. Event creation/editing visual redesign

This creates the first vertical slice:

Dashboard
→ Habit Details
→ Log Event
→ Save
→ Updated Progress

---

# Hard constraints

Do NOT change:

- Android applicationId
- Android namespace
- Kotlin package names
- Firebase configuration
- SQLDelight schema
- database identifiers
- existing domain behavior
- navigation architecture
- signing
- ad SDK integration

Do NOT add advertising yet.

Do NOT redesign every remaining screen yet.

Do NOT introduce bottom navigation simply because it appears in the brand board.

---

# Phase 1 — Design system extraction

The current root theme contains hard-coded branding.

Move visual tokens into a dedicated shared design layer under commonMain.

Use a sensible structure such as:

`.../design/`
- `HablyraTheme.kt`
- `HablyraColors.kt`
- `HablyraTypography.kt`
- `HablyraShapes.kt`
- `HablyraSpacing.kt`

Adapt naming to repository conventions if a better fit exists.

Requirements:
- light theme
- dark theme
- semantic tokens
- no screen-specific raw brand hex values after migration of redesigned screens

Do not over-engineer a custom design framework.

Use Material 3 where it helps.

---

# Phase 2 — Core reusable components

Create only components required by the pilot slice.

Likely candidates:
- HablyraPrimaryButton
- HablyraSecondaryButton if required
- HablyraCard
- ProgressMetric
- EmptyState

Do not create wrappers for every Material component.

---

# Phase 3 — Dashboard redesign

Preserve behavior:
- list habits
- open habit
- add habit
- show no-events state
- show current abstinence/progress

Improve hierarchy.

Desired structure:

- calm app header
- clear list hierarchy
- redesigned HabitCard
- progress metric more prominent than secondary metadata
- polished empty state
- clear Add Habit action

No ads in this task.

Acceptance:
- current habits still load
- live progress still updates
- habit tap still navigates
- Add Habit still navigates
- no data schema changes

---

# Phase 4 — Habit Details redesign

Preserve:
- current progress
- edit habit action
- add event action
- existing downstream calendar/statistics sections

For this first slice, redesign the top/core hierarchy first.

Desired priority:

1. Habit identity
2. Current progress
3. Log Event CTA
4. Secondary history/analytics content

Do not rewrite analytics logic.

If deeper cards remain old-style temporarily, make the transition coherent but avoid redesigning all of them in this task.

---

# Phase 5 — Event creation/editing redesign

Preserve all current behavior and validation.

Fields:
- occurrences
- time/date or range
- comment

Goals:
- simple linear flow
- clearer labels
- inline errors
- strong Save Event CTA
- lower-emphasis Delete action
- both create/edit modes remain functional

Do not change SQL queries or persisted data representation.

---

# UX copy

Use approved neutral terminology.

Prefer:
- Habit
- Event
- Current streak
- Time since last event
- Occurrences
- History
- Insights
- Save event
- Add habit

Avoid:
- failure
- relapse
- mistake
- bad person / good person language
- medical terminology

If existing resource architecture supports localized strings, add/update strings there rather than hard-coding new UI copy.

---

# Accessibility

Verify:
- actionable icons have descriptions
- decorative icons are not redundantly announced
- long habit names behave correctly
- text scaling is resilient
- controls have appropriate touch targets
- error state is not color-only

---

# Validation

At minimum:

1. run relevant formatting/static checks if configured
2. run common/shared tests relevant to changed code
3. run:

`./gradlew :android-app:assembleDebug`

If a task is unavailable, inspect Gradle tasks rather than guessing.

---

# Manual scenarios

Verify:

### Dashboard
- zero habits
- one habit
- multiple habits
- long habit name
- habit with no events
- habit with current progress

### Habit details
- habit with no records
- habit with records
- edit navigation
- log event navigation

### Event form
- create event
- validation error
- valid save
- edit existing event
- delete existing event
- long comment
- date/time range

### Theme
- light
- dark

---

# Deliverable

At the end report:

1. files changed
2. design-system structure introduced
3. behavior intentionally preserved
4. screenshots/previews generated if available
5. tests/build commands run
6. any UX compromises or technical debt
7. recommended next screen to migrate

Do not proceed to ad integration or technical package rebranding in the same task.
