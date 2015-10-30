package com.shekhar.hr.ingest.jobtitle;

import com.comcast.ebi.common.urlutils.UtilitiesURL;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.TreeSet;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sagraw200
 */
public class LinkedinJobTitle {
    
    private static final String baseurl = "https://www.linkedin.com/ta/titleV2?query=";
    private static final String outputFile = "src/main/resources/data/ingest/linkedin.jobtitle.tmp";
    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    
    private static UtilitiesURL uUrl;
    
    public LinkedinJobTitle() {
        uUrl = new UtilitiesURL();
    }
    
    /**
     * 
     * @param nums 
     */
    public void getLinkedinJobTitle(List<Integer> nums) {
        TreeSet<String> set = new TreeSet<>();
        TreeSet<String> tmp = new TreeSet<>();
        
        if (nums.contains(1)) {
            tmp = getOneChar();
            set.addAll(tmp); tmp.clear();
        }
        
        if (nums.contains(2)) {
            tmp = getTwoChar();
            set.addAll(tmp); tmp.clear();
        }
        
        if (nums.contains(3)) {
            tmp = getThreeChar();
            set.addAll(tmp); tmp.clear();
        }
        
        if (nums.contains(4)) {
            tmp = getFourChar();
            set.addAll(tmp); tmp.clear();
        }
        
        File f = new File(outputFile);
        if (f.exists()) {
            f.delete();
        }
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
            StringBuilder builder = new StringBuilder();
            for (String s : set) {
                builder.append(s).append("\n");
            }
            bw.write(builder.toString());
            bw.close();
        } catch (Exception ex) {
            System.out.println("Error in writing the output file: " + ex);
        }
        
    }
    
    private TreeSet<String> getOneChar() {
        TreeSet<String> tmp = new TreeSet<>();
        
        for (int i = 0 ; i < alphabet.length; ++i) {
            String uri = baseurl + alphabet[i];
            pr(uri);
            String res = uUrl.readStringFromUrl(uri);
            if (res != null && !res.isEmpty()) {
                try {
                    JSONObject json = new JSONObject(res);
                    if (json.has("resultList")) {
                        JSONArray jArr = json.getJSONArray("resultList");
                        if (jArr != null && jArr.length() > 0) {
                            for (int x = 0 ; x < jArr.length(); ++x) {
                                JSONObject item = jArr.getJSONObject(x);
                                if (item.has("displayName") && item.get("displayName") != null) {
                                    tmp.add(item.getString("displayName").toLowerCase());
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Error in parsing url json: " + ex);
                }
            }
        }
        return tmp;
    }
    
    private TreeSet<String> getTwoChar() {
        TreeSet<String> tmp = new TreeSet<>();
        
        for (int i = 0 ; i < alphabet.length; ++i) {
            for (int j = 0 ; j < alphabet.length; ++j) {
                String uri = baseurl + alphabet[i] + alphabet[j];
                pr(uri);
                String res = uUrl.readStringFromUrl(uri);
                if (res != null && !res.isEmpty()) {
                    try {
                        JSONObject json = new JSONObject(res);
                        if (json.has("resultList")) {
                            JSONArray jArr = json.getJSONArray("resultList");
                            if (jArr != null && jArr.length() > 0) {
                                for (int x = 0 ; x < jArr.length(); ++x) {
                                    JSONObject item = jArr.getJSONObject(x);
                                    if (item.has("displayName") && item.get("displayName") != null) {
                                        tmp.add(item.getString("displayName").toLowerCase());
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println("Error in parsing url json: " + ex);
                    }
                }
            }
        }
        return tmp;
    }
    
    private TreeSet<String> getThreeChar() {
        TreeSet<String> tmp = new TreeSet<>();
        
        for (int i = 0 ; i < alphabet.length; ++i) {
            for (int j = 0 ; j < alphabet.length; ++j) {
                for (int k = 0 ; k < alphabet.length; ++k) {
                    String uri = baseurl + alphabet[i] + alphabet[j] + alphabet[k];
                    pr(uri);
                    String res = uUrl.readStringFromUrl(uri);
                    if (res != null && !res.isEmpty()) {
                        try {
                            JSONObject json = new JSONObject(res);
                            if (json.has("resultList")) {
                                JSONArray jArr = json.getJSONArray("resultList");
                                if (jArr != null && jArr.length() > 0) {
                                    for (int x = 0 ; x < jArr.length(); ++x) {
                                        JSONObject item = jArr.getJSONObject(x);
                                        if (item.has("displayName") && item.get("displayName") != null) {
                                            tmp.add(item.getString("displayName").toLowerCase());
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Error in parsing url json: " + ex);
                        }
                    }
                }
            }
        }
        return tmp;
    }
    
    private TreeSet<String> getFourChar() {
        TreeSet<String> tmp = new TreeSet<>();
        
        for (int i = 0 ; i < alphabet.length; ++i) {
            for (int j = 0 ; j < alphabet.length; ++j) {
                for (int k = 0 ; k < alphabet.length; ++k) {
                    for (int y = 0 ; y < alphabet.length; ++y) {
                        String uri = baseurl + alphabet[i] + alphabet[j] + alphabet[k] + alphabet[y];
                        pr(uri);
                        String res = uUrl.readStringFromUrl(uri);
                        if (res != null && !res.isEmpty()) {
                            try {
                                JSONObject json = new JSONObject(res);
                                if (json.has("resultList")) {
                                    JSONArray jArr = json.getJSONArray("resultList");
                                    if (jArr != null && jArr.length() > 0) {
                                        for (int x = 0 ; x < jArr.length(); ++x) {
                                            JSONObject item = jArr.getJSONObject(x);
                                            if (item.has("displayName") && item.get("displayName") != null) {
                                                tmp.add(item.getString("displayName").toLowerCase());
                                            }
                                        }
                                    }
                                }
                            } catch (Exception ex) {
                                System.out.println("Error in parsing url json: " + ex);
                            }
                        }
                    }
                }
            }
        }
        return tmp;
    }
    
    private void pr(Object o) {
        System.out.println(o);
    }
}
