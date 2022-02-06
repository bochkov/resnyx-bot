package com.sb.resnyxbot.common;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public final class ResnyxAnswers {

    private final ApplicationContext context;
    private final Reflections scan;
    private final BiPredicate<String, String> predicate = (source, val) ->
            source.toLowerCase().contains(val.toLowerCase());

    public List<TgMethod<Message>> choose(final String token, final Message msg) {
        Set<Class<? extends ResnyxService>> allIfcs = scan.getSubTypesOf(ResnyxService.class);
        for (Class<?> clz : allIfcs) {
            if (clz.isAnnotationPresent(ChooseScope.class)) {
                ChooseScope scope = clz.getAnnotation(ChooseScope.class);
                for (String str : scope.value()) {
                    if (predicate.test(msg.getText(), str)) {
                        Class<?> beanClass = scope.clz() == void.class ? clz : scope.clz();
                        ResnyxService service = (ResnyxService) context.getBean(beanClass);
                        LOG.debug("choosed service = {}", context.getBean(beanClass));
                        return service.answer(token, msg);
                    }
                }
            }
        }
        Long chatId = msg.getChat().getId();
        String txt = String.format("Your chat_id = %s", chatId);
        return Collections.singletonList(
                new SendMessage(token, chatId, txt)
        );
    }
}
