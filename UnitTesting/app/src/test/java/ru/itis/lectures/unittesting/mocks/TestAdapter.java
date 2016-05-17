package ru.itis.lectures.unittesting.mocks;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import ru.itis.lectures.unittesting.mockito.BaseAdapter;

/**
 * @author Artur Vasilov
 */
public class TestAdapter extends BaseAdapter<TestHolder, Integer> {

    public TestAdapter(@NonNull List<Integer> items) {
        super(items);
    }

    @Override
    public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestHolder(parent);
    }

    @Override
    public void onBindViewHolder(TestHolder holder, int position) {
    }
}
