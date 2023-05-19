package cn.sc.love.item.service.impl;

import cn.sc.love.item.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YPT
 * @create 2023-05-17-13:53
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public HashMap<String, Object> getItem(Long skuId) {
        Map<String, Object> resultMap = new HashMap<>();
        return null;
    }
}
