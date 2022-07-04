package com.hacg.community.dto;

import com.hacg.community.model.Question;
import com.hacg.community.model.User;
import lombok.Data;

import javax.security.auth.Subject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ReplyDto {
    private Integer id;

    private Integer userId;

    private Integer questId;

    @NotBlank(message = "回复不能为空")
    private String reply;

    private User user;

    private Long gmtCreate;

    private Long gmtModified;

    //默认创建
    private SubPageInfo subPageInfo = new SubPageInfo();

    //subReplys:该回复的子回复,后面可以使用mybatis的懒加载实现
    private List<ReplyDto> SubReplys;

    private Integer parent;

}
