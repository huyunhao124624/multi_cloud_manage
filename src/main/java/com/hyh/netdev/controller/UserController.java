package com.hyh.netdev.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyh.netdev.bo.*;
import com.hyh.netdev.dto.AddAccountDto;
import com.hyh.netdev.dto.DeleteAccountDto;
import com.hyh.netdev.dto.GetAccountListDto;
import com.hyh.netdev.dto.UpdateAccountDto;
import com.hyh.netdev.entity.UserRole;
import com.hyh.netdev.service.UserRoleService;
import com.hyh.netdev.service.UserService;
import com.hyh.netdev.vo.MPage;
import com.hyh.netdev.vo.PageLimit;
import com.hyh.netdev.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author hyh
 */
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",allowCredentials = "true")
public class UserController {
    private UserService userService;
    private UserRoleService userRoleService;





    @PreAuthorize("hasRole('ROLE_UPDATE_SELF_INFORMATION')")
    @PostMapping("/api/user/password/self")
    public Result updatePasswordSelf(@RequestBody PasswordUpdateBo passwordUpdateBo, UsernamePasswordAuthenticationToken token) {
        return userRoleService.updatePasswordSelf(Integer.parseInt(token.getName()), passwordUpdateBo);
    }

    @PostMapping("/api/user/password/admin")
    public Result updatePasswordAdmin(@RequestBody UserRole newData, UsernamePasswordAuthenticationToken token) {
        // 6为预设值后期配合安全框架
        return userRoleService.updatePasswordAdmin(Integer.parseInt(token.getName()), newData);
    }


    @PostMapping("/api/user/login")
    public Result<UserLoginResultBo> login(@RequestBody UserLoginBo userLoginBo) {
        return userService.login(userLoginBo);
    }

    @GetMapping("/api/user/getMenuUserInfo")
    public Result<MenuUserInfoBo> getMenuUserInfo(UsernamePasswordAuthenticationToken token){
        return userService.getMenuUserInfo(Integer.parseInt(token.getName()));
    }




    @PreAuthorize("hasRole('ROLE_IMAGE_UPLOAD')")
    @PostMapping("/api/img/upload")
    @CrossOrigin(origins = "*",allowCredentials = "true")
    public Result<ImgUploadBo> imgUpload(@RequestPart("file") MultipartFile file) {
        return userService.imgUpload(file);
    }


    @PostMapping("/api/user/register")
    public Result<RegisterValidBo> register(@RequestBody RegisterBo registerBo) throws Exception {
        return userService.register(registerBo);
    }

    @PostMapping("/api/ignore/activateAccount")
    public Result<ActivateAccountBo> activateAccount(@RequestParam("token") String token){
        return userService.activateAccount(token);
    }

    @PostMapping("/api/user/getAccountList")
    public Result<MPage<GetAccountListBo>> getAccountList(@RequestBody GetAccountListDto requestDto){
        PageLimit pageLimit  = new PageLimit(requestDto.getPage(),requestDto.getLimit());
        return userService.getAccountList(pageLimit);
    }

    @PostMapping("/api/user/updateAccount")
    public Result updateAccount(@RequestBody UpdateAccountDto requestDto){
        return userService.updateAccount(requestDto);
    }

    @PostMapping("/api/user/addAccount")
    public Result addAccount(@RequestBody AddAccountDto requestDto){
        return userService.addAccount(requestDto);
    }

    @PostMapping("/api/user/deleteAccountById")
    public Result deleteAccount(@RequestBody DeleteAccountDto requestDto){
        return userService.deleteAccountById(requestDto);
    }






}
