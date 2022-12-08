package com.passwordsafe.decorator;

public class PasswordHasMinimumLength extends RulesetTesterDecorator{

    public PasswordHasMinimumLength(IPasswordRulesetTester strengthTester) {
        super(strengthTester);
    }
    @Override
    protected boolean getSpecificRuleset(String password) {
        return password.length() >= 8;
    }
}
