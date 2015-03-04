package com.careerly.controllers.spider;

import com.careerly.common.support.response.StandardJsonObject;
import com.careerly.controllers.AbstractController;
import com.careerly.controllers.Domain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 实现描述:爬取知乎网站控制器
 *
 * @author shengyahuang
 * @version v1.0.0
 * @see
 * @since 15-3-4 下午1:03
 */
@Controller("zhihuController")
@RequestMapping(Domain.spider_zhihu)
public class ZhihuController extends AbstractController {

    @ResponseBody
    @RequestMapping(value = "/getZhihuUsers")
    public StandardJsonObject getZhihuUsers() throws Exception {
        StandardJsonObject standardJsonObject = StandardJsonObject.newCorrectJsonObject();
        standardJsonObject.putData("AAAAA");
        return standardJsonObject;
    }
}
