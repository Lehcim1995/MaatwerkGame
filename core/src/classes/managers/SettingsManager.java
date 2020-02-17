package classes.managers;

import org.ini4j.Ini;

import java.util.Dictionary;
import java.util.List;

public class SettingsManager
{
    static Dictionary<String, Integer> Keymap;

    static List<String> Controlls;

    static {
        // Get file
        Ini ini = new Ini();
    }
}
