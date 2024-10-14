package com.dxjunkyard.spocomi.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
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

@CrossOrigin
@Controller
@RequestMapping("/v1")
public class UIController {
    private Logger logger = LoggerFactory.getLogger(com.dxjunkyard.spocomi.controller.UIController.class);

    @Value("${backend-api.uri}")
    private String api_uri;

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
        String api_url = api_uri + "/v1/api/auth?code=" + code;
        logger.info("sns register API");
        String lineId = ""; // LINE user ID
        /*
         * LINE APIを使用してLINE user IDを取得する。
         */
        try {
            String lineIdToken= userService.lineAuth(code);
            lineId = tokenService.getSnsIdFromLineToken(lineIdToken);
            if (lineId.isEmpty()) throw new RestClientException("get lineId error.");
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }


        /*
         * このシステムへの登録を行う
         */
        api_url = api_uri + "/v1/api/user/register/" + lineId;
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
            return "home";
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
        String api_url = api_uri + "/v1/api/user/register/";
        logger.info("sns register API");
        String token = "";
        try {
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
            CommunityList communityList = new CommunityList();
            model.addAttribute(communityList);
            return "community_list";
        } catch (RestClientException e) {
            logger.info("RestClient error : {}", e.toString());
            return "error"; // error page遷移
        }
    }

}
