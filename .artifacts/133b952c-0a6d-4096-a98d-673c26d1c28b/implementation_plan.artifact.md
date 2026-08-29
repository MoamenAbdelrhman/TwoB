# Preview for ProfileScreen

The goal is to add a Jetpack Compose Preview for the `ProfileScreen`. Since `ProfileScreen` depends on a `ViewModel` and Koin injection, the preview will call the stateless `ProfileContent` composable instead.

## Proposed Changes

### Profile UI

#### [MODIFY] [ProfileScreen.kt](file:///E:/Android_Projectes/AndroidByCompose/TwoB/app/src/main/java/com/example/twob/profile/ProfileScreen.kt)
- Add a `@Preview` function at the end of the file.
- The preview will use `TwoBTheme`.
- The preview will call `ProfileContent` with sample `ProfileState` data.
- Two previews will be provided: one for the Loaded state and one for the Loading state.

## Verification Plan

### Manual Verification
- Render the `ProfileScreenPreview` in Android Studio's Design view to ensure it looks correct and reflects the provided sample data.
