package com.hacg.community.model;

import com.hacg.community.groups.publish.QuestionDefault;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 需要引入hibernate-validator
 */
@Data
@Builder
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
    @Pattern(regexp = "^([\\u4e00-\\u9fa5 \\w]*,)*[\\u4e00-\\u9fa5 \\w]+$", message = "标签的格式必须为:xxx,xxx,...s", groups = {QuestionDefault.class})
    private String tag;

}
