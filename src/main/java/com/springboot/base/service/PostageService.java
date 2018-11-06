package com.springboot.base.service;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.entity.PostageInfo;
import com.springboot.base.data.vo.PostageVO;

/**
 * 描述：
 * Created by jay on 2017-11-24.
 */
public interface PostageService {

    Page listPage(int limit, int offset, String searchStr, String orderBy, boolean desc);

    PostageInfo save(PostageInfo postageInfo) throws Exception;

    PostageVO getDetail(Long postageId);

    PostageInfo update(PostageInfo postageInfo) throws Exception;

    void delete(Long[] postageIds) throws Exception;

}
