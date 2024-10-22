package com.dxjunkyard.spocomi.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dxjunkyard.spocomi.api.client.CommunityRestClient;
import com.dxjunkyard.spocomi.domain.resource.*;
import com.dxjunkyard.spocomi.domain.resource.request.*;
import com.dxjunkyard.spocomi.domain.resource.response.*;
import com.dxjunkyard.spocomi.service.EventService;
import com.dxjunkyard.spocomi.service.TokenService;
import com.dxjunkyard.spocomi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/v1")
public class UIController {
    private Logger logger = LoggerFactory.getLogger(com.dxjunkyard.spocomi.controller.UIController.class);

    @Value("${line-login.client_id}")
    private String client_id;

    @Value("${line-login.client_secret}")
    private String client_secret;

    @Value("${line-login.login_redirect_uri}")
    private String login_redirect_uri;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CommunityRestClient communityRestClient;

    @GetMapping("/user/line-login")
    @ResponseBody
    public void linelogin(HttpServletResponse httpServletResponse) {
        logger.info("line login API");
        String redirect_url = "https://access.line.me/oauth2/v2.1/authorize?response_type=code&client_id=" + client_id + "&redirect_uri=" + login_redirect_uri + "&state=1&scope=openid%20profile";
        httpServletResponse.setHeader("Location", redirect_url);
        httpServletResponse.setStatus(302);
    }

    /**
     * LINE Auth
     */
    @GetMapping("/auth")
    public String line_auth(HttpServletResponse response, @RequestParam("code") String code, Model model){
        logger.info("LINE Auth API");
        logger.info("sns register API");
        String lineId = ""; // LINE user ID
        String token = ""; // LINE user ID
        /*
         * LINE APIを使用してLINE user IDを取得する。
         */
        try {
            String lineIdToken= userService.lineAuth(code);
            lineId = tokenService.getSnsIdFromLineToken(lineIdToken);
            token = userService.createUserIfNotExist(lineId);
            if (token.isEmpty()) throw new RestClientException("get token by lineId error.");
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }


        /*
         * 登録済/未登録に応じて対応を分ける
         * lineIDが既に登録されている場合：
         * このシステムへの登録を行う
         */
        logger.info("register API");
        try {
            // cookieを設定
            Cookie cookie = new Cookie("_token",token);
            cookie.setPath("/");
            response.addCookie(cookie);

            // ホーム画面に設定する情報をAPIで収集する
            // コミュニティへのお誘い（おせっかい機能）
            // 所属コミュニティの直近の活動予定一覧
            // 所属コミュニティの直近の活動履歴一覧
            MyPage myPage = communityRestClient.getMyPage(token);
            model.addAttribute(myPage);
            return "spocomi_home";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }


    /*
     * スポコミホーム
     */
    @GetMapping("/mypage")
    public String getMyHome(
            @CookieValue(value="_token", required=false) String token,
            Model model) {
        logger.info("myhome API");
        try {
            MyPage myPage = communityRestClient.getMyPage(token);
            model.addAttribute(myPage);
            return "spocomi_home";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     * コミュニティ新規作成
     */
    @GetMapping("/community/new")
    public String getNewCommunity(
            @CookieValue(value="_token", required=false) String token,
                                 Model model) {
        logger.info("new community registration API");
        try {
            Community newCommunity = new Community();
            model.addAttribute(newCommunity);
            return "community_registration";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     * コミュニティ新規作成
     */
    @PostMapping("/community/new")
    public String postNewCommunity(
            @CookieValue(value="_token", required=false) String token,
                                 Community community,
                                 Model model) {
        logger.info("new community registration API");
        try {
            Community regiCommunity = communityRestClient.postCommunityRegistration(token,community);
            // modelに変数を設定
            model.addAttribute(regiCommunity);
            return "community_registration_confirm";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }


    /*
     *
     */
    @GetMapping("/community/community_list")
    public String community_list(
            @CookieValue(value="_token", required=false) String token,
            HttpServletResponse response, Model model) {
        logger.info("community list API");
        try {
            List<CommunitySummary> communitySummaryList = communityRestClient.getCommunityListApi();
            /*
            for(CommunitySummary communitySummary : communitySummaryList) {
                //communitySummary.setSummaryImageUrl("http://localhost:8081/v1/api/communities/images/1.jpg");
                communitySummary.setSummaryImageUrl("/img/1.png");
            }
             */
            model.addAttribute(communitySummaryList);
            return "community_list";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     *
     */
    @GetMapping("/community/{community_id}/community-home")
    public String communityHome(
            @CookieValue(value="_token", required=false) String token,
            HttpServletResponse response,
            @PathVariable Long community_id,
            Model model) {
        logger.info("community list API");
        try {
            // 該当コミュニティにおけるユーザーのロールを確認
            // 該当コミュニティの情報を取得
            int role = 1;
            //CommunityPage communityPage = communityRestClient.getCommunityPage(token, community_id);
            //Community community = new Community();
            //community.setId(community_id);

            // modelに変数を設定
            //model.addAttribute(communityPage);
            if (role == 1) {
                // adminの場合
                if (community_id == 3L) {
                    return "community_admin_sample";
                }
                return "community_admin";
            } else if (role == 2) {
                return "community_member";
            }else {
                return "community_visitor";
            }
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     * イベント新規作成
     */
    @GetMapping("/event/new")
    public String getNewEvent(
            @CookieValue(value="_token", required=false) String token,
            Model model) {
        logger.info("new event registration API");
        try {
            Event newEvent = new Event();
            // modelに変数を設定
            model.addAttribute(newEvent);
            return "event_registration";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     * コミュニティ新規作成
     */
    @PostMapping("/event/new")
    public String postNewEvent(
            @CookieValue(value="_token", required=false) String token,
            Event event,
            Model model) {
        logger.info("new event registration API");
        try {
            AddEventRequest regiEvent = eventService.addEvent(token,event);
            // modelに変数を設定
           model.addAttribute(regiEvent);
            return "event_registration_confirm";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     * イベント新規作成
     */
    @GetMapping("/network/new")
    public String getNewNetwork(
            @CookieValue(value="_token", required=false) String token,
            Model model) {
        logger.info("new network registration API");
        try {
            //Event newEvent = new Event();
            // modelに変数を設定
            //model.addAttribute(newEvent);
            return "networking";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }
}
