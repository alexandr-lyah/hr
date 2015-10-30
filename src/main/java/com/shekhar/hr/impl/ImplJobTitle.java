package com.shekhar.hr.impl;

import com.shekhar.hr.ingest.jobtitle.LinkedinJobTitle;
import com.shekhar.hr.ingest.location.LinkedinJobLocation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sagraw200
 */
public class ImplJobTitle {
    
     public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1); list.add(2); list.add(3); list.add(4);
        
        LinkedinJobTitle xyz = new LinkedinJobTitle();
        xyz.getLinkedinJobTitle(list);
        
    }
    
}
