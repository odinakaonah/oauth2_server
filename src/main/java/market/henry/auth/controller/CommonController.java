  package market.henry.auth.controller;

import market.henry.auth.dto.SecretRequest;
import market.henry.auth.enums.ResponseCode;
import market.henry.auth.exceptions.AuthServerException;
import market.henry.auth.services.AuthorizationService;
import market.henry.auth.services.CommonService;
import market.henry.auth.utils.Response;
import market.henry.auth.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

  @RestController
public class CommonController {

    @Autowired
    private CommonService commonService;
    @Autowired
    private AuthorizationService authorizationService;

  @GetMapping("/validate/bvn")
  public ResponseEntity bvnValidation(@RequestParam String bvn, HttpServletRequest httpServletRequest) {
      Response response = authorizationService.validateInternalCall(httpServletRequest);
      if (!"00".equalsIgnoreCase(response.getStatusCode()))return ResponseEntity.ok(response);

      return commonService.validateBvn(bvn);
  }
  @GetMapping("/generate/secret")
  public ResponseEntity generateSecret(@RequestParam String phoneNumber, HttpServletRequest httpServletRequest) {
     try {
         Response response = authorizationService.validateInternalCall(httpServletRequest);
         if (!"00".equalsIgnoreCase(response.getStatusCode())) return ResponseEntity.ok(response);
         String channelCode = httpServletRequest.getHeader("ChannelCode");
         return commonService.generateSecret(phoneNumber, channelCode);
     }catch (AuthServerException e){
         return Response.setUpResponse(e.getHttpCode(),e.getMessage(),"",null);
     }

  }

  @PostMapping("/validate/secret")
  public ResponseEntity validateSecret(@RequestBody SecretRequest request, HttpServletRequest httpServletRequest) {
     try {
         Response response = authorizationService.validateInternalCall(httpServletRequest);
         if (!"00".equalsIgnoreCase(response.getStatusCode())) return ResponseEntity.ok(response);
         String channelCode = httpServletRequest.getHeader("ChannelCode");
         String error = Validation.validateSecretRequest(request, channelCode);
         if (error != null)
             return Response.setUpResponse(ResponseCode.BAD_REQUEST, error, null);
         return commonService.validateSecret(request.getPhoneNumber(), request.getSecret(), channelCode);
     }catch (AuthServerException e){
         return Response.setUpResponse(e.getHttpCode(),e.getMessage(),"",null);
     }
  }

  @RolesAllowed({"ROLE_ADMIN"})
  @GetMapping("/admin")
  public String welcomeAdmin(HttpServletRequest httpServletRequest)
  {
      String channel = httpServletRequest.getHeader("channel");
      String authorization = httpServletRequest.getHeader("Authorization");

      return "welcome admin";
  }

  @RolesAllowed({"ROLE_USER"})
  @GetMapping("/user")
  public String welcomeUser() {
    return "welcome user";
  }
}