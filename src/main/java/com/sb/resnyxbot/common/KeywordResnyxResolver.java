package com.sb.resnyxbot.common;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public final class KeywordResnyxResolver implements ResnyxResolver {

    private final Map<String, ResnyxService> services;

    private boolean analyzeAnnotation(Class<?> clz, String analyzedText) {
        ChooseScope scope = clz.getAnnotation(ChooseScope.class);
        for (String str : scope.value()) {
            if (analyzedText.toLowerCase().contains(str.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ResnyxService resolve(String analyzedText) {
        for (ResnyxService service : services.values()) {
            Class<?> clz = service.getClass();
            if (clz.isAnnotationPresent(ChooseScope.class) && analyzeAnnotation(clz, analyzedText)) {
                LOG.info("choosed service = {}", service);
                return service;
            } else {
                for (Class<?> ifc : clz.getInterfaces()) {
                    if (ifc.isAnnotationPresent(ChooseScope.class) && analyzeAnnotation(ifc, analyzedText)) {
                        LOG.info("choosed service = {}", service);
                        return service;
                    }
                }
            }
        }
        return null;
    }
}
