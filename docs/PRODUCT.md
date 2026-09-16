# PRODUCT.md

## Document status

Status: Working product source of truth  
Product name: Hablyra (working/release name pending clearance)  
Last major decision owner: Human product owner

Codex may propose changes to this document, but it must not invent final positioning, pricing, legal claims, medical claims, or a final brand identity without explicit approval.

---

## 1. Existing product

The current application is a mobile tool for people who want to stop or reduce unwanted habits by tracking occurrences and measuring the time since the last occurrence.

The existing implementation centers on two domain objects:

### Habit
A habit has:
- id
- name
- icon

### Habit event record
An event record belongs to a habit and contains:
- start time
- end time
- event count
- comment

Deleting a habit cascades to its event records.

---

## 2. Existing user value

The current product helps a user answer:

- What habit am I trying to stop?
- How long has it been since the last occurrence?
- When did occurrences happen?
- How often did they happen?
- Are my abstinence periods improving over time?

The strongest existing product mechanic is the live abstinence duration shown for each habit.

---

## 3. Current core user flow

### A. Create a habit
1. Open dashboard.
2. Add a new habit.
3. Enter a habit name.
4. Choose an icon.
5. Save.

### B. Track progress
1. See all habits on the dashboard.
2. See current abstinence duration or a no-events state.
3. Open a habit.

### C. Record an occurrence
1. Open a habit.
2. Add a record.
3. Enter event count.
4. Select a date/time or time range.
5. Optionally add a comment.
6. Save.

### D. Review history
The habit detail experience can show:
- current abstinence duration
- calendar visualization
- abstinence histogram
- statistics
- event history/details

---

## 4. Existing major screens

1. Dashboard
2. Habit creation/editing
3. Habit details
4. Habit event/history view
5. Habit event creation/editing
6. Destructive confirmation dialogs

Navigation currently follows roughly:

Dashboard
→ Habit Details
→ Habit Event Editing

Dashboard
→ Habit Editing

Habit Details
→ Habit Editing

Habit Details
→ Event History
→ Event Editing

---

## 5. Product problem to solve in the rebrand

The rebrand must do more than replace visual branding.

We need to decide what the new product stands for and make the product experience support that positioning.

### Primary product question

Approved positioning:

Hablyra is a private, non-medical behavior reset and habit reduction tracker for adults who want to reduce or stop unwanted everyday behaviors.

It combines:
- fast event logging
- clear time-since-last-event progress
- history and statistics
- non-judgmental reflection

Hablyra is not positioned as addiction treatment, therapy, diagnosis, or a medical device.

Core promise:

"Break unwanted loops. Understand your patterns. See your progress."

The product should focus on awareness, reduction and progress rather than perfection.

---

## 6. Target audience

Primary target: adults 18+ who want to reduce or stop recurring unwanted everyday habits and prefer a private, lightweight tracker over a social or coaching product.

### Primary user

An adult who has one or more recurring behaviors they want to reduce, stop or understand better.

### User motivation

- create awareness
- increase time between unwanted events
- see progress over time
- identify patterns
- recover from setbacks without losing the full history

### Typical use

- check current progress quickly
- log an event immediately or retrospectively
- review progress several times per week
- inspect history and statistics occasionally

### Emotional context

A user may open the app after a positive streak or directly after an unwanted event.

Therefore the UX must remain factual and non-judgmental in both situations.

### Success

The user feels more aware and in control of their behavior and can see meaningful progress over time.

### Retention risk

Users will leave if:
- logging is too slow
- ads interrupt sensitive actions
- the product feels judgmental
- statistics are confusing
- history feels unreliable
- privacy expectations are unclear

Hablyra is not positioned as treatment for addiction or any medical condition.

---

## 7. Product principles

Until replaced by approved principles, use these:

### 1. Fast to log
Logging an event should require as little friction as possible.

### 2. Progress without shame
The product should communicate facts and progress without moralizing, guilt, humiliation, or punitive UX.

### 3. Useful at a glance
The user should understand current progress immediately after opening the app.

### 4. History should create insight
Charts and statistics should answer meaningful user questions rather than exist as decoration.

### 5. Private by default
Behavioral tracking can be sensitive. Avoid unnecessary data collection and clearly justify analytics.

### 6. Reliable history
A redesign must not silently lose or reinterpret existing records.

---

## 8. Current MVP capabilities to preserve during rebranding

Unless an approved product change says otherwise:

- create a habit
- edit a habit
- delete a habit
- choose a habit icon
- view habits
- view time since last recorded occurrence
- add an event record
- edit an event record
- delete an event record
- event count
- date/time range
- comments
- calendar/history visualization
- progress statistics
- abstinence histogram

---

## 9. Approved product terminology

Use simple, neutral terminology.

| Domain concept | User-facing terminology |
|---|---|
| Habit | Habit |
| HabitEventRecord | Event |
| Abstinence | Current streak / Time since last event |
| New habit | Add habit |
| Event count | Occurrences |
| Event history | History |
| Statistics | Insights |

Notes:

- Use "event" rather than "failure", "relapse" or "mistake".
- Use "current streak" only when the concept is actually a continuous time-since-last-event metric.
- Avoid moral or medical terminology.
- Internal code names do not need to be renamed merely to match UI copy.

---

## 10. Success metrics

Primary product and monetization metrics:

- habit creation completion
- first event/history interaction
- logging completion rate
- 7-day retention
- 30-day retention
- active tracked habits per user
- repeat review of progress/history
- deletion/uninstall signals where observable

Also track monetization health:
- ad impressions per active user
- ad revenue per daily active user
- retention segmented by ad exposure
- crash-free users

Analytics should be implemented through the new application's Firebase project and must respect consent and privacy requirements.

Do not log sensitive habit names, comments or user-entered behavioral content as analytics parameters.

---

## 11. Non-goals for the first rebrand release

Unless explicitly approved:

- social network
- public profiles
- leaderboards
- gamification economy
- AI coaching
- medical diagnosis
- therapy replacement
- large backend/account system
- major data model rewrite

---

## 12. Approved strategic decisions

1. Working product name: Hablyra
2. Positioning: non-medical habit / behavior reset tracker
3. Target audience: adults 18+
4. Tone: calm, direct, non-judgmental, minimal and premium
5. Distribution: new Google Play listing
6. Existing old-app user-data migration: not required for the new listing unless explicitly added later
7. Launch priority: Android first
8. Theme: light and dark mode
9. Monetization: free, ad-supported
10. Initial ad platform: Google AdMob
11. Initial analytics: new Firebase project/app for Hablyra
12. Accounts/backend: no account required in V1

### Release-name warning

Preliminary screening found no obvious exact-match mobile app or prominent software/wellness brand named "Hablyra".

This is not a legal trademark opinion. Perform formal trademark and domain clearance before public release and before making irreversible legal/marketing commitments.

---

## 13. Acceptance principle

Every proposed feature or redesign should be explainable in one sentence:

"This helps the user ______ with less friction / more clarity / better insight."

If that sentence cannot be completed convincingly, reconsider the change.
