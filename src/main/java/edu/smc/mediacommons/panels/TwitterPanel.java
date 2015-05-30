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

package edu.smc.mediacommons.panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jasypt.util.text.BasicTextEncryptor;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import edu.smc.mediacommons.Resources;
import edu.smc.mediacommons.Utils;
import edu.smc.mediacommons.modules.TwitterModule;

public class TwitterPanel extends JPanel {

    private JButton jButton;
    private JLabel message;
    private JPasswordField passwordField;
    private TwitterModule twitterModule;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel main = new JPanel();
        main.setLayout(null);

        main.add(Utils.createButton("", 10, 120, 100, 100, new ImageIcon(Resources.IMAGE_TWITTER_HOME)));
        main.add(Utils.createButton("", 110, 120, 100, 100, new ImageIcon(Resources.IMAGE_TWITTER_PROFILE)));
        main.add(Utils.createButton("", 210, 120, 100, 100, new ImageIcon(Resources.IMAGE_TWITTER_TIMELINE)));
        main.add(Utils.createButton("", 310, 120, 100, 100, new ImageIcon(Resources.IMAGE_TWITTER_TWEET)));

        frame.add(main);
        frame.setVisible(true);
    }

    public TwitterPanel() {
        setLayout(null);

        jButton = Utils.createButton("Login", 210, 100, 100, 20, null);
        add(jButton);

        passwordField = new JPasswordField();
        passwordField.setBounds(110, 100, 100, 20);
        add(passwordField);

        message = Utils.createLabel("Sign-in to Authenticate API calls", 80, 70, 300, 20, Resources.VERDANA_14_BOLD);
        add(message);

        // You can use your own credentials, and choose to encrypt them or not
        
        /*
        String tempEncrypted = null;

        
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("YOUR_PATH");
            InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(streamReader);
            tempEncrypted = reader.readLine();
        } catch (IOException e) {

        }*/

        final String encrypted = null;

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
                    textEncryptor.setPassword(passwordField.getText());
                    String[] decrypted = textEncryptor.decrypt(encrypted).split("\\$");

                    ConfigurationBuilder cb = new ConfigurationBuilder();
                    cb.setDebugEnabled(true).setOAuthConsumerKey(decrypted[0]).setOAuthConsumerSecret(decrypted[1])
                            .setOAuthAccessToken(decrypted[2]).setOAuthAccessTokenSecret(decrypted[3]);

                    TwitterFactory tf = new TwitterFactory(cb.build());
                    twitterModule = new TwitterModule(tf.getInstance());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(getParent(), "The input password was not correct.");
                    return;
                }

                JOptionPane.showMessageDialog(getParent(), "Authentication successful.");
                restructurePanel();
            }
        });
    }

    private void restructurePanel() {
        remove(jButton);
        remove(passwordField);
        remove(message);
        repaint();

        JButton home = Utils.createButton("", 10, 20, 100, 100, new ImageIcon(Resources.IMAGE_TWITTER_HOME));
        JButton profile = Utils.createButton("", 110, 20, 100, 100, new ImageIcon(Resources.IMAGE_TWITTER_PROFILE));
        final JButton timeline = Utils.createButton("", 210, 20, 100, 100, new ImageIcon(Resources.IMAGE_TWITTER_TIMELINE));
        final JButton tweet = Utils.createButton("", 310, 20, 100, 100, new ImageIcon(Resources.IMAGE_TWITTER_TWEET));

        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder builder = new StringBuilder();

                try {
                    ResponseList<Status> status = twitterModule.getTwitter().getHomeTimeline();

                    for (Status home : status) {
                        builder.append("User: " + home.getUser().getName() + "\nPosted At: " + home.getCreatedAt() +
                                "\nLanguage: " + home.getLang() + "\nContent: " + home.getText() + "\nFavorites: "
                                + home.getFavoriteCount() + "\nRetweets: " + home.getRetweetCount() + "\n\n");
                    }

                    if (builder.length() == 0) {
                        JOptionPane.showMessageDialog(getParent(), "There appear to be no tweets on the timeline!");
                    } else {
                        JTextArea jTextArea = new JTextArea();
                        jTextArea.setText(builder.toString());
                        JScrollPane jScrollPane = new JScrollPane(jTextArea);
                        jScrollPane.setSize(new Dimension(600, 400));
                        jScrollPane.setPreferredSize(new Dimension(600, 400));
                        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                        JOptionPane.showMessageDialog(getParent(), jScrollPane, "Timeline", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (TwitterException ex) {
                    ex.printStackTrace();
                }
            }
        });

        profile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder builder = new StringBuilder();

                try {
                    String language = twitterModule.getTwitter().getAccountSettings().getLanguage();
                    String screenName = twitterModule.getTwitter().getAccountSettings().getScreenName();

                    builder.append("Profile Information\nLanguage: " + language + "\nScreen name: " + screenName);
                    JOptionPane.showMessageDialog(getParent(), builder.toString());
                } catch (TwitterException ex) {
                    ex.printStackTrace();
                }
            }
        });

        tweet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = Utils.getTextInput(getParent());

                if(text == null) {
                    return;
                }

                if (text.isEmpty()) {
                    JOptionPane.showMessageDialog(getParent(), "That tweet was blank!", "Couldn't Tweet!", JOptionPane.ERROR_MESSAGE);
                } else {
                    twitterModule.updateStatus(text.length() > 140 ? text.substring(0, 140) : text);
                    JOptionPane.showMessageDialog(getParent(), "Your tweet has been posted!");
                }
            }
        });

        add(home);
        add(profile);
        add(timeline);
        add(tweet);
    }
}