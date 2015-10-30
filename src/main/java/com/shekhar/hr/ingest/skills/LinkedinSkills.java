package com.shekhar.hr.ingest.skills;

import com.comcast.ebi.common.fileutils.UtilitiesFile;
import java.io.File;
import java.util.TreeSet;
import com.comcast.ebi.common.urlutils.UtilitiesURL;
import com.shekhar.hr.utils.General;
import java.util.List;

/**
 *
 * @author sagraw200
 */
public class LinkedinSkills {
    
    private static final String baseurl = "https://www.linkedin.com/ta/skill?query=";
    private static final String outputFile = "src/main/resources/data/ingest/linkedin.skill.tmp";
    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    
    private static UtilitiesURL uUrl;
    private static UtilitiesFile uFile;
    
    public LinkedinSkills() {
        uUrl = new UtilitiesURL();
        uFile = new UtilitiesFile();
    }
    
    /**
     *
     * @param nums
     */
    public void getLinkedinSkills(List<Integer> nums) {
        
        try {
            File f = new File(outputFile);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
        } catch (Exception ex) {
            System.out.println("Error in deleting/creating file: " + ex);
        }
        
        TreeSet<String> set = new TreeSet<>();
        TreeSet<String> tmp = new TreeSet<>();
        
        if (nums.contains(1)) {
            tmp = General.getOneChar(alphabet, baseurl, uUrl);
            set.addAll(tmp); tmp.clear();
        }
        uFile.appendToFile(outputFile, General.setToBuilder(set)); set.clear();
        
        if (nums.contains(2)) {
            tmp = General.getTwoChar(alphabet, baseurl, uUrl);
            set.addAll(tmp); tmp.clear();
        }
        uFile.appendToFile(outputFile, General.setToBuilder(set)); set.clear();
        
        if (nums.contains(3)) {
            tmp = General.getThreeChar(alphabet, baseurl, uUrl);
            set.addAll(tmp); tmp.clear();
        }
        uFile.appendToFile(outputFile, General.setToBuilder(set)); set.clear();
        
        if (nums.contains(4)) {
            tmp = General.getFourChar(alphabet, baseurl, uUrl);
            set.addAll(tmp); tmp.clear();
        }
        uFile.appendToFile(outputFile, General.setToBuilder(set)); set.clear();
        
    }
    
    
}
