package com.passwordsafe.decorator;

public abstract class RulesetTesterDecorator implements IPasswordRulesetTester {
    private IPasswordRulesetTester rulesetTester;

    public RulesetTesterDecorator(IPasswordRulesetTester strengthTester) {
        this.rulesetTester = strengthTester;
    }

    @Override
    public boolean getRuleset (String password) {
        return this.getSpecificRuleset(password) && rulesetTester.getRuleset(password);
    }

    protected abstract boolean getSpecificRuleset(String password);
}
