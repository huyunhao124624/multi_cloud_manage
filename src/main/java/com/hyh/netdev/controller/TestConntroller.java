package com.hyh.netdev.controller;

import com.hyh.netdev.dto.ApplyResourceDto;
import com.hyh.netdev.service.ResourceService;
import com.hyh.netdev.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author hyh
 */
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",allowCredentials = "true")
public class TestConntroller {
    @Autowired
    private ResourceService resourceService;

    @PostMapping("/api/ignore/applyResource")
    public Result applyResource(@RequestBody ApplyResourceDto requestDto, @RequestParam("userId") Integer userId, @RequestParam("departmentId") Long departmentId) throws IOException {
        return resourceService.applyPrivateResource(requestDto,userId,departmentId);

    }
}
