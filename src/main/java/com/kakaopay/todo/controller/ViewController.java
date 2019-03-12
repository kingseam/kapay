package com.kakaopay.todo.controller;

import com.kakaopay.todo.dto.RequestTodoDto;
import com.kakaopay.todo.dto.ResponseTodoDto;
import com.kakaopay.todo.exception.ValidCustomException;
import com.kakaopay.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
