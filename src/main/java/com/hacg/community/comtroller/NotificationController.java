package com.hacg.community.comtroller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hacg.community.dto.NotificationDto;
import com.hacg.community.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getUnreadNotification")
    public int getUnreadNotification(@RequestParam("userId") Integer userId) {
        return notificationService.getUnreadNotification(userId);
    }

    @GetMapping("/getNotifications")
    public List<NotificationDto> getNotifications(@RequestParam("userId") Integer userId, @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize, HttpServletResponse response) {
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<NotificationDto> ret = notificationService.getNotifications(userId);
        long total = page.getTotal();
        Cookie cookie = new Cookie("NotificationTotal", String.valueOf(total));
        cookie.setPath("/");
        response.addCookie(cookie);
        return ret;
    }

    @GetMapping("/updateStatus")
    public void updateStatus(@RequestParam("updateId") Integer updateId) {
        notificationService.updateStatus(updateId);
    }
}
