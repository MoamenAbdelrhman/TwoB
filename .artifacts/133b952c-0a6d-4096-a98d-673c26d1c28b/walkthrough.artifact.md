# Profile Screen Previews

I have added two Jetpack Compose previews for the `ProfileScreen` in `ProfileScreen.kt`.

## Changes Made

### UI Components
- **Stateless Preview**: Added `@Preview` functions that call the stateless `ProfileContent` composable. This avoids issues with `ViewModel` and dependency injection in the preview environment.
- **Theme Integration**: Wrapped the previews in `TwoBTheme` to ensure consistent styling.
- **State Variations**:
    - `ProfileScreenPreview`: Shows the profile with sample data (Success state).
    - `ProfileScreenLoadingPreview`: Shows the loading indicator (Loading state).

## Verification Results

### Previews
The previews have been verified to render correctly in Android Studio.

> [!NOTE]
> I've used `ProfileContent` instead of `ProfileScreen` for the previews because `ProfileScreen` uses `koinViewModel()`, which is not supported in the standard preview environment without additional setup.

render_diffs(file:///E:/Android_Projectes/AndroidByCompose/TwoB/app/src/main/java/com/example/twob/profile/ProfileScreen.kt)
