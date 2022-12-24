package com.smd.health.data;

public interface IStepCountDao {
    void saveStepCount(long count);
    long getStepCount();

    void attachStepCountListener(IStepCountListener listener);
    void removeStepCountListener(IStepCountListener listener);

    public interface IStepCountListener {
        void onStepCountUpdated(long count);
    }
}
