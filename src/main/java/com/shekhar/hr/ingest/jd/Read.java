/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shekhar.hr.ingest.jd;

import com.comcast.ebi.common.fileutils.UtilitiesFile;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author shekhar
 */
public class Read {
    
    private static String rareDelim = "\u2764\u0191\u01DC\u0108\u0137\u2764";
    
    public static void main(String[] args) {
        String ifile = "/home/shekhar/Documents/dev/team-personal/hr/src/main/resources/data/ingest/linkedin.jd.tmp";
        UtilitiesFile uf = new UtilitiesFile();
        String text = uf.readFileInString(ifile);
        
        text = text.replaceAll("<html>", "").replaceAll("<head>", "").replaceAll("</head>", "");
        text = text.replaceAll("</html>", "").replaceAll("<body>", "").replaceAll("</body>", "");
        text = text.replaceAll("<br />", "").replaceAll("<br/>", "").replaceAll("<br>", "");
        text = text.replaceAll("<ul>", "").replaceAll("</ul>", "");
        text = text.replaceAll("<li>", "").replaceAll("</li>", "");
        text = text.replaceAll("<strong>", "").replaceAll("</strong>", "");
        text = text.replaceAll("<p>", "").replaceAll("</p>", "");
        text = text.replaceAll(rareDelim, "");
        text = text.replaceAll("&nbsp;", "");
        text = text.replaceAll("URL:(.*?)\n", "").replaceAll("\n", " ");
        
        String[] parts = text.split(rareDelim);
        System.out.println(parts.length);
        
//        StringBuilder builder = new StringBuilder();
//        for (String p : parts) {
//            p = p.replaceAll("\n", " ");
//            builder.append(p).append("\n");
//        }
        String ofile = "/home/shekhar/Documents/dev/team-personal/hr/src/main/resources/data/ingest/linkedin.jd.tmp.clean";
        uf.writeToFile(ofile, text);
        
    }
    
}
