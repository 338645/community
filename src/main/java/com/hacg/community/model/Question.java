package com.hacg.community.model;

import com.hacg.community.groups.publish.QuestionDefault;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 需要引入hibernate-validator
 */
public class Question {
    private Integer id;
    @NotBlank(message = "问题标题不能为空", groups = {QuestionDefault.class})
    private String title;
    @NotBlank(message = "问题描述不能为空", groups = {QuestionDefault.class})
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private Integer creator;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    @NotBlank(message = "标签不能为空", groups = {QuestionDefault.class})
    @Pattern(regexp = "^(\\w*,)*$", message = "标签的格式必须为:xxx,xxx,...s", groups = {QuestionDefault.class})
    private String tag;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGmt_create(Long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public void setGmt_modified(Long gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public void setView_count(Integer view_count) {
        this.view_count = view_count;
    }

    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getGmt_create() {
        return gmt_create;
    }

    public Long getGmt_modified() {
        return gmt_modified;
    }

    public Integer getCreator() {
        return creator;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public Integer getView_count() {
        return view_count;
    }

    public Integer getLike_count() {
        return like_count;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + '\'' + ", gmt_create=" + gmt_create + ", gmt_modified=" + gmt_modified + ", creator=" + creator + ", comment_count=" + comment_count + ", view_count=" + view_count + ", like_count=" + like_count + ", tag='" + tag + '\'' + '}';
    }
}
