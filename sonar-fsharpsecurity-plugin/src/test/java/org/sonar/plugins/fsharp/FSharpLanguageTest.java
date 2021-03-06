/*
 * Sonar FSharpSecurity Plugin, open source software quality management tool.
 *
 * Sonar FSharpSecurity Plugin is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar FSharpSecurity Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */

package org.sonar.plugins.fsharp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.sonar.api.config.Configuration;
import org.sonar.api.config.internal.MapSettings;

/*
Check that the FSharp Language class overrides equals/hash etc
*/


public class FSharpLanguageTest {
    @Test
    public void baseClassConstructorCall() {
        Configuration configuration = (new MapSettings()).asConfig();
        FSharpLanguage fsharp = new FSharpLanguage(configuration);

        assertEquals(FSharpPlugin.LANGUAGE_KEY, fsharp.getKey(), "Language Key");
        assertEquals(FSharpPlugin.LANGUAGE_NAME, fsharp.getName(), "Language Name");
    }

    @Test
    public void hashcode_equals_true() {
        Configuration configuration = (new MapSettings()).asConfig();
        FSharpLanguage fsharp1 = new FSharpLanguage(configuration);
        FSharpLanguage fsharp2 = new FSharpLanguage(configuration);

        boolean areEqual = fsharp1.equals(fsharp2);

        assertTrue(areEqual);
        assertEquals(fsharp1.hashCode(), fsharp2.hashCode());
    }

    @Test
    public void hashcode_equals_false() {
        Configuration configuration1 = (new MapSettings()).asConfig();
        Configuration configuration2 = (new MapSettings()).asConfig();
        FSharpLanguage fsharp1 = new FSharpLanguage(configuration1);
        FSharpLanguage fsharp2 = new FSharpLanguage(configuration2);

        boolean areEqual = fsharp1.equals(fsharp2);

        assertFalse(areEqual);
        assertNotEquals(fsharp1.hashCode(), fsharp2.hashCode());
    }

    @Test
    public void fileSuffixes_default() {
        Configuration configuration = (new MapSettings()).asConfig();
        FSharpLanguage fsharp = new FSharpLanguage(configuration);

        String[] suffixes = fsharp.getFileSuffixes();

        assertEquals(3, suffixes.length);
        for (String suffix : suffixes) {
            assertTrue(FSharpPlugin.FILE_SUFFIXES_DEFVALUE.contains(suffix), "`" + suffix + "` not found");
        }
    }

    @Test
    public void fileSuffixes_userDefined() {
        String keys = ".fs,.fsx";
        int no_keys = 1 + StringUtils.countMatches(keys, ",");
        MapSettings settings = new MapSettings();
        settings.setProperty(FSharpPlugin.FILE_SUFFIXES_KEY, keys);
        Configuration configuration = settings.asConfig();
        FSharpLanguage fsharp = new FSharpLanguage(configuration);

        String[] suffixes = fsharp.getFileSuffixes();

        assertEquals(no_keys, suffixes.length);
        for (String suffix : suffixes) {
            assertTrue(keys.contains(suffix), "`" + suffix + "` not found");
        }
    }
}
