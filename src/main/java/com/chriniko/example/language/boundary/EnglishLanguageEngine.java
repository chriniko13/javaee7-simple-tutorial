package com.chriniko.example.language.boundary;

import com.chriniko.example.language.control.Language;

import javax.enterprise.inject.Typed;
import java.io.Serializable;

@Language(
        value = Language.LangChoice.ENGLISH,
        description = "this is the engine for the english language"
)
@Typed(value = LanguageEngine.class)
public class EnglishLanguageEngine implements LanguageEngine, Serializable {
    @Override
    public String welcomeMessage() {
        return "Welcome to a good demo on JavaEE.";
    }

}
