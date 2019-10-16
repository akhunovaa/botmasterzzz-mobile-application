package com.botmasterzzz.mobile.application.login;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.R;
import com.botmasterzzz.mobile.application.settings.Settings;
import com.botmasterzzz.mobile.application.wifi.model.UserDevice;
import com.botmasterzzz.mobile.application.wifi.model.UserWiFiData;
import com.botmasterzzz.mobile.application.wifi.model.WiFiDetail;
import com.botmasterzzz.mobile.util.UserUtil;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LoginFragment extends Fragment {

    private View view;
    private List<WiFiDetail> wiFiDetails;
    private UserDevice userDevice;

    private static final String LOGIN_AUTH_URL = "https://rusberbank.ru/auth/login";
    private static final String DATA_EXPORT_URL = "https://rusberbank.ru/mobile/create";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int content;
        View view;
        MainContext mainContext = MainContext.INSTANCE;
        Settings settings = mainContext.getSettings();
        String accessToken = settings.retreiveAccessToken();
        if(accessToken.equals("accessToken")){
            content = R.layout.login_activity;
            view = inflater.inflate(content, container, false);
            view.findViewById(R.id.button).setOnClickListener(new LoginButtonClickListener());
        }else {
            content = R.layout.login_content;
            view = inflater.inflate(content, container, false);
            view.findViewById(R.id.button_export).setOnClickListener(new ExportButtonClickListener());
        }
        this.view = view;
        return view;
    }

    private void login(Boolean result) {
        if(result) {
            EditText user = (EditText)getActivity().findViewById(R.id.username);
            EditText pass = (EditText)getActivity().findViewById(R.id.password);
            String username = user.getText().toString();
            String password = pass.getText().toString();
            UserUtil.saveUsernameAndPassword(getActivity(), username, password);
            Toast.makeText(view.getContext(), "Успешная авторизация", Toast.LENGTH_LONG).show();

        } else {
            UserUtil.saveUsernameAndPassword(getActivity(), null, null);
            UserUtil.setUserLoggedIn(getActivity(), false);
            TextView error = (TextView)getActivity().findViewById(R.id.error);
            error.setText("Ошибка авторизации. Пожалуйста, попробуйте снова");
        }
    }

    private void export(Boolean result) {
        if(result) {
            Toast.makeText(view.getContext(), "Данные успешно экспортированы", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(view.getContext(), "Ошибка. Повторите попытку позднее... Мы уже работаем над этим.", Toast.LENGTH_LONG).show();
        }
    }

    private void tryLogin(View view) {
        EditText user = (EditText)getActivity().findViewById(R.id.username);
        EditText pass = (EditText)getActivity().findViewById(R.id.password);
        String username = user.getText().toString();
        String password = pass.getText().toString();

        if(!username.isEmpty() && !password.isEmpty()) {
            CheckLoginTask loginTask = new CheckLoginTask();
            loginTask.execute(username, password);
        }
    }

    private void tryExport(View view) {
        wiFiDetails = getWiFiDetails();
        if (!dataAvailable(wiFiDetails)) {
            Toast.makeText(view.getContext(), R.string.no_data, Toast.LENGTH_LONG).show();
            return;
        }
        ExportDataTask exportDataTask = new ExportDataTask();
        exportDataTask.execute("s");
    }

    private class LoginButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
                tryLogin(view);
            try {
                Toast.makeText(view.getContext(), "Авторизация", Toast.LENGTH_LONG).show();
            } catch (ActivityNotFoundException e) {
                Toast.makeText(view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ExportButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
                tryExport(view);
            try {
                Toast.makeText(view.getContext(), "Экспорт данных", Toast.LENGTH_LONG).show();
            } catch (ActivityNotFoundException e) {
                Toast.makeText(view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public class CheckLoginTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("login", params[0]);
                jsonObject.accumulate("password", params[1]);
            } catch (JSONException e) {
                Log.d("JSONException", e.getLocalizedMessage());
                return false;
            }

            boolean success = sendPost(LOGIN_AUTH_URL, jsonObject);
            return success;
        }

        private boolean sendPost(String url, JSONObject parameters) {
            boolean requestResult;
            InputStream inputStream;
            String result;
            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                String json;

                json = parameters.toString();

                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);

                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                HttpResponse httpResponse = httpclient.execute(httpPost);

                inputStream = httpResponse.getEntity().getContent();

                if (inputStream != null) {
                    String accessToken;
                    result = convert(inputStream, StandardCharsets.UTF_8);
                    JSONObject jsonObject = new JSONObject(result);
                    try {
                        accessToken = jsonObject.getString("accessToken");
                        MainContext mainContext = MainContext.INSTANCE;
                        Settings settings = mainContext.getSettings();
                        settings.saveAccessToken(accessToken);
                        requestResult = true;
                    }catch (JSONException e){
                        result = e.getLocalizedMessage();
                        requestResult = false;
                    }
                } else {
                    result = "Did not work!";
                    requestResult = false;
                }
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
                requestResult = false;
            }
            return requestResult;
        }

        private String convert(InputStream inputStream, Charset charset) throws IOException {

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }

            return stringBuilder.toString();
        }

            @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            login(result);
        }
    }

    public class ExportDataTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            MainContext mainContext = MainContext.INSTANCE;
            String deviceName = getDeviceName();
            String androidVersion = getAndroidVersion();
            Context context = mainContext.getMainActivity().getApplicationContext();
            WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInf = wifiMan.getConnectionInfo();
            int ipAddress = wifiInf.getIpAddress();
            String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));
            String macAddress = wifiInf.getMacAddress();
            userDevice = new UserDevice();
            userDevice.setModelName(deviceName);
            userDevice.setOsVersion(androidVersion);
            userDevice.setIpAddress(ip);
            userDevice.setMacAddress(macAddress);
            for (WiFiDetail wiFiDetail : wiFiDetails) {
                UserWiFiData userWiFiData = new UserWiFiData();
                userWiFiData.setBssid(wiFiDetail.getBSSID());
                userWiFiData.setSsid(wiFiDetail.getSSID());
                userWiFiData.setChannel(wiFiDetail.getSSID());
                userWiFiData.setChannel(wiFiDetail.getWiFiSignal().getChannelDisplay());
                userWiFiData.setSecurity(wiFiDetail.getCapabilities());
                int level = wiFiDetail.getWiFiSignal().getLevel();
                userWiFiData.setRssi(level + "ddBm");
                userWiFiData.setCc("RU");
                userWiFiData.setDistance(wiFiDetail.getWiFiSignal().getDistance());
                userWiFiData.setIs80211mc(wiFiDetail.getWiFiSignal().is80211mc());
                userWiFiData.setPrimaryFrequency(wiFiDetail.getWiFiSignal().getPrimaryFrequency());
                userWiFiData.setCenterFrequency(wiFiDetail.getWiFiSignal().getFrequencyStart());
                userWiFiData.setEndFrequency(wiFiDetail.getWiFiSignal().getFrequencyEnd());
                userDevice.addUserWifiData(userWiFiData);
            }
            Gson gson = new Gson();
            String jsonData = gson.toJson(userDevice, UserDevice.class);
            boolean success = sendPost(DATA_EXPORT_URL, jsonData);
            return success;
        }

        private String getDeviceName() {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
                return capitalize(model);
            } else {
                return capitalize(manufacturer) + " " + model;
            }
        }


        private String capitalize(String s) {
            if (s == null || s.length() == 0) {
                return "";
            }
            char first = s.charAt(0);
            if (Character.isUpperCase(first)) {
                return s;
            } else {
                return Character.toUpperCase(first) + s.substring(1);
            }
        }

        private String getAndroidVersion() {
            String release = Build.VERSION.RELEASE;
            int sdkVersion = Build.VERSION.SDK_INT;
            return "Android SDK: " + sdkVersion + " (" + release +")";
        }

        private boolean sendPost(String url, String jsonData) {
            MainContext mainContext = MainContext.INSTANCE;
            Settings settings = mainContext.getSettings();
            String accessToken = settings.retreiveAccessToken();
            String authHeader = "Bearer " + accessToken;
            boolean requestResult;
            InputStream inputStream;
            String result;
            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                StringEntity se = new StringEntity(jsonData);
                httpPost.setEntity(se);

                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Authorization", authHeader);

                HttpResponse httpResponse = httpclient.execute(httpPost);

                inputStream = httpResponse.getEntity().getContent();

                if (inputStream != null) {
                    boolean response;
                    result = convert(inputStream, StandardCharsets.UTF_8);
                    JSONObject jsonObject = new JSONObject(result);
                    try {
                        response = jsonObject.getBoolean("success");
                        requestResult = response;
                    }catch (JSONException e){
                        result = e.getLocalizedMessage();
                        requestResult = false;
                        settings = mainContext.getSettings();
                        settings.saveAccessToken("accessToken");
                    }
                } else {
                    settings = mainContext.getSettings();
                    settings.saveAccessToken("accessToken");
                    result = "Did not work!";
                    requestResult = false;
                }
            } catch (Exception e) {
                settings = mainContext.getSettings();
                settings.saveAccessToken("accessToken");
                Log.d("InputStream", e.getLocalizedMessage());
                requestResult = false;
            }
            return requestResult;
        }

        private String convert(InputStream inputStream, Charset charset) throws IOException {

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            export(result);
        }
    }


    @NonNull
    private List<WiFiDetail> getWiFiDetails() {
        return MainContext.INSTANCE.getScannerService().getWiFiData().getWiFiDetails();
    }

    private boolean dataAvailable(@NonNull List<WiFiDetail> wiFiDetails) {
        return !wiFiDetails.isEmpty();
    }

}
