package com.cloud.c_talk.im.controller;

import com.cloud.c_talk.im.service.ImService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("im")
public class ImController {

    @Autowired
    private ImService imService;

}
