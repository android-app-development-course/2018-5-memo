package com.example.x.memo.Base;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.xw.repo.BubbleSeekBar;

public class display_text implements BubbleSeekBar.CustomSectionTextArray {

    @NonNull
    @Override
    public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
        array.clear();

        array.put(0, "特小");
        array.put(1, "小");
        array.put(2, "标准");
        array.put(3, "大");
        array.put(4, "特大");
        return array;
    }
}
