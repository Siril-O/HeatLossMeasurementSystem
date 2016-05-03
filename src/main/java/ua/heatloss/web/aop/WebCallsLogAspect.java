package ua.heatloss.web.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class WebCallsLogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(WebCallsLogAspect.class);

    @Autowired
    private HttpServletRequest request;

    public Object adviceLogging(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        final long startExecutionMillis = System.currentTimeMillis();

        try {
            result = joinPoint.proceed();
        } catch (final Throwable throwable) {
            result = throwable;
        }

        final long finishExecutionMillis = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        LogsWrapper msg = new LogsWrapper();

        msg.setHandlerMethodSignature(signature.toString());
        msg.setExecutionTimeMillis(finishExecutionMillis - startExecutionMillis);
        msg.setRequest(request);
        msg.setArguments(signature.getParameterNames(), joinPoint.getArgs());
        msg.setHandledResult(result);

        final Logger log = LoggerFactory.getLogger(targetClass);
        log.debug(msg.toString());

        if (result instanceof Throwable) {
            throw (Throwable) result;
        }
        return result;
    }


    private static class LogsWrapper {

        private String handlerMethodSignature;
        private HttpServletRequest request;
        private long executionTimeMillis;
        private Map<String, Object> params = new LinkedHashMap<>();
        private Object handledResult;

        public String getHandlerMethodSignature() {
            return handlerMethodSignature;
        }

        public void setHandlerMethodSignature(String handlerMethodSignature) {
            this.handlerMethodSignature = handlerMethodSignature;
        }

        public HttpServletRequest getRequest() {
            return request;
        }

        public void setRequest(HttpServletRequest request) {
            this.request = request;
        }

        public long getExecutionTimeMillis() {
            return executionTimeMillis;
        }

        public void setExecutionTimeMillis(long executionTimeMillis) {
            this.executionTimeMillis = executionTimeMillis;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }

        public Object getHandledResult() {
            return handledResult;
        }

        public void setHandledResult(Object handledResult) {
            this.handledResult = handledResult;
        }

        public void setArguments(final String[] argumentNames, final Object[] argumentValues) {
            for (int i = 0; i < argumentNames.length; i++) {
                params.put(argumentNames[i], argumentValues[i]);
            }
        }

        public String toString() {
            final StringBuilder builder = new StringBuilder()
                    .append("Requested URL: ").append(request.getMethod())
                    .append(" ").append(request.getRequestURL().toString()).append('\n')
                    .append("Handler method: ").append(handlerMethodSignature).append('\n')
                    .append("Execution(ms): ").append(executionTimeMillis).append('\n');

            final Set<Map.Entry<String, Object>> entries = params.entrySet();
            if (!entries.isEmpty()) {
                builder.append("Params: [\n");
                for (final Map.Entry<String, Object> e : params.entrySet()) {
                    builder.append(e.getKey()).append(" = ").append(e.getValue()).append('\n');
                }
                builder.append("]\n");
            }
            builder.append("Result: ").append(handledResult);
            return builder.toString();
        }
    }
}


