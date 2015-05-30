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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

import lombok.Getter;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.JSONTokener;

@Getter
public class WeatherModule {
    
    private final JSONObject response;

    public WeatherModule(String search) {
        InputStream is = null;
        JSONObject result = null;
        
        String baseUrl = "http://query.yahooapis.com/v1/public/yql?q=";
        String query = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"" + search + "\")";
        
        try {
            String fullUrlStr = baseUrl + URLEncoder.encode(query, "UTF-8") + "&format=json";
            URL fullUrl = new URL(fullUrlStr);
            is = fullUrl.openStream();

            JSONTokener tok = new JSONTokener(is);
            result = new JSONObject(tok);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        response = result;
    }
}