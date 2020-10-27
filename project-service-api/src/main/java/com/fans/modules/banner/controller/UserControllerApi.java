package com.fans.modules.banner.controller;

import com.fans.modules.banner.entity.UserEntity;
import com.fans.utils.page.Paging;
import com.fans.vo.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * interfaceName: UserControllerApi
 *
 * @author k
 * @version 1.0
 * @description 控制层
 * swagger中paramType请求类型注释：
 * (@PathVariable) --> path
 * (@RequestBody) --> body
 * (@RequestPart) --> formData
 * (@RequestHeader) --> header
 * (@RequestParam) --> query，解析方式和无注解时一致
 * @date 2020-10-27 16:05:48
 **/
@RequestMapping("/banner/user")
@Api(value = "UserControllerApi", tags = "UserControllerApi")
public interface UserControllerApi {
    /**
     * 列表
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "查询列表--分页", notes = "查询列表--分页")
    JsonData<Paging<UserEntity>> list(@RequestParam Map<String, Object> params);

    /**
     * 信息
     */
    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息", notes = "查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", required = true, paramType = "path", dataType = "integer")
    })
    JsonData<UserEntity> info(@PathVariable("id") Integer id);

    /**
     * 保存
     */
    @PostMapping(value = "/save")
    @ApiOperation(value = "保存信息", notes = "保存信息")
    JsonData<String> save(@RequestBody UserEntity user);

    /**
     * 修改
     */
    @PostMapping(value = "/update")
    @ApiOperation(value = "更新信息", notes = "更新信息")
    JsonData<String> update(@RequestBody UserEntity user);

    /**
     * 删除
     */
    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除信息", notes = "删除信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "列表", required = true, allowMultiple = true, paramType = "body", dataType = "integer")
    })
    JsonData<String> delete(@RequestBody Integer[] ids);

}
