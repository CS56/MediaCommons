/**
* The MIT License (MIT)
* 
* Copyright (c) 2015 CS56
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
 */

package edu.smc.mediacommons.modules;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PastebinModule {

    private String token;
    private String devkey;
    private String loginURL = "http://www.pastebin.com/api/api_login.php";
    private String pasteURL = "http://www.pastebin.com/api/api_post.php";

    public PastebinModule(String devkey) {
        this.devkey = devkey;
    }

    @Getter
    @AllArgsConstructor
    public enum PastebinModes {
        PUBLIC(0), UNLISTED(1), PRIVATE(2);

        private int type;
    }

    public String checkResponse(String response) {
        if (response.substring(0, 15) == "Bad API request")
            return response.substring(17);
        return "";
    }

    public String login(String username, String password) throws UnsupportedEncodingException {
        String api_user_name = URLEncoder.encode(username, "UTF-8");
        String api_user_password = URLEncoder.encode(password, "UTF-8");
        String data = "api_dev_key=" + this.devkey + "&api_user_name="
                + api_user_name + "&api_user_password=" + api_user_password;

        String response = this.page(this.loginURL, data);

        String check = this.checkResponse(response);
        if (!check.equals(""))
            return "false";
        this.token = response;
        return response;
    }

    public String makePaste(String code, PastebinModes mode, String name, String format) throws UnsupportedEncodingException {
        String content = URLEncoder.encode(code, "UTF-8");
        String title = URLEncoder.encode(name, "UTF-8");
        String data = "api_option=paste&api_user_key=" + this.token + "&api_paste_private=" + mode.getType() + "&api_paste_name="
                + title + "&api_paste_expire_date=N&api_paste_format=" + format + "&api_dev_key=" + this.devkey + "&api_paste_code=" + content;
        String response = this.page(this.pasteURL, data);
        String check = this.checkResponse(response);
        if (!check.equals(""))
            return check;
        return response;
    }

    public String page(String uri, String urlParameters) {
        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = rd.readLine()) != null) {
                response.append(line);
            }

            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}