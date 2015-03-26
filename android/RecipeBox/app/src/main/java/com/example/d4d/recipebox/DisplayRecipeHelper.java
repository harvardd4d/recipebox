package com.example.d4d.recipebox;

import java.util.List;

/**
 * Created by Mark on 3/18/2015.
 */
public class DisplayRecipeHelper {

    // TODO: MAKE THIS (NOT)WIP!
    // NOTE: WORK IN PROGRESS
    // IGNORE THIS FOR NOW!

    // converts a List of Strings to a String of bulleted (String) entries
    public String HTMLbulletify(List<String> input) {

        // primer for unordered HTML list
        String workingstring = "<ul>";
        int listsize = input.size();

        for (int i = 0; i < listsize; i++) {
            workingstring += "<li>" + input.get(i) + "</li>";
        }
        // closer for unordered list
        workingstring += "</ul>";

        // testing output
        System.out.println(workingstring);

        return workingstring;
    }
}
