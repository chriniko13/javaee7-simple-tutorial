package com.chriniko.example.language.boundary;

import com.chriniko.example.language.control.Language;

import javax.enterprise.inject.Typed;
import java.io.Serializable;

@Language(
        value = Language.LangChoice.GREEK,
        description = "this is the engine for the greek language"
)
@Typed(value = LanguageEngine.class)
public class GreekLanguageEngine implements LanguageEngine, Serializable {

    @Override
    public String welcomeMessage() {
        return "Καλως ήρθατε σε ένα φιλοσοφημένο παράδειγμα περί JavaEE.";
    }
}
