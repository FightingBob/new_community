package com.littlebob.community.controller;

import com.littlebob.community.dto.QuestionDTO;
import com.littlebob.community.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @Value("${pagination.page_size}")
    private Integer pageSize;

    @GetMapping("/")
    public String index(@RequestParam(name = "currentPage", required = false, defaultValue = "1") Integer currentPage,
                        HttpServletRequest request, Model model) {
        List<QuestionDTO> questionDTOs = indexService.index(request, currentPage, pageSize);
        model.addAttribute("list", questionDTOs);
        return "index";
    }
}
