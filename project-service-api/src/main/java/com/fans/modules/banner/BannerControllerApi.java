package com.fans.modules.banner;

import com.fans.modules.banner.mo.FriendLinkMO;
import com.fans.modules.banner.vo.BannerVO;
import com.fans.utils.page.Paging;
import com.fans.vo.JsonData;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * interfaceName: BannerControllerApi
 *
 * @author k
 * @version 1.0
 * @description 轮播图
 * swagger中paramType请求类型注释：
 * (@PathVariable) --> path
 * (@RequestBody) --> body
 * (@RequestPart) --> formData
 * (@RequestHeader) --> header
 * (@RequestParam) --> query，解析方式和无注解时一致
 * @date 2020-10-26 21:04
 **/
@RequestMapping("/banner")
@Api(value = "首页轮播图-api", tags = "首页轮播图-api")
public interface BannerControllerApi {

    @GetMapping(value = "/pageList")
    @ApiOperation(value = "查询录播图列表-带分页-通过List分页-自定义缓存写法", notes = "查询录播图列表-带分页-通过List分页-自定义缓存写法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", defaultValue = "1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面元素个数", defaultValue = "10", paramType = "query", dataType = "int")
    })
    JsonData<Paging<BannerVO>> list(@RequestParam(defaultValue = "1", required = false) Integer page,
                                    @RequestParam(defaultValue = "10", required = false) Integer pageSize);

    @ApiOperation(value = "查询录播图列表-带分页-通过sql分页-注解缓存写法", notes = "查询录播图列表-带分页-注解缓存写法")
    @GetMapping(value = "/sqlList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", defaultValue = "0", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面元素个数", defaultValue = "5", paramType = "query", dataType = "int")
    })
    JsonData<Paging<BannerVO>> getBannerListByPage(@RequestParam(defaultValue = "1", required = false) Integer page,
                                                   @RequestParam(defaultValue = "10", required = false) Integer pageSize);

    @ApiOperation(value = "修改轮播图名称--测试缓存", notes = "修改轮播图名称--测试缓存")
    @PostMapping(value = "/update")
    JsonData<String> updateBannerName(@ApiParam(value = "轮播图名称", name = "bannerName") @RequestParam String bannerName);

    @ApiOperation(value = "测试rabbitmq", notes = "测试rabbitmq")
    @PostMapping(value = "/rabbitmq")
    JsonData<String> rabbitmq();

    @ApiOperation(value = "测试延迟消息rabbitmq", notes = "测试延迟消息rabbitmq")
    @PostMapping(value = "/delay")
    JsonData<String> delay();

    @ApiOperation(value = "mongodb中gridfs测试", notes = "mongodb中gridfs测试")
    @PostMapping(value = "/gridfs")
    JsonData<String> gridfs() throws FileNotFoundException;

    @ApiOperation(value = "mongodb中增删改查", notes = "mongodb中增删改查")
    @PostMapping(value = "/mongodb")
    JsonData<List<FriendLinkMO>> mongodb();

    @ApiOperation(value = "文件下载", notes = "文件下载")
    @GetMapping(value = "/download")
    void download(HttpServletRequest request, HttpServletResponse response);

    @ApiOperation(value = "url文件下载", notes = "url文件下载")
    @GetMapping(value = "/urlDownload")
    void urlDownload(HttpServletRequest request, HttpServletResponse response);

    @GetMapping(value = "/test")
    @ApiOperation(value = "test", notes = "test")
    void test();
}
