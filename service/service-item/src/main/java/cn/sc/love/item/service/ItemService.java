package cn.sc.love.item.service;

import java.util.HashMap;

/**
 * @author YPT
 * @create 2023-05-17-13:52
 */

public interface ItemService {

    HashMap<String, Object> getItem(Long skuId);
}
