package utils;

import com.qf.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;

import static utils.Constant.LOGIN_TOKEN;

@Aspect
public class loginAop {
    @Autowired
    private  HttpServletRequest request;
    @Autowired
    private RedisTemplate redisTemplate;
    @Around("execution(* *..*Controller.*(..))&& @annotation(utils.isLogin)")
    public Object isLogin(ProceedingJoinPoint proceedingJoinPoint){
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        isLogin isLogin = method.getAnnotation(isLogin.class);
        boolean tologin = isLogin.tologin();
        System.out.println("注解中的属性是否为ture："+tologin);
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies!=null){
            for (int i = 0; i <cookies.length; i++) {
                if(cookies[i].getName().equals(LOGIN_TOKEN)){
                    token = cookies[i].getValue();
                    break;
                }
            }
        }
        User user = null;
        if(token!=null){
            user = (User) redisTemplate.opsForValue().get(token);
        }
        Object[] args = proceedingJoinPoint.getArgs();
        if(user!=null){
            //已经登录

            for (int i = 0; i <args.length; i++) {
                    if (args[i] != null&&User.class == args[i].getClass()) {
                        args[i] = user;
                    }
                }
        }else {
            //没有登录
        }
        if(user==null&&isLogin.tologin()){
            String resultUrl= request.getRequestURL()+"?"+request.getQueryString();
            resultUrl = resultUrl.replace("&", "*");
            System.out.println("--------------resultUrl:"+resultUrl);
            return "redirect:http://localhost:8084/tologin?resultUrl="+resultUrl;
        }
        System.out.println("之前环绕");
        Object object = null;
        try {
            object = proceedingJoinPoint.proceed(args);


        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }
}
