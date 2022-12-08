package com.passwordsafe.decorator;

import java.util.regex.Pattern;

public class PasswordContainsSpecialCaracter extends RulesetTesterDecorator {

    public PasswordContainsSpecialCaracter(IPasswordRulesetTester strengthTester) {
        super(strengthTester);
    }
    @Override
    protected boolean getSpecificRuleset(String password) {
        return Pattern.matches(".*[*#!?%$@].*", password);
    }
}
