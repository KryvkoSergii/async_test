package ua.com.smiddle.async_test.core;

/**
 * @author ksa on 16.06.17.
 * @project async_test
 */
public interface SomeUtil {

    void task1();
    void task2();
    void task3();
    Object getState();
    void stop(String id);
}
