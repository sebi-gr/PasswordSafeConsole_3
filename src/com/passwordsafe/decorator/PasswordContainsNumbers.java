package com.passwordsafe.decorator;

import java.util.regex.Pattern;

public class PasswordContainsNumbers extends RulesetTesterDecorator{

    public PasswordContainsNumbers(IPasswordRulesetTester strengthTester) {
        super(strengthTester);
    }
    @Override
    protected boolean getSpecificRuleset(String password) {
        return Pattern.matches(".*[0-9].*", password);
    }
}
