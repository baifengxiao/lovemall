package cn.sc.love.product.service;

/**
 * @author YPT
 * @create 2023-05-27-23:03
 */
public interface TestService {
    void testLock();

    String readLock();

    String writeLock();
}
