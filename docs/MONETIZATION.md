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
