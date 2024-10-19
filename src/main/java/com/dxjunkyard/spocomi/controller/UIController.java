package com.dxjunkyard.spocomi.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dxjunkyard.spocomi.api.client.CommunityRestClient;
import com.dxjunkyard.spocomi.domain.resource.*;
import com.dxjunkyard.spocomi.domain.resource.request.*;
import com.dxjunkyard.spocomi.domain.resource.response.*;
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
        logger.info("sns register API");
        try {
            RestTemplate restTemplate = new RestTemplate();
        /*
            ResponseEntity<SnsTokenResponse> registerResponse = restTemplate
                    .exchange(api_url, HttpMethod.GET, null, SnsTokenResponse.class);
            String token = registerResponse.getBody().getToken();
            if (token.isEmpty()) throw new RestClientException("get token error.");

            // cookieを設定
            Cookie cookie = new Cookie("_token",token);
            cookie.setPath("/");
            response.addCookie(cookie);

            // modelに変数を設定
            RegisterUserRequest registerUserRequest = new RegisterUserRequest();
            model.addAttribute(registerUserRequest);
            //response.setHeader("Location", api_uri + "/v1/user/");
         */
            model.addAttribute(token);
            return "home";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

    /*
     * セキュリティ的な懸念があるため、要改善
     */
    @GetMapping("/community/new")
    public String community_list(HttpServletResponse response,
                                 @RequestHeader("Authorization") String authHeader,
                                 Model model) {
        logger.info("sns register API");
        String token = "";
        try {
            List<CommunitySummary> communitySummaryList = communityRestClient.getCommunityListApi();

            /*
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<SnsTokenResponse> registerResponse = restTemplate
                    .exchange(api_url, HttpMethod.GET, null, SnsTokenResponse.class);
            SnsTokenResponse body = registerResponse.getBody();
            token = body.getToken();
            if (token.isEmpty()) throw new RestClientException("get token error.");

            // cookieを設定
            Cookie cookie = new Cookie("_token",token);
            cookie.setPath("/");
            response.addCookie(cookie);
*/
            // modelに変数を設定
            model.addAttribute(communitySummaryList);
            return "community_list";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }


    /*
     * セキュリティ的な懸念があるため、要改善
     */
    @GetMapping("/community/community_list")
    public String community_list(HttpServletResponse response, Model model) {
        logger.info("sns register API");
        String token = "";
        try {
            List<CommunitySummary> communitySummaryList = communityRestClient.getCommunityListApi();

            /*
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<SnsTokenResponse> registerResponse = restTemplate
                    .exchange(api_url, HttpMethod.GET, null, SnsTokenResponse.class);
            SnsTokenResponse body = registerResponse.getBody();
            token = body.getToken();
            if (token.isEmpty()) throw new RestClientException("get token error.");

            // cookieを設定
            Cookie cookie = new Cookie("_token",token);
            cookie.setPath("/");
            response.addCookie(cookie);
*/
            // modelに変数を設定
            model.addAttribute(communitySummaryList);
            return "community_list";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

}
