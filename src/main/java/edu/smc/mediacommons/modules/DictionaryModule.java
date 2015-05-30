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

import edu.smc.mediacommons.Main;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Getter
public class DictionaryModule {

    private Connection dbConnection;
    private Map<UUID, DictionaryCallback> callbacks = new HashMap<>();

    public DictionaryModule() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        openConnection();
    }

    public void openConnection() {
        try {
            // dbConnection = DriverManager.getConnection("jdbc:sqlite:dictionary.db");
            dbConnection = DriverManager.getConnection("jdbc:sqlite::resource:" + Main.class.getClassLoader().getResource("edu/smc/mediacommons/resources/dictionary.db").toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class DictionaryData {
        private String word;
        private String wordtype;
        private String definition;
    }

    public interface DictionaryCallback {
        void dataReceived(List<DictionaryData> dictionaryData);
    }

    public void queryWord(final String word, final DictionaryCallback dictionaryCallback) {
        final UUID uuid = UUID.randomUUID();
        callbacks.put(uuid, dictionaryCallback);

        new Thread() {
            @Override
            public void run() {
                List<DictionaryData> dictionaryData = new ArrayList<>();

                try {
                    ResultSet set = dbConnection.prepareStatement("SELECT * FROM `entries` WHERE LOWER(`word`)=LOWER('" + word + "')").executeQuery();

                    while (set.next()) {
                        dictionaryData.add(new DictionaryData(set.getString("word"), set.getString("wordtype"), set.getString("definition")));
                    }

                    set.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    DictionaryCallback callback = callbacks.remove(uuid);
                    callback.dataReceived(dictionaryData);
                }
            }
        }.start();
    }
}