package com.passwordsafe.factory;

import com.passwordsafe.decorator.*;

public class StrengthTesterFactory {
    public IPasswordRulesetTester getTester (String mode) {
        if (mode.equals("weak")) {
            return new PasswordHasMinimumLength(new PasswordContainsUpperCase(new RulesetTesterImpl()));
        } else if (mode.equals("strict")) {
            return new PasswordContainsNumbers(new PasswordHasMinimumLength(new PasswordContainsSpecialCaracter(new PasswordContainsUpperCase(new RulesetTesterImpl()))));
        }

        return null;
    }
}
