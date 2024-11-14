package com.dxjunkyard.spocomi.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.dxjunkyard.spocomi.api.client.CommunityRestClient;
import com.dxjunkyard.spocomi.api.client.EventRestClient;
import com.dxjunkyard.spocomi.domain.resource.*;
import com.dxjunkyard.spocomi.domain.resource.response.*;
import com.dxjunkyard.spocomi.service.CommunityService;
import com.dxjunkyard.spocomi.service.EventService;
import com.dxjunkyard.spocomi.service.TokenService;
import com.dxjunkyard.spocomi.service.UserService;
import com.dxjunkyard.spocomi.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private EventRestClient eventRestClient;

    @Autowired
    private CommunityService communityService;

    @GetMapping("/user/line-login")
    @ResponseBody
    public void linelogin(HttpServletResponse httpServletResponse) {
        logger.info("line login API");
        String redirect_url = "https://access.line.me/oauth2/v2.1/authorize?response_type=code&client_id=" + client_id + "&redirect_uri=" + login_redirect_uri + "&state=1&scope=openid%20profile";
        httpServletResponse.setHeader("Location", redirect_url);
        httpServletResponse.setStatus(302);
    }

    private static String combineResources(List<String> resourcePaths) {
        return resourcePaths.stream()
                .map(path -> {
                    try {
                        ClassPathResource resource = new ClassPathResource(path);
                        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
                            return FileCopyUtils.copyToString(reader);
                        }
                    } catch (IOException e) {
                        // エラー処理（ここでは空文字を返す）
                        return "";
                    }
                })
                .collect(Collectors.joining("\n"));  // 各リソースを改行で区切って結合
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
        //    return "error"; // error page遷移
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
            model.addAttribute(new DateUtils());
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
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
            model.addAttribute(new DateUtils());

            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);

            return "spocomi_home";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        } catch (Exception e) {
            logger.info("error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     * コミュニティ新規作成（登録画面の表示）
     */
    @GetMapping("/community/new")
    public String getNewCommunity(
            @CookieValue(value="_token", required=false) String token,
                                 Model model) {
        logger.info("new community registration API");
        try {
            Community newCommunity = new Community();
            model.addAttribute(newCommunity);
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
            return "community_registration";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     * コミュニティ新規作成（登録結果確認）
     */
    @PostMapping("/community/new")
    public String postNewCommunity(
            @CookieValue(value="_token", required=false) String token,
                                 Community community,
                                 Model model) {
        logger.info("new community registration API");
        try {
            Community regiCommunity = communityRestClient.postCommunityRegistration(token,community);
            String newPhotoName = communityService.renamePhoto(regiCommunity.getOwnerId(),regiCommunity.getId());
            regiCommunity.setSummaryImageUrl(newPhotoName);
            communityRestClient.updateCommunity(token, regiCommunity);
            // modelに変数を設定
            model.addAttribute(regiCommunity);
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
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
            List<CommunitySummary> communitySummaryList = communityRestClient.getCommunityListApi(token);
            model.addAttribute(communitySummaryList);
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
            return "community_list";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    @GetMapping("/community/keyword-search")
    public String community_keyword_search(
            @CookieValue(value="_token", required=false) String token,
            @RequestParam(value = "keyword", required = true) String keyword, // keywordパラメータを追加
            HttpServletResponse response, Model model) {
        logger.info("community keyword-search API");
        try {
            List<CommunitySummary> communitySummaryList = communityRestClient.getCommunityKeywordSearchApi(token, keyword);
            model.addAttribute(communitySummaryList);
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
            return "community_list";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     * 各コミュニティのホーム
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
            //int role = 1;
            CommunityPage communityPage = communityRestClient.getCommunityPage(token, community_id);

            // modelに変数を設定
            model.addAttribute(communityPage);
            model.addAttribute(new DateUtils());
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
            return "community_admin";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     * イベント新規作成（登録画面の表示）
     */
    @GetMapping("/community/event/new/step2/{community_id}")
    public String getNewEvent(
            @CookieValue(value="_token", required=false) String token,
            @PathVariable Long community_id,
            Model model) {
        logger.info("new event registration API");
        try {
            EventPage eventPage = new EventPage();
            // modelに変数を設定
            eventPage.setCommunityId(community_id);
            model.addAttribute(eventPage);
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js","static/js/date_formatter.js","static/js/visibility.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
            return "event_registration";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     * イベント新規作成（登録画面の表示）
     */
    @GetMapping("/community/event/new/step1")
    public String communitySelector(
            @CookieValue(value="_token", required=false) String token,
            Model model) {
        logger.info("new event registration API");
        try {
            // token check
            // modelに変数を設定
            MyPage myPage = communityRestClient.getMyPage(token);
            model.addAttribute(myPage);
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
            return "community_selector";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     * イベント新規作成（登録実行）
     */
    @PostMapping("/event/new")
    public String postNewEvent(
            @CookieValue(value="_token", required=false) String token,
            EventPage ePage,
            Model model) {
        logger.info("new event registration API");
        try {
            EventPage eventPage = eventService.addEvent(token,ePage);
            // modelに変数を設定
            model.addAttribute(eventPage);
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js","static/js/date_formatter.js","static/js/visibility.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
            return "event";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        } catch (Exception e) {
            logger.info("error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     *
     */
    @GetMapping("/event/event_list")
    public String event_list(
            @CookieValue(value="_token", required=false) String token,
            HttpServletResponse response, Model model) {
        logger.info("event list API");
        try {
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js","static/js/date_formatter.js","static/js/visibility.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
            List<EventPage> eventList = eventRestClient.getEventListApi();
            model.addAttribute("eventList",eventList);
            return "event_list";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }
    /*
     * 各イベント情報表示画面
     */
    @GetMapping("/event/{event_id}/display")
    public String eventPage(
            @CookieValue(value="_token", required=false) String token,
            HttpServletResponse response,
            @PathVariable Long event_id,
            Model model) {
        logger.info("eventPage API");
        try {
            // 該当コミュニティにおけるユーザーのロールを確認
            // 該当コミュニティの情報を取得
            EventPage eventPage = eventRestClient.getEventPage(token, event_id);

            // modelに変数を設定
            model.addAttribute(eventPage);
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
            return "event";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }


    @GetMapping("/event/keyword-search")
    public String event_keyword_search(
            @CookieValue(value="_token", required=false) String token,
            @RequestParam(value = "keyword", required = true) String keyword, // keywordパラメータを追加
            HttpServletResponse response, Model model) {
        logger.info("community keyword-search API");
        try {
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js","static/js/date_formatter.js","static/js/visibility.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
            List<EventPage> eventList = eventRestClient.searchEventByKeywordApi(keyword);
            model.addAttribute("eventList",eventList);
            return "event_list";
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
            // css/jsの設定
            // resourceをそれぞれ1つの文字列に結合
            List<String> cssPaths = List.of("static/css/spocomi.css");
            String cssContents = combineResources(cssPaths);
            List<String> jsPaths = List.of("static/js/spocomi_menu.js","static/js/date_formatter.js","static/js/visibility.js");
            String jsContents = combineResources(jsPaths);
            // Thymeleafのモデルにresourceを設定
            model.addAttribute("inlineCss", cssContents);
            model.addAttribute("inlineJs", jsContents);
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
