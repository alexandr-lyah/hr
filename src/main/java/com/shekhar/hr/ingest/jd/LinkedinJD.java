package com.shekhar.hr.ingest.jd;

import com.comcast.ebi.common.fileutils.UtilitiesFile;
import com.comcast.ebi.common.urlutils.UtilitiesURL;
import com.shekhar.hr.utils.General;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sagraw200
 */
public class LinkedinJD {

    private static final String basejoburl = "https://www.linkedin.com/vsearch/j?orig=JSHP&keywords=Software+Engineer&distance=50&locationType=I&countryCode=us&trk=two_box_geo_fill";
    private static final String baseurl = "https://www.linkedin.com/jobs2/view/";
    private static int [] startJobIds = {95387955, 75038247, 78999695, 91083808, 92380009};
    private static String rareDelim = "\u2764\u0191\u01DC\u0108\u0137\u2764";
    private static UtilitiesURL uUrl;
    private static UtilitiesFile uFile;

    public LinkedinJD() {
        uUrl = new UtilitiesURL();
        uFile = new UtilitiesFile();
    }

    public void getLinkedinJD() {
        String res = uUrl.readStringFromUrl(basejoburl);
        pr(res);
    }

    public static void main (String [] args) {
        LinkedinJD jd = new LinkedinJD();
//        jd.getLinkedinJD();
//        jd.getJobIds();

        // read for each start job ids and then try for +1 -1 and save for valid html
        jd.parseLinkedinJdPage(baseurl);

    }

    private void parseLinkedinJdPage(String page) {
        String output = "/Users/sagraw200/Documents/dev/team-personal/hr/src/main/resources/data/ingest/full_jd_html/output_sample.txt";
        StringBuilder builder = new StringBuilder();
        /**
         *
         // this is the sequence to read
         html
         body
         div#application-body
         div.job-desc
         div.main
         div.description-module.container
         div.content
         div.description-section
         div.rich-text

         */

        int jobId = 0;
        for (int i = 0 ; i < 10; ++i) {
            jobId = startJobIds[0] + i;
            String url = page + jobId;

            String content = null, html = null;
            try {
                General.pr(url);
                content = uUrl.readStringFromUrl(url);
//          content = uFile.readFileInString("/Users/sagraw200/Documents/dev/team-personal/hr/src/main/resources/data/ingest/full_jd_html/inner1.html");
            } catch (Exception ex) {
                General.pr("Error in getting html for page: " + page + "\n" + ex);
            }

            if (content != null) {
                try {
                    Document doc = Jsoup.parse(content, "UTF-8");
                    if (doc != null) {
                        Element elem = doc.select("div.rich-text").first();
                        if (elem != null) {
                            html = elem.html();
                            if (html != null) {
                                builder.append(html).append("\n\n");
                                builder.append(rareDelim).append("\n\n");
                            }
                        }
                    }
                } catch (Exception e1x) {
                    General.pr("Error in parsing html for page: " + page + "\n" + e1x);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e2x) {
                General.pr("error in sleeping: " + e2x);
            }

        }

        uFile.writeToFile(output, builder.toString());

    }

    private void getJobIds() {
        HashSet<String> set = new HashSet<>();

        String folder = "/Users/sagraw200/Documents/dev/team-personal/hr/src/main/resources/data/ingest/full_jd_html";
        File f = new File(folder);
        if (f.exists()) {
            File[] files = f.listFiles();
            for (File file : files) {
                String text = uFile.readFileInString(file.getAbsolutePath());
                String regex = "(\"id\":).*?(,)";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(text);
                while (m.find()) {
                    String ss = m.group().replaceAll("\"","").replaceAll(":", "").replaceAll("," , "").replaceAll("id","").trim();
                    set.add(ss);
                }
            }
        }

        General.pr(set.size());
        for (String s : set) {
            General.pr(s + ", ");
        }

    }

    private void pr(Object o) {
        System.out.println(o);
    }

}
