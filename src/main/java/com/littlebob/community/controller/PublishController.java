package com.littlebob.community.controller;

import com.littlebob.community.service.PublishService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private PublishService publishService;

    @GetMapping("/publish")
    public String publish(HttpServletRequest request, Model model) {
        if (publishService.hasLogined(request) == false) {
            model.addAttribute("error", "未登录，请先登录");
        }
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(name = "title") String title,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "tag") String tag,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
        }else if (StringUtils.isBlank(description)) {
            model.addAttribute("error", "描述不能为空");
        }else if (StringUtils.isBlank(tag)) {
            model.addAttribute("error", "标签不能为空");
        }else {
            int status = publishService.publish(title, description, tag, request);
            if (status == 1) {
                return "redirect:/";
            }
            model.addAttribute("error", "未登录，请先登录！");
        }
        return "publish";
    }
}
