package com.shekhar.hr.impl;

import com.shekhar.hr.ingest.skills.IndeedSkills;
import com.shekhar.hr.ingest.skills.LinkedinSkills;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sagraw200
 */
public class ImplGetIndeedSkills {
    
    public static void main(String[] args) {
        
        List<Integer> list = new ArrayList<>();
        list.add(1); list.add(2); list.add(3); list.add(4);
        
        IndeedSkills getSkills = new IndeedSkills();
        getSkills.getIndeedSkills(list);
        
    }
    
}
