package com.passwordsafe.decorator;

import java.util.regex.Pattern;

public class PasswordContainsUpperCase extends RulesetTesterDecorator{

    public PasswordContainsUpperCase(IPasswordRulesetTester strengthTester) {
        super(strengthTester);
    }
    @Override
    protected boolean getSpecificRuleset(String password) {
        return Pattern.matches(".*[A-Z].*", password);
    }
}
