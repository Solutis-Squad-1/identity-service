package br.com.solutis.squad1.identityservice.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoggerAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("execution(public * br.com.solutis.squad1.identityservice.*.*.*(..))")
    public void methodsPointCut() {
    }

    @Pointcut(
            "@annotation(org.springframework.web.bind.annotation.GetMapping), " +
                    "@annotation(org.springframework.web.bind.annotation.PostMapping), " +
                    "@annotation(org.springframework.web.bind.annotation.PutMapping), " +
                    "@annotation(org.springframework.web.bind.annotation.DeleteMapping)"
    )
    public void requestMappingPointCut() {
    }

    @Around("methodsPointCut()")
    public Object logMethodEntryAndExit(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;

        try {
            Object[] args = joinPoint.getArgs();
            LOGGER.info("Entering method [{}] with args {}", joinPoint.getSignature(), args);

            long startTime = System.currentTimeMillis();
            result = joinPoint.proceed();
            long endTime = System.currentTimeMillis() - startTime;

            LOGGER.info("Exiting method [{}] with result: {}", joinPoint.getSignature(), result);
            LOGGER.info("Method [{}] executed in: {} ms", joinPoint.getSignature(), endTime);
        } catch (Exception e) {
            LOGGER.error("{} - {}", joinPoint.getSignature(), e.getMessage());
            throw e;
        }

        return result;
    }

    @Around("requestMappingPointCut()")
    public Object logRequestMapping(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            LOGGER.info("Request {} {} - Parameters: {}", request.getMethod(), request.getRequestURI(), request.getParameterMap());

            result = joinPoint.proceed();

            if (result instanceof ResponseEntity<?> response) {
                LOGGER.info("Response: Status {} - Body: {}", response.getStatusCode(), response.getBody());
            }


        } catch (Exception e) {
            LOGGER.error("[{}] - {}", joinPoint.getSignature(), e.getMessage());
            throw e;
        }

        return result;
    }
}
