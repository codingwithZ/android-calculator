# Logo Design in Figma

This guide is for making your app logo in Figma. Since your professor taught you how to use Figma from your recent research on making apps for the community then this is easy for you. 
For the rest, follow the instructions carefully. Do not be in a rush. Bes relax :3

Do not use AI to make your own logo.

Steps to Make the Logo in Figma
1. Open Figma and create a new design file.
2. Create a square frame such as 512 by 512 so your logo stays crisp when scaled down. Or click F on your keyboard and draw your Frame.
3. Build your icon shape using basic shapes or vector tools and keep details clean so they stay readable on small screens.
4. Select your logo frame or group and export it as 4x PNG.

Using the Exported Logo in Android Studio
1. Open the Project panel on the left and go to app then res then drawable or mipmap.
2. Paste your exported PNG file into the folder and give it a lowercase name with underscores such as app_logo.png.
3. Reference the logo in your layout XML or splash screen using the file name without the extension.

# Importing the Exported PNG Logo

1. Drag and drop your exported PNG file into res > drawable.
2. Delete both ic_launcher_background.xml and ic_launcher_foreground.xml from res/drawable if they are there.
4. Right-click your PNG file in drawable select Refactor then Rename and rename it to app_logo so the file becomes app_logo.png.
5. Go to res > mipmap > ic_launcher > open ic_launcher.xml.
6. Delete the line or attribute with android:ic_launcher_background and change "ic_launcher_foreground" inside the "" to "@drawable/app_logo".
7. Open ic_launcher_round.xml inside mipmap then ic_launcher and repeat the same change so it also points to "@drawable/app_logo".

Or copy this code and paste it both inside the ic_launcher.xml and ic_launcher_round.xml

```java
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <foreground android:drawable="@drawable/app_logo" />
    <monochrome android:drawable="@drawable/app_logo" />
</adaptive-icon>
```

<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <foreground android:drawable="@drawable/app_logo" />
    <monochrome android:drawable="@drawable/app_logo" />
</adaptive-icon>
