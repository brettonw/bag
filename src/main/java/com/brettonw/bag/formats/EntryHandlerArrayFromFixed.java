package com.brettonw.bag.formats;

import com.brettonw.bag.BagArray;

public class EntryHandlerArrayFromFixed extends EntryHandlerArray {
    private int[] widths;
    private int totalWidth;
    private String ignore;

    public EntryHandlerArrayFromFixed (int[] widths, EntryHandler entryHandler) {
        super (entryHandler);
        this.widths = widths;
        totalWidth = 0;
        for (int width : widths) {
            totalWidth += width;
        }
    }

    public EntryHandlerArrayFromFixed ignore (String ignore) {
        this.ignore = ignore;
        return this;
    }

    /**
     *
     * @param first The relative offset of the beginning of the line compared to the
     *              offsets in 'positions'. I find it easier to use my text editor to
     *              identify positions, and it counts the first column as '1', so I can
     *              set 'first' = 1, and then use the column number of the rest of the
     *              fields as reported by my text editor.
     * @param positions An array of numbers indicating relative offsets between fields.
     * @return
     */
    public static int[] widthsFromPositions (int first, int... positions) {
        int[] widths = new int[positions.length];
        for (int i = 0; i < positions.length; ++i) {
            widths[i] = positions[i] - first;
            first = positions[i];
        }
        return widths;
    }

    @Override
    protected BagArray strategy (String input) {
        // skip any kind of comment line
        if ((ignore != null) && input.startsWith (ignore)) {
            return null;
        }

        // pad the input with spaces to match the expected width
        input = String.format ("%1$-" + totalWidth + "s", input);

        // split the input up into all the little substrings...
        BagArray bagArray = new BagArray (widths.length);
        int start = 0;
        for (int i = 0; i < widths.length; ++i) {
            int end = start + widths[i];
            bagArray.add (input.substring (start, end).trim ());
            start = end;
        }
        return bagArray;
    }
}
