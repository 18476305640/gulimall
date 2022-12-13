package com.zhuangjie.gulimall.search.service.impl;

import com.zhuangjie.gulimall.search.service.MallSearchService;
import com.zhuangjie.gulimall.search.vo.SearchParam;
import com.zhuangjie.gulimall.search.vo.SearchResult;
import org.springframework.stereotype.Service;

/**
 * 商城搜索服务impl
 * @author manzhuangjie
 * @date 2022/11/28
 */
@Service
public class MallSearchServiceImpl implements MallSearchService {
    /**
     * 去ES中检索
     * @param param  检索的所有参数
     * @return
     */
    @Override
    public SearchResult search(SearchParam param) {

        return null;
    }
}
