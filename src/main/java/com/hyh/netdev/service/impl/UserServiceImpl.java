package com.hyh.netdev.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyh.netdev.bo.*;
import com.hyh.netdev.constant.ResultConstant;
import com.hyh.netdev.dao.*;
import com.hyh.netdev.dto.AddAccountDto;
import com.hyh.netdev.dto.DeleteAccountDto;
import com.hyh.netdev.dto.UpdateAccountDto;
import com.hyh.netdev.entity.*;
import com.hyh.netdev.enums.RoleEnum;
import com.hyh.netdev.security.util.RedisUtil;
import com.hyh.netdev.service.SecurityService;
import com.hyh.netdev.service.UserEmailService;
import com.hyh.netdev.service.UserService;
import com.hyh.netdev.vo.MPage;
import com.hyh.netdev.vo.PageLimit;
import com.hyh.netdev.vo.Result;
import com.hyh.netdev.vo.UserInformation;
import io.netty.util.internal.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author hyh
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final static String TIME_STAT_PREFIX = "UserStatTime-";


    @NonNull
    private UserMapper userMapper;
    @NonNull
    private UserEmailMapper userEmailMapper;
    @NonNull
    private UserEmailService userEmailService;
    @NonNull
    private UserRoleMapper userRoleMapper;

    @NonNull
    private RoleMapper roleMapper;

    @NonNull
    private RolePermissionMapper rolePermissionMapper;
    @NonNull
    private PermissionMapper permissionMapper;
    @NonNull
    private SecurityService securityService;
    @NonNull
    private RedisUtil redisUtil;

    @NonNull
    private DepartmentUserMapper departmentUserMapper;

    @NonNull
    private DepartmentMapper departmentMapper;




    @Value("${file.prefixURL}")
    private String prefixUrl;
    @Value("${file.uploadPath}")
    private String uploadPath;

    @Value("${backend.url}")
    private String backendUrl;

    private static Map<Integer,String> roleMap = null;



    @PostConstruct
    public void initMap(){
        roleMap = new HashMap<>();
        List<Role> roleList = roleMapper.selectList(new QueryWrapper<>());
        roleList.forEach((role)->{
            roleMap.put(role.getRoleId(),role.getName());
        });

    }



    @SuppressWarnings("unchecked")
    @Override
    public Result<UserLoginResultBo> login(UserLoginBo userLoginBo) {
        // 1???????????????????????????
        if (Objects.isNull(userLoginBo.getUsername()) || Objects.isNull(userLoginBo.getPassword())) {
            return ResultConstant.ARGS_ERROR;
        }

        // 2???????????????????????????
        User user = getBaseMapper().selectByAccount(userLoginBo.getUsername());
        if (Objects.isNull(user)) {
            return ResultConstant.USERNAME_PASSWORD_ERROR;
        }

        // 2.1????????????????????????
//        UserEmail userEmail = userEmailMapper.selectOne(new QueryWrapper<UserEmail>().eq("user_id",user.getUserId()));
//        if(userEmail.getActivateStatus() == 0){
//            return ResultConstant.USER_NOT_ACTIVATED;
//        }

        // 3???????????????????????????
        UserRole userRole = userRoleMapper.selectById(user.getUserId());
        if (!userRole.getPassword().equals(userLoginBo.getPassword())) {
            return ResultConstant.USERNAME_PASSWORD_ERROR;
        }

        // 4?????????????????????????????????
        List<Permission> permissionList = permissionMapper.selectBatchIds(rolePermissionMapper.selectPermission(userRole.getRoleId()));
        List<String> permission = new LinkedList<>();
        permissionList.forEach(e -> permission.add(e.getName()));



        // 5??????????????????????????? token
        UserInformation userInformation = new UserInformation();
        userInformation.setUserId(user.getUserId());
        userInformation.setRole(userRole.getRoleId());
        userInformation.setPermission(permission);
        String token = securityService.signToken(userInformation);

        // 6???????????????
        UserLoginResultBo userLoginResultBo = new UserLoginResultBo();
        userLoginResultBo.setRole(userRole.getRoleId());
        userLoginResultBo.setToken(token);

        // 7???????????????????????????
        redisUtil.delete(TIME_STAT_PREFIX + user.getUserId());

        return new Result<>(userLoginResultBo);
    }








    @SuppressWarnings("unchecked")
    @Override
    public Result<ImgUploadBo> imgUpload(MultipartFile file) {
        try {
            // ?????????????????????
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(file.getInputStream());
            if (!ImageIO.getImageReaders(imageInputStream).hasNext()) {
                imageInputStream.close();
                return ResultConstant.FILE_ERROR;
            }
            imageInputStream.close();

            // ????????????
            String filename = UUID.randomUUID() + ".jpg";
            Thumbnails
                    .of(file.getInputStream())
                    .scale(1)
                    .outputQuality(0.5f)
                    .outputFormat("jpg")
                    .toFile(uploadPath + filename);
            return new Result<>(new ImgUploadBo(filename, prefixUrl + filename));
        } catch (IOException e) {
            logger.warn("??????????????????", e);
            return ResultConstant.FILE_ERROR;
        }
    }

    @Override
    @Transactional
    public Result register(RegisterBo registerBo) throws Exception {

        User existUser = userMapper.selectOne(new QueryWrapper<User>().eq("account",registerBo.getUsername()));
        if(existUser != null){
            return ResultConstant.ACCOUNT_ALLOCATED;
        }
        UserEmail existEmail = userEmailMapper.selectOne(new QueryWrapper<UserEmail>().eq("email",registerBo.getEmail()));
        if(existEmail != null){
            return ResultConstant.EMAIL_USED;
        }

        User user = new User();
        user.setAccount(registerBo.getUsername());
        userMapper.insert(user);

        UserRole userRole = new UserRole();
        userRole.setRoleId(1);
        userRole.setPassword(registerBo.getPassword());
        userRole.setUserId(user.getUserId());
        userRoleMapper.insert(userRole);

        UserEmail userEmail = new UserEmail();
        userEmail.setEmail(registerBo.getEmail());
        userEmail.setActivateStatus(0);
        userEmail.setUserId(user.getUserId());

        userEmailMapper.insert(userEmail);


        userEmailService.sendRegisterActivateEmail(user.getUserId(),registerBo.getEmail(),backendUrl);

        return ResultConstant.SUCCESS;
    }

    @Override
    @Transactional
    public Result<ActivateAccountBo> activateAccount(String token) {
        String s = redisUtil.get("email-active-" + token);
        Integer userId = Integer.parseInt(s);
        UserEmail userEmail = new UserEmail();
        userEmail.setUserId(userId);
        userEmail.setActivateStatus(1);

        Integer updated = userEmailMapper.update(userEmail,new UpdateWrapper<UserEmail>().eq("user_id",userId));
        User user = userMapper.selectById(userId);
        ActivateAccountBo activateAccountBo = new ActivateAccountBo();
        activateAccountBo.setUserId(userId);
        activateAccountBo.setUserName(user.getAccount());
        return new Result(activateAccountBo);
    }

    @Override
    public Result<MenuUserInfoBo> getMenuUserInfo(Integer userId) {
        User user = userMapper.selectById(userId);
        UserRole userRole = userRoleMapper.selectById(userId);
        MenuUserInfoBo userInfoBo = new MenuUserInfoBo();
        userInfoBo.setAvatar(user.getAvatar());

        userInfoBo.setName(user.getName());
        List<String> roles = new ArrayList<>();
        roles.add(userRole.getRoleId()+"");
        userInfoBo.setRoles(roles);
        return new Result<>(userInfoBo);
    }

    @Override
    public Result<MPage<GetAccountListBo>> getAccountList(PageLimit pageLimit) {
        Page page = new Page<User>(pageLimit.getPage(), pageLimit.getLimit());

        Integer total = userMapper.selectCount(new QueryWrapper<>());

        IPage<User> iPage = userMapper.selectPage(page, new QueryWrapper<>());

        List<User> rawList = iPage.getRecords();

        List<GetAccountListBo> resultList = new ArrayList<>();
        rawList.forEach((usr)->{
            GetAccountListBo tempBo = new GetAccountListBo();
            QueryWrapper<UserRole> userRoleCondition = new QueryWrapper<>();

            UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("user_id", usr.getUserId()));

            DepartmentUser departmentUser = departmentUserMapper.selectOne(new QueryWrapper<DepartmentUser>().eq("user_id",usr.getUserId()));
            Long departmentId = departmentUser.getDepartmentId();
            Department department = departmentMapper.selectById(departmentId);

            if(departmentUser != null && department != null) {
                tempBo.setDepartmentId(departmentId + "");
                tempBo.setDepartmentName(department.getDepartmentName());
            }

            Integer roleId = userRole.getRoleId();
            if(RoleEnum.ADMIN.getCode().equals(roleId)){
                return;
            }

            tempBo.setRoleId(roleId+"");
            tempBo.setRoleName(roleMap.get(roleId));


            tempBo.setAccount(usr.getAccount());
            tempBo.setUserId(usr.getUserId()+"");
            tempBo.setUserName(usr.getName());
            resultList.add(tempBo);
        });

        MPage mPage = new MPage(total,resultList);

        return new Result<>(mPage);
    }

    @Override
    @Transactional
    public Result updateAccount(UpdateAccountDto requestDto) {
        User user = new User();
        Integer userId = requestDto.getUserId();
        user.setAccount(requestDto.getAccount());
        user.setUserId(requestDto.getUserId());
        user.setName(requestDto.getUserName());
        userMapper.updateById(user);

        UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("user_id",userId));
        userRole.setPassword(requestDto.getPassword());
        userRole.setRoleId(requestDto.getRoleId());
        userRoleMapper.update(userRole,new UpdateWrapper<UserRole>().eq("user_id",userId));

        DepartmentUser departmentUser = departmentUserMapper.selectOne(new QueryWrapper<DepartmentUser>().eq("user_id",userId));
        if(departmentUser != null){
            departmentUser.setDepartmentId(requestDto.getDepartmentId());
            departmentUserMapper.update(departmentUser,new UpdateWrapper<DepartmentUser>().eq("user_id",userId));
        }else{
            departmentUser = new DepartmentUser();
            departmentUser.setDepartmentId(requestDto.getDepartmentId());
            departmentUser.setUserId(userId);
            departmentUserMapper.insert(departmentUser);
        }

        return ResultConstant.SUCCESS;
    }

    @Override
    public Result addAccount(AddAccountDto requestDto) {

        List<User> existUserList = userMapper.selectList(new QueryWrapper<User>().eq("account",requestDto.getAccount()));
        if(!CollectionUtils.isEmpty(existUserList)){
            return ResultConstant.ACCOUNT_ALLOCATED;
        }

        User user = new User();
        user.setName(requestDto.getUserName());
        user.setAccount(requestDto.getAccount());
        userMapper.insert(user);
        Integer userId = user.getUserId();

        UserRole userRole = new UserRole();
        userRole.setPassword(requestDto.getPassword());
        userRole.setUserId(userId);
        userRole.setRoleId(requestDto.getRoleId());
        userRoleMapper.insert(userRole);

        DepartmentUser departmentUser = new DepartmentUser();
        departmentUser.setUserId(userId);
        departmentUser.setDepartmentId(requestDto.getDepartmentId());
        departmentUserMapper.insert(departmentUser);

        return ResultConstant.SUCCESS;
    }

    @Override
    @Transactional
    public Result deleteAccountById(DeleteAccountDto deleteAccountDto) {
        Integer userId = deleteAccountDto.getUserId();
        userRoleMapper.delete(new UpdateWrapper<UserRole>().eq("user_id",userId));
        departmentUserMapper.delete(new UpdateWrapper<DepartmentUser>().eq("user_id",userId));
        userMapper.deleteById(userId);
        return ResultConstant.SUCCESS;
    }


}
