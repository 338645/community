package com.hacg.community.service;

import com.hacg.community.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    public List<String> getTags() {
        List<String> tags = tagMapper.selectTags();
        return tags;
    }
}
