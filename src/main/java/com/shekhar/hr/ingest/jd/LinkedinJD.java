package com.shekhar.hr.ingest.jd;

import com.comcast.ebi.common.urlutils.UtilitiesURL;

/**
 *
 * @author sagraw200
 */
public class LinkedinJD {
    
    private static final String basejoburl = "https://www.linkedin.com/vsearch/j?orig=JSHP&keywords=Software+Engineer&distance=50&locationType=I&countryCode=us&trk=two_box_geo_fill";
    private static final String baseurl = "https://www.linkedin.com/jobs2/view/93961214";
    private static UtilitiesURL uUrl;
    
    public LinkedinJD() {
        uUrl = new UtilitiesURL();
    }
    
    public void getLinkedinJD() {
        String res = uUrl.readStringFromUrl(baseurl);
        pr(res);
    }
    
    public static void main (String [] args) {
        LinkedinJD jd = new LinkedinJD();
        jd.getLinkedinJD();
        
    }
    
    private void pr(Object o) {
        System.out.println(o);
    }
    
}
