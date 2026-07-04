# Leone IPTV — Rebuilt Project

This is a reconstruction of your app, built from what could be recovered out of the
decompiled APK you uploaded (JADX output of `classes.dex`). It is a **from-scratch
Kotlin rewrite**, not a restored original — the original Kotlin source, resources,
and Gradle files were not recoverable from a compiled/obfuscated APK. Use this as a
working starting skeleton and rebuild forward from here.

## What's real (recovered directly from the decompiled classes)

- Package name: `com.genuine.leone`
- Room schema — table/column names came straight out of Kotlin `@Metadata` on the
  decompiled classes, so these should match your original DB exactly:
  - `StreamEntity`: mediaStreamUrl, cookieValue, refererValue, originValue,
    drmLicense, userAgent, drmSchema, createdAt
  - `PlaylistEntity`: playlistUrl, playlistName, userAgent, sortNum, filePath
  - `FavoriteChannelEntity`: favoriteChannel, playlistUrl
- Screen map: Splash → Main (Home / Playlist / Channel / Favorites / Settings
  fragments) → Player, matching the Activities/Fragments found in the decompile.
- Library stack confirmed from the decompile: Room, Media3/ExoPlayer, OkHttp,
  Glide, Firebase (Analytics, Crashlytics, Remote Config), Google Mobile Ads,
  Lottie/Shimmer for UI polish (not wired up here, just noting they were present).

## What's rebuilt/placeholder (logic could not be recovered — obfuscated bytecode)

- All method bodies (DAOs, parsing logic, player error-handling, ad loading,
  Firebase Remote Config keys) are **new implementations**, written to match the
  behavior your app is known to need (M3U parsing, custom HTTP headers, DRM via
  ClearKey/Widevine), not decompiled line-for-line.
- `google-services.json` — placeholder. Replace with your real one from the
  Firebase console or Firebase init calls will fail at runtime.
- AdMob `APPLICATION_ID` in the manifest — placeholder, replace with your real ID.
- App icon — using a system placeholder icon; drop your real launcher icons into
  `res/mipmap-*` and point the manifest back at `@mipmap/ic_launcher`.
- `ChannelFragment` currently loads playlist id `1` as a stand-in for "currently
  selected playlist" — wire this to your real playlist-selection flow.

## Setup

1. Open the project root in Android Studio (Koala+ recommended).
2. Replace `app/google-services.json` with your real Firebase config.
3. Replace the AdMob `APPLICATION_ID` placeholder in `AndroidManifest.xml`.
4. Add real launcher icons, then switch the manifest/layout back to `@mipmap/ic_launcher`.
5. Sync Gradle and run.

## Suggested next step

If you still have *any* old copy of the real project anywhere — old laptop,
email attachment, a GitHub/GitLab repo, Android Studio's local history cache
(`.idea` / `System Cache` folders), or an old build's `mapping.txt` from
`app/build/outputs/mapping/release/` — that would let us recover real method
names via ProGuard's mapping file and get much closer to the original, rather
than a rewrite. Worth a search before continuing to build forward on this skeleton.
