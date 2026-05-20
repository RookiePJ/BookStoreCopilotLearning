package pjr.bookstore.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect that logs entry, exit and returned values for all methods under pjr.bookstore packages.
 * <p>
 * - Pointcut: execution(* pjr.bookstore..*(..))
 * - Advices: @Before, @After, @AfterReturning
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /** Pointcut matching execution of any method in pjr.bookstore..* */
    public void applicationPackagePointcut() {
        // pointcut signature method - body is ignored
    }

    @Before("execution(* pjr.bookstore..*(..))")
    public void logBefore(final JoinPoint joinPoint) {
        try {
            log.debug("[pjr.bookstore] - BEFORE: {}.{}() with args = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()));
        } catch (final Exception ex) {
            // avoid letting logging break business logic
            log.debug("[pjr.bookstore] - BEFORE: failed to log join point: {}", ex.getMessage());
        }
    }

    @After("execution(* pjr.bookstore..*(..))")
    public void logAfter(final JoinPoint joinPoint) {
        try {
            log.debug("[pjr.bookstore] - AFTER: {}.{}()",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
        } catch (final Exception ex) {
            log.debug("[pjr.bookstore] - AFTER: failed to log join point: {}", ex.getMessage());
        }
    }

    @AfterReturning(pointcut = "execution(* pjr.bookstore..*(..))", returning = "result")
    public void logAfterReturning(final JoinPoint joinPoint, final Object result) {
        try {
            log.debug("[pjr.bookstore] - AFTER RETURNING: {}.{}() returned = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    result);
        } catch (final Exception ex) {
            log.debug("[pjr.bookstore] - AFTER RETURNING: failed to log join point: {}", ex.getMessage());
        }
    }
}

