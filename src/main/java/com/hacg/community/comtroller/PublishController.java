package com.hacg.community.comtroller;

import com.hacg.community.mapper.QuestionMapper;
import com.hacg.community.model.Question;
import com.hacg.community.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @Param("title") String title,
            @Param("description") String description,
            @Param("tag") String tag,
            HttpServletRequest request,
            Model model
    ) {
        Question question = new Question();
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getId() != null) question.setCreator(user.getId());
        else {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        //有效之后才进行操作
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());

        questionMapper.insertQuestion(question);

        return "redirect:/";
    }
}
