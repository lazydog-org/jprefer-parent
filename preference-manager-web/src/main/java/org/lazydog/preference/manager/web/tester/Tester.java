package org.lazydog.preference.manager.web.tester;

import java.util.prefs.Preferences;


/**
 *
 * @author R4R
 */
public class Tester {

    public static void main(String[] args) throws Exception {

System.out.println(Preferences.systemRoot().node("org/lazydog/name").get("first", null));
System.out.println(Preferences.systemRoot().node("org/lazydog/name").get("zipcode", null));
    }
}
