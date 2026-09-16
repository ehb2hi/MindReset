# ARCHITECTURE.md

## Document status

Status: Current architecture map  
Source of truth for exact dependency versions: Gradle files in the repository.

---

## 1. Overview

The project is a Kotlin Multiplatform mobile application with shared Compose UI and shared product/domain logic.

Major modules:

### `android-app`
Android application host.

Responsibilities include:
- Android application configuration
- Android namespace/application ID
- Android launcher resources
- Firebase Android integration
- Android entry point

### `multiplatform`
Shared product implementation.

Contains:
- common UI
- navigation
- product/domain logic
- resources/strings abstractions
- SQLDelight schema and queries
- Android/iOS platform implementations
- shared calendar UI

### `ios-app`
iOS host/experiment consuming shared Kotlin/Compose functionality.

---

## 2. Current platform/configuration observations

At the time of this rebrand audit:

Android namespace:
`app.hablyra.mobile`

Android applicationId:
`app.hablyra.mobile`

Android uses Firebase Analytics and Crashlytics.

Do not treat this document as the source of truth for version numbers. Verify `gradle/libs.versions.toml` and module Gradle files before dependency work.

---

## 3. UI technology

The application uses Compose and Material 3.

Shared screens currently live primarily in:

`src/commonMain/kotlin/app/hablyra/screens/`

A reusable UI-kit area exists in:

`src/commonMain/kotlin/app/hablyra/uikit/`

The root screen currently defines the application color scheme directly.

A rebrand should centralize this theme before large-scale screen restyling.

---

## 4. Navigation

Navigation is implemented with Compose navigation.

Observed root destinations include:

- Dashboard
- Habit Details
- Habit Editing
- Habit Event Records Details
- Habit Event Record Editing

Typed routes are used.

Avoid replacing navigation architecture as part of visual rebranding unless a concrete UX requirement needs it.

---

## 5. Persistence

The project uses SQLDelight.

Current core tables:

### Habit
- id
- name
- iconId

### HabitEventRecord
- id
- habitId
- startTime
- endTime
- eventCount
- comment

Habit event records have a foreign key to Habit with cascade delete.

Persistent data is a high-risk area for rebranding.

Do not rename tables, columns or database identifiers merely for cosmetic consistency.

---

## 6. Current domain behavior

Observed product logic includes:

- habit name validation
- unique habit names
- event count validation
- event date/time validation
- abstinence calculation
- statistics
- histogram data
- event ranges/calendar visualization

Keep domain logic outside purely presentational components.

---

## 7. Platform-specific code

Use:
- `commonMain` for shared behavior/UI
- `androidMain` for Android-only implementations
- `iosMain` for iOS-only implementations

Do not introduce Android APIs into common code.

---

## 8. Firebase boundary

Firebase dependencies currently exist in the Android app.

Rebranding technical identity may require:
- new Firebase Android app
- new `google-services.json`
- analytics continuity decision
- Crashlytics continuity decision

Never fabricate Firebase configuration.

Never expose or copy production credentials into docs/prompts.

---

## 9. Technical identity matrix

Treat each independently.

| Identity | Current | Change policy |
|---|---|---|
| User-facing product name | Hablyra | Changed for the new product identity |
| Android applicationId | `app.hablyra.mobile` | New Google Play application identity |
| Android namespace | `app.hablyra.mobile` | Updated with Android identity phase |
| Kotlin packages | `app.hablyra...` | Updated with technical identity phase |
| Firebase identity | Existing config | Explicit migration/new-project decision |
| SQLDelight schema | Existing | Preserve unless migration required |
| Store identity | Existing Play listing | Human decision required |
| Signing identity | Existing/unknown | Never modify casually |

---

## 10. Architecture principles for the rebrand

### Preserve behavior before redesign
Refactor visual infrastructure with minimal behavior changes.

### Centralize brand decisions
Theme and design tokens should have one source of truth.

### Prefer vertical slices
Redesign one complete journey before broad mechanical changes.

### Avoid speculative rewrites
Do not migrate architecture frameworks just because the rebrand creates an opportunity.

### Protect data
User history is more important than internal naming cleanliness.

---

## 11. Validation expectations

After relevant work, run the narrowest useful checks plus an Android build.

Typical candidate:

`./gradlew :android-app:assembleDebug`

Also run relevant shared/common tests when domain logic changes.

If iOS/shared build behavior is affected, validate the appropriate KMP/iOS tasks available in the environment.

---

## 12. High-risk change list

Changes requiring explicit planning:

- applicationId
- app signing
- Firebase app/project
- SQLDelight schema
- package mass rename
- database location/name
- serialization formats
- analytics identity
- deep links
- migration between old/new store apps
- deletion of old resources before replacement is verified

---

## 13. Desired architecture outcome after rebrand

The repository should end with clear separation between:

### Brand
Name, iconography, voice and visual identity.

### Design system
Reusable theme tokens and components.

### Product/domain
Habits, events, progress and statistics.

### Platform identity
Android/iOS packaging, store identity, Firebase and signing.

This separation allows future brand iteration without endangering user data or release identity.


## 14. New-app release strategy

Hablyra is planned as a NEW Google Play application rather than an update of the existing Break Bad Habits listing.

Therefore:
- a new Android applicationId is expected
- a new Firebase app/project is recommended
- a new AdMob app registration is required
- new signing/release configuration must be established
- migration from the old Play listing is not a release requirement

However, the exact applicationId must not be guessed from the working brand name.

Choose it after:
1. final release-name clearance
2. publisher/company namespace decision
3. Play Console uniqueness verification

Recommended pattern:
`<publisher-domain-reversed>.hablyra`

Do not use `com.hablyra.app` blindly.

## 15. Advertising architecture

Initial monetization provider: Google AdMob.

Android responsibilities should remain in the Android host/platform layer where possible.

The shared UI may expose abstract ad slots/components if needed, but common product/domain logic must not depend directly on Android ad SDK classes.

### Consent

Ad initialization must respect consent requirements.

For EEA/UK/Switzerland, implement a compliant consent flow before requesting personalized ads.

Recommended implementation direction:
- Google User Messaging Platform (UMP)
- explicit consent/bootstrap service in Android platform code
- do not load ads before required consent state is resolved
- ensure analytics/ads consent state is coordinated with Firebase where required

### Privacy boundary

Never send the following as ad/analytics metadata:
- habit names
- event comments
- event descriptions
- sensitive user-entered content

## 16. Firebase strategy

Because Hablyra is a new Play Store application:

Create a NEW Firebase project or clearly isolated new Firebase application identity for the release.

Do not reuse the upstream app's `google-services.json` for production Hablyra.

Expected services:
- Firebase Analytics
- Firebase Crashlytics

Any additional Firebase services require explicit product need.
