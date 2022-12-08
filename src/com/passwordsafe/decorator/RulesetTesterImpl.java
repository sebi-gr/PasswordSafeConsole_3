package com.passwordsafe.decorator;

public class RulesetTesterImpl implements IPasswordRulesetTester {
    @Override
    public boolean getRuleset (String password) {
        return !password.isEmpty();
    }
}
