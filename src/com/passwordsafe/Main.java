package com.passwordsafe;

import com.passwordsafe.decorator.*;
import com.passwordsafe.factory.StrengthTesterFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final MasterPasswordRepository masterRepository = new MasterPasswordRepository("./master.pw");
    private static PasswordSafeEngine passwordSafeEngine = null;

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Passwordsafe");

        boolean abort = false;
        boolean locked = true;
        Scanner read = new Scanner(System.in);
        while (!abort) {
            System.out.println("Enter master (1), show all (2), show single (3), add (4), delete(5), set new master (6), Abort (0)");
            int input = read.nextInt();
            switch (input) {
                case 0: {
                    abort = true;
                    break;
                }
                case 1: {
                    System.out.println("Enter master password");
                    String masterPw = read.next();
                    locked = !masterRepository.MasterPasswordIsEqualTo(masterPw);
                    if (!locked) {
                        passwordSafeEngine = new PasswordSafeEngine("./passwords.pw", new CipherFacility(masterPw));
                        System.out.println("unlocked");
                    } else {
                        System.out.println("master password did not match ! Failed to unlock.");
                    }
                    break;
                }
                case 2: {
                    if (locked) {
                        System.out.println("Please unlock first by entering the master password.");
                    } else {
                        Arrays.stream(passwordSafeEngine.GetStoredPasswords()).forEach(pw -> System.out.println(pw));
                    }
                    break;
                }
                case 3: {
                    if (locked) {
                        System.out.println("Please unlock first by entering the master password.");
                    } else {
                        System.out.println("Enter password name");
                        String passwordName = read.next();
                        System.out.println(passwordSafeEngine.GetPassword(passwordName));
                    }
                    break;
                }
                case 4: {
                    if (locked) {
                        System.out.println("Please unlock first by entering the master password.");
                    } else {
                        System.out.println("Enter new name of password");
                        String passwordName = read.next();
                        System.out.println("Enter password");
                        String password = read.next();
                        passwordSafeEngine.AddNewPassword(new PasswordInfo(password, passwordName));
                    }
                    break;
                }
                case 5: {
                    if (locked) {
                        System.out.println("Please unlock first by entering the master password.");
                    } else {
                        System.out.println("Enter password name");
                        String passwordName = read.next();
                        passwordSafeEngine.DeletePassword(passwordName);
                    }
                    break;
                }
                case 6: {
                    locked = true;
                    passwordSafeEngine = null;
                    StrengthTesterFactory strengthTesterFactory = new StrengthTesterFactory();
                    IPasswordRulesetTester rulesetTester = null;
                    boolean abortMode = false;
                    do {
                        System.out.println("Please choose the rule configuration: (1) Weak, (2) Strict");
                        String mode = read.next();
                        switch (mode) {
                            case "1":
                                rulesetTester = strengthTesterFactory.getTester("weak");
                                abortMode = true;
                                break;
                            case "2":
                                rulesetTester = strengthTesterFactory.getTester("strict");
                                abortMode = true;
                                break;
                        }
                    } while (!abortMode);
                    System.out.println("Enter new master password ! (Warning you will loose all already stored passwords)");
                    String masterPw = read.next();
                    while (!rulesetTester.getRuleset(masterPw)) {
                        System.out.println("The master password is not equivalent to the defined rulesets!\nPlease try again:");
                        masterPw = read.next();
                    }
                    System.out.println("The master password contains all rulesets!");
                    masterRepository.setMasterPasswordPlain(masterPw);
                    // urgent hotfix delete old passwords after changing the master
                    File oldPasswords = new File("./passwords.pw");
                    if (oldPasswords.isDirectory())
                    {
                        String[] children = oldPasswords.list();
                        for (int i=0; i<children.length; i++) {
                            (new File(oldPasswords, children[i])).delete();
                        }
                    }
                    // The directory is now empty or this is a file so delete it
                    oldPasswords.delete();
                    break;
                }
                default:
                    System.out.println("Invalid input");
            }
        }

        System.out.println("Good by !");
    }
}
