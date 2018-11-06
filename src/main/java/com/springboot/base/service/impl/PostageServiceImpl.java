package com.springboot.base.service.impl;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.PostageInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.PostageVO;
import com.springboot.base.mapper.PostageMapper;
import com.springboot.base.service.PostageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;

/**
 * 描述：
 * Created by jay on 2017-11-24.
 */
@Service
@Slf4j
public class PostageServiceImpl implements PostageService {

    @Inject
    private PostageMapper postageMapper;

    @Override
    public Page listPage(int limit, int offset, String searchStr, String orderBy, boolean desc) {
        if (!"-1".equals(searchStr)) {
            searchStr = "%" + searchStr + "%";
        }
        String descStr = "";
        if (!StringUtils.isEmpty(orderBy) && desc) {
            descStr = "desc";
        }
        Page page = new Page();
        Long count = postageMapper.count(searchStr);
        if (count != 0) {
            page.setCount(count);
            page.setList(postageMapper.listPage(limit, offset, searchStr, orderBy, descStr));
        }
        return page;
    }

    @Override
    public PostageInfo save(PostageInfo postageInfo) throws Exception {
        int count = postageMapper.save(postageInfo);
        if (count > 0) {
            return postageInfo;
        }
        throw new PrivateException(ErrorInfo.SAVE_ERROR);
    }

    @Override
    public PostageVO getDetail(Long postageId) {
        return postageMapper.getDetailById(postageId);
    }

    @Override
    public PostageInfo update(PostageInfo postageInfo) throws Exception  {
        int count = postageMapper.update(postageInfo);
        if (count > 0) {
            return postageInfo;
        }
        throw new PrivateException(ErrorInfo.UPDATE_ERROR);
    }

    @Override
    public void delete(Long[] postageIds) throws Exception {
        int i = postageMapper.remove(postageIds);
        if (i <= 0) {
            throw new PrivateException(ErrorInfo.DELETE_ERROR);
        }
    }

}
