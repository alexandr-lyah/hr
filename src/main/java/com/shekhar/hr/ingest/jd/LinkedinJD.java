package com.shekhar.hr.ingest.jd;

import com.comcast.ebi.common.fileutils.UtilitiesFile;
import com.comcast.ebi.common.urlutils.UtilitiesURL;
import com.shekhar.hr.utils.General;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sagraw200
 */
public class LinkedinJD {

    private static final String basejoburl = "https://www.linkedin.com/vsearch/j?orig=JSHP&keywords=Software+Engineer&distance=50&locationType=I&countryCode=us&trk=two_box_geo_fill";
    private static final String baseurl = "https://www.linkedin.com/jobs2/view/";
    private static final String outputFile = "src/main/resources/data/ingest/linkedin.jd.tmp";

    // taken first 50 job ids
    private static int [] startJobIds = {95387955,75038247,78999695,91083808,92380009,81172486,81811613,94754612,72802835,91534278,79661664,77990305,72854155,79693156,92363581,81893033,95114435,95710678,80883004,81831118,76607082,80886995,94457257,77929115,81862863,41673785,70767897,81828624,70767896,86549718,91289011,79699711,92452972,95119105,88258504,95212089,77967300,92945031,93431539,82776892,80826655,92479505,80877847,82776890,94356763,74343608,95264594,79699171,85456982,88844528};
    private static String rareDelim = "\u2764\u0191\u01DC\u0108\u0137\u2764";

    private static UtilitiesURL uUrl;
    private static UtilitiesFile uFile;

    private static TreeSet<String> tmpJobId = new TreeSet<>();

    public LinkedinJD() {
        uUrl = new UtilitiesURL();
        uFile = new UtilitiesFile();
    }

    public static void main (String [] args) {
        LinkedinJD jd = new LinkedinJD();
//        jd.getJobIdsFromJobSearchPage();

        // read for each start job ids and then try for +1 -1 and save for valid html
        jd.parseLinkedinJdPage(baseurl);

    }

    private void parseLinkedinJdPage(String page) {

        // TODO: get position, company, location
        StringBuilder builder = new StringBuilder();
        File f = new File(outputFile);
        try {
            if (!f.exists())
                f.createNewFile();
        } catch (Exception ex) {
            General.pr("Error in creating file.. " + ex);
        }
        /**
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

        int N = 1000;
        int count = 0;
        for (int i = 0 ; i < N; ++i) {

            for (int id : startJobIds) {

                String url1 = page + (id + i);
                String url2 = page + (id - i);

                if (!tmpJobId.contains(url1)) {
                    count++;
                    tmpJobId.add(url1);
                    General.pr(count + ": " + url1);
                    readHtmlContent(url1, builder);
                }

                if (!tmpJobId.contains(url2)) {
                    count++;
                    tmpJobId.add(url2);
                    General.pr(count + ": " + url2);
                    readHtmlContent(url2, builder);
                }

                if (count >= 100) {
                    uFile.appendToFile(outputFile, builder.toString());
                    builder = new StringBuilder();
                    count = 0;
                }

                try {
                    Thread.sleep(100);
                } catch (Exception e2x) {
                    General.pr("error in sleeping: " + e2x);
                }
            }

        }

    }

    private void readHtmlContent(String url, StringBuilder builder) {
        String html = null, content = null;

        try {
            content = uUrl.readStringFromUrl(url);
        } catch (Exception ex) {
            General.pr("Error in getting html for page: " + url + "\n" + ex);
        }

        if (content != null) {
            try {
                Document doc = Jsoup.parse(content, "UTF-8");
                if (doc != null) {
                    Element elem = doc.select("div.rich-text").first();
                    if (elem != null) {
                        html = elem.html();
                        if (html != null) {
                            Document tidy = Jsoup.parseBodyFragment(html);
                            builder.append("URL:").append(url).append("\n");
                            builder.append(tidy.toString()).append("\n\n");
                            builder.append(rareDelim).append("\n\n");
                        }
                    }
                }
            } catch (Exception e1x) {
                General.pr("Error in parsing html for page: " + url + "\n" + e1x);
            }
        }

    }

    private void getJobIdsFromJobSearchPage() {
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
            General.pr(s);
        }

    }

    private void pr(Object o) {
        System.out.println(o);
    }

}
