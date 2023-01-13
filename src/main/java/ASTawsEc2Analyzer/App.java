package ASTawsEc2Analyzer;

import ASTawsEc2Analyzer.Parser.ListClassesNames;
import ASTawsEc2Analyzer.Parser.ListMethodsNames;

import java.io.File;
import java.io.IOException;

public class App {



    public static void main(String[] args) {
        File projectDir = new File("resources\\pulumi-aws\\sdk\\java\\src\\main\\java\\com\\pulumi\\aws\\ec2");

        //ListClassesNames.listClasses(projectDir);
        ListMethodsNames.listMethods(projectDir);
    }
}