# MONETIZATION.md

## Status

Approved monetization direction for V1.

Business model:
Free Android application funded primarily by advertising.

Initial provider:
Google AdMob.

---

## 1. Monetization principle

Revenue must not undermine the core product promise.

Hablyra may be opened at emotionally sensitive moments.

The app must never punish, shame or aggressively monetize a user immediately after they record an unwanted event.

---

## 2. V1 ad formats

### Adaptive banner
Approved for V1.

Preferred placements:
- dashboard
- history / insights surfaces

Use one predictable ad area rather than multiple banners on the same screen.

### Native ads
Optional after the core redesign.

Only use where a native placement can be clearly labeled as an advertisement and visually separated from user data.

### Interstitial
Not part of the first monetization implementation.

May be evaluated later after retention data exists.

If introduced:
- only at a natural navigation break
- strict frequency cap
- never directly after event logging
- never on app launch before core content
- never between form entry and save result

### Rewarded
Out of scope for V1.

There is currently no natural optional reward mechanic that justifies a rewarded ad.

### App-open ads
Out of scope for V1.

Fast access to current progress is more important than launch monetization.

---

## 3. Ad-free option

Not required for V1.

Architecture should not make a future one-time "Remove Ads" purchase difficult, but do not implement billing until explicitly requested.

---

## 4. Consent and privacy

Advertising must be designed with consent from day one.

For users where required:
- resolve consent before personalized advertising
- provide privacy options entry point
- respect updated consent choices
- coordinate analytics and advertising consent state

Never include sensitive product data in ad targeting or analytics parameters.

Examples that must remain local:
- habit names
- comments
- event text
- behavioral categories entered by users

---

## 5. Analytics

Use analytics to understand product and monetization health, not to profile sensitive behavior.

Approved examples:
- screen_view
- habit_created
- event_logged
- history_opened
- ad_impression
- ad_failed_to_load

Do not attach user-entered habit names or comments.

Prefer coarse product events over detailed behavioral content.

---

## 6. Revenue UX metrics

Monitor:

- ad revenue per daily active user
- impressions per active user
- fill rate
- click-through rate where appropriate
- retention before/after ad changes
- event-log completion rate
- crash-free users
- session abandonment near ad placements

If an ad change increases short-term revenue but materially harms logging completion or retention, revert it.

---

## 7. Initial rollout

Recommended order:

1. redesign core product without ads
2. create stable ad-slot component
3. integrate consent
4. integrate AdMob test ads
5. add one dashboard banner placement
6. add one history/insights placement if layout supports it
7. validate retention and usability
8. only then evaluate additional formats

---

## 8. Technical rules

- use test ad IDs during development
- never commit private credentials
- keep production AdMob IDs configurable and documented
- isolate Android ad SDK integration from common domain logic
- handle load failure gracefully
- app functionality must remain usable when ads fail or consent disallows personalized ads
- reserve/animate ad space carefully to minimize layout shift

## 9. Android AdMob and UMP configuration

The Android implementation uses Google Mobile Ads SDK `25.5.0` and UMP SDK `4.0.0`.
Firebase is initialized before UMP, and Mobile Ads initialization is gated by
`ConsentInformation.canRequestAds()`. UMP is the source of truth for consent; product code must not
infer consent from cached strings or map consent categories itself.

### Build configuration

Debug builds always use Google's official sample AdMob app ID and adaptive banner ad unit ID. Release
IDs are intentionally absent from source control and must be supplied as Gradle properties or
environment variables:

- `HABLYRA_ADMOB_APP_ID`: the AdMob app ID for Android package `app.hablyra.mobile`
- `HABLYRA_ADMOB_BANNER_ID`: the production Dashboard banner ad unit ID

Locally, put them in the developer's global `~/.gradle/gradle.properties`, pass them with `-P`,
or export same-named environment variables. Do not put them in the repository's `gradle.properties`
and do not duplicate them in Kotlin or UI code.

### UMP debug testing

Debug builds accept these optional Gradle properties:

- `HABLYRA_UMP_DEBUG_GEOGRAPHY=eea` forces the EEA/UK/Switzerland consent path.
- `HABLYRA_UMP_DEBUG_GEOGRAPHY=not_eea` forces the non-regulated `OTHER` geography.
- `HABLYRA_UMP_TEST_DEVICE_ID=<hashed-id>` registers a physical device reported by UMP logcat.
- `HABLYRA_UMP_RESET=true` calls UMP's supported `reset()` once on each debug process launch.

Android emulators are UMP test devices automatically. To replay first launch, run once with reset
enabled, then disable it before checking the returning-user flow. These overrides are generated as
release-safe resource values; release always uses normal geography and never resets consent.

Before release, create and publish the required GDPR message and privacy-options form in AdMob
Privacy & messaging, enable Consent Mode in AdMob, and verify all four consent signals in Firebase
Analytics debug logs. The Settings row is displayed only when UMP reports that privacy options are
required.

### Production ad unit requirement

The Dashboard integration loads a banner directly with the Google Mobile Ads SDK. It therefore
requires a **standard AdMob Banner ad unit**. A Partner Bidding ad unit created for a third-party
mediation platform is not compatible with this direct integration and must not be supplied as
`HABLYRA_ADMOB_BANNER_ID`.

Release builds run `validateReleaseAdMobConfiguration` before `preReleaseBuild`. They fail when an
identifier is missing or does not have the appropriate app-ID (`~`) or ad-unit-ID (`/`) shape. This
format validation cannot distinguish a standard unit from Partner Bidding; that serving type must be
verified in the AdMob console.

Local configuration in `~/.gradle/gradle.properties`:

```properties
HABLYRA_ADMOB_APP_ID=<production-app-id>
HABLYRA_ADMOB_BANNER_ID=<standard-production-banner-id>
```

Equivalent environment variables are supported. The manual Android release workflow reads both
names from GitHub repository or environment variables without embedding values in the workflow:

```yaml
jobs:
  release:
    environment: production
    env:
      HABLYRA_ADMOB_APP_ID: ${{ vars.HABLYRA_ADMOB_APP_ID }}
      HABLYRA_ADMOB_BANNER_ID: ${{ vars.HABLYRA_ADMOB_BANNER_ID }}
```

The values must be configured under **Settings > Secrets and variables > Actions > Variables** before
running `.github/workflows/release.yml`. Signing material is stored separately as encrypted GitHub
Actions secrets; see `docs/RELEASING.md`.
