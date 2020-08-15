package com.haris.meal4u.Utility;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatDelegate;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.AppController;
import com.haris.meal4u.ObjectUtil.Base64Object;
import com.haris.meal4u.ObjectUtil.DateTimeObject;
import com.haris.meal4u.ObjectUtil.GeocodeObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {


    /**
     * <p>Show the Toast in Activity</p>
     *
     * @param context context of activity or either Fragment
     * @param message your message you want to show in Toast
     * @param length  length of Toast
     */
    public static void Toaster(Context context, String message, int length) {
        Toast.makeText(context, message, length).show();
    }


    /**
     * <p>Show the Message in Logcat</p>
     *
     * @param tag     tag you want to use in your logger
     * @param message message you want to show in logcat
     */
    public static void Logger(String tag, String message) {
        Log.e(tag, message);
    }


    /**
     * <p>Show the Message in Logcat</p>
     *
     * @param tag     tag you want to use in your logger
     * @param message message you want to show in logcat
     */
    public static void LoggerForImportantMessages(String tag, String message) {
        Log.i(tag, message);
    }


    public static void extraData(String TAG, String message) {
        int maxLogSize = 2000;
        for (int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            Log.e(TAG, message.substring(start, end));
        }
    }


    /**
     * <p>Share your app  with friend & Colleagues</p>
     *
     * @param context reference from the acitivty or fragment
     */
    public static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out " + getStringFromRes(context, R.string.app_name) + " app at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    /**
     * <p>Share your app  with friend & Colleagues</p>
     *
     * @param context reference from the acitivty or fragment
     */
    public static void shareApp(Context context, String wallpaperLink) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_TEXT, wallpaperLink);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    /**
     * <p>Check the Connection status either it is available or not</p>
     *
     * @param context reference from activity or either fragment
     * @return true if internet connection available
     */
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) { // connected to the internet
            ////Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }


    /**
     * <p>Check any specific text either it's null or not</p>
     *
     * @param text text about which we want to know about
     * @return true if text is Empty
     */
    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }


    /**
     * <p>It is used to open playstore app link for rating</p>
     *
     * @param context
     */
    public static void rateApp(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }


    /**
     * <p>It is used to open web url</p>
     *
     * @param context
     * @param url
     */
    public static void openWebUrl(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }


    /**
     * <p>It is used to copy textual data </p>
     *
     * @param context
     * @param text
     */
    public static void copyData(Context context, String text) {

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Place Address", text);
        clipboard.setPrimaryClip(clip);

    }


    /**
     * <p>It is used to open phone dialer with number</p>
     *
     * @param context
     * @param phone
     */
    public static void openDialer(Context context, String phone) {
        String mobileNo = phone;

        Intent intent = new Intent(Intent.ACTION_DIAL).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + mobileNo));
        context.startActivity(intent);
    }


    /**
     * <p>It is used to Save Image into App Private Memorys</p>
     *
     * @param bitmapImage
     * @param name
     * @return
     */
    public static String saveToInternalStorage(Context context, Bitmap bitmapImage, String name) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, name);

        Utility.Logger("Image Path", mypath.toString());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.toString();
    }


    /**
     * <p>It is used to Capitalize the Word first letter</p>
     *
     * @param capString
     * @return
     */
    public static String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }


    /**
     * <p>It is used to get Colour from Resource file</p>
     *
     * @param context
     * @param colour
     * @return
     */
    public static int getColourFromRes(Context context, int colour) {
        return context.getResources().getColor(colour);
    }


    /**
     * <p>It is used to get String values from Resource file</p>
     *
     * @param context
     * @param label
     * @return
     */
    public static String getStringFromRes(Context context, int label) {
        if (context == null || label == 0)
            return null;
        return context.getResources().getString(label);
    }


    /**
     * <p>It is used to get Current Time</p>
     *
     * @return
     */
    public static String getCurrentTime() {
        String date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.TimeDateFormat.timeDateFormat12);
        date = simpleDateFormat.format(new Date());
        return date;
    }


    /**
     * <p>It is used to read Json file from Asset Folder</p>
     *
     * @param context
     * @param textFileName
     * @return
     */
    public static String getFileData(Context context, String textFileName) {
        String strJSON;
        StringBuilder buf = new StringBuilder();
        InputStream json;
        try {
            json = context.getAssets().open(textFileName);

            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));

            while ((strJSON = in.readLine()) != null) {
                buf.append(strJSON);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buf.toString();
    }


    /**
     * <p>It is used to round off value to n decimal points</p>
     *
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    /**
     * <p>It is used to dismiss Keyboard</p>
     *
     * @param context
     * @param editText
     */
    public static void hideKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    /**
     * <p>It is used to set Wallpaper</p>
     *
     * @param context
     * @param uri
     */
    public static void setWallpaper(Context context, Uri uri) {

        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        //intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        //intent.putExtra("mimeType", "image/*");
        context.startActivity(intent);


    }


    /**
     * <p>It is used to share wallpaper with other app</p>
     *
     * @param context
     * @param uri
     */
    public static void shareBook(Context context, Uri uri) {

        Utility.Logger("Sharing Uri", uri.toString());

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        //shareIntent.putExtra(Intent.EXTRA_TEXT, getStringFromRes(context, R.string.download_wallpaper));
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "Share with"));

    }


    /**
     * <p>It is used to parse Time & Date into required format</p>
     *
     * @param
     * @return
     */
    public static DateTimeObject parseTimeDate(DateTimeObject dateTimeObject) {
        DateTimeObject timeObject = null;
        Date dateObject = null;
        String time = null;
        String date = null;


        if (dateTimeObject.getDatetimeType() == Constant.DATETIME.BOTH12) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.TimeDateFormat.timeDateFormat12);

            try {

                if (dateTimeObject.isDateInLong()) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(dateTimeObject.getDateTimeInLong());

                    dateObject = calendar.getTime();

                } else if (dateTimeObject.isCurrentDate()) {
                    dateObject = new Date();
                } else {
                    dateObject = simpleDateFormat.parse(dateTimeObject.getDatetime());
                }

                date = new SimpleDateFormat(Constant.TimeDateFormat.dateFormat12).format(dateObject);
                time = new SimpleDateFormat(Constant.TimeDateFormat.timeFormat12).format(dateObject);

                timeObject = new DateTimeObject().setDate(date).setTime(time).setDatetime(date + " " + time);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else if (dateTimeObject.getDatetimeType() == Constant.DATETIME.BOTH24) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.TimeDateFormat.timeDateFormat24);

            try {

                if (dateTimeObject.isDateInLong()) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(dateTimeObject.getDateTimeInLong());

                    dateObject = calendar.getTime();

                } else if (dateTimeObject.isCurrentDate()) {
                    dateObject = new Date();
                } else {
                    dateObject = simpleDateFormat.parse(dateTimeObject.getDatetime());
                }

                date = new SimpleDateFormat(Constant.TimeDateFormat.dateFormat24).format(dateObject);
                time = new SimpleDateFormat(Constant.TimeDateFormat.timeFormat24).format(dateObject);

                timeObject = new DateTimeObject().setDate(date).setTime(time).setDatetime(date + " " + time);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else if (dateTimeObject.getDatetimeType() == Constant.DATETIME.DATE_DD_MM_YYYY_hh_mm_ss) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.TimeDateFormat.timeDateFormat12);

            if (dateTimeObject.isCurrentDate()) {
                dateObject = new Date();
            } else if (dateTimeObject.isCustomDateObject()) {
                dateObject = dateTimeObject.getDateObject();
            }

            ///date = new SimpleDateFormat(Constant.TimeDateFormat.dateFormat12).format(dateObject);
            ///time = new SimpleDateFormat(Constant.TimeDateFormat.timeFormat12).format(dateObject);

            timeObject = new DateTimeObject().setDatetime(simpleDateFormat.format(dateObject));

        }


        return timeObject;
    }


    /**
     * <p>It is used to get calculated values of counters></p>
     *
     * @param counter
     * @return
     */
    public static String getCalculatedValue(String counter) {
        int count = Integer.parseInt(counter);
        double calculatedAmount;
        String k = "k";

        if (count > 999) {
            calculatedAmount = count / 1000;
            k = String.valueOf(calculatedAmount) + "k";
        } else {
            calculatedAmount = count;
            k = String.valueOf((int) calculatedAmount);
        }

        return k;
    }


    /**
     * <p>It is used to check Numeric</p>
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return str.matches("[+-]?\\d*(\\.\\d+)?");
    }


    /**
     * <p>It is used to convert Bitmap into String</p>
     *
     * @param image
     * @return
     */
    public static String bitmapIntoString(Bitmap image) {
        if (image == null)
            return null;

        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }


    /**
     * <p>It is used to convert String into Bitmap</p>
     *
     * @param input
     * @return
     */
    public static Bitmap stringIntoBitmap(String input) {

        if (Utility.isEmptyString(input))
            return null;

        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


    /**
     * <p>It is used to convert dp into px</p>
     *
     * @param i
     * @return
     */
    public static int dpToPx(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }


    /**
     * <p>It is used to get Current Date Time</p>
     *
     * @param dateTimeObject
     * @return
     */
    public static DateTimeObject getCurrentDateTime(DateTimeObject dateTimeObject) {


        String time;
        String date;
        DateTimeObject dateObject = new DateTimeObject();
        SimpleDateFormat simpleDateFormat = null;

        if (dateTimeObject.getDatetimeType() == Constant.DATETIME.DATE) {

            date = new SimpleDateFormat(Constant.TimeDateFormat.dayFormat).format(new Date());
            dateObject.setDate(date);

        } else if (dateTimeObject.getDatetimeType() == Constant.DATETIME.TIME) {

            time = new SimpleDateFormat(Constant.TimeDateFormat.timeFormat24).format(new Date());
            dateObject.setTime(time);

        }

        return dateObject;

    }


    /**
     * <p>It is used to check either Service running or not</p>
     *
     * @param serviceClassName
     * @return
     */
    public static boolean isServiceRunning(String serviceClassName) {
        final ActivityManager activityManager = (ActivityManager) AppController.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isLocked(int pos) {

        boolean isLocked = false;
        //Random r = new Random();
        //int random = r.nextInt(45 - 12) + 12;

        if (pos % 25 == 0 && pos != 0) {
            isLocked = true;
        }

        return isLocked;
    }


    public static String encodeUrl(String url) {
        String encodedText = null;

        try {
            encodedText = URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedText;
    }


    /**
     * <p>It is used for conversion of Text & Bitmap into Base64 & vice versa </p>
     *
     * @param base64Object
     * @return
     */

    public static String base64Converter(Base64Object base64Object) {
        String result = null;

        //Utility.Logger("Base64", base64Object.toString());

        if (base64Object.getBitmap() != null && base64Object.isEncode()) {

            int width = 0;
            int height = 0;
            float aspectRatio;

            if (!base64Object.isOriginal()) {

                aspectRatio = base64Object.getBitmap().getWidth() /
                        (float) base64Object.getBitmap().getHeight();
                width = 280;
                height = Math.round(width / aspectRatio);

            } else {

                width = base64Object.getBitmap().getWidth();
                height = base64Object.getBitmap().getHeight();

            }

            Bitmap yourSelectedImage = Bitmap.createScaledBitmap(
                    base64Object.getBitmap(), width, height, false);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteFormat = stream.toByteArray();
            // get the base 64 string
            result = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        } else if (base64Object.isEncode() && base64Object.getText() != null) {

            /*byte[] data = new byte[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                data = base64Object.getText().getBytes(StandardCharsets.UTF_8);
            } else {

                try {
                    data = base64Object.getText().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            result = Base64.encodeToString(data, Base64.NO_WRAP);*/

        } else if (base64Object.isDecode() && base64Object.getText() != null) {

            byte[] data = Base64.decode(base64Object.getText(), Base64.NO_WRAP);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                result = new String(data, StandardCharsets.UTF_8);
            } else {

                try {
                    result = new String(data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }


        Utility.extraData("Base64 Resized = ", result);

        return result;
    }

    public static boolean isImage(String file) {
        return ((file.endsWith(".jpg") || file.endsWith(".png") || file.endsWith(".jpeg") || file.endsWith(".gif"))) ? true : false;

    }

    public static boolean isVideo(String file) {

        return ((file.endsWith(".mp4") || file.endsWith(".avi") || file.endsWith(".mkv"))) ? true : false;

    }


    /**
     * <p>It is used to change app theme </p>
     *
     * @param context
     */
    public static void changeAppTheme(Context context) {
        Management management = new Management(context);
        PrefObject prefObject = management.getPreferences(new PrefObject()
                .setRetrieveNightMode(true));

        if (!prefObject.isNightMode()) {

            context.setTheme(R.style.AppTheme);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        } else {

            context.setTheme(R.style.AppTheme_Base_Night);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }

    }


    /**
     * <p>It is used to get Night Mode</p>
     *
     * @param context
     * @return
     */
    public static boolean isNightMode(Context context) {

        Management management = new Management(context);
        PrefObject prefObject = management.getPreferences(new PrefObject()
                .setRetrieveNightMode(true));

        return prefObject.isNightMode();
    }

    /**
     * <p>It is used to get Color from Attribute</p>
     *
     * @param context
     * @param attr
     * @return
     */
    public static int getAttrColor(Context context, int attr) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attr, typedValue, true);
        @ColorInt int color = typedValue.data;
        return color;
    }


    /**
     * <p>It is used to Playstore Url</p>
     *
     * @param context
     * @return
     */
    public static String getAppPlaystoreUrl(Context context) {
        final String appPackageName = context.getPackageName();
        return "https://play.google.com/store/apps/details?id=" + appPackageName;
    }


    /**
     * <p>It is used to get numeric data from Stirng</p>
     *
     * @param str
     * @return
     */
    public static String extractNumericDataFromString(String str) {

        if (str == null) {
            return null;
        }

        StringBuffer strBuff = new StringBuffer();
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);

            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }


    /**
     * <p>It is used to split the String by Space
     * For example "UserObject Name" , after splitting
     * it would be converted into "UserObject" , "Name"</p>
     *
     * @param str
     * @return
     */
    public static String[] splitingBySpace(String str) {
        String[] splited = new String[10];
        if (!str.matches("\\S+")) {
            splited = str.split("\\s+");

        } else {
            splited[0] = str;
        }
        return splited;
    }


    public static String getColourForBackground(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorBackground, typedValue, true);
        @ColorInt int colors = typedValue.data;
        //Utility.Logger("ColourForBackground ", String.valueOf(Utility.getColourFromRes(context, typedValue.resourceId)));
        return Integer.toHexString(colors).substring(2);
    }

    public static String getColourForText(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorHeading, typedValue, true);
        @ColorInt int color = typedValue.data;
        return Integer.toHexString(color).substring(2);
    }

    public static String getColourForLink(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorLink, typedValue, true);
        @ColorInt int color = typedValue.data;
        return Integer.toHexString(color).substring(2);
    }


    public static String getColourForDivBg(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorTagline, typedValue, true);
        @ColorInt int color = typedValue.data;
        return Integer.toHexString(color).substring(2);
    }


    public static int getScreenWidth(Context context) {

        /*Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;*/

        Configuration configuration = context.getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        int screenHeightDp = configuration.screenHeightDp;
        int smallestScreenWidthDp = configuration.smallestScreenWidthDp;

        /*DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;*/

        return screenWidthDp;
    }


    /**
     * <p>Used to apply different color to required
     * specific area</p>
     *
     * @param currency
     * @param budget
     * @return
     */
    public static SpannableString getBudgetType(Context context, String currency, String budget) {

        int expense = Integer.parseInt(budget);
        StringBuilder noOfBudget = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            noOfBudget.append(currency);
        }

        SpannableString spannableStr = new SpannableString(noOfBudget.toString());

        ForegroundColorSpan selectedColorSpan = new ForegroundColorSpan(Utility.getAttrColor(context, R.attr.colorBottomNavigation));
        ForegroundColorSpan defaultColorSpan = new ForegroundColorSpan(Utility.getAttrColor(context, R.attr.colorDefaultNavigationIcon));

        spannableStr.setSpan(selectedColorSpan, 0, expense, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStr.setSpan(defaultColorSpan, expense, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableStr;
    }


    /**
     * <p>It is used to mask some few characters of Card No</p>
     *
     * @param cardNo
     * @return
     */
    public static String maskSomeCharacter(String cardNo) {

        StringBuilder cardNoBuilder = new StringBuilder();

        for (int j = 0; j < cardNo.length(); j++) {
            if (j < (cardNo.length() - 3)) {
                cardNoBuilder.append("\u002A");
            } else {
                cardNoBuilder.append(cardNo.charAt(j));
            }
        }

        return cardNoBuilder.toString();
    }


    /**
     * <p>It is used to get Geo Code Object</p>
     *
     * @param context
     * @param latitude
     * @param longitude
     * @return
     */
    public static GeocodeObject getGeoCodeObject(Context context, double latitude, double longitude) {
        GeocodeObject geocodeObject = null;
        List<Address> addresses = null;
        Geocoder geocoder;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger("GeoCodeObject", addresses.toString());

        geocodeObject = new GeocodeObject()
                .setAddress(addresses.get(0).getAddressLine(0))  // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                .setCity(addresses.get(0).getLocality())
                .setState(addresses.get(0).getAdminArea())
                .setCountry(addresses.get(0).getCountryName())
                .setPostalCode(addresses.get(0).getPostalCode())
                .setKnownName(addresses.get(0).getFeatureName());   // Only if available else return NULL

        return geocodeObject;

    }


    /**
     * <p>It is used to check Location provider enable or disable</p>
     *
     * @param context
     * @return
     */
    public static boolean isLocationProviderAvailable(Context context, boolean isGps) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception ex) {
            Utility.Logger("Error Gps", ex.getMessage());
        }

        Utility.Logger("Gps Status", String.valueOf(gps_enabled));

        if (gps_enabled) {
            return true;
        }

        return false;
    }

    public static boolean emailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
