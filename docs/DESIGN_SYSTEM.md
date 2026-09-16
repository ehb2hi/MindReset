# DESIGN_SYSTEM.md

## Status

Approved visual direction for the first Hablyra implementation.

This document is the UI source of truth unless a later approved design decision replaces it.

The visual reference is:

`docs/brand/hablyra_brand_board.png`

Codex should use the image as direction, but this document takes precedence for exact implementation values.

---

# 1. Design intent

Hablyra should feel:

- calm
- premium
- focused
- private
- modern
- non-judgmental

The product must NOT feel:

- medical
- childish
- aggressively motivational
- gamified
- visually noisy
- like a generic red "quit habit" application

The visual metaphor is:

**Break the loop. Build your progress.**

The UI should communicate awareness and forward movement rather than punishment.

---

# 2. Design principles

## Clarity first

The user should understand their current progress within seconds.

## One dominant action per screen

Avoid competing primary actions.

## Progress over punishment

Use neutral, factual language after an event is recorded.

## Calm surfaces

Use whitespace, restrained borders and soft elevation.

## Data should feel human

Charts and metrics should be readable and meaningful, not dashboard decoration.

## Sensitive flows stay interruption-free

Do not show ads inside habit creation, event logging, confirmation or destructive flows.

---

# 3. Color system

Use semantic tokens.

Do NOT scatter raw hex values through screens.

## Light theme

### Brand

`brandPrimary`
- HEX: `#2E7D6B`
- Purpose: primary actions, selected navigation, progress emphasis

`brandPrimaryHoverPressed`
- HEX: `#256A5B`

`brandSecondary`
- HEX: `#A7D7C5`
- Purpose: subtle highlight, progress backgrounds, secondary illustration accents

`brandAccent`
- HEX: `#FF7A7A`
- Purpose: sparing accent only
- Do not use as primary CTA color

### Background and surfaces

`background`
- HEX: `#F6F8F7`

`surface`
- HEX: `#FFFFFF`

`surfaceSubtle`
- HEX: `#EEF3F1`

`surfaceElevated`
- HEX: `#FFFFFF`

### Content

`contentPrimary`
- HEX: `#1F2937`

`contentSecondary`
- HEX: `#667085`

`contentTertiary`
- HEX: `#98A2B3`

`contentOnPrimary`
- HEX: `#FFFFFF`

### Structure

`borderSubtle`
- HEX: `#E4E7EC`

`divider`
- HEX: `#EAECF0`

### Semantic

`success`
- HEX: `#3F8F7C`

`successContainer`
- HEX: `#DFF7E9`

`warning`
- HEX: `#D99A3D`

`warningContainer`
- HEX: `#FFF1D6`

`error`
- HEX: `#D95C5C`

`errorContainer`
- HEX: `#FCE8E8`

`info`
- HEX: `#7C6EE6`

`infoContainer`
- HEX: `#EEEAFE`

---

## Dark theme

### Brand

`brandPrimary`
- HEX: `#71C8AE`

`brandPrimaryHoverPressed`
- HEX: `#5AB298`

`brandSecondary`
- HEX: `#244A40`

`brandAccent`
- HEX: `#FF8E8E`

### Background and surfaces

`background`
- HEX: `#10191D`

`surface`
- HEX: `#162228`

`surfaceSubtle`
- HEX: `#1C2B31`

`surfaceElevated`
- HEX: `#213239`

### Content

`contentPrimary`
- HEX: `#F4F7F6`

`contentSecondary`
- HEX: `#B7C2BF`

`contentTertiary`
- HEX: `#84928E`

`contentOnPrimary`
- HEX: `#0C1714`

### Structure

`borderSubtle`
- HEX: `#2B3A40`

`divider`
- HEX: `#26353B`

### Semantic

`success`
- HEX: `#71C8AE`

`successContainer`
- HEX: `#183B32`

`warning`
- HEX: `#E8B562`

`warningContainer`
- HEX: `#3E311A`

`error`
- HEX: `#EF8585`

`errorContainer`
- HEX: `#432121`

`info`
- HEX: `#A99AF4`

`infoContainer`
- HEX: `#2A254A`

---

# 4. Typography

Use a clean sans-serif stack.

For the first implementation, prefer platform/system typography unless Inter is already safely available and licensed in the repository.

Do not block the redesign on font integration.

## Typography roles

### ProgressDisplay
Purpose:
- current streak
- key time-since-last-event metric

Suggested:
- 40sp
- medium/semi-bold
- tight line height
- tabular or visually stable numerals where practical

### ScreenTitle
Suggested:
- 28sp
- semi-bold

### SectionTitle
Suggested:
- 20sp
- semi-bold

### CardTitle
Suggested:
- 16sp
- semi-bold

### Body
Suggested:
- 16sp
- regular

### BodySmall
Suggested:
- 14sp
- regular

### Label
Suggested:
- 13sp
- medium

### Caption
Suggested:
- 12sp
- regular

Do not use more than 3 typography weights on a single screen unless justified.

---

# 5. Spacing

Use this scale:

- `space4` = 4dp
- `space8` = 8dp
- `space12` = 12dp
- `space16` = 16dp
- `space20` = 20dp
- `space24` = 24dp
- `space32` = 32dp
- `space40` = 40dp

Default screen horizontal padding:
- 20dp

Dense secondary lists may use:
- 16dp

Do not introduce arbitrary values unless a component truly needs them.

---

# 6. Shape system

Use soft but not playful rounding.

- small control radius: 10dp
- input radius: 12dp
- card radius: 16dp
- modal/sheet radius: 24dp
- pill/full: 999dp

Avoid excessive pill-shaped cards.

Reserve full rounding mainly for:
- segmented controls
- chips
- compact filters
- icon backgrounds

---

# 7. Elevation

Prefer border + tonal separation before heavy shadows.

### Flat
- default app background

### Card
- subtle tonal difference
- optional 1dp border
- very restrained shadow if platform rendering needs separation

### Floating action
- stronger elevation allowed

Do not use deep material shadows throughout the app.

---

# 8. Icon style

Use simple outline icons with consistent visual weight.

Primary principles:
- clean
- rounded where appropriate
- no cartoon styling
- no filled icon mix unless state change requires it

Use filled/selected treatment only for selected navigation or active state.

---

# 9. Logo direction

Core visual idea:

**an interrupted circular loop**

The mark should suggest:
- interruption of repetition
- re-entry into control
- continuous progress
- no shame / no warning symbolism

Avoid:
- brain icon
- lightning bolt
- broken chain cliché
- prohibition sign
- red "stop" symbol
- checkmark as the primary logo

The current brand-board mark is approved as a direction, not yet as the final production asset.

---

# 10. Core components

## PrimaryButton

Visual:
- brandPrimary background
- contentOnPrimary text
- 52dp minimum height
- 12dp radius
- horizontal padding 20–24dp
- no gradient

Behavior:
- disabled state is visually clear
- progress/loading state must not change width unpredictably

---

## SecondaryButton

Visual:
- transparent or surface
- borderSubtle
- contentPrimary

Use for:
- edit
- cancel
- secondary actions

---

## DestructiveButton

Visual:
- error semantic color
- never visually compete with save/primary action

Use destructive actions lower in hierarchy.

---

## HabitCard

Purpose:
communicate the user's current state immediately.

Content hierarchy:

1. habit icon + habit name
2. current progress / streak
3. compact secondary trend or last-event context
4. optional directional affordance

Do not overload each card with charts.

Preferred structure:

- surface card
- 16dp radius
- 16dp internal padding
- one small semantic accent/icon container
- progress metric has stronger weight than metadata

---

## ProgressMetric

Use large numeric hierarchy.

Examples:
- "3 days"
- "18h 42m"
- "No events yet"

Never show progress in a way that makes a newly logged event look like punishment.

After an event:
- update factually
- show that tracking/history remains intact

---

## EmptyState

Each empty state includes:

- short title
- one helpful sentence
- clear CTA if relevant
- optional simple icon

Avoid illustrations that visually dominate the screen.

---

## InputCard

Keep form structure calm and linear.

Preferred:
- label
- optional helper text
- input
- inline error text

Avoid nesting inputs inside multiple visible card layers.

---

## AdSlot

Approved only on non-sensitive surfaces.

Initial style:
- clearly separated from user data
- predictable reserved area
- never mimic a habit card
- never visually compete with primary CTA
- must remain usable when empty/not loaded

---

# 11. Navigation

For the first rebrand phase, preserve existing app navigation architecture.

Do not introduce bottom navigation solely because it appears in the visual board.

The board is visual inspiration, not a requirement to invent new information architecture.

If a future product decision adds bottom navigation, treat that as a separate UX decision.

---

# 12. Dashboard

## User goal

Within 3 seconds, the user should know:
- what they are tracking
- current progress
- how to log/add something

## Layout direction

### Top area
- Hablyra title or concise greeting
- optional settings affordance only if useful

### Habit list
- vertically stacked HabitCards
- 12–16dp spacing
- meaningful metric visible without opening details

### Primary action
Preferred:
- clear "Add habit" floating or anchored action
- do not compete with quick event logging inside habit details

## Empty state

Suggested message direction:

Title:
"Start with one habit"

Body:
"Choose something you want to understand or reduce. Hablyra will help you track the pattern over time."

CTA:
"Add habit"

---

# 13. Habit details

## Hierarchy

1. habit name + icon
2. current progress metric
3. primary "Log event" action
4. recent context
5. history
6. analytics/insights

The user should not need to scroll through analytics before logging an event.

## Header

Suggested:
- centered or left-aligned identity depending on final prototype
- strong progress metric
- one clear primary action

Avoid oversized decorative hero areas.

---

# 14. Event logging

This is the most sensitive flow.

## Priorities

1. fast
2. clear
3. neutral
4. forgiving

## Field order

Recommended:

1. occurrences
2. date/time
3. optional duration/range
4. optional note/comment

If the common case is a single point-in-time event, do not force range complexity upfront.

## Save action

Use a single strong CTA:
- "Save event"

## Editing

Use:
- "Save changes"

## Delete

Place as a lower-emphasis destructive action separated from Save.

---

# 15. Insights and charts

Charts should answer a question.

Examples:
- Are events becoming less frequent?
- Are streaks getting longer?
- How many events happened this week/month?
- What does the recent pattern look like?

Avoid:
- decorative charts
- too many colors
- 3D effects
- dense legends

Use brandPrimary as default data emphasis.

---

# 16. Light and dark mode

Both are required.

Rules:
- semantic tokens only
- test both modes for every redesigned component
- do not mechanically invert colors
- ensure charts work in both modes
- ads must not create glaring contrast blocks

---

# 17. Accessibility

Required:

- 48dp minimum interactive target where practical
- meaningful content descriptions for actionable icons
- decorative icons not redundantly announced
- text remains readable with larger font settings
- long habit names truncate gracefully
- no color-only meaning
- contrast appropriate for text and controls
- errors include text, not only border color

---

# 18. Advertising UX

Hablyra is ad-supported, but ads must not exploit sensitive moments.

## V1 approved
- one adaptive banner area on dashboard
- optional second banner on history/insights if the layout remains calm

## V1 not approved
- app-open ads
- rewarded ads
- interstitial ads
- ads inside event forms
- ads directly after logging an event
- ads inside destructive confirmation

Ads must be clearly distinguishable from user data.

---

# 19. Implementation order

1. Extract theme/design tokens.
2. Keep behavior unchanged.
3. Implement reusable core components.
4. Redesign dashboard.
5. Redesign habit details.
6. Redesign event logging.
7. Validate vertical slice.
8. Expand to remaining screens.
9. Integrate ads after core UX is stable.

Do NOT combine visual redesign, package rename, Firebase migration and ad integration into one task.

---

# 20. Definition of visual done

A redesigned screen is complete only when:

- light theme works
- dark theme works
- no raw brand colors are scattered locally
- empty state works
- long text works
- large text does not break layout
- primary action is obvious
- destructive actions are lower hierarchy
- user data is not visually confused with ads
- behavior remains correct
