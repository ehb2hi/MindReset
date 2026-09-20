# Android releases

Android releases are created manually with `.github/workflows/release.yml`. The workflow always
checks out `main`, builds signed artifacts, then creates `release/v<version>`, the matching
`v<version>` tag, and a GitHub release. A failed build does not push the branch or tag to GitHub.

## GitHub configuration

Configure these encrypted repository secrets under **Settings > Secrets and variables > Actions**:

- `HABLYRA_UPLOAD_KEYSTORE_BASE64`
- `HABLYRA_UPLOAD_KEY_ALIAS`
- `HABLYRA_UPLOAD_KEY_PASSWORD`
- `HABLYRA_UPLOAD_STORE_PASSWORD`
- `HABLYRA_GOOGLE_SERVICES_JSON_BASE64`

Create a GitHub Environment named `production` and configure these environment variables under its
**Variables** tab:

- `HABLYRA_ADMOB_APP_ID`
- `HABLYRA_ADMOB_BANNER_ID`

`HABLYRA_GOOGLE_SERVICES_JSON_BASE64` is the unchanged release `google-services.json` encoded
as a single Base64 value. The debug Firebase configuration is not needed for a release build.

The workflow must have permission to write repository contents so it can push the release branch and
tag and create the GitHub release. Do not store the upload keystore or any password in the repository.

## Running a release

Open **Actions > Android release > Run workflow** and provide:

- `version`: semantic release version without a required `v` prefix, for example `1.0.0`
- `version_code`: positive, monotonically increasing Play Store version code
- `runner`: `github-hosted` or `physical`
- `upload_bundle`: build and publish the Play Store `.aab`
- `upload_apk`: build and publish a directly installable release `.apk`

At least one artifact must be selected. Existing release branches and tags are rejected instead of
being overwritten.

## Physical runner

The `physical` option targets a current GitHub Actions self-hosted runner. Keep the runner software
updated so it supports the Node runtime required by the pinned action majors. It must be a Linux x64 machine with
the Android SDK and Android build tools installed and available through `ANDROID_SDK_ROOT` or
`ANDROID_HOME`. It also needs Git, Bash, Java tooling, `base64`, and GitHub CLI (`gh`). Java 21 and
Gradle caching are configured by the workflow actions.

Keep the runner patched and dedicated to trusted repository workflows. The decoded upload keystore and Firebase configuration exist only for the build and are removed by
the workflow's final step.
