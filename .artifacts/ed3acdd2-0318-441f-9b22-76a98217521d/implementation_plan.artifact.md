# Add Preview for ProfileScreen

The goal is to add a Jetpack Compose Preview for the `ProfileScreen` to allow developers to visualize the UI without running the app.

## Proposed Changes

### [Component Name]

#### [MODIFY] [ProfileScreen.kt](file:///E:/Android_Projectes/AndroidByCompose/TwoB/app/src/main/java/com/example/twob/profile/ProfileScreen.kt)
- Add a `@Preview` function at the end of the file that calls `ProfileContent` with a sample `ProfileState`.
- Use `TwoBTheme` to ensure the preview matches the app's styling.

## Verification Plan

### Manual Verification
- Verify the preview renders correctly in the Android Studio Design tab.
